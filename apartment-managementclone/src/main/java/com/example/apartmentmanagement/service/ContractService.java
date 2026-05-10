package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.entity.ApartmentBill;
import com.example.apartmentmanagement.entity.ApartmentContract;
import com.example.apartmentmanagement.entity.ApartmentRoom;
import com.example.apartmentmanagement.mapper.ApartmentRoomMapper;
import com.example.apartmentmanagement.mapper.BillMapper;
import com.example.apartmentmanagement.mapper.ContractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal; // 导入高精度数值处理类

@Service
public class ContractService {

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private ApartmentRoomMapper roomMapper;

    @Autowired
    private BillMapper billMapper;

    /**
     * 执行线下直接办理入住业务（包含合同、状态变更及账单生成）
     */
    @Transactional
    public void checkIn(ApartmentContract contract) {
        // 执行租赁合同记录入库操作
        contractMapper.insertContract(contract);

        // 根据实际入住人数动态更新房源业务状态
        updateRoomStatusBasedOnOccupancy(contract.getRoomId());

        // 获取房源详细信息以提取租金单价
        ApartmentRoom room = roomMapper.getRoomById(contract.getRoomId());

        // 获取租赁时长数据，并执行非空校验及默认值处理
        int months = (contract.getLeaseMonths() != null && contract.getLeaseMonths() > 0) ? contract.getLeaseMonths() : 1;

        // 计算账单总金额：单价与租赁月份执行高精度乘法运算
        BigDecimal totalAmount = room.getRentPrice().multiply(new BigDecimal(months));

        // 构造并持久化首期入住账单数据
        ApartmentBill firstBill = new ApartmentBill();
        firstBill.setContractId(contract.getId());
        firstBill.setBillName("首次入住账单 (包含" + months + "个月租金)");
        firstBill.setAmount(totalAmount);

        billMapper.insertBill(firstBill);
    }

    /**
     * 撤销合同并执行关联资源的级联清理
     */
    @Transactional
    public void removeContract(Long contractId) {
        // 根据标识查询合同记录以获取关联资源信息
        ApartmentContract contract = contractMapper.getById(contractId);

        if (contract != null) {
            // 执行级联删除：清理合同项下的所有账单记录
            contractMapper.deleteBillsByContractId(contractId);

            // 执行合同主表记录删除
            contractMapper.deleteById(contractId);

            // 根据实际入住人数动态更新房源业务状态
            updateRoomStatusBasedOnOccupancy(contract.getRoomId());
        }
    }

    /**
     * 审批通过线上租房申请并触发后续业务流程
     */
    @Transactional
    public void approveContract(Long contractId) {
        // 校验合同记录是否存在
        ApartmentContract contract = contractMapper.getById(contractId);
        if (contract == null) return;

        // 变更合同状态为生效（状态码：0）
        contractMapper.updateContractStatus(contractId, 0);

        // 根据实际入住人数动态更新房源业务状态
        updateRoomStatusBasedOnOccupancy(contract.getRoomId());

        // 获取房源单价并自动触发首笔租金账单生成
        ApartmentRoom room = roomMapper.getById(contract.getRoomId());

        ApartmentBill firstBill = new ApartmentBill();
        firstBill.setContractId(contractId);
        firstBill.setBillName("首次入住账单 (预付" + contract.getLeaseMonths() + "个月租金)");
        firstBill.setAmount(room.getRentPrice().multiply(new java.math.BigDecimal(contract.getLeaseMonths())));
        firstBill.setStatus(0); // 初始化支付状态为待处理

        billMapper.insertBill(firstBill);
    }

    /**
     * 根据当前实际入住情况更新房源状态
     */
    private void updateRoomStatusBasedOnOccupancy(Long roomId) {
        ApartmentRoom room = roomMapper.getRoomById(roomId);
        if (room == null) return;
        
        int activeCount = contractMapper.countActiveContractsByRoomId(roomId);
        
        if (activeCount == 0) {
            roomMapper.updateRoomStatus(roomId, 0); // 0-空闲
        } else if (activeCount >= room.getCapacity()) {
            roomMapper.updateRoomStatus(roomId, 2); // 2-满房
        } else {
            roomMapper.updateRoomStatus(roomId, 1); // 1-部分入住
        }
    }
}