<template>
  <div class="app-container">
    <h1 class="main-title">智能会议室预约管理系统</h1>
    <router-view></router-view>
  </div>
</template>



<style scoped>
.app-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
  text-align: center;
}

.main-title {
  font-size: 2.5rem;
  color: #2c3e50;
  text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);
  margin-bottom: 2rem;
  padding-bottom: 0.8rem;
  border-bottom: 2px solid #42b983;
  position: relative;
}

.enter-button {
  display: inline-block;
  padding: 8px 20px;
  background-color: #42b983;
  color: white;
  text-decoration: none;
  border-radius: 4px;
  font-size: 1rem;
  transition: background-color 0.3s;
  margin: 10px;
}

.enter-button:hover {
  background-color: #3aa876;
}

.login-page {
    border-radius: 8px;
    width: 400px;
    margin: 0 auto;
    margin-top: 50px;
    padding: 20px;
    .code {
        text-align: center;
        margin-bottom: 10px;
    }
    .login_btn {
        width:130px;
        margin: 3px;
    }
}



</style>