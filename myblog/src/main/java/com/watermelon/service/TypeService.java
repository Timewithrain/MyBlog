package com.watermelon.service;

import com.watermelon.DAO.TypeRepository;
import com.watermelon.entity.Type;
import com.watermelon.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TypeService {

    @Autowired
    private TypeRepository typeRepository;

    public Type saveType(Type type){
        return typeRepository.save(type);
    }

    @Transactional
    public Type getType(Long id){
        return typeRepository.getOne(id);
    }

    public Type updateType(Long id, Type type){
        Type type1 = typeRepository.getOne(id);
        if(type1==null){
            throw new NotFoundException("未找到对应Type");
        }
        //将传入的type的值赋给从数据库读取的type1
        BeanUtils.copyProperties(type,type1);
        return typeRepository.save(type1);
    }

    public void deleteType(Long id){
        typeRepository.deleteById(id);
    }

    @Transactional
    public Page<Type> listType(Pageable pageable){
        return typeRepository.findAll(pageable);
    }

}
