<template>
  <div class="register-wrapper">
    <div class="glass-card">
      <div class="register-header">
        <h2 class="title">租客用户注册</h2>
        <p class="subtitle">填写以下信息加入我们</p>
      </div>
      
      <el-form :model="regForm" class="register-form" label-position="top">
        <el-form-item label="手机号">
          <el-input 
            v-model="regForm.username" 
            placeholder="请输入手机号" 
            size="large"
            class="custom-input"
          />
        </el-form-item>
        
        <el-form-item label="登录密码">
          <el-input 
            v-model="regForm.password" 
            type="password" 
            placeholder="请输入密码" 
            size="large"
            show-password 
            class="custom-input"
          />
        </el-form-item>
        
        <el-form-item label="真实姓名">
          <el-input 
            v-model="regForm.realName" 
            placeholder="请输入真实姓名" 
            size="large"
            class="custom-input"
          />
        </el-form-item>

        <el-button type="primary" @click="handleRegister" size="large" class="submit-btn" style="margin-top: 10px;">
          立即注册
        </el-button>
        
        <div class="login-prompt">
          已有账号？ <el-link type="primary" :underline="false" @click="$router.push('/login')">返回登录</el-link>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

const router = useRouter()
const regForm = reactive({ 
  username: '', 
  password: '', 
  realName: '', 
  phone: '' 
})

// 执行用户注册业务逻辑
const handleRegister = async () => {
  if (!regForm.username || !regForm.password || !regForm.realName) {
    return ElMessage.warning('请完善注册信息')
  }

  // 默认手机号字段与账号一致
  regForm.phone = regForm.username 
  
  try {
    const res = await axios.post('http://localhost:8080/api/users/register', regForm)
    if (res.data === '注册成功，请登录') {
      ElMessage.success(res.data)
      router.push('/login')
    } else {
      ElMessage.error(res.data || '注册失败，请检查输入')
    }
  } catch (error) {
    ElMessage.error('系统接口调用异常')
  }
}
</script>

<style scoped>
.register-wrapper {
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
  from { opacity: 0; transform: translateY(40px); }
  to { opacity: 1; transform: translateY(0); }
}

.register-header {
  text-align: center;
  margin-bottom: 30px;
}

.title {
  margin: 0 0 10px 0;
  font-size: 28px;
  font-weight: 700;
  color: #ffffff;
}

.subtitle {
  margin: 0;
  font-size: 14px;
  color: rgba(255, 255, 255, 0.8);
}

.register-form { width: 100%; }

:deep(.el-form-item__label) {
  color: rgba(255, 255, 255, 0.9) !important;
  font-weight: 500;
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
}

:deep(.el-input__inner) { color: #fff !important; }
:deep(.el-input__inner::placeholder) { color: rgba(255, 255, 255, 0.5) !important; }

.submit-btn {
  width: 100%;
  border-radius: 12px;
  font-weight: 600;
  font-size: 16px;
  padding: 22px 0;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  box-shadow: 0 4px 15px rgba(118, 75, 162, 0.4);
  transition: transform 0.2s, box-shadow 0.2s;
}

.submit-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(118, 75, 162, 0.6);
}

.login-prompt {
  text-align: center;
  margin-top: 24px;
  font-size: 14px;
  color: rgba(255, 255, 255, 0.7);
}

.login-prompt .el-link {
  color: #a3bffa !important;
  font-weight: 500;
  margin-left: 4px;
}
.login-prompt .el-link:hover { color: #ffffff !important; }
</style>