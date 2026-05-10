<template>
  <div class="market-container">
    <el-row :gutter="20">
      <el-col :span="6" v-for="room in availableRooms" :key="room.id" style="margin-bottom: 20px;">
        <el-card :body-style="{ padding: '0px' }" class="room-card" shadow="hover">
          <img src="https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?q=80&w=400" class="room-img" />
          <div style="padding: 14px">
            <div class="room-info">
              <span class="room-no">{{ room.buildingNo }}号楼 - {{ room.roomNo }}</span>
              <el-tag type="success" size="small">待出租</el-tag>
            </div>
            <div class="price-box">
              <span class="price">￥{{ room.rentPrice }}</span>
              <span class="unit">/月</span>
            </div>
            <div style="margin-top: 15px;">
              <el-button type="primary" style="width: 100%" @click="openApplyDialog(room)">发起租赁申请</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="dialogVisible" title="房源租赁申请" width="400px">
      <el-form :model="applyForm" label-width="80px">
        <el-form-item label="目标房源">
          <el-input :value="selectedRoomName" disabled />
        </el-form-item>
        <el-form-item label="起租日期">
          <el-date-picker 
            v-model="applyForm.startDate" 
            type="date" 
            placeholder="选择起租日期" 
            value-format="YYYY-MM-DD"
            style="width: 100%;"
          />
        </el-form-item>
        <el-form-item label="租赁时长">
          <el-select v-model="applyForm.leaseMonths" style="width: 100%;">
            <el-option v-for="n in 12" :key="n" :label="n + '个月'" :value="n" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="success" @click="submitApply">确认提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'

const availableRooms = ref([])
const dialogVisible = ref(false)
const selectedRoomName = ref('')

// 初始化申请表单数据模型
const applyForm = reactive({
  userId: localStorage.getItem('user_id'),
  roomId: null,
  startDate: '',
  leaseMonths: 1
})

// 加载空闲房源列表
const loadRooms = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/rooms')
    // 筛选业务状态为“空闲（0）”的房源记录
    availableRooms.value = res.data.filter(r => r.status === 0)
  } catch (error) {
    ElMessage.error('获取房源列表失败')
  }
}

onMounted(() => loadRooms())

// 唤起申请录入弹窗
const openApplyDialog = (room) => {
  applyForm.roomId = room.id
  selectedRoomName.value = `${room.buildingNo}号楼 - ${room.roomNo}`
  dialogVisible.value = true
}

// 提交租赁申请业务逻辑
const submitApply = async () => {
  if (!applyForm.startDate) {
    return ElMessage.warning('请选择预期的起租日期')
  }
  try {
    await axios.post('http://localhost:8080/api/contracts/apply', applyForm)
    ElMessage.success('申请提交成功，请等待系统审核')
    dialogVisible.value = false
    loadRooms() // 提交后刷新房源列表状态
  } catch (error) {
    ElMessage.error('申请提交异常')
  }
}
</script>

<style scoped>
.room-card { border-radius: 8px; overflow: hidden; }
.room-img { width: 100%; height: 160px; object-fit: cover; }
.room-info { display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px; }
.room-no { font-weight: bold; font-size: 16px; color: #303133; }
.price-box { color: #f56c6c; margin-top: 5px; }
.price { font-size: 22px; font-weight: bold; }
.unit { font-size: 14px; color: #909399; }
</style>