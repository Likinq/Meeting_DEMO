<template>
    <el-form 
        label-width="auto"
        ref="ruleFormRef"
        style="width: 700px;"
        :model="addBookData"
        :rules="rules">

        <el-form-item label="会议主题" prop="topic"><el-input v-model="addBookData.topic"/></el-form-item>
        <el-form-item label="会议室" prop="roomid">
        <el-select v-model="addBookData.roomid">                        
            <el-option label="会议室1" value=1 />
            <el-option label="会议室2" value=2 />
            <el-option label="会议室3" value=3 />
            <el-option label="会议室4" value=4 />
        </el-select>
        </el-form-item>
        <el-form-item label="部门" prop="department">
        <el-select v-model="addBookData.department">    
            <el-option label="部门1" value="部门1" />
            <el-option label="部门2" value="部门2" />
            <el-option label="部门3" value="部门3" />
        </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="person"><el-input v-model="addBookData.person"/></el-form-item>
        <el-form-item label="设备需求" prop="equipment"><el-input v-model="addBookData.equipment"/></el-form-item>
        <el-form-item label="预约时间" prop="time">
            <div class="example-basic">
                <el-date-picker v-model="addBookData.date" prop="time" type="date" placeholder="选择日期" style="margin-right: 8px" :disabled-date="disabledDate"/>
                <el-button size="default" @click="choose()">选择时间</el-button>
<!--                 <el-time-picker v-model="addBookData.begintime" placeholder="开始时间"  style="margin: 8px" format='HH:mm' value-format='HH:mm:00' :default-value="new Date(0, 0, 0, 7, 0, 0)"/>
                <el-time-picker v-model="addBookData.finishtime" placeholder="结束时间" style="margin-left: 8px" format='HH:mm' value-format='HH:mm:00' :default-value="new Date(0, 0, 0, 7, 0, 0)"/> -->
            </div>
        </el-form-item>
        <el-row>
        <el-col :span="8">
        </el-col>
        <el-col :span="16">            
        <el-form-item>
            <el-button class="login_btn" type="primary" size="default" @click="addBook(ruleFormRef)">提交</el-button>
            <el-button class="login_btn" size="default" @click="resetForm(ruleFormRef)">重置</el-button>
        </el-form-item>
        </el-col>
        </el-row>
    </el-form>

    <el-dialog v-model="dialogVisible" title="选择时间段" width="500">
        <el-button 
        style="margin: 6px;width: 80px;"
        v-for="button in buttons"
        :key="button.id"
        :type="getButtonType(button.id)"
        :disabled="button.value"
        @click="handleClick(button.id)">
            {{ button.text }}
        </el-button>
        <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="add">确认</el-button>
        </template>
    </el-dialog>
</template>

<script lang="ts" setup>
    import { ref, toRaw } from 'vue'
    import axios from 'axios'
    import { ElMessage, FormInstance, ElMessageBox  } from 'element-plus'
    import dayjs from 'dayjs'

    const user = JSON.parse(localStorage.getItem("information"))
    const disabledDate = (time: Date) => {
        return time.getTime() < Date.now()
    }
    const ruleFormRef = ref<FormInstance>()
    const addBookData = ref({
        roomid:null,
        date:'',
        topic:'',
        begintime:null,
        finishtime:null,
        userid:user.data.id,
        department:'',
        person:'',
        equipment:'',
        state:"待审核",
        reviewerid:0
    })
    const rules = ({
        roomid: [{required: true, message: '该项不能为空'}],
        topic: [{required: true, message: '该项不能为空'}],    
        department: [{required: true, message: '该项不能为空'}]
    })

    const addBook = async (formEl: FormInstance | undefined) => {
        if (!formEl) return
        await formEl.validate((valid, fields) => {
            if (valid) {
                console.log(addBookData.value)
                if (addBookData.value.date == "" || addBookData.value.begintime == "" || addBookData.value.finishtime == ""){
                    ElMessage({ type: 'error', message: '会议预约时间不能为空！' })
                }
                else {axios.post("http://localhost:8080/book/add", addBookData.value);
                    ElMessage({ type: 'success', message: '添加成功！' })
                    //location=location
                }
            } else {
                console.log('error submit!', fields)
            }
        })
        }
    
    const resetForm = (formEl: FormInstance | undefined) => {
        if (!formEl) return
        formEl.resetFields()
    }

    const dialogVisible = ref(false)
    const buttons=ref([])
    const begintime = ref(null)
    const finishtime = ref(null)
    const selectedButtons = ref([])
    
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

    function choose() {
        if (addBookData.value.roomid==null || addBookData.value.date=="") {ElMessage({ type: 'error', message: '请填写会议室与日期' })}
        else {
            dialogVisible.value=true
            let date = ToDate(addBookData.value.date)
            axios.get(`http://localhost:8080/book/timeslots?roomid=${addBookData.value.roomid}&date=${date}`)
            .then(response => {buttons.value = response.data})
            .catch(error => {console.error("请求出错：", error)})
            console.log(buttons.value)
        }
    }

    function getButtonType(id) {
    if (begintime.value === id) return 'primary'
    if (finishtime.value === id) return 'success'
    return ''
    }

    function handleClick(id) {
    if (!begintime.value) {
        begintime.value = id
        selectedButtons.value[0] = id
    } else if (!finishtime.value && id !== begintime.value) {
        finishtime.value = id
        selectedButtons.value[1] = id
    } else {
        // 如果已选两个则重置选择
        if (selectedButtons.value.includes(id)) {
        if (begintime.value === id) begintime.value = null
        if (finishtime.value === id) finishtime.value = null
        }
    }
    }

    function add() {
    if (!begintime.value) {
        ElMessage({ type: 'error', message: '请选择开始时间和结束时间！' })
        return
    }
    else if (begintime.value && !finishtime.value) {
        addBookData.value.begintime=begintime.value
        addBookData.value.finishtime = begintime.value
        dialogVisible.value = false
    }
    else if (begintime.value >= finishtime.value) {
        ElMessage({ type: 'error', message: '结束时间必须晚于开始时间！' })
        return
    }
    else {
        addBookData.value.begintime=begintime.value
        addBookData.value.finishtime = finishtime.value
        dialogVisible.value = false
    }
    }
</script>