<template>
  <div>
    <div style="margin-bottom: 20px;">
      <el-button type="primary" @click="handleAdd">新增房源信息</el-button>
    </div>

    <el-card shadow="hover">
      <el-table :data="tableData" border stripe style="width: 100%">
        <el-table-column prop="id" label="房源编号" width="100" />
        <el-table-column prop="buildingNo" label="楼宇编号" />
        <el-table-column prop="roomNo" label="房间号" />
        <el-table-column prop="capacity" label="核定床位" />
        <el-table-column prop="rentPrice" label="租金标准 (元/月)" />
        <el-table-column prop="status" label="当前状态">
          <template #default="scope">
            <el-tag v-if="scope.row.status === 0" type="success">空闲</el-tag>
            <el-tag v-else-if="scope.row.status === 1" type="warning">部分入住</el-tag>
            <el-tag v-else-if="scope.row.status === 2" type="danger">满房</el-tag>
          </template>
        </el-table-column>
        
        <el-table-column label="操作" width="150">
          <template #default="scope">
            <el-button size="small" type="primary" link @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" link @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="400px">
      <el-form :model="roomForm" label-width="100px">
        <el-form-item label="楼宇编号">
          <el-input v-model="roomForm.buildingNo" placeholder="请输入楼宇信息" />
        </el-form-item>
        <el-form-item label="房间号">
          <el-input v-model="roomForm.roomNo" placeholder="请输入房间编号" />
        </el-form-item>
        <el-form-item label="核定床位">
          <el-input-number v-model="roomForm.capacity" :min="1" :max="10" style="width: 100%" />
        </el-form-item>
        <el-form-item label="租金标准">
          <el-input v-model="roomForm.rentPrice" placeholder="请输入金额" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue'
import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus' 

const tableData = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')

// 房源表单模型
const roomForm = reactive({
  id: null,
  buildingNo: '',
  roomNo: '',
  capacity: 4,
  rentPrice: ''
})

// 获取全量房源列表数据
const loadRooms = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/rooms')
    tableData.value = res.data
  } catch (error) {
    ElMessage.error('数据加载异常')
  }
}

onMounted(() => { loadRooms() })

// 触发新增流程
const handleAdd = () => {
  dialogTitle.value = '新增房源信息'
  // 重置表单状态，ID置空判定为新增操作
  Object.assign(roomForm, { id: null, buildingNo: '', roomNo: '', capacity: 4, rentPrice: '' })
  dialogVisible.value = true
}

// 触发编辑流程
const handleEdit = (row) => {
  dialogTitle.value = '修改房源信息'
  // 同步行数据至表单模型
  Object.assign(roomForm, row)
  dialogVisible.value = true
}

// 提交房源信息表单
const submitForm = async () => {
  try {
    if (roomForm.id) {
      // 执行更新逻辑
      await axios.put('http://localhost:8080/api/rooms', roomForm)
      ElMessage.success('房源信息已更新')
    } else {
      // 执行持久化逻辑
      await axios.post('http://localhost:8080/api/rooms', roomForm)
      ElMessage.success('新增房源成功')
    }
    dialogVisible.value = false
    loadRooms()
  } catch (error) {
    ElMessage.error('操作执行失败，请检查后端响应')
  }
}

// 执行房源记录物理删除
const handleDelete = (row) => {
  ElMessageBox.confirm(`确认删除楼宇 ${row.buildingNo} 房间 ${row.roomNo} 的房源记录吗？`, '系统警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(async () => {
    try {
      await axios.delete(`http://localhost:8080/api/rooms/${row.id}`)
      ElMessage.success('记录已删除')
      loadRooms()
    } catch (error) {
      ElMessage.error('删除操作异常')
    }
  }).catch(() => {})
}
</script>