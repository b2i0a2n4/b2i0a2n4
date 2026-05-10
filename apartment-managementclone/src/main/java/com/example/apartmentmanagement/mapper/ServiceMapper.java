package com.example.apartmentmanagement.mapper;

import com.example.apartmentmanagement.entity.ApartmentService;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ServiceMapper {

    // 插入新增综合服务工单记录
    @Insert("INSERT INTO apartment_service(type, room_id, user_id, content, appointment_time, status, create_time) " +
            "VALUES(#{type}, #{roomId}, #{userId}, #{content}, #{appointmentTime}, 0, NOW())")
    int insertService(ApartmentService service);

    // 查询全量服务工单详情（包含关联用户信息及房源信息）
    @Select("SELECT s.*, u.real_name AS userName, CONCAT(r.building_no, '-', r.room_no) AS roomStr " +
            "FROM apartment_service s " +
            "JOIN sys_user u ON s.user_id = u.id " +
            "LEFT JOIN apartment_room r ON s.room_id = r.id " +
            "ORDER BY s.create_time DESC")
    List<ApartmentService> getAllServices();

    // 根据用户标识查询个人关联的服务工单及房源信息
    @Select("SELECT s.*, CONCAT(r.building_no, '-', r.room_no) AS roomStr " +
            "FROM apartment_service s " +
            "LEFT JOIN apartment_room r ON s.room_id = r.id " +
            "WHERE s.user_id = #{userId} ORDER BY s.create_time DESC")
    List<ApartmentService> getMyServices(Long userId);

    // 更新指定工单的处理状态
    @Update("UPDATE apartment_service SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    // 根据主键标识删除服务工单记录
    @Delete("DELETE FROM apartment_service WHERE id = #{id}")
    int deleteService(Long id);
}