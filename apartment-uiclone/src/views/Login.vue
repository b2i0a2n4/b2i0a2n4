<template>
  <div class="login-wrapper">
    <div class="glass-card">
      <div class="login-header">
        <h2 class="title">公寓管理系统</h2>
        <p class="subtitle">欢迎回来，请登录您的账号</p>
      </div>
      
      <el-form :model="loginForm" class="login-form" label-position="top">
        <el-form-item label="账号">
          <el-input 
            v-model="loginForm.username" 
            placeholder="请输入账号或手机号" 
            size="large"
            :prefix-icon="User"
            class="custom-input"
          />
        </el-form-item>
        
        <el-form-item label="密码">
          <el-input 
            v-model="loginForm.password" 
            type="password" 
            placeholder="请输入登录密码" 
            size="large"
            show-password 
            :prefix-icon="Lock"
            class="custom-input"
            @keyup.enter="handleLogin"
          />
        </el-form-item>

        <div class="form-options">
          <el-checkbox v-model="rememberMe" class="custom-checkbox">记住账号</el-checkbox>
          <el-link type="primary" :underline="false" class="forgot-link">忘记密码？</el-link>
        </div>

        <el-button type="primary" @click="handleLogin" size="large" class="submit-btn" :loading="loading">
          登 录
        </el-button>

        <div class="register-prompt">
          还没有账号？ <el-link type="primary" :underline="false" @click="$router.push('/register')">立即注册</el-link>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { User, Lock } from '@element-plus/icons-vue'

const router = useRouter()
const rememberMe = ref(true) // 持久化账号信息标识
const loading = ref(false)

const loginForm = reactive({
  username: '',
  password: ''
})

onMounted(() => {
  // 初始化读取本地存储的账号信息
  const savedUsername = localStorage.getItem('saved_username')
  if (savedUsername) {
    loginForm.username = savedUsername
  }
})

// 执行用户身份验证逻辑
const handleLogin = async () => {
  if (!loginForm.username || !loginForm.password) {
    return ElMessage.warning('请输入完整的身份认证信息')
  }

  loading.value = true
  try {
    const res = await axios.post('http://localhost:8080/api/users/login', loginForm)
    const resData = res.data

    if (resData.success) {
      ElMessage.success('登录成功')

      // 持久化用户信息与访问凭证
      localStorage.setItem('apartment_token', resData.token)
      localStorage.setItem('user_id', resData.user.id)
      localStorage.setItem('user_role', resData.user.role)
      localStorage.setItem('user_name', resData.user.realName)

      // 处理账号自动填充逻辑
      if (rememberMe.value) {
        localStorage.setItem('saved_username', loginForm.username)
      } else {
        localStorage.removeItem('saved_username')
      }

      // 重定向至系统主页
      router.push('/layout')
    } else {
      ElMessage.error(resData.message || '身份校验失败')
    }
  } catch (error) {
    ElMessage.error('系统连接异常，请检查网络或后端状态')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap');

.login-wrapper {
  height: 100vh;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}

.glass-card {
  position: relative;
  z-index: 2;
  width: 100%;
  max-width: 440px;
  padding: 40px;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(16px);
  -webkit-backdrop-filter: blur(16px);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 24px;
  box-shadow: 0 8px 32px 0 rgba(0, 0, 0, 0.3);
  animation: slideUpFadeIn 0.8s cubic-bezier(0.16, 1, 0.3, 1) forwards;
  color: #fff;
}

@keyframes slideUpFadeIn {
  from {
    opacity: 0;
    transform: translateY(40px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.login-header {
  text-align: center;
  margin-bottom: 36px;
}

.title {
  margin: 0 0 10px 0;
  font-size: 28px;
  font-weight: 700;
  letter-spacing: 0.5px;
  color: #ffffff;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
}

.subtitle {
  margin: 0;
  font-size: 14px;
  color: rgba(255, 255, 255, 0.8);
}

.login-form {
  width: 100%;
}

:deep(.el-form-item__label) {
  color: rgba(255, 255, 255, 0.9) !important;
  font-weight: 500;
  font-size: 14px;
  padding-bottom: 6px;
}

:deep(.el-input__wrapper) {
  background-color: rgba(255, 255, 255, 0.1) !important;
  box-shadow: none !important;
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 12px;
  transition: all 0.3s ease;
}

:deep(.el-input__wrapper.is-focus),
:deep(.el-input__wrapper:hover) {
  background-color: rgba(255, 255, 255, 0.15) !important;
  border-color: rgba(255, 255, 255, 0.5) !important;
  box-shadow: 0 0 0 2px rgba(255, 255, 255, 0.1) !important;
}

:deep(.el-input__inner) {
  color: #fff !important;
}

:deep(.el-input__inner::placeholder) {
  color: rgba(255, 255, 255, 0.5) !important;
}

:deep(.el-input__prefix-inner) {
  color: rgba(255, 255, 255, 0.7);
}

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 16px 0 24px;
}

.custom-checkbox {
  --el-checkbox-text-color: rgba(255, 255, 255, 0.9);
}

:deep(.el-checkbox__label) {
  color: rgba(255, 255, 255, 0.9) !important;
}

.forgot-link {
  color: rgba(255, 255, 255, 0.8) !important;
  font-size: 14px;
  transition: color 0.3s;
}

.forgot-link:hover {
  color: #ffffff !important;
}

.submit-btn {
  width: 100%;
  border-radius: 12px;
  font-weight: 600;
  font-size: 16px;
  letter-spacing: 1px;
  padding: 22px 0;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  box-shadow: 0 4px 15px rgba(118, 75, 162, 0.4);
  transition: transform 0.2s, box-shadow 0.2s;
}

.submit-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(118, 75, 162, 0.6);
  background: linear-gradient(135deg, #768df2 0%, #8b5dbf 100%);
}

.submit-btn:active {
  transform: translateY(0);
}

.register-prompt {
  text-align: center;
  margin-top: 24px;
  font-size: 14px;
  color: rgba(255, 255, 255, 0.7);
}

.register-prompt .el-link {
  color: #a3bffa !important;
  font-weight: 500;
  margin-left: 4px;
}

.register-prompt .el-link:hover {
  color: #ffffff !important;
}
</style>