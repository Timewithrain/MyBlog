package com.watermelon.service;

import com.watermelon.DAO.ResourceRepository;
import com.watermelon.entity.Resource;
import com.watermelon.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class ResourceService {

    @Value("${system-params.file.path}")
    private String filePath;

    @Autowired
    private ResourceRepository resourceRepository;

    public Resource getResource(Long id){
        return resourceRepository.getOne(id);
    }

    public Resource addResource(MultipartFile file, User user) throws IOException {
        Resource resource = new Resource();
        String fileName = file.getOriginalFilename();
        String oriName = fileName.substring(0,fileName.lastIndexOf("."));
        String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        resource.setName(fileName);
        resource.setOriginalName(oriName);
        resource.setExtensionName(extName);
        resource.setType(file.getContentType());
        resource.setSize(file.getSize());
        resource.setStrSize(sizeToString(file.getSize()));
        resource.setPath(filePath);
        resource.setUser(user);
        resource.setUploadTime(new Date());
        System.out.println(fileName);
        System.out.println(oriName);
        System.out.println(extName);
        System.out.println("type:"+resource.getType());
        System.out.println("size:"+resource.getSize());
        System.out.println("size:"+resource.getStrSize());
        System.out.println("size:"+resource.getPath());
        //存储文件
//        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath+fileName));
//        outputStream.write(file.getBytes());
//        outputStream.flush();
//        outputStream.close();
//        resourceRepository.save(resource);
        return null;
    }

    public Page<Resource> listResource(Pageable pageable){
        return resourceRepository.findAll(pageable);
    }

    public void deleteResource(Long id){
        resourceRepository.deleteById(id);
    }

    //将size转换为对应大小字符
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

}
