<template>
  <el-button type="primary" @click="getList">点击查询</el-button>
    <el-table :data="tableData" style="width: 1040px" :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
        <el-table-column prop="id" label="ID" width="130" />
        <el-table-column prop="roomname" label="名称" width="130" />
        <el-table-column prop="location" label="位置" width="130" />
        <el-table-column prop="capacity" label="容量（人）" width="130" />
        <el-table-column prop="equipment" label="设备" width="130" />
        <el-table-column prop="time" label="开放时间" width="260" :formatter="formatter"/>
        <el-table-column prop="state" label="状态" width="130" />
    </el-table>


</template>

<script setup>
  import axios from 'axios'
  import {ref} from 'vue'

  const tableData = ref([])
  function getList(){
      axios.get("http://localhost:8080/room/list")
      .then(response => {tableData.value = response.data})
      .catch(error => {console.error("请求出错：", error)})
  }

  function formatter(row) {
    return row.open + " ~ " + row.close
  }

    

</script>