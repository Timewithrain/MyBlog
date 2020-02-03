package com.watermelon.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    //建立多对多关系，设置由Blog中的tags进行主动维护关系，此处为被维护端
    @ManyToMany(mappedBy = "tags")
    private List<Blog> Blog = new ArrayList<Blog>();

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<com.watermelon.entity.Blog> getBlog() {
        return Blog;
    }

    public void setBlog(List<com.watermelon.entity.Blog> blog) {
        Blog = blog;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}