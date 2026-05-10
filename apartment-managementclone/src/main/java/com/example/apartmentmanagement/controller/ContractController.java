package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.entity.ApartmentContract;
import com.example.apartmentmanagement.entity.ContractVO;
import com.example.apartmentmanagement.mapper.ContractMapper;
import com.example.apartmentmanagement.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractMapper contractMapper;

    // 查询全量合同信息列表
    @GetMapping("/list")
    public List<ContractVO> getContractList() {
        return contractMapper.getAllContracts();
    }

    // 查询指定用户的当前生效合同
    @GetMapping("/my")
    public ContractVO getMyContract(@RequestParam Long userId) {
        return contractMapper.getMyActiveContract(userId);
    }

    // 管理员办理租客入住业务
    @PostMapping("/checkIn")
    public String checkIn(@RequestBody ApartmentContract contract) {
        contract.setStatus(0); // 设置初始状态为生效中

        // 根据起租日和租期自动计算退租日期
        if (contract.getStartDate() != null && contract.getLeaseMonths() != null) {
            contract.setEndDate(contract.getStartDate().plusMonths(contract.getLeaseMonths()));
        }

        contractService.checkIn(contract);
        return "办理入住成功";
    }

    // 租客提交在线租房申请
    @PostMapping("/apply")
    public String apply(@RequestBody ApartmentContract contract) {
        contract.setStatus(2); // 设置初始状态为待审批

        // 根据起租日和租期自动计算退租日期
        if (contract.getStartDate() != null && contract.getLeaseMonths() != null) {
            contract.setEndDate(contract.getStartDate().plusMonths(contract.getLeaseMonths()));
        }

        contractMapper.insertContract(contract);
        return "申请已提交，等待管理员审核";
    }

    // 审批通过指定的租房申请
    @PutMapping("/{id}/approve")
    public String approve(@PathVariable Long id) {
        contractService.approveContract(id);
        return "审批通过，已锁定房间并生成账单";
    }

    // 撤销合同并释放相关房间资源
    @DeleteMapping("/{id}")
    public String deleteContract(@PathVariable Long id) {
        contractService.removeContract(id);
        return "撤销成功，房间已释放";
    }
}