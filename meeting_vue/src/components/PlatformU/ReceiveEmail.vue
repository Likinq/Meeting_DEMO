<template>
    <el-button type="primary" @click="myList(user.data.id)">收到信息</el-button>

    <el-table :data="tableData" style="width: 100%" :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
        <el-table-column prop="fromname" label="发信人" width="130" />
        <el-table-column prop="about" label="关于" width="300" />
        <el-table-column prop="content" label="内容" width="200" />
        <el-table-column prop="time" label="发送时间" width="200" />
        <el-table-column prop="change" label="操作">
        <template #default="scope">
            <el-button type="danger" :icon="Delete" circle @click="deleteMessage(scope.row.id)"/>
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
    import { Delete, ChatLineSquare } from '@element-plus/icons-vue'
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
        fromname:'',
        time:''
    })
    const rules = ({
        content:[{required: true, message: '该项不能为空'}],
        toname:[{required: true, message: '该项不能为空'}],        
    })


    function myList(id){
        axios.get(`http://localhost:8080/message/mylist?toid=${id}`)
        .then(response => {tableData.value = response.data})
        .catch(error => {console.error("请求出错：", error)})
    }    

    onMounted(async () => {
        myList(user.data.id)
    })

    async function deleteMessage(id: any){
        ElMessageBox.confirm(
            '确定删除信息吗?',
            'Warning',
            {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
            }
        ).then(() => {
            axios.get(`http://localhost:8080/message/delete?id=${id}`);
            ElMessage({ type: 'success', message: '删除成功！' });
            myList(user.data.username);
            myList(user.data.username)
        }) .catch(() => {
            console.error("删除失败");
        })       
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

    function tocommunicate(data){
        dialogVisible.value = true;
        addMessageData.value.about=data.about
        addMessageData.value.toname=data.fromname
    }    

    const communicate = async (formEl: FormInstance | undefined) => {
        if (!formEl) return
        await formEl.validate((valid, fields) => {
            if (valid) {
                addMessageData.value.fromname = user.data.username
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