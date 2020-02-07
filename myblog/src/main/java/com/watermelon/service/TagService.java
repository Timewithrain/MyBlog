package com.watermelon.service;

import com.watermelon.DAO.TagRepository;
import com.watermelon.entity.Tag;
import com.watermelon.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Page<Tag> listTag(Pageable pageable){
        return tagRepository.findAll(pageable);
    }

    public List<Tag> listTag(){
        return tagRepository.findAll();
    }

}
