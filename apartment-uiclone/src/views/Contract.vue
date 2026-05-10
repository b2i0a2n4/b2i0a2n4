<template>
  <div>
    <div style="margin-bottom: 20px;">
      <el-button type="success" @click="openDialog">线下办理入住</el-button>
    </div>

    <el-card shadow="hover">
      <el-table :data="tableData" border stripe style="width: 100%">
        <el-table-column prop="id" label="合同编号" width="90" />
        <el-table-column prop="tenantName" label="租客姓名" />
        <el-table-column prop="tenantPhone" label="联系电话" width="130" />
        <el-table-column label="分配房源">
          <template #default="scope">
            {{ scope.row.buildingNo }} - {{ scope.row.roomNo }}
          </template>
        </el-table-column>
        <el-table-column prop="startDate" label="起租日期" width="120" />
        <el-table-column prop="endDate" label="到期日期" width="120" />
        
        <el-table-column prop="status" label="业务状态" width="100">
          <template #default="scope">
            <el-tag v-if="scope.row.status === 0" type="success">生效中</el-tag>
            <el-tag v-else-if="scope.row.status === 1" type="info">已终止</el-tag>
            <el-tag v-else-if="scope.row.status === 2" type="warning" effect="dark">待审批</el-tag>
          </template>
        </el-table-column>
        
        <el-table-column label="操作" width="160">
          <template #default="scope">
            <el-button 
              v-if="scope.row.status === 2" 
              size="small" 
              type="warning" 
              @click="handleApprove(scope.row.id)"
            >审批通过</el-button>

            <el-button 
              v-if="scope.row.status === 0"
              size="small" 
              type="danger" 
              plain
              @click="handleDelete(scope.row)"
            >终止合同</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" title="办理入住手续" width="500px">
      <el-form :model="contractForm" label-width="100px">
        <el-form-item label="承租人">
          <el-select v-model="contractForm.userId" placeholder="请选择租客" style="width: 100%;">
            <el-option v-for="user in userList" :key="user.id" :label="user.realName + ' (' + user.phone + ')'" :value="user.id" />
          </el-select>
        </el-form-item>

        <el-form-item label="分配房源">
          <el-select v-model="contractForm.roomId" placeholder="请选择可用房间" style="width: 100%;">
            <el-option v-for="room in availableRooms" :key="room.id" :label="room.buildingNo + ' - ' + room.roomNo + ' (￥' + room.rentPrice + '/月)'" :value="room.id" />
          </el-select>
        </el-form-item>

        <el-form-item label="起租日期">
          <el-date-picker 
            v-model="contractForm.startDate" 
            type="date" 
            placeholder="选择日期" 
            value-format="YYYY-MM-DD" 
            style="width: 100%;" 
            @change="calculateEndDate" 
          />
        </el-form-item>

        <el-form-item label="租赁周期">
          <el-select v-model="contractForm.leaseMonths" placeholder="选择租赁时长" style="width: 100%;" @change="calculateEndDate">
            <el-option v-for="n in 12" :key="n" :label="n + ' 个月'" :value="n" />
          </el-select>
        </el-form-item>

        <el-form-item label="到期日期">
          <el-input v-model="contractForm.endDate" disabled placeholder="自动计算" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitCheckIn">确认办理</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'

const dialogVisible = ref(false)
const userList = ref([])
const roomList = ref([])
const tableData = ref([])

const contractForm = reactive({
  userId: null,
  roomId: null,
  startDate: '',
  leaseMonths: 1, 
  endDate: ''
})

// 计算属性：筛选当前状态为空闲的房源
const availableRooms = computed(() => roomList.value.filter(room => room.status === 0))

// 获取全量合同列表
const loadContracts = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/contracts/list')
    tableData.value = res.data
  } catch (error) {
    ElMessage.error('列表数据获取失败')
  }
}

onMounted(() => loadContracts())

// 初始化入住办理对话框数据
const openDialog = async () => {
  try {
    const userRes = await axios.get('http://localhost:8080/api/users')
    userList.value = userRes.data
    const roomRes = await axios.get('http://localhost:8080/api/rooms')
    roomList.value = roomRes.data
    Object.assign(contractForm, { userId: null, roomId: null, startDate: '', leaseMonths: 1, endDate: '' })
    dialogVisible.value = true
  } catch (error) {
    ElMessage.error('基础数据加载异常')
  }
}

// 自动计算合同结束日期
const calculateEndDate = () => {
  if (contractForm.startDate && contractForm.leaseMonths) {
    const start = new Date(contractForm.startDate)
    start.setMonth(start.getMonth() + contractForm.leaseMonths)
    const year = start.getFullYear()
    const month = String(start.getMonth() + 1).padStart(2, '0')
    const day = String(start.getDate()).padStart(2, '0')
    contractForm.endDate = `${year}-${month}-${day}`
  }
}

// 提交线下办理入住请求
const submitCheckIn = async () => {
  if (!contractForm.userId || !contractForm.roomId || !contractForm.startDate) {
    return ElMessage.warning('请完善必填的合同信息')
  }
  try {
    await axios.post('http://localhost:8080/api/contracts/checkIn', contractForm)
    ElMessage.success('入住手续办理完成')
    dialogVisible.value = false
    loadContracts() 
  } catch (error) {
    ElMessage.error('办理操作失败')
  }
}

// 审批线上租房申请
const handleApprove = async (id) => {
  try {
    await axios.put(`http://localhost:8080/api/contracts/${id}/approve`)
    ElMessage.success('审批已通过，相关财务账单已自动生成')
    loadContracts()
  } catch (error) {
    ElMessage.error('审批接口调用异常')
  }
}

// 终止租赁合同并执行资源回收
const handleDelete = (row) => {
  ElMessageBox.confirm(
    '确认终止该租赁合同吗？操作后将同步清理未支付账单并释放房源。',
    '系统警告',
    {
      confirmButtonText: '确认终止',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    try {
      await axios.delete(`http://localhost:8080/api/contracts/${row.id}`)
      ElMessage.success('合同已终止，资源释放成功')
      loadContracts()
    } catch (error) {
      ElMessage.error('合同撤销失败')
    }
  }).catch(() => {})
}
</script>