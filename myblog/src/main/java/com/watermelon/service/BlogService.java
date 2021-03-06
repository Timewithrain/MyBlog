package com.watermelon.service;

import com.watermelon.DAO.BlogRepository;
import com.watermelon.entity.Blog;
import com.watermelon.entity.BlogQuery;
import com.watermelon.entity.Type;
import com.watermelon.exception.NotFoundException;
import com.watermelon.util.MarkdownUtils;
import com.watermelon.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public Blog getBlog(Long id){
        return blogRepository.getOne(id);
    }

    public Blog getBlogAndConvert(Long id){
        Blog blog = blogRepository.getOne(id);
        if (blog == null||!blog.getPublished()){
            throw new NotFoundException("博客不存在");
        }
        //获取到blog以后将view值加1
        addViews(id);
        String content = blog.getContent();
        //调用markdown转换方法获取html内容后赋值给blog1再返回至前端
        String convertedContent = MarkdownUtils.markdownToHtmlExtensions(content);
        Blog blog1 = new Blog();
        BeanUtils.copyProperties(blog,blog1);
        blog1.setContent(convertedContent);
        return blog1;
    }

    public void addViews(Long id){
        Blog blog = blogRepository.getOne(id);
        blog.setViews(blog.getViews()+1);
        blogRepository.save(blog);
    }

    public Blog updateBlog(Blog blog){
        Blog blog1 = blogRepository.getOne(blog.getId());
        if (blog1==null){
            throw new NotFoundException("未找到对应blog");
        }
        //前端页面返回的blog中为null的属性不写入数据库
        BeanUtils.copyProperties(blog,blog1, MyBeanUtils.getNullPropertiesName(blog));
        blog1.setUpdateTime(new Date());
        return blogRepository.save(blog1);
    }

    public Blog saveBlog(Blog blog){
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        return blogRepository.save(blog);
    }

    public Page<Blog> listBlog(Pageable pageable){
        return blogRepository.findAll(pageable);
    }

    //admin页面调用
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
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.isRecommended()));
                }//添加Blog是否被推荐条件约束
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    //search调用,通过Specification实现过滤
    public Page<Blog> listBlogByStringAndCovert(String query,Pageable pageable){
//        //通过findByQuery方法实现过滤
//        Page<Blog> page = blogRepository.findByQuery(query, pageable);
        Page<Blog> page = blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (query!=null){
                    //根据query进行like模糊查询
                    list.add(criteriaBuilder.like(root.get("content").as(String.class),query));
                    list.add(criteriaBuilder.like(root.get("title").as(String.class),query));
                    list.add(criteriaBuilder.equal(root.get("published"),true));
                }
                criteriaQuery.where(list.toArray(new Predicate[list.size()]));
                return null;
            }
        },pageable);
        return convertPage(page,pageable);
    }

    //index页面、footer调用
    public List<Blog> listTopBlog(Integer size){
        Sort sort = Sort.by(Sort.Direction.DESC,"views");
        Pageable pageable = PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    //index页面调用
    public Page<Blog> listBlogAndConvert(Pageable pageable){
        Page<Blog> page =  blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                List<Predicate> predicates = new ArrayList<Predicate>();
//                predicates.add(criteriaBuilder.equal(root.<Boolean>get("published"), true));
//                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaBuilder.equal(root.<Boolean>get("published"), true);
            }
        }, pageable);
        return convertPage(page,pageable);
    }

    //types页面、tags页面调用
    public Page<Blog> listBlogAndConvert(Pageable pageable,BlogQuery blogQuery){
        Page<Blog> page =  listBlogByTypeAndTag(pageable,blogQuery);
        //调用convertPage()方法将markdown格式的content转化为html格式
        return convertPage(page,pageable);
    }

    //listBlogAndConvert方法调用
    public Page<Blog> listBlogByTypeAndTag(Pageable pageable, BlogQuery blogQuery){
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (blogQuery.getTypeId() != null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blogQuery.getTypeId()));
                }
                if (blogQuery.getTagId() != null){
                    //此处为一个关联查询
                    Join join = root.join("tags");
                    predicates.add(criteriaBuilder.equal(join.get("id"),blogQuery.getTagId()));
                }
                predicates.add(criteriaBuilder.equal(root.<Boolean>get("published"), true));
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    public void deleteBlog(Long id){
        blogRepository.deleteById(id);
    }

    /**
     * 将Page集合中的所有blog的content从markdown格式转换为html格式
     * parameter:Page<Blog>需要转换的Page集合;Pageable构造的Page约束
     * return Page<Blog>
     * @author watermelon
     * @version 1.0, 2020-2-18
     * @see MarkdownUtils
     */
    private Page<Blog> convertPage(Page<Blog> page,Pageable pageable){
        int shortCut = 0;
        List<Blog> list = new ArrayList<Blog>();
        //获取page中所有的blog的content并转换为html
        Iterator<Blog> iter = page.iterator();
        while(iter.hasNext()){
            Blog b = iter.next();
            String converted = MarkdownUtils.markdownToHtmlExtensions(b.getContent());
            b.setContent(converted);
            list.add(b);
        }
        //根据原有的pageable通过list构造一个新的page
        Page<Blog> convertedPage = new PageImpl<>(list,pageable,page.getTotalElements());
        return convertedPage;
    }
}
