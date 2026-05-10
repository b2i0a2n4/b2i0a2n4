package com.example.apartmentmanagement.mapper;

import com.example.apartmentmanagement.entity.ApartmentContract;
import com.example.apartmentmanagement.entity.ContractVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContractMapper {

    // 统计指定房源当前生效的合同数量（入住人数）
    @Select("SELECT COUNT(*) FROM apartment_contract WHERE room_id = #{roomId} AND status = 0")
    int countActiveContractsByRoomId(Long roomId);

    // 插入租赁合同记录并返回自增主键
    @Insert("INSERT INTO apartment_contract(user_id, room_id, start_date, end_date, status, lease_months, create_time) " +
            "VALUES(#{userId}, #{roomId}, #{startDate}, #{endDate}, #{status}, #{leaseMonths}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertContract(ApartmentContract contract);

    // 多表关联查询全量合同及关联房源、租客详情
    @Select("SELECT c.id, c.user_id AS userId, c.room_id AS roomId, c.start_date AS startDate, c.end_date AS endDate, c.status, " +
            "u.real_name AS tenantName, u.phone AS tenantPhone, " +
            "r.building_no AS buildingNo, r.room_no AS roomNo " +
            "FROM apartment_contract c " +
            "JOIN sys_user u ON c.user_id = u.id " +
            "JOIN apartment_room r ON c.room_id = r.id " +
            "ORDER BY c.create_time DESC")
    List<ContractVO> getAllContracts();

    // 分页查询指定租客名下当前生效的合同记录
    @Select("SELECT c.id, c.user_id AS userId, c.room_id AS roomId, c.start_date AS startDate, c.end_date AS endDate, c.status, " +
            "r.building_no AS buildingNo, r.room_no AS roomNo, r.rent_price AS rentPrice " +
            "FROM apartment_contract c " +
            "JOIN apartment_room r ON c.room_id = r.id " +
            "WHERE c.user_id = #{userId} AND c.status = 0 " +
            "ORDER BY c.create_time DESC LIMIT 1")
    ContractVO getMyActiveContract(Long userId);

    // 根据主键标识查询单条合同详情
    @Select("SELECT * FROM apartment_contract WHERE id = #{id}")
    ApartmentContract getById(Long id);

    // 更新指定合同的业务状态
    @Update("UPDATE apartment_contract SET status = #{status} WHERE id = #{id}")
    int updateContractStatus(@Param("id") Long id, @Param("status") Integer status);

    // 根据记录标识删除合同
    @Delete("DELETE FROM apartment_contract WHERE id = #{id}")
    int deleteContract(Long id);

    // 级联删除指定合同关联的所有账单记录
    @Delete("DELETE FROM apartment_bill WHERE contract_id = #{contractId}")
    int deleteBillsByContractId(Long contractId);

    // 根据记录标识执行物理删除（兼容性接口）
    @Delete("DELETE FROM apartment_contract WHERE id = #{id}")
    int deleteById(Long id);
}