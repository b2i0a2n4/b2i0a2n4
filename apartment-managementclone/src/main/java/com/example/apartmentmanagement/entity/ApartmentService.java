package com.example.apartmentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class ApartmentService {

    private Long id; // 服务工单唯一标识
    private String type; // 工单业务类型（报修/保洁/看房/巡查）
    private Long roomId; // 关联房间标识
    private Long userId; // 申请用户标识
    private String content; // 业务需求详细描述

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime appointmentTime; // 期望上门服务时间

    private Integer status; // 工单处理状态：0-待接单，1-处理中，2-已完成

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // 记录创建时间

    // 联合查询扩展映射字段
    private String userName; // 申请人姓名
    private String roomStr; // 关联房间拼接信息

    // 属性的 Getter 与 Setter 方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalDateTime appointmentTime) { this.appointmentTime = appointmentTime; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getRoomStr() { return roomStr; }
    public void setRoomStr(String roomStr) { this.roomStr = roomStr; }
}