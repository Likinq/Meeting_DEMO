<template>
    <div style="display: flex;text-align: center;" class="title1">
        <div style="flex:1">
            <h3>会议室总数</h3>
            <h4>{{ roomnum }}</h4>
        </div>
        <div style="flex:1">
            <h3>用户总数</h3>
            <h4>{{ rolenum }}</h4>
        </div>
        <div style="flex:1">
            <h3>预约会议总数</h3>
            <h4>{{ booknum }}</h4>
        </div>
    </div>
    <div ref="chartRef" style="width: 1200px; height: 300px;"></div>

    <el-form :model="abc" ref="ruleFormRef">
        <el-select v-model="abc.department" style="width: 200px;margin: 8px;" placeholder="选择部门">
            <el-option label="部门1" value="部门1" />
            <el-option label="部门2" value="部门2" />
            <el-option label="部门3" value="部门3" />
        </el-select>
        <el-date-picker v-model="abc.dateoo" prop="time" type="date" placeholder="选择日期" style="width: 200px;margin: 8px;"/>
        <el-button type="primary" @click="togetList()" style="margin: 8px;">点击查询</el-button>
    </el-form>

    <el-table :data="tableData" style="width: 100%" :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
        <el-table-column prop="topic" label="会议主题" width="130" />
        <el-table-column prop="roomname" label="会议室" width="130" />
        <el-table-column prop="department" label="部门" width="130" />
        <el-table-column prop="person" label="备注"/>
        <el-table-column prop="equipment" label="设备需求"/>
        <el-table-column prop="time" label="预约时间" width="260" :formatter="formatter1" />
        <el-table-column prop="state" label="状态" width="130" />
        <el-table-column prop="username" label="申请人" width="130" />
        <el-table-column prop="reviewername" label="审核人" width="130" />
    </el-table>


</template>

<script setup lang="ts" >
    import { ref, onMounted, onBeforeUnmount, reactive } from 'vue'
    import * as echarts from 'echarts'
    import axios from 'axios'
    import { ElMessage, FormInstance, ElMessageBox  } from 'element-plus'

    const ruleFormRef = ref<FormInstance>()
    const roomnum = ref(0);
    const rolenum = ref(0);
    const booknum = ref(0)
    const tableData = ref([])
    const abc = ref({
        department:'',
        dateoo:''
    })

    function formatter1(row) {
        return row.date + " " + row.begintime + "~" + row.finishtime
    }

    axios.get("http://localhost:8080/room/count")
    .then(response => { roomnum.value = response.data })
    .catch(error => console.error("请求出错：", error));

    axios.get("http://localhost:8080/role/count")
    .then(response => { rolenum.value = response.data })
    .catch(error => console.error("请求出错：", error));

    axios.get("http://localhost:8080/book/count")
    .then(response => { booknum.value = response.data })
    .catch(error => console.error("请求出错：", error));

    const chartRef = ref(null)
    let chartInstance = null

    const initChart = () => {
        if (chartRef.value) {
            chartInstance = echarts.init(chartRef.value);
            const option = {
                title: {
                    text: '预约会议数量统计',
                    left: 'center',
                    textStyle: {
                        fontSize: 18,
                        fontFamily: 'sans-serif'
                    }
                },
                tooltip: {trigger: 'axis'},
                xAxis: {
                    type: 'category',
                    data: meetingData.value.map(item => item.date)
                },
                yAxis: {
                    type: 'value',
                    name: '会议数量'
                },
                series: [{
                    name: '会议数量',
                    type: 'line',
                    data: meetingData.value.map(item => item.count)
                }]
            };
            chartInstance.setOption(option);
        }
    };

    const meetingData = ref([]);

    const chart = async () => {
      try {
        const response = await axios.get('http://localhost:8080/book/chart');
        console.log(response.data)
        meetingData.value = response.data;
      } catch (error) {
        console.error('请求失败:', error);
      }
    };

    const resizeChart = () => {
        chartInstance?.resize()
    }

    onMounted(async () => {
        await chart()
        initChart()
        getList()
        window.addEventListener('resize', resizeChart)
    })

    onBeforeUnmount(() => {
        window.removeEventListener('resize', resizeChart)
        chartInstance?.dispose() 
    })

    function togetList() {
        const date = ToDate(abc.value.dateoo)
        console.log(abc.value.department,date)
        if (!abc.value.department && !abc.value.dateoo) {getList()}
        else if (abc.value.department && !abc.value.dateoo) {getListDep()}
        else if (!abc.value.department && abc.value.dateoo) {getListDate(date)}
        else {getListDD(date)}
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

    function getList() {
        axios.get("http://localhost:8080/book/list")
        .then(response => { tableData.value = response.data })
        .catch(error => console.error("请求出错：", error));        
    }
    
    function getListDep() {
        axios.get(`http://localhost:8080/book/departmentlist?department=${abc.value.department}`)
        .then(response => { tableData.value = response.data })
        .catch(error => console.error("请求出错：", error));        
    }

    function getListDate(date) {
        axios.get(`http://localhost:8080/book/datelist?date=${date}`)
        .then(response => { tableData.value = response.data })
        .catch(error => console.error("请求出错：", error));           
    }

    function getListDD(date) {
        axios.get(`http://localhost:8080/book/ddlist?date=${date}&department=${abc.value.department}`)
        .then(response => { tableData.value = response.data })
        .catch(error => console.error("请求出错：", error));           
    }    


</script>

<style>
.title1{
    margin-bottom: 2rem;
    padding-bottom: 0.8rem;
    border-bottom: 1px solid #dbdbdb;
}

</style>