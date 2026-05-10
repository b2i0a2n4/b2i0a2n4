package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.entity.ApartmentService;
import com.example.apartmentmanagement.mapper.ServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceMapper serviceMapper;

    // 查询全量综合服务工单列表
    @GetMapping("/list")
    public List<ApartmentService> list() {
        return serviceMapper.getAllServices();
    }

    // 根据用户ID查询关联的服务工单记录
    @GetMapping("/my")
    public List<ApartmentService> myServices(@RequestParam Long userId) {
        return serviceMapper.getMyServices(userId);
    }

    // 提交新增综合服务工单申请
    @PostMapping("/add")
    public String add(@RequestBody ApartmentService service) {
        serviceMapper.insertService(service);
        return "提交成功";
    }

    // 更新指定工单的处理状态
    @PutMapping("/status")
    public String updateStatus(@RequestParam Long id, @RequestParam Integer status) {
        serviceMapper.updateStatus(id, status);
        return "更新成功";
    }

    // 删除指定服务工单记录
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        serviceMapper.deleteService(id);
        return "删除成功";
    }
}