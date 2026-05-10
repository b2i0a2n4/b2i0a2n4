<template>
  <div class="tenant-bill-container">
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <span>个人财务账单</span>
          <el-tag type="info">请于截止日期前完成支付</el-tag>
        </div>
      </template>

      <el-table :data="myBills" border stripe style="width: 100%">
        <el-table-column prop="billName" label="费用项目" />
        <el-table-column prop="roomNo" label="关联房间" width="120" />
        <el-table-column prop="amount" label="账单金额" width="120">
          <template #default="scope">
            <span class="amount-text">￥{{ scope.row.amount }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="出单日期" width="180" />
        <el-table-column label="支付状态" width="120">
          <template #default="scope">
            <el-tag :type="scope.row.status === 0 ? 'danger' : 'success'">
              {{ scope.row.status === 0 ? '待缴费' : '已缴费' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="业务操作" width="150">
          <template #default="scope">
            <el-button 
              v-if="scope.row.status === 0" 
              type="primary" 
              size="small" 
              @click="payMyBill(scope.row)"
            >立即支付</el-button>
            <span v-else class="pay-date">完成时间：{{ scope.row.payTime }}</span>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'

const myBills = ref([])

// 加载当前登录用户的个人账单列表
const loadMyBills = async () => {
  const userId = localStorage.getItem('user_id')
  try {
    const res = await axios.get(`http://localhost:8080/api/bills/my?userId=${userId}`)
    myBills.value = res.data
  } catch (error) {
    ElMessage.error('获取个人账单失败')
  }
}

onMounted(() => {
  loadMyBills()
})

// 执行账单在线支付流程
const payMyBill = (row) => {
  ElMessageBox.confirm(
    `确认支付费用项目：${row.billName}，总计金额：￥${row.amount} 吗？`,
    '缴费确认',
    {
      confirmButtonText: '确认支付',
      cancelButtonText: '取消',
      type: 'success',
    }
  ).then(async () => {
    try {
      // 执行后端支付状态更新接口
      await axios.put(`http://localhost:8080/api/bills/pay/${row.id}`)
      ElMessage.success('账单支付成功')
      loadMyBills() 
    } catch (error) {
      ElMessage.error('支付操作执行异常')
    }
  }).catch(() => {})
}
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
.amount-text { color: #f56c6c; font-weight: bold; }
.pay-date { color: #909399; font-size: 12px; }
</style>