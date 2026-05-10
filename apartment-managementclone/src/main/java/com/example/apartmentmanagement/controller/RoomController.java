package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.entity.ApartmentRoom;
import com.example.apartmentmanagement.mapper.ApartmentRoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class RoomController {

    @Autowired
    private ApartmentRoomMapper roomMapper;

    // 查询全部房源信息列表
    @GetMapping("/api/rooms")
    public List<ApartmentRoom> getRoomList() {
        return roomMapper.getAllRooms();
    }

    // 新增房源记录
    @PostMapping("/api/rooms")
    public String addRoom(@RequestBody ApartmentRoom room) {
        // 设置房源初始状态为空闲 (0)
        room.setStatus(0);
        roomMapper.insertRoom(room);
        return "添加成功";
    }

    // 删除指定房源记录
    @DeleteMapping("/api/rooms/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomMapper.deleteRoomById(id);
        return "删除成功";
    }

    // 更新指定房源信息
    @PutMapping("/api/rooms")
    public String updateRoom(@RequestBody ApartmentRoom room) {
        roomMapper.updateRoom(room);
        return "修改成功";
    }
}