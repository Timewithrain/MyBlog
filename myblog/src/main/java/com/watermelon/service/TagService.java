package com.watermelon.service;

import com.watermelon.DAO.TagRepository;
import com.watermelon.entity.Tag;
import com.watermelon.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Tag getTag(Long id){
        return tagRepository.getOne(id);
    }

    public Tag getTagByName(String  name){
        return tagRepository.findByName(name);
    }

    public Tag saveTag(Tag tag){
        return tagRepository.save(tag);
    }

    public Tag updateTag(Long id,Tag tag){
        Tag tag1 = tagRepository.getOne(id);
        if (tag1==null){
            throw new NotFoundException("未找到对应Tag");
        }
        BeanUtils.copyProperties(tag,tag1);
        return tagRepository.save(tag1);
    }

    public void deleteTag(Long id){
        tagRepository.deleteById(id);
    }

    //传入tag的索引id构成的字符串，以","分割,将字符串转换为对应索引list以后通过findAllById()方法完成查询
    public List<Tag> listTag(String index){
        if (index.equals("")){
            return null;
        }
        List<Long> list = new ArrayList<Long>();
        String[] str = index.split(",");
        for (int i=0;i<str.length;i++){
            list.add(Long.parseLong(str[i]));
        }
        return tagRepository.findAllById(list);
    }

    public Page<Tag> listTag(Pageable pageable){
        return tagRepository.findAll(pageable);
    }

    public List<Tag> listTag(){
        return tagRepository.findAll();
    }

}
