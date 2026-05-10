package com.example.apartmentmanagement.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ApartmentContract {

    private Long id;
    private Long userId; // 关联租客标识
    private Long roomId; // 关联房间标识
    private LocalDate startDate; // 合同起租日期
    private LocalDate endDate; // 合同结束日期
    private Integer status; // 合同状态：0-生效中，1-已退租，2-待审批
    private LocalDateTime createTime; // 记录创建时间
    private Integer leaseMonths; // 租赁总月数

    // 属性的 Getter 与 Setter 方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public Integer getLeaseMonths() { return leaseMonths; }
    public void setLeaseMonths(Integer leaseMonths) { this.leaseMonths = leaseMonths; }
}