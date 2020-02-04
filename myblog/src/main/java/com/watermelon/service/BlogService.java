package com.watermelon.service;

import com.watermelon.DAO.BlogRepository;
import com.watermelon.entity.Blog;
import com.watermelon.entity.Type;
import com.watermelon.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public Blog getBlog(Long id){
        return blogRepository.getOne(id);
    }

    public Blog updateBlog(Long id, Blog blog){
        Blog blog1 = blogRepository.getOne(id);
        if (blog1==null){
            throw new NotFoundException("未找到对应blog");
        }
        BeanUtils.copyProperties(blog,blog1);
        return blogRepository.save(blog1);
    }

    public Blog saveBlog(Blog blog){
        return blogRepository.save(blog);
    }

    public Page<Blog> listBlog(Pageable pageable, Blog blog){
        return blogRepository.findAll(new Specification<Blog>() {
            //使用Specification类进行条件查询，通过添加predicate自动构造查询语句添加查询限定条件
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //用于存放各种查询限定条件
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (!blog.getTitle().equals("") && blog.getTitle()!=null){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"),blog.getTitle()));
                }//添加Blog的Title标题条件约束
                if (blog.getType().getId() != null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blog.getType().getId()));
                }//添加Blog的Type类型条件约束
                if (blog.getShareStatement()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("shareStatement"), blog.getShareStatement()));
                }//添加Blog是否被推荐条件约束
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    public Blog deleteBlog(Long id){
        return blogRepository.deleteBlogById(id);
    }
}
