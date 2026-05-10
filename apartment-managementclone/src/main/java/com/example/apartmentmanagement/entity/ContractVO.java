package com.example.apartmentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class ContractVO {

    private Long id; // 合同视图唯一标识
    private Long userId; // 关联租客标识
    private Long roomId; // 关联房间标识
    private String tenantName; // 关联租客姓名
    private String tenantPhone; // 关联租客联系电话
    private String buildingNo; // 楼宇编号
    private String roomNo; // 房间编号
    private Double rentPrice; // 租金价格

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate; // 合同起租日期

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate; // 合同结束日期

    private Integer status; // 合同状态：0-生效中，1-已退租，2-待审批

    // 属性的 Getter 与 Setter 方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }

    public String getTenantName() { return tenantName; }
    public void setTenantName(String tenantName) { this.tenantName = tenantName; }

    public String getTenantPhone() { return tenantPhone; }
    public void setTenantPhone(String tenantPhone) { this.tenantPhone = tenantPhone; }

    public String getBuildingNo() { return buildingNo; }
    public void setBuildingNo(String buildingNo) { this.buildingNo = buildingNo; }

    public String getRoomNo() { return roomNo; }
    public void setRoomNo(String roomNo) { this.roomNo = roomNo; }

    public Double getRentPrice() { return rentPrice; }
    public void setRentPrice(Double rentPrice) { this.rentPrice = rentPrice; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}