<template>
  <div class="tenant-container">
    <el-row :gutter="20">
      <el-col :span="24" style="margin-bottom: 20px;">
        <el-card class="welcome-card" shadow="never">
          <h2>系统用户：{{ userName }}，欢迎登录。</h2>
          <p class="tips">您可以在此查看个人合同详情、处理待缴账单或提交综合服务申请。</p>
        </el-card>
      </el-col>

      <el-col :span="14">
        <el-card shadow="hover">
          <template #header>
            <span style="font-weight: bold">房源与合同详情</span>
          </template>
          <div v-if="contractInfo" class="info-list">
            <el-descriptions :column="1" border>
              <el-descriptions-item label="承租房源">
                <el-tag size="large" effect="plain">{{ contractInfo.buildingNo }} - {{ contractInfo.roomNo }}</el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="起租日期">{{ contractInfo.startDate }}</el-descriptions-item>
              <el-descriptions-item label="到期日期">{{ contractInfo.endDate }}</el-descriptions-item>
              <el-descriptions-item label="租赁单价">
                <span class="price">￥{{ contractInfo.rentPrice }} / 月</span>
              </el-descriptions-item>
              <el-descriptions-item label="业务状态">
                <el-tag type="success">合同生效中</el-tag>
              </el-descriptions-item>
            </el-descriptions>
          </div>
          <el-empty v-else description="暂无生效中的合同记录" />
        </el-card>
      </el-col>

      <el-col :span="10">
        <el-card shadow="hover">
          <template #header>
            <span style="font-weight: bold">快捷业务办理</span>
          </template>
          <div class="quick-actions">
            <el-button type="primary" size="large" @click="$router.push('/tenant-bill')">账单缴纳</el-button>
            <el-button type="warning" size="large" @click="$router.push('/service')">服务申请</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const userName = ref(localStorage.getItem('user_name'))
const contractInfo = ref(null)

// 初始化加载个人租赁合同信息
onMounted(async () => {
  const userId = localStorage.getItem('user_id')
  try {
    const res = await axios.get(`http://localhost:8080/api/contracts/my?userId=${userId}`)
    if (res.data) {
      contractInfo.value = res.data
    }
  } catch (error) {
    console.error("加载个人租赁数据异常", error)
  }
})
</script>

<style scoped>
.welcome-card {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
}
.welcome-card h2 { margin: 0; font-size: 22px; }
.tips { opacity: 0.8; margin-top: 10px; font-size: 14px; }
.info-list { padding: 5px 0; }
.price { color: #f56c6c; font-weight: bold; font-size: 18px; }
.quick-actions {
  display: flex;
  flex-direction: column;
  gap: 15px;
  align-items: center;
  padding: 30px 0;
}
.quick-actions .el-button {
  width: 80%;
  margin-left: 0;
}
</style>