<template>
  <div>
    <div style="margin-bottom: 20px;">
      <el-button type="primary" size="large" @click="openDialog"> 💰 生成新账单</el-button>
    </div>

    <el-card>
      <el-table :data="tableData" border stripe style="width: 100%">
        <el-table-column prop="id" label="账单号" width="80" />
        <el-table-column prop="tenantName" label="租客姓名" width="100" />
        <el-table-column prop="roomNo" label="房间号" width="100" />
        <el-table-column prop="billName" label="账单名目" />
        <el-table-column prop="amount" label="金额(元)" width="100">
          <template #default="scope">
            <span style="color: #f56c6c; font-weight: bold;">￥{{ scope.row.amount }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="支付状态" width="100">
          <template #default="scope">
            <el-tag v-if="scope.row.status === 0" type="danger">待支付</el-tag>
            <el-tag v-else type="success">已支付</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="payTime" label="支付时间" width="180" />
        
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button 
              v-if="scope.row.status === 0" 
              size="small" 
              type="success" 
              @click="handlePay(scope.row)"
            >模拟收款</el-button>
            <el-button 
            size="small" 
            type="danger" 
            plain
            @click="handleDelete(scope.row)"
            >删除</el-button>
            <span v-if="scope.row.status === 1" style="color: #909399; font-size: 13px; margin-left:10px">交易完成</span>
           </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" title="开具新账单" width="450px">
      <el-form :model="billForm" label-width="100px">
        
        <el-form-item label="选择合同">
          <el-select v-model="billForm.contractId" placeholder="请选择要收费的租客合同" style="width: 100%;">
            <el-option 
              v-for="contract in contractList" 
              :key="contract.id" 
              :label="contract.tenantName + ' (' + contract.buildingNo + '-' + contract.roomNo + ')'" 
              :value="contract.id" 
            />
          </el-select>
        </el-form-item>

        <el-form-item label="账单名目">
          <el-input v-model="billForm.billName" placeholder="如: 2026年4月水费、押金等" />
        </el-form-item>

        <el-form-item label="收费金额">
          <el-input-number v-model="billForm.amount" :min="0.01" :precision="2" :step="10" style="width: 100%;" />
        </el-form-item>

      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitBill">生 成 账 单</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'

const dialogVisible = ref(false)
const tableData = ref([])
const contractList = ref([]) // 存放合同下拉框数据

const billForm = reactive({
  contractId: null,
  billName: '',
  amount: 0
})

// 加载账单列表
const loadBills = async () => {
  const res = await axios.get('http://localhost:8080/api/bills/list')
  tableData.value = res.data
}

onMounted(() => {
  loadBills()
})

// 点击“生成账单”打开弹窗，顺便拉取合同列表
const openDialog = async () => {
  const res = await axios.get('http://localhost:8080/api/contracts/list')
  // 只过滤出生效中的合同（status === 0）
  contractList.value = res.data.filter(c => c.status === 0)
  
  Object.assign(billForm, { contractId: null, billName: '', amount: 100 })
  dialogVisible.value = true
}

// 提交生成账单
const submitBill = async () => {
  if (!billForm.contractId || !billForm.billName) {
    ElMessage.warning('请将账单信息填写完整')
    return
  }
  try {
    await axios.post('http://localhost:8080/api/bills/generate', billForm)
    ElMessage.success('账单已成功下发给租客！')
    dialogVisible.value = false
    loadBills()
  } catch (error) {
    ElMessage.error('生成账单失败')
  }
}

// 模拟收款核心逻辑
const handlePay = (row) => {
  ElMessageBox.confirm(
    `请提醒租客扫码支付 ￥${row.amount}，确认已收到款项吗？`,
    '模拟扫码收款',
    {
      confirmButtonText: '已收到款项',
      cancelButtonText: '未收到',
      type: 'success',
      center: true
    }
  ).then(async () => {
    try {
      // 呼叫后端支付接口
      await axios.put(`http://localhost:8080/api/bills/pay/${row.id}`)
      ElMessage.success(`收款成功！已进账 ￥${row.amount}`)
      loadBills() // 刷新列表，状态变成已支付
    } catch (error) {
      ElMessage.error('收款网络异常')
    }
  }).catch(() => {})
}

const handleDelete = (row) => {
  ElMessageBox.confirm(`确定要删除此笔账单吗？删除后不可恢复。`, '警告', {
    confirmButtonText: '确定删除',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(async () => {
    try {
      await axios.delete(`http://localhost:8080/api/bills/${row.id}`)
      ElMessage.success('账单已删除')
      loadBills() // 刷新列表
    } catch (error) {
      ElMessage.error('删除失败')
    }
  }).catch(() => {})
}

</script>