<template>
    <el-button type="primary" @click="getList(user.data.id)">点击查询</el-button>

    <el-table :data="tableData" style="width: 100%" :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
        <el-table-column prop="topic" label="会议主题" width="130" />
        <el-table-column prop="roomid" label="会议室" width="130" :formatter="formatter1" />
        <el-table-column prop="department" label="部门" width="130" />
        <el-table-column prop="person" label="备注" width="180" />
        <el-table-column prop="equipment" label="设备需求" width="180" />
        <el-table-column prop="time" label="预约时间" width="260" :formatter="formatter" />
        <el-table-column prop="state" label="状态" width="130" />
        <el-table-column prop="change" label="操作">
        <template #default="scope">
            <el-button type="primary" :icon="Edit" circle @click="toeditRoom(scope.row)" />
            <el-button type="danger" :icon="Delete" circle @click="deleteRoom(scope.row.id)"/>
        </template>
        </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" title="编辑会议申请" width="500">
        <el-form 
            label-width="auto"
            ref="ruleFormRef"
            :model="editBookData"
            :rules="rules">

            <el-form-item label="会议主题" prop="topic"><el-input v-model="editBookData.topic"/></el-form-item>
            <el-form-item label="会议室" prop="roomid">
            <el-select v-model="editBookData.roomid">                        
                <el-option label="会议室1" value=1 />
                <el-option label="会议室2" value=2 />
                <el-option label="会议室3" value=3 />
                <el-option label="会议室4" value=4 />
            </el-select>
            </el-form-item>
            <el-form-item label="备注" prop="person"><el-input v-model="editBookData.person"/></el-form-item>
            <el-form-item label="设备需求" prop="equipment"><el-input v-model="editBookData.equipment"/></el-form-item>
            <el-form-item label="预约日期">
                <el-date-picker v-model="editBookData.date" prop="time" type="date" placeholder="选择日期" style="margin-right: 8px" :disabled-date="disabledDate"/>
            </el-form-item>
            <el-form-item label="预约时间">
                <div class="example-basic">
                    <el-time-picker v-model="editBookData.begintime" placeholder="开始时间"  style="margin: 8px" format='HH:mm' value-format='HH:mm:00' :default-value="new Date(0, 0, 0, 7, 0, 0)"/>
                    <el-time-picker v-model="editBookData.finishtime" placeholder="结束时间" style="margin-left: 8px" format='HH:mm' value-format='HH:mm:00' :default-value="new Date(0, 0, 0, 7, 0, 0)"/>
                </div>
            </el-form-item>            
            <el-row>
            <el-col :span="8">
            </el-col>
            <el-col :span="16">            
            <el-form-item>
                <el-button class="login_btn" type="primary" size="default" @click="editBook(ruleFormRef)">确定</el-button>
                <el-button class="login_btn" size="default" @click="dialogVisible = false">取消</el-button>
            </el-form-item>
            </el-col>
            </el-row>
        </el-form>        

    </el-dialog>
</template>

<script lang="ts" setup>
    import { Delete,Edit } from '@element-plus/icons-vue'
    import { ref, onMounted } from 'vue'
    import axios from 'axios'
    import { ElMessage, FormInstance, ElMessageBox  } from 'element-plus'

    const tableData = ref([])
    const user = JSON.parse(localStorage.getItem("information"))
    const dialogVisible = ref(false)
    const ruleFormRef = ref<FormInstance>()
    const editBookData = ref({
        id:null,
        roomid:'',
        date:'',
        topic:'',
        begintime:'',
        finishtime:'',
        userid:null,
        person:'',
        equipment:'',
        department:'',
        state:'',
        reviewername:null
    })
    const rules = ({
        roomname: [{required: true, message: '该项不能为空'}],
        topic: [{required: true, message: '该项不能为空'}],    
    })
    const disabledDate = (time: Date) => {
        return time.getTime() < Date.now()
    }

    onMounted(async () => {
        getList(user.data.id)
    })

    function getList(id){
        axios.get(`http://localhost:8080/book/mylist?userid=${id}`)
        .then(response => {tableData.value = response.data})
        .catch(error => {console.error("请求出错：", error)})
    }

    function formatter(row) {
        return row.date + " " + row.begintime + "~" + row.finishtime
    }

    function formatter1(row) {
        if (row.roomid==1) {return "会议室1";}
        else if (row.roomid==2) {return "会议室2";}
        else if (row.roomid==3) {return "会议室3";}
        else {return "会议室4"}
    }

    function toeditRoom(data){
        dialogVisible.value=true;
        data.state = "待审核";
        editBookData.value=data
    }

    const editBook = async (formEl: FormInstance | undefined) => {
        if (!formEl) return
        await formEl.validate((valid, fields) => {
            if (valid) {
                console.log(editBookData.value)
                axios.post("http://localhost:8080/book/update", editBookData.value);
                dialogVisible.value = false;
                ElMessage({ type: 'success', message: '修改成功！' })
                getList(user.data.id);
                getList(user.data.id);
            } else {
                console.log('error submit!', fields)
            }
        })
        }    
    async function deleteRoom(id: any){
        ElMessageBox.confirm(
            '确定删除会议预约申请吗?',
            'Warning',
            {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
            }
        ).then(() => {
            axios.get(`http://localhost:8080/book/delete?id=${id}`);
            ElMessage({ type: 'success', message: '删除成功！' });
            getList(user.data.id);
            getList(user.data.id);
        }) .catch(() => {
            console.error("删除失败");
        })       
    }


</script>