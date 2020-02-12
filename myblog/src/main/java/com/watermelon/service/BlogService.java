package com.watermelon.service;

import com.watermelon.DAO.BlogRepository;
import com.watermelon.entity.Blog;
import com.watermelon.entity.BlogQuery;
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
import java.util.Date;
import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public Blog getBlog(Long id){
        return blogRepository.getOne(id);
    }

    public Blog updateBlog(Blog blog){
        Blog blog1 = blogRepository.getOne(blog.getId());
        if (blog1==null){
            throw new NotFoundException("未找到对应blog");
        }
        blog.setUpdateTime(new Date());
        return blogRepository.save(blog);
    }

    public Blog saveBlog(Blog blog){
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        return blogRepository.save(blog);
    }

    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog){
        return blogRepository.findAll(new Specification<Blog>() {
            //使用Specification类进行条件查询，通过添加predicate自动构造查询语句添加查询限定条件
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //用于存放各种查询限定条件
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (blog.getTitle()!=null && !blog.getTitle().equals("")){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"),blog.getTitle()));
                }//添加Blog的Title标题条件约束
                if (blog.getTypeId() != null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }//添加Blog的Type类型条件约束
                if (blog.getRecommend()!=null && blog.isRecommended()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("shareStatement"), blog.isRecommended()));
                }//添加Blog是否被推荐条件约束
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    public Page<Blog> listBlog(Pageable pageable){
        return blogRepository.findAll(pageable);
    }

    public void deleteBlog(Long id){
        blogRepository.deleteById(id);
    }
}
