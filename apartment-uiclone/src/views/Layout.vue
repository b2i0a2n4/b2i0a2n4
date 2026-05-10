<template>
  <el-container class="layout-container">
    <el-aside width="240px" class="aside">
      <div class="logo">
        <el-icon style="margin-right: 10px"><OfficeBuilding /></el-icon>
        公寓管理系统
      </div>
      
      <el-menu
        router
        active-text-color="#409eff"
        background-color="transparent"
        text-color="rgba(255,255,255,0.85)"
        :default-active="$route.path"
        class="el-menu-vertical"
      >
        <template v-if="userRole === '0'">
          <el-menu-item-group title="系统管理模块">
            <el-menu-item index="/room">
              <el-icon><House /></el-icon>
              <span>房源信息管理</span>
            </el-menu-item>
            <el-menu-item index="/user">
              <el-icon><User /></el-icon>
              <span>用户信息管理</span>
            </el-menu-item>
            <el-menu-item index="/contract">
              <el-icon><Document /></el-icon>
              <span>租赁合同管理</span>
            </el-menu-item>
            <el-menu-item index="/bill">
              <el-icon><Money /></el-icon>
              <span>财务账单管理</span>
            </el-menu-item>
            <el-menu-item index="/service">
              <el-icon><Service /></el-icon>
              <span>服务工单管理</span>
            </el-menu-item>
            <el-menu-item index="/community">
              <el-icon><ChatDotRound /></el-icon>
              <span>社区交流管理</span>
            </el-menu-item>
          </el-menu-item-group>
        </template>

        <template v-else>
          <el-menu-item-group title="个人服务门户">
            <el-menu-item index="/tenant-market">
              <el-icon><Search /></el-icon>
              <span>房源浏览</span>
            </el-menu-item>
            <el-menu-item index="/tenant-home">
              <el-icon><UserFilled /></el-icon>
              <span>我的租赁信息</span>
            </el-menu-item>
            <el-menu-item index="/tenant-bill">
              <el-icon><Wallet /></el-icon>
              <span>我的账单费用</span>
            </el-menu-item>
            <el-menu-item index="/service">
              <el-icon><Service /></el-icon>
              <span>服务申请</span>
            </el-menu-item>
            <el-menu-item index="/community">
              <el-icon><ChatDotRound /></el-icon>
              <span>社区交流</span>
            </el-menu-item>
          </el-menu-item-group>
        </template>
      </el-menu>
    </el-aside>

    <el-container class="right-container">
      <el-header class="header">
        <div class="header-left">
          <el-breadcrumb separator="/" class="custom-breadcrumb">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ userRole === '0' ? '管理工作台' : '租客个人中心' }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <span class="user-info">
            当前用户：<b>{{ userName }}</b> 
            <el-tag size="small" :type="userRole === '0' ? 'danger' : 'success'" style="margin-left: 8px">
              {{ userRole === '0' ? '系统管理员' : '租客用户' }}
            </el-tag>
          </span>
          <el-button type="danger" size="small" @click="handleLogout" style="margin-left: 20px; background: rgba(245, 108, 108, 0.8); border: none;">退出登录</el-button>
        </div>
      </el-header>

      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { 
  House, OfficeBuilding, User, Document, Money, 
  UserFilled, Wallet, Service, ChatDotRound, Search 
} from '@element-plus/icons-vue'

const router = useRouter()
const userRole = ref('')
const userName = ref('')

onMounted(() => {
  userRole.value = localStorage.getItem('user_role') || '1'
  userName.value = localStorage.getItem('user_name') || '未认证'
})

const handleLogout = () => {
  localStorage.removeItem('apartment_token')
  localStorage.removeItem('user_id')
  localStorage.removeItem('user_role')
  localStorage.removeItem('user_name')
  router.push('/login')
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
  background: transparent;
}

.aside {
  background: rgba(30, 40, 60, 0.4);
  backdrop-filter: blur(16px);
  -webkit-backdrop-filter: blur(16px);
  border-right: 1px solid rgba(255, 255, 255, 0.1);
  transition: width 0.3s;
  box-shadow: 2px 0 10px rgba(0, 0, 0, 0.3);
}

.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  color: #fff;
  font-size: 18px;
  font-weight: 700;
  background: rgba(20, 25, 40, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
}

.el-menu-vertical {
  border-right: none;
}

.el-menu-item:hover {
  background-color: rgba(255, 255, 255, 0.1) !important;
}

.el-menu-item.is-active {
  background-color: rgba(64, 158, 255, 0.2) !important;
  border-right: 3px solid #409eff;
}

:deep(.el-menu-item-group__title) {
  color: rgba(255, 255, 255, 0.5);
  font-weight: bold;
  padding-top: 20px;
}

.right-container {
  background: transparent;
}

.header {
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(16px);
  -webkit-backdrop-filter: blur(16px);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  z-index: 9;
}

:deep(.custom-breadcrumb .el-breadcrumb__inner) {
  color: rgba(255, 255, 255, 0.7) !important;
}

:deep(.custom-breadcrumb .el-breadcrumb__item:last-child .el-breadcrumb__inner) {
  color: #fff !important;
  font-weight: 600;
}

.header-right {
  display: flex;
  align-items: center;
  font-size: 14px;
}

.user-info {
  color: #fff;
}

.main-content {
  background: transparent;
  padding: 24px;
  overflow-y: auto;
}
</style>