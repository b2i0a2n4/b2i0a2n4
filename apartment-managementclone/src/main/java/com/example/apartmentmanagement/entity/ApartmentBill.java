package com.example.apartmentmanagement.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ApartmentBill {

    private Long id;
    private Long contractId; // 关联的合同唯一标识
    private String billName; // 账单项目名称
    private BigDecimal amount; // 费用金额
    private Integer status; // 支付状态：0-待支付，1-已支付
    private LocalDateTime createTime; // 记录创建时间
    private LocalDateTime payTime; // 支付完成时间

    // 属性的 Getter 与 Setter 方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getContractId() { return contractId; }
    public void setContractId(Long contractId) { this.contractId = contractId; }

    public String getBillName() { return billName; }
    public void setBillName(String billName) { this.billName = billName; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public LocalDateTime getPayTime() { return payTime; }
    public void setPayTime(LocalDateTime payTime) { this.payTime = payTime; }
}