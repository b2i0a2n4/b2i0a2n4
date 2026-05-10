<template>
  <div class="service-container">
    <el-card v-if="userRole === '1'" shadow="hover" style="margin-bottom: 20px;">
      <template #header>
        <span style="font-weight: bold; font-size: 16px;">提交综合服务工单</span>
      </template>
      <el-form :inline="true" :model="form" class="demo-form-inline">
        <el-form-item label="工单类型">
          <el-select v-model="form.type" placeholder="选择类型" style="width: 140px">
            <el-option label="故障报修" value="故障报修" />
            <el-option label="保洁服务" value="保洁服务" />
            <el-option label="看房预约" value="看房预约" />
            <el-option label="设备巡查" value="设备巡查" />
          </el-select>
        </el-form-item>
        <el-form-item label="关联房间">
          <el-select v-model="form.roomId" placeholder="选择房间" style="width: 140px">
            <el-option v-for="r in roomList" :key="r.id" :label="r.buildingNo + '-' + r.roomNo" :value="r.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="期望时间">
          <el-date-picker 
            v-model="form.appointmentTime" 
            type="datetime" 
            placeholder="选择日期时间" 
            value-format="YYYY-MM-DD HH:mm:ss" 
            style="width: 190px" 
          />
        </el-form-item>
        <el-form-item label="需求说明">
          <el-input v-model="form.content" placeholder="请输入具体需求详细说明" style="width: 200px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitService">提交工单</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card shadow="hover">
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span style="font-weight: bold; font-size: 16px;">
            {{ userRole === '0' ? '综合服务处理中心' : '个人工单进度' }}
          </span>
          <el-button size="small" type="primary" plain @click="loadData">刷新数据</el-button>
        </div>
      </template>
      
      <el-table :data="tableData" border stripe style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="单号" width="70" />
        <el-table-column prop="userName" label="申请人" width="100" v-if="userRole === '0'" />
        <el-table-column prop="type" label="工单类型" width="130">
          <template #default="scope">
            <el-tag :type="scope.row.type === '故障报修' ? 'danger' : 'info'" effect="plain">
              {{ scope.row.type }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="roomStr" label="关联房间" width="120" />
        <el-table-column prop="content" label="需求说明" show-overflow-tooltip />
        <el-table-column prop="appointmentTime" label="期望时间" width="170" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag v-if="scope.row.status === 0" type="warning" effect="dark">待接单</el-tag>
            <el-tag v-else-if="scope.row.status === 1" type="primary" effect="dark">处理中</el-tag>
            <el-tag v-else type="success" effect="dark">已完成</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <el-button v-if="userRole === '0' && scope.row.status === 0" size="small" type="primary" @click="changeStatus(scope.row.id, 1)">接单</el-button>
            <el-button v-if="userRole === '0' && scope.row.status === 1" size="small" type="success" @click="changeStatus(scope.row.id, 2)">完工</el-button>
            <el-button size="small" type="danger" plain @click="handleDelete(scope.row.id)">删除</el-button>
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

const userRole = ref('')
const userId = ref('')
const tableData = ref([])
const roomList = ref([])
const loading = ref(false)

const form = ref({ type: '', roomId: null, appointmentTime: '', content: '' })

onMounted(async () => {
  userRole.value = localStorage.getItem('user_role') || '1'
  userId.value = localStorage.getItem('user_id')
  loadData()
  if (userRole.value === '1') {
    try {
      const res = await axios.get('http://localhost:8080/api/rooms')
      roomList.value = res.data
    } catch(e) { console.error('房间数据获取异常') }
  }
})

// 加载工单数据列表
const loadData = async () => {
  loading.value = true
  try {
    const url = userRole.value === '0' 
      ? 'http://localhost:8080/api/services/list' 
      : `http://localhost:8080/api/services/my?userId=${userId.value}`
    const res = await axios.get(url)
    tableData.value = res.data
  } catch (error) { 
    ElMessage.error('数据加载失败') 
  } finally { 
    loading.value = false 
  }
}

// 提交新增工单
const submitService = async () => {
  if (!form.value.type || !form.value.roomId || !form.value.appointmentTime) {
    return ElMessage.warning('请完善工单必要信息')
  }
  try {
    await axios.post('http://localhost:8080/api/services/add', { ...form.value, userId: userId.value })
    ElMessage.success('工单提交成功')
    form.value = { type: '', roomId: null, appointmentTime: '', content: '' }
    loadData()
  } catch (error) { 
    ElMessage.error('服务提交异常') 
  }
}

// 变更工单业务状态
const changeStatus = async (id, status) => {
  try {
    await axios.put(`http://localhost:8080/api/services/status?id=${id}&status=${status}`)
    ElMessage.success('状态更新成功')
    loadData()
  } catch (error) { 
    ElMessage.error('操作执行失败') 
  }
}

// 删除指定工单记录
const handleDelete = (id) => {
  ElMessageBox.confirm('确认删除该条服务记录吗？', '提示', { type: 'warning' }).then(async () => {
    try {
      await axios.delete(`http://localhost:8080/api/services/${id}`)
      ElMessage.success('删除成功')
      loadData()
    } catch (error) { 
      ElMessage.error('删除操作异常') 
    }
  }).catch(() => {})
}
</script>

<style scoped>
.service-container { max-width: 100%; }
</style>