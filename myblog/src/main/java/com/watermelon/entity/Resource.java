package com.watermelon.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "resource")
public class Resource {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String originalName;
    private String extensionName;
    private Integer downloadTimes;
    private Long size;
    private String strSize;
    private String path;
    private String type;
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadTime;

    @ManyToOne
    private User user;

    public Resource(){
        this.downloadTimes = 0;
        this.size = 0l;
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

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    public Integer getDownloadTimes() {
        return downloadTimes;
    }

    public void setDownloadTimes(Integer downloadTimes) {
        this.downloadTimes = downloadTimes;
    }

    public Long getSize() {
        return size;
    }

    public String getStrSize() {
        return strSize;
    }

    public void setStrSize(String strSize) {
        this.strSize = strSize;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", originalName='" + originalName + '\'' +
                ", extensionName='" + extensionName + '\'' +
                ", downloadTimes=" + downloadTimes +
                ", size=" + size +
                ", strSize='" + strSize + '\'' +
                ", path='" + path + '\'' +
                ", type='" + type + '\'' +
                ", uploadTime=" + uploadTime +
                ", user=" + user +
                '}';
    }
}
