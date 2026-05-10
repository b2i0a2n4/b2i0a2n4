<template>
  <div class="community-container">
    <el-card shadow="never" class="post-card">
      <template #header>
        <div class="header-title">公寓社区信息交流平台</div>
      </template>

      <div class="publish-area">
        <el-input
          v-model="newPost"
          type="textarea"
          :rows="3"
          placeholder="请输入您要分享的内容或建议..."
          maxlength="200"
          show-word-limit
        />
        <div class="publish-action">
          <el-button type="primary" @click="submitPost" :disabled="!newPost.trim()">
            发布留言
          </el-button>
        </div>
      </div>

      <el-divider />

      <div class="post-list" v-loading="loading">
        <div v-if="postList.length === 0" class="empty-state">
          暂无留言记录
        </div>
        
        <div v-for="post in postList" :key="post.id" class="post-item">
          <div class="post-avatar">
            <el-avatar :style="{ background: post.userRole === 0 ? '#f56c6c' : '#409eff' }">
              {{ post.userName.charAt(0) }}
            </el-avatar>
          </div>
          <div class="post-content-area">
            <div class="post-meta">
              <span class="user-name">{{ post.userName }}</span>
              <el-tag size="small" :type="post.userRole === 0 ? 'danger' : 'success'" class="role-tag">
                {{ post.userRole === 0 ? '管理员' : '租客' }}
              </el-tag>
              <span class="post-time">{{ post.createTime }}</span>
            </div>
            <div class="post-text">
              {{ post.content }}
            </div>
            
            <div class="post-actions">
              <el-button 
                v-if="currentUserRole === '0'" 
                type="primary" link size="small" 
                @click="openEditDialog(post)"
              >编辑</el-button>
              
              <el-button 
                v-if="currentUserRole === '0' || String(post.userId) === String(currentUserId)" 
                type="danger" link size="small" 
                @click="handleDelete(post.id)"
              >删除</el-button>
            </div>
          </div>
        </div>
      </div>
    </el-card>

    <el-dialog v-model="editDialogVisible" title="编辑留言内容" width="500px">
      <el-input v-model="editForm.content" type="textarea" :rows="4" />
      <template #footer>
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitEdit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'

const currentUserRole = ref('')
const currentUserId = ref('')
const newPost = ref('')
const postList = ref([])
const loading = ref(false)

const editDialogVisible = ref(false)
const editForm = ref({ id: null, content: '' })

// 获取全量留言列表
const loadPosts = async () => {
  loading.value = true
  try {
    const res = await axios.get('http://localhost:8080/api/posts/list')
    postList.value = res.data
  } catch (error) {
    ElMessage.error('获取留言列表异常')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  // 初始化用户信息与权限标识
  currentUserRole.value = localStorage.getItem('user_role') || '1'
  currentUserId.value = localStorage.getItem('user_id') || ''
  loadPosts()
})

// 提交新增留言
const submitPost = async () => {
  if (!newPost.value.trim()) return
  try {
    await axios.post('http://localhost:8080/api/posts/add', {
      userId: currentUserId.value,
      content: newPost.value
    })
    ElMessage.success('留言发布成功')
    newPost.value = '' 
    loadPosts() 
  } catch (error) {
    ElMessage.error('留言发布失败')
  }
}

// 唤起编辑弹窗
const openEditDialog = (post) => {
  editForm.value = { id: post.id, content: post.content }
  editDialogVisible.value = true
}

// 提交编辑更新
const submitEdit = async () => {
  try {
    await axios.put('http://localhost:8080/api/posts/update', editForm.value)
    ElMessage.success('信息更新成功')
    editDialogVisible.value = false
    loadPosts()
  } catch (error) {
    ElMessage.error('更新操作失败')
  }
}

// 删除留言记录
const handleDelete = (id) => {
  ElMessageBox.confirm('确认删除该条留言记录吗？', '系统提示', {
    type: 'warning',
    confirmButtonText: '确定',
    cancelButtonText: '取消'
  }).then(async () => {
    try {
      await axios.delete(`http://localhost:8080/api/posts/${id}`)
      ElMessage.success('记录已删除')
      loadPosts()
    } catch (error) {
      ElMessage.error('删除操作异常')
    }
  }).catch(() => {})
}
</script>

<style scoped>
.community-container { max-width: 800px; margin: 0 auto; }
.header-title { font-size: 18px; font-weight: bold; color: #fff; }
.publish-action { margin-top: 10px; text-align: right; }
.post-list { min-height: 200px; }
.empty-state { text-align: center; color: rgba(255, 255, 255, 0.7); padding: 40px 0; }
.post-item { display: flex; margin-bottom: 20px; padding-bottom: 15px; border-bottom: 1px solid rgba(255, 255, 255, 0.1); }
.post-item:last-child { border-bottom: none; }
.post-content-area { margin-left: 15px; flex: 1; }
.post-meta { margin-bottom: 8px; display: flex; align-items: center; }
.user-name { font-weight: bold; font-size: 15px; margin-right: 8px; color: #fff; }
.role-tag { margin-right: 10px; }
.post-time { font-size: 12px; color: rgba(255, 255, 255, 0.6); }
.post-text { font-size: 14px; color: rgba(255, 255, 255, 0.9); line-height: 1.6; }
.post-actions { margin-top: 8px; text-align: right; }
</style>