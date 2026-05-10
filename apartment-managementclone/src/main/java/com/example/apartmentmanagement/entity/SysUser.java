package com.example.apartmentmanagement.entity;

import java.time.LocalDateTime;

public class SysUser {

    private Long id; // 用户唯一标识
    private String username; // 登录账号
    private String password; // 登录密码
    private String realName; // 用户真实姓名
    private String phone; // 联系电话
    private Integer role; // 用户角色：0-管理员，1-普通租客
    private LocalDateTime createTime; // 记录创建时间

    // 属性的 Getter 与 Setter 方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRealName() { return realName; }
    public void setRealName(String realName) { this.realName = realName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Integer getRole() { return role; }
    public void setRole(Integer role) { this.role = role; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}