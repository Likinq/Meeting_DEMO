<template>
    <el-row>
        <el-col :span="5">
        <div class="example-basic">
            <el-date-picker v-model="dateoo" prop="time" type="date" placeholder="选择日期" :disabled-date="disabledDate"/>
        </div>
        </el-col>
        <el-col :span="19">
            <el-button type="primary" @click="getList">点击查询</el-button>
        </el-col>        
    </el-row>

    <el-table :data="tableData" style="width: 100%" :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
        <el-table-column prop="roomname" label="名称" width="130" />
        <el-table-column prop="location" label="位置" width="130" />
        <el-table-column prop="capacity" label="容量（人）" width="130" />
        <el-table-column prop="equipment" label="设备" width="130" />
        <el-table-column prop="time" label="开放时间" width="260" :formatter="formatter"/>
        <el-table-column prop="state" label="状态" width="130" />
        <el-table-column prop="free" label="查询空闲时间">   
        <template #default="scope">
            <el-button type="primary" :icon="Search" circle @click="choose(scope.row.id)" />
        </template>
        </el-table-column>  
        <el-table-column prop="change" label="查询已预约信息">   
        <template #default="scope">
            <el-button type="primary" :icon="Search" circle @click="tonlist(scope.row.id)" />
        </template>
        </el-table-column>  
    </el-table>

    <el-dialog v-model="dialogVisible" title="预约信息" width="80%">
        <el-table :data="tableData1" style="width: 100%" :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
            <el-table-column prop="topic" label="会议主题" width="100" />
            <el-table-column prop="roomname" label="会议室" width="100" />
            <el-table-column prop="department" label="部门" width="100" />
            <el-table-column prop="person" label="备注" width="150" />
            <el-table-column prop="equipment" label="设备需求"/>
            <el-table-column prop="time" label="预约时间" width="260" :formatter="formatter1" />
            <el-table-column prop="state" label="状态" width="100" />
            <el-table-column prop="username" label="申请人" width="100" />
            <el-table-column prop="reviewername" label="审核人" width="100" />
        </el-table>
    </el-dialog>

    <el-dialog v-model="dialogVisible1" title="空闲时间" width="500">
        <el-button 
        style="margin: 6px;width: 80px;"
        v-for="button in buttons"
        :key="button.id"
        :disabled="button.value">
            {{ button.text }}
        </el-button>
    </el-dialog>
</template>


<script lang="ts" setup>
    import { ref, onMounted } from 'vue'
    import axios from 'axios'
    import { ElMessage, FormInstance, ElMessageBox  } from 'element-plus'
    import { Search } from '@element-plus/icons-vue'

    const tableData = ref([])
    const tableData1 = ref([])
    const buttons = ref([])
    const dialogVisible = ref(false)
    const dialogVisible1 = ref(false)
    const disabledDate = (time: Date) => {
        return time.getTime() < Date.now()
    }
    const dateoo = ref('')

    onMounted(async () => {
        getList()
    })

    function formatter(row) {
        return row.open + " ~ " + row.close
    }

    function formatter1(row) {
        return row.date + " " + row.begintime + "~" + row.finishtime
    }

    function ToDate (msec) {
        let datetime = new Date(msec);
        let year = datetime.getFullYear();
        let month = datetime.getMonth();
        let date = datetime.getDate();
        let result = year + 
                    '-' + 
                    ((month + 1) >= 10 ? (month + 1) : '0' + (month + 1)) + 
                    '-' + 
                    ((date + 1) < 10 ? '0' + date : date);    
        return result;
    }

    function getList(){
        axios.get("http://localhost:8080/room/list")
        .then(response => {tableData.value = response.data})
        .catch(error => {console.error("请求出错：", error)})
    }

    function tonlist(roomid) {
        dialogVisible.value=true,
        nlist(roomid)
    }

    function nlist(roomid) {
        if (dateoo.value=='' || dateoo.value==null) {
            axios.get(`http://localhost:8080/room/nlist?roomid=${roomid}`)
            .then(response => {tableData1.value = response.data})
            .catch(error => {console.error("请求出错：", error)})
        }
        else {
            let date=ToDate(dateoo.value)
            axios.get(`http://localhost:8080/room/ndlist?roomid=${roomid}&date=${date}`)
            .then(response => {tableData1.value = response.data})
            .catch(error => {console.error("请求出错：", error)})
        }
    }

    function choose(roomid) {
        if (dateoo.value=='' || dateoo.value==null) {ElMessage({ type: 'error', message: '请填写日期' })}
        else {
            dialogVisible1.value=true
            let date = ToDate(dateoo.value)
            axios.get(`http://localhost:8080/book/timeslots?roomid=${roomid}&date=${date}`)
            .then(response => {buttons.value = response.data})
            .catch(error => {console.error("请求出错：", error)})
            console.log(buttons.value)
        }
    }

</script>