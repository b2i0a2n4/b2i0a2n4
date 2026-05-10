<template>
  <div>
    <div style="margin-bottom: 20px;">
      <el-button type="primary" @click="handleAdd">新增租客信息</el-button>
    </div>

    <el-card shadow="hover">
      <el-table :data="tableData" border stripe style="width: 100%">
        <el-table-column prop="id" label="用户编号" width="100" />
        <el-table-column prop="username" label="登录账号" />
        <el-table-column prop="realName" label="真实姓名" />
        <el-table-column prop="phone" label="联系电话" />
        <el-table-column prop="createTime" label="注册时间" width="180" />
        <el-table-column label="操作" width="150">
          <template #default="scope">
            <el-button size="small" type="primary" link @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" link @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="400px">
      <el-form :model="userForm" label-width="100px">
        <el-form-item label="登录账号">
          <el-input v-model="userForm.username" placeholder="请输入登录账号" />
        </el-form-item>
        <el-form-item label="初始密码" v-if="!userForm.id">
          <el-input v-model="userForm.password" type="password" placeholder="请输入登录密码" show-password />
        </el-form-item>
        <el-form-item label="真实姓名">
          <el-input v-model="userForm.realName" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="userForm.phone" placeholder="请输入手机号" />
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

// 用户信息表单模型
const userForm = reactive({
  id: null,
  username: '',
  password: '',
  realName: '',
  phone: ''
})

// 获取全量租客数据列表
const loadUsers = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/users')
    tableData.value = res.data
  } catch (error) {
    ElMessage.error('用户数据加载异常')
  }
}

onMounted(() => { loadUsers() })

// 触发新增流程
const handleAdd = () => {
  dialogTitle.value = '新增租客信息'
  // 重置表单并设置默认初始密码
  Object.assign(userForm, { id: null, username: '', password: '123456', realName: '', phone: '' })
  dialogVisible.value = true
}

// 触发编辑流程
const handleEdit = (row) => {
  dialogTitle.value = '修改租客信息'
  Object.assign(userForm, row)
  dialogVisible.value = true
}

// 提交用户信息表单
const submitForm = async () => {
  try {
    if (userForm.id) {
      // 执行更新逻辑
      await axios.put('http://localhost:8080/api/users', userForm)
      ElMessage.success('用户信息更新成功')
    } else {
      // 执行新增逻辑
      await axios.post('http://localhost:8080/api/users', userForm)
      ElMessage.success('租客账号创建成功')
    }
    dialogVisible.value = false
    loadUsers()
  } catch (error) {
    ElMessage.error('操作执行失败')
  }
}

// 执行用户账号物理删除
const handleDelete = (row) => {
  ElMessageBox.confirm(`确认删除租客 ${row.realName} 的账号信息吗？此操作不可撤销。`, '系统警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(async () => {
    try {
      await axios.delete(`http://localhost:8080/api/users/${row.id}`)
      ElMessage.success('账号已成功删除')
      loadUsers()
    } catch (error) {
      ElMessage.error('删除操作异常')
    }
  }).catch(() => {})
}
</script>