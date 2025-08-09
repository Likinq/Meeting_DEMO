<template>
    <el-button type="primary" @click="getList(user.data.department)">点击查询</el-button>

    <el-table :data="tableData" style="width: 100%" :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
        <el-table-column prop="topic" label="会议主题" width="130" />
        <el-table-column prop="roomname" label="会议室" width="130" />
        <el-table-column prop="username" label="申请人" width="130" />
        <el-table-column prop="person" label="备注"/>
        <el-table-column prop="equipment" label="设备需求"/>
        <el-table-column prop="time" label="预约时间" width="260" :formatter="formatter" />
        <el-table-column prop="change" label="操作">
        <template #default="scope">
            <el-button type="success" :icon="Select" circle @click="yesReview(scope.row)" />
            <el-button type="danger" :icon="CloseBold" circle @click="noReview(scope.row)"/>
            <el-button type="primary" :icon="ChatLineSquare" circle @click="tocommunicate(scope.row)"/>
        </template>
        </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" title="发送信息" width="500">
        <el-form 
            label-width="70px"
            ref="ruleFormRef"
            :model="addMessageData"
            :rules="rules">
            <el-form-item label="关于" prop="about">
                {{ addMessageData.about }}              
            </el-form-item>
            <el-form-item label="内容" prop="content">
                <el-input
                    v-model="addMessageData.content"
                    :rows="2"
                    type="textarea"
                    placeholder="Please input"
                />                         
            </el-form-item>
            <el-form-item label="发送给" prop="toname">
                <el-input v-model="addMessageData.toname"/>                   
            </el-form-item>
 
            <el-row>
            <el-col :span="5">
            </el-col>
            <el-col :span="19">          
            <el-form-item>
                <el-button class="login_btn" type="primary" size="default" @click="communicate(ruleFormRef)">确定</el-button>
                <el-button class="login_btn" size="default" @click="dialogVisible = false">取消</el-button>
            </el-form-item>
            </el-col>
            </el-row>
        </el-form>
    </el-dialog>             
</template>

<script lang="ts" setup>
    import { ref, onMounted } from 'vue'
    import axios from 'axios'
    import { Select,CloseBold,ChatLineSquare } from '@element-plus/icons-vue'
    import { ElMessage, FormInstance, ElMessageBox  } from 'element-plus'

    const tableData = ref([])
    const user = JSON.parse(localStorage.getItem("information"))
    const dialogVisible = ref(false)
    const ruleFormRef = ref<FormInstance>()
    const addMessageData = ref({
        about:'',
        content:'',
        toname:'',
        fromname:'',
        time:''
    })
    const rules = ({
        content:[{required: true, message: '该项不能为空'}],
        toname:[{required: true, message: '该项不能为空'}],        
    })

    onMounted(async () => {
        getList(user.data.department)
    })    

    function formatter(row) {
        return row.date + " " + row.begintime + "~" + row.finishtime
    }

    function getList(department){
        axios.get(`http://localhost:8080/book/reviewlist?department=${department}`)
        .then(response => {tableData.value = response.data})
        .catch(error => {console.error("请求出错：", error)})
    }

    async function yesReview(data){
        try{
            data.state="通过";
            data.reviewername=user.data.username;
            await axios.post("http://localhost:8080/book/update",data)
            ElMessage({ type: 'success', message: '修改成功！' })
            getList(user.data.department)
        }catch (error) {
            console.error("修改失败：", error);
        }
    }

    async function noReview(data){
        ElMessageBox.confirm(
            '确定拒绝申请吗?',
            'Warning',
            {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
            }
        ).then(() => {
            data.state="拒绝";
            data.reviewername=user.data.username;
            axios.post("http://localhost:8080/book/update",data)
            ElMessage({ type: 'success', message: '修改成功！' })
            getList(user.data.department)
        }) .catch(() => {
            console.error("修改失败");
        })       
    }

    function tocommunicate(data){
        dialogVisible.value = true;
        addMessageData.value.about=data.topic + " " + data.date + " " + data.begintime + "~" + data.finishtime
        addMessageData.value.toname=data.username
        addMessageData.value.fromname=data.reviewername
    }

    var getTime = new Date().getTime(); //获取到当前时间戳
    var time = new Date(getTime); //创建一个日期对象
    function nowDate(time) {
        var year = time.getFullYear(); // 年
        var month = (time.getMonth() + 1).toString().padStart(2, '0'); // 月
        var date = time.getDate().toString().padStart(2, '0'); // 日
        var hour = time.getHours().toString().padStart(2, '0'); // 时
        var minute = time.getMinutes().toString().padStart(2, '0'); // 分
        var second = time.getSeconds().toString().padStart(2, '0'); // 秒
        return (
            year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second
        )
    }
  

    const communicate = async (formEl: FormInstance | undefined) => {
        if (!formEl) return
        await formEl.validate((valid, fields) => {
            if (valid) {
                addMessageData.value.time = nowDate(time)
                console.log(addMessageData.value)
                axios.post("http://localhost:8080/message/add", addMessageData.value);
                dialogVisible.value = false;
                ElMessage({ type: 'success', message: '发送成功！' })
            } else {
                console.log('error submit!', fields)
            }
        })
        }


</script>