package com.example.apartmentmanagement.mapper;

import com.example.apartmentmanagement.entity.ApartmentPost;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {

    // 插入新增留言记录
    @Insert("INSERT INTO apartment_post(user_id, content, create_time) VALUES(#{userId}, #{content}, NOW())")
    int insertPost(ApartmentPost post);

    // 联表查询全量留言记录及发布者用户信息
    @Select("SELECT p.*, u.real_name AS userName, u.role AS userRole " +
            "FROM apartment_post p " +
            "JOIN sys_user u ON p.user_id = u.id " +
            "ORDER BY p.create_time DESC")
    List<ApartmentPost> getAllPosts();

    // 根据主键标识删除指定留言记录
    @Delete("DELETE FROM apartment_post WHERE id = #{id}")
    int deletePost(Long id);

    // 更新指定留言记录的正文内容
    @Update("UPDATE apartment_post SET content = #{content} WHERE id = #{id}")
    int updatePost(ApartmentPost post);
}