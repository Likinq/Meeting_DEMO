<template>
   <el-button type="primary" @click="getList(user.data.department)">点击查询</el-button>

    <el-table :data="tableData" style="width: 100%" :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
        <el-table-column prop="topic" label="会议主题" width="130" />
        <el-table-column prop="roomname" label="会议室" width="130" />
        <el-table-column prop="person" label="备注"/>
        <el-table-column prop="equipment" label="设备需求"/>
        <el-table-column prop="time" label="预约时间" width="260" :formatter="formatter" />
        <el-table-column prop="state" label="状态" width="130" />
        <el-table-column prop="username" label="申请人" width="130" />
        <el-table-column prop="reviewername" label="审核人" width="130" />
    </el-table>
</template>

<script lang="ts" setup>
    import { ref, onMounted } from 'vue'
    import axios from 'axios'

    const tableData = ref([])
    const user = JSON.parse(localStorage.getItem("information"))
    function formatter(row) {
        return row.date + " " + row.begintime + "~" + row.finishtime
    }

    function getList(department){
        axios.get(`http://localhost:8080/book/departmentlist?department=${department}`)
        .then(response => {tableData.value = response.data})
        .catch(error => {console.error("请求出错：", error)})
    }

    onMounted(async () => {
        getList(user.data.department)
    })
</script>