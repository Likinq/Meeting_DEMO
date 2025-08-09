<template>
    <el-button type="primary" @click="myaddList(user.data.id)">点击查询</el-button> 
    <el-button @click="dialogVisible = true">发送信息</el-button>   

    <el-table :data="tableData" style="width: 830px" :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
        <el-table-column prop="toname" label="收信人" width="130" />
        <el-table-column prop="about" label="关于" width="300" />
        <el-table-column prop="content" label="内容" width="200" />
        <el-table-column prop="time" label="发送时间" width="200" />
    </el-table>

    <el-dialog v-model="dialogVisible" title="发送信息" width="500">
        <el-form 
            label-width="70px"
            ref="ruleFormRef"
            :model="addMessageData"
            :rules="rules">
            <el-form-item label="关于" prop="about">
                <el-input v-model="addMessageData.about"/>          
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
    import { ElMessage, FormInstance, ElMessageBox  } from 'element-plus'

    const tableData = ref([])
    const user = JSON.parse(localStorage.getItem("information"))
    const dialogVisible = ref(false)
    const ruleFormRef = ref<FormInstance>()
    const addMessageData = ref({
        about:'',
        content:'',
        toname:'',
        fromid:'',
        time:'',
        toid:null
    })
    const rules = ({
        content:[{required: true, message: '该项不能为空'}],
        toname:[{required: true, message: '该项不能为空'}],        
    })  

    onMounted(async () => {
        myaddList(user.data.id)
    })

    function myaddList(id){
        axios.get(`http://localhost:8080/message/myaddlist?fromid=${id}`)
        .then(response => {tableData.value = response.data})
        .catch(error => {console.error("请求出错：", error)})
    }

    var getTime = new Date().getTime(); //获取到当前时间戳
    var time = new Date(getTime); //创建一个日期对象

    function nowDate (msec) {
        let datetime = new Date(msec);
        let year = datetime.getFullYear();
        let month = datetime.getMonth();
        let date = datetime.getDate();
        let hour = datetime.getHours();
        let minute = datetime.getMinutes();
        let second = datetime.getSeconds();
    
        let result = year + 
                    '-' + 
                    ((month + 1) >= 10 ? (month + 1) : '0' + (month + 1)) + 
                    '-' + 
                    ((date + 1) < 10 ? '0' + date : date) + 
                    ' ' + 
                    ((hour + 1) < 10 ? '0' + hour : hour) +
                    ':' + 
                    ((minute + 1) < 10 ? '0' + minute : minute) + 
                    ':' + 
                    ((second + 1) < 10 ? '0' + second : second);
        return result;
    }

    const communicate = async (formEl: FormInstance | undefined) => {
        if (!formEl) return
        await formEl.validate((valid, fields) => {
        if (valid) {
            addMessageData.value.fromid = user.data.id
            addMessageData.value.time = nowDate(time)
            axios.get(`http://localhost:8080/role/findid?toname=${addMessageData.value.toname}`)
                .then(response => {
                    addMessageData.value.toid = response.data
                    console.log(addMessageData.value)
                    return axios.post("http://localhost:8080/message/add", addMessageData.value)
                })
                .then(() => {
                    dialogVisible.value = false
                    ElMessage({ type: 'success', message: '发送成功！' })
                })
                .catch(error => {
                    console.error("请求出错：", error)
                })
        } else {
            console.log('error submit!', fields)
        }
        })
        }


</script>