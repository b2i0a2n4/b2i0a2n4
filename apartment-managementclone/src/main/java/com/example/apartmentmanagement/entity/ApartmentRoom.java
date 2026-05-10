package com.example.apartmentmanagement.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ApartmentRoom {

    private Long id; // 房源唯一标识
    private String buildingNo; // 楼宇编号
    private String roomNo; // 房间编号
    private Integer capacity; // 额定居住人数
    private BigDecimal rentPrice; // 租金价格
    private Integer status; // 房间状态：0-空闲，1-已出租
    private LocalDateTime createTime; // 记录创建时间

    // 属性的 Getter 与 Setter 方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBuildingNo() { return buildingNo; }
    public void setBuildingNo(String buildingNo) { this.buildingNo = buildingNo; }

    public String getRoomNo() { return roomNo; }
    public void setRoomNo(String roomNo) { this.roomNo = roomNo; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public BigDecimal getRentPrice() { return rentPrice; }
    public void setRentPrice(BigDecimal rentPrice) { this.rentPrice = rentPrice; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}