package com.example.apartmentmanagement.mapper;

import com.example.apartmentmanagement.entity.SysUser;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface SysUserMapper {

    // 根据用户名查询用户信息
    @Select("SELECT * FROM sys_user WHERE username = #{username}")
    SysUser getByUsername(String username);

    // 查询所有租客角色（role = 1）的用户列表
    @Select("SELECT * FROM sys_user WHERE role = 1")
    List<SysUser> getAllTenants();

    // 插入新增租客记录（默认权限角色为1）
    @Insert("INSERT INTO sys_user (username, password, real_name, phone, role) " +
            "VALUES (#{username}, #{password}, #{realName}, #{phone}, 1)")
    int insertTenant(SysUser user);

    // 更新指定租客的基础信息
    @Update("UPDATE sys_user SET username=#{username}, real_name=#{realName}, phone=#{phone} WHERE id=#{id}")
    int updateTenant(SysUser user);

    // 根据记录标识删除指定用户记录
    @Delete("DELETE FROM sys_user WHERE id = #{id}")
    int deleteUserById(Long id);

    // 根据用户标识查询单条用户详情
    @Select("SELECT * FROM sys_user WHERE id = #{id}")
    SysUser findUserById(Long id);
}