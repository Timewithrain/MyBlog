package com.watermelon.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "blog")
public class Blog {
    //将id设为数据库中的主键并自动生成
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @Basic(fetch = FetchType.LAZY)//懒加载
    @Lob//长内容类型
    private String content;
    private String picture;
    private String flag;
    private Integer views;
    private Boolean appreciation;
    private Boolean published;
    private Boolean recommend;
    private Boolean shareStatement;
    private Boolean commentTable;
    //定义Date映射至数据库中所使用的类型
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToOne
    private User user;

    //设置多对一的数据库实体关系，多个博客对应一个type
    @ManyToOne
    private Type type;

    //@Transient注解，标注不写入数据库的普通属性
    @Transient
    private String tagsId;

    //设置级联增加，当blog的tag增加，会将相应的tag保存至数据库中
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<Tag>();

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<Comment>();

    public Blog() {
        this.views = 0;
        this.appreciation = false;
        this.published = false;
        this.recommend = false;
        this.shareStatement = false;
        this.commentTable = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(Boolean appreciation) {
        this.appreciation = appreciation;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(Boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public Boolean getCommentTable() {
        return commentTable;
    }

    public void setCommentTable(Boolean commentTable) {
        this.commentTable = commentTable;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getTagsId() {
        return tagsId;
    }

    public void setTagsId(String tagsId) {
        this.tagsId = tagsId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public Boolean isRecommended(){
        return recommend;
    }

    public String tagsToIds(List<Tag> tags){
        if(tags!=null || !tags.isEmpty()){
            StringBuffer sb = new StringBuffer();
            for (int i=0;i<tags.size();i++){
                if(i!=0){
                    sb.append(",");
                }
                sb.append(tags.get(i).getId());
            }
            return sb.toString();
        }else{
            return tagsId;
        }
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", name='" + title + '\'' +
                ", content='" + content + '\'' +
                ", picture='" + picture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", appreciation=" + appreciation +
                ", published=" + published +
                ", shareStatement=" + shareStatement +
                ", commentTable=" + commentTable +
                '}';
    }
}
