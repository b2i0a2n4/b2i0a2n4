package com.example.apartmentmanagement.mapper;

import com.example.apartmentmanagement.entity.ApartmentBill;
import com.example.apartmentmanagement.entity.BillVO;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface BillMapper {

    // 多表关联查询全量账单详细信息
    @Select("SELECT b.id, u.real_name AS tenantName, r.room_no AS roomNo, " +
            "b.bill_name AS billName, b.amount, b.status, b.create_time AS createTime, b.pay_time AS payTime " +
            "FROM apartment_bill b " +
            "LEFT JOIN apartment_contract c ON b.contract_id = c.id " +
            "LEFT JOIN sys_user u ON c.user_id = u.id " +
            "LEFT JOIN apartment_room r ON c.room_id = r.id " +
            "ORDER BY b.create_time DESC")
    List<BillVO> getAllBills();

    // 插入新增账单记录
    @Insert("INSERT INTO apartment_bill (contract_id, bill_name, amount, status) " +
            "VALUES (#{contractId}, #{billName}, #{amount}, 0)")
    int insertBill(ApartmentBill bill);

    // 更新账单支付状态并同步支付时间
    @Update("UPDATE apartment_bill SET status = 1, pay_time = NOW() WHERE id = #{id}")
    int payBill(Long id);

    // 根据用户标识查询个人账单及关联房源信息
    @Select("SELECT b.id, b.bill_name AS billName, b.amount, b.status, b.create_time AS createTime, b.pay_time AS payTime, r.room_no AS roomNo " +
            "FROM apartment_bill b " +
            "LEFT JOIN apartment_contract c ON b.contract_id = c.id " +
            "LEFT JOIN apartment_room r ON c.room_id = r.id " +
            "WHERE c.user_id = #{userId} " +
            "ORDER BY b.create_time DESC")
    List<BillVO> getMyBills(Long userId);

    // 根据主键标识删除账单记录
    @Delete("DELETE FROM apartment_bill WHERE id = #{id}")
    int deleteBill(Long id);

}