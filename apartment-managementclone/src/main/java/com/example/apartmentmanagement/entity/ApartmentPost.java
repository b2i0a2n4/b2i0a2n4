package com.example.apartmentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class ApartmentPost {

    private Long id; // 留言唯一标识
    private Long userId; // 发布用户标识
    private String content; // 留言正文内容

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // 记录创建时间

    // 联合查询扩展字段
    private String userName; // 发布者姓名
    private Integer userRole; // 发布者角色标识

    // 属性的 Getter 与 Setter 方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Integer getUserRole() { return userRole; }
    public void setUserRole(Integer userRole) { this.userRole = userRole; }
}