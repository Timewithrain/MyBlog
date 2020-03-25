package com.watermelon.service;

import com.watermelon.DAO.ResourceRepository;
import com.watermelon.entity.Resource;
import com.watermelon.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class ResourceService {

    @Value("${system-params.file.path}")
    private String filePath;

    @Value("${system-params.picture.path}")
    private String picturePath;

    @Autowired
    private ResourceRepository resourceRepository;

    public Resource getResource(Long id){
        return resourceRepository.getOne(id);
    }

    public Resource getAndDownloadResource(Long id){
        Resource resource = resourceRepository.getOne(id);
        resource.setDownloadTimes(resource.getDownloadTimes()+1);
        resourceRepository.save(resource);
        return resource;
    }

    //存储资源
    public Resource addResource(MultipartFile file, User user) throws IOException {
        Resource resource = setResourceFields(file,user);
        resource.setOfGallery(false);
        resource.setPath(filePath);
        saveFile(file,resource);
        return resourceRepository.save(resource);
    }

    //存储图片,由于画廊模块未拓展因此未调用此方法
    public Resource addPicture(MultipartFile file, User user) throws IOException{
        Resource resource = setResourceFields(file,user);
        resource.setOfGallery(true);
        resource.setPath(picturePath);
        saveFile(file,resource);
        return resourceRepository.save(resource);
    }

    public void updatePrivate(Long id){
        Resource resource = getResource(id);
        if (resource.getPrivate()){
            resource.setPrivate(false);
        }else{
            resource.setPrivate(true);
        }
        resourceRepository.save(resource);
    }

    public Page<Resource> listResource(Pageable pageable){
        return resourceRepository.findAll(pageable);
    }

    public Page<Resource> listPicture(Pageable pageable){
        Page<Resource> page =  resourceRepository.findAll(new Specification<Resource>() {
            @Override
            public Predicate toPredicate(Root<Resource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.<Boolean>get("isOfGallery"), true);
            }
        }, pageable);
        return page;
    }

    public void deleteResource(Long id){
        Resource resource = getResource(id);
        File file = new File(resource.getPath()+resource.getName());
        if (file.exists()){
            file.delete();
        }
        resourceRepository.deleteById(id);
    }

    /**
     * 根据资源id从数据库中获取资源信息，并将其从文件中读取出来作为响应
     * @param id:Long
     * @throws IOException
     * @return ResponseEntity
     * @author watermelon
     * @version 1.0, 2020-3-25
     */
    public ResponseEntity downloadFile(Long id) throws IOException {
        Resource resource = getAndDownloadResource(id);
        FileSystemResource file = new FileSystemResource(resource.getPath()+resource.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Dispositon","attachment; filename="+resource.getName());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }

    /**
     * 通过上传的file构造resource，用于存储资源的相关信息(除path以外，不同资源的path需要在上级方法中区分)
     * @param file:MultipartFile用于获取文件的相关信息
     * @param user:User用于记录上传用户
     * @return Resource
     * @author watermelon
     * @version 1.0, 2020-3-14
     */
    private Resource setResourceFields(MultipartFile file, User user){
        Resource resource = new Resource();
        String fileName = file.getOriginalFilename();
        String oriName = fileName.substring(0,fileName.lastIndexOf("."));
        String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        resource.setName(fileName);
        resource.setOriginalName(oriName);
        resource.setExtensionName(extName);
        resource.setType(typeTransform(file.getContentType(),extName));
        resource.setSize(file.getSize());
        resource.setStrSize(sizeToString(file.getSize()));
        resource.setUser(user);
        resource.setUploadTime(new Date());
        return resource;
    }

    /**
     * 将文件写入文件系统实现持久化
     * @param file:MultipartFile
     * @param resource:Resource
     * @throws IOException
     * @return void
     * @author watermelon
     * @version 1.0, 2020-3-14
     */
    private void saveFile(MultipartFile file, Resource resource) throws IOException{
        FileOutputStream fileOutputStream = new FileOutputStream(resource.getPath()+resource.getName());
        BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream);
        outputStream.write(file.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 传入一个Long类型数据，转化为易读的对应磁盘容量大小的字符串
     * @param size:Long
     * @return String
     * @author watermelon
     * @version 1.0
     */
    private String sizeToString(Long size){
        int i = 0;
        int decimal = 0;
        String sizeStr = null;
        while (size>=1024){
            decimal = (int) (size%1024);
            size = size/1024;
            i++;
        }
        switch (i){
            case 0 : sizeStr = size + "." + decimal + "Byte";break;
            case 1 : sizeStr = size + "." + decimal + "KB";break;
            case 2 : sizeStr = size + "." + decimal + "MB";break;
            case 3 : sizeStr = size + "." + decimal + "GB";break;
            case 4 : sizeStr = size + "." + decimal + "TB";break;
        }
        return sizeStr;
    }

    private String typeTransform(String fileType,String extName){
        String type = null;
        if (fileType.length()>20){
            type = fileType.substring(fileType.lastIndexOf(".")+1);
            if (type.length()>20){
                type = extName.substring(1);
            }
        }
        return type;
    }
}
