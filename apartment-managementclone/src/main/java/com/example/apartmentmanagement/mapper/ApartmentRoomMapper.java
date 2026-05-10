package com.example.apartmentmanagement.mapper;

import com.example.apartmentmanagement.entity.ApartmentRoom;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ApartmentRoomMapper {

    // 查询全部房源信息列表
    @Select("SELECT * FROM apartment_room")
    List<ApartmentRoom> getAllRooms();

    // 插入新增房源记录
    @Insert("INSERT INTO apartment_room (building_no, room_no, capacity, rent_price, status) " +
            "VALUES (#{buildingNo}, #{roomNo}, #{capacity}, #{rentPrice}, #{status})")
    int insertRoom(ApartmentRoom room);

    // 根据记录标识删除指定房源
    @Delete("DELETE FROM apartment_room WHERE id = #{id}")
    int deleteRoomById(Long id);

    // 更新指定房源基础属性
    @Update("UPDATE apartment_room SET building_no=#{buildingNo}, room_no=#{roomNo}, capacity=#{capacity}, rent_price=#{rentPrice} WHERE id=#{id}")
    int updateRoom(ApartmentRoom room);

    // 更新指定房源的当前业务状态
    @Update("UPDATE apartment_room SET status = #{status} WHERE id = #{id}")
    int updateRoomStatus(@Param("id") Long id, @Param("status") Integer status);

    // 根据记录标识查询单条房源详情
    @Select("SELECT * FROM apartment_room WHERE id = #{id}")
    ApartmentRoom getRoomById(Long id);

    // 根据记录标识查询单条房源详情（兼容历史接口调用）
    @Select("SELECT * FROM apartment_room WHERE id = #{id}")
    ApartmentRoom getById(Long id);
}