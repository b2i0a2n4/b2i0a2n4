package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.entity.ApartmentBill;
import com.example.apartmentmanagement.entity.BillVO;
import com.example.apartmentmanagement.mapper.BillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillMapper billMapper;

    // 查询全部账单信息
    @GetMapping("/list")
    public List<BillVO> getList() {
        return billMapper.getAllBills();
    }

    // 新增账单记录
    @PostMapping("/generate")
    public String generate(@RequestBody ApartmentBill bill) {
        billMapper.insertBill(bill);
        return "账单生成成功";
    }

    // 更新账单支付状态
    @PutMapping("/pay/{id}")
    public String pay(@PathVariable Long id) {
        billMapper.payBill(id);
        return "收款状态更新成功";
    }

    // 根据用户ID查询关联账单
    @GetMapping("/my")
    public List<BillVO> getMyBills(@RequestParam Long userId) {
        return billMapper.getMyBills(userId);
    }

    // 删除指定账单记录
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        billMapper.deleteBill(id);
        return "删除成功";
    }
}