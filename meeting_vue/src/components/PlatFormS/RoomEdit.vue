<template>
    <el-button type="primary" @click="getList">点击查询</el-button>
    <el-button @click="dialogVisible1 = true">添加会议室</el-button>

    <el-table :data="tableData" style="width: 100%" :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
        <el-table-column prop="id" label="ID" width="130" />
        <el-table-column prop="roomname" label="名称" width="130" />
        <el-table-column prop="location" label="位置" width="130" />
        <el-table-column prop="capacity" label="容量（人）" width="130" />
        <el-table-column prop="equipment" label="设备" width="130" />
        <el-table-column prop="time" label="开放时间" width="260" :formatter="formatter"/>
        <el-table-column prop="state" label="状态" width="130" />
        <el-table-column prop="change" label="操作">
        <template #default="scope">
            <el-button type="primary" :icon="Edit" circle @click="toeditRoom(scope.row)" />
            <el-button type="danger" :icon="Delete" circle @click="deleteRoom(scope.row.id)"/>
        </template>
        </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible1" title="添加会议室" width="500">
        <el-form 
            label-width="auto"
            ref="ruleFormRef1"
            :model="addRoomData"
            :rules="rules">
            <div style="display: flex;">
                <div style="flex: 1">
                    <el-form-item label="名称" prop="roomname"><el-input v-model="addRoomData.roomname"/></el-form-item>
                    <el-form-item label="位置" prop="location"><el-input v-model="addRoomData.location"/></el-form-item>
                    <el-form-item label="容量" prop="capacity">
                        <el-select v-model="addRoomData.capacity">
                            <el-option label="20" value=20 />
                            <el-option label="30" value=30 />
                            <el-option label="40" value=40 />
                            <el-option label="50" value=50 />
                        </el-select>
                    </el-form-item>
                </div>
                <div style="flex: 1">   
                    <el-form-item label="设备" prop="equipment">
                        <el-input
                            v-model="addRoomData.equipment"
                            style="width: 200px"
                            :rows="2"
                            type="textarea"
                            placeholder="Please input"
                        />
                    </el-form-item>
                    <el-form-item label="状态" prop="state">
                        <el-select v-model="addRoomData.state">
                            <el-option label="正常" value="正常" />
                            <el-option label="停用" value="停用" />
                        </el-select>
                    </el-form-item>
                </div>
            </div>
            <el-form-item label="开放时间">
                <div class="example-basic">
                    <el-time-picker v-model="addRoomData.open" placeholder="Open time"  style="margin-right: 8px" format='HH:mm' value-format='HH:mm:00' :default-value="new Date(0, 0, 0, 7, 0, 0)"/>
                    <el-time-picker v-model="addRoomData.close" placeholder="Close time" style="margin-left: 8px" format='HH:mm' value-format='HH:mm:00' :default-value="new Date(0, 0, 0, 7, 0, 0)"/>
                </div>
            </el-form-item>
            <el-row>
            <el-col :span="8">
            </el-col>
            <el-col :span="16">            
            <el-form-item>
                <el-button class="login_btn" type="primary" size="default" @click="addRoom(ruleFormRef1)">确定</el-button>
                <el-button class="login_btn" size="default" @click="dialogVisible1 = false">取消</el-button>
            </el-form-item>
            </el-col>
            </el-row>
        </el-form>
    </el-dialog> 
    
    <el-dialog v-model="dialogVisible2" title="编辑会议室" width="500">
        <el-form 
            label-width="auto"
            ref="ruleFormRef2"
            :model="editRoomData"
            :rules="rules">
            <div style="display: flex;">
                <div style="flex: 1">
                    <el-form-item label="名称" prop="roomname"><el-input v-model="editRoomData.roomname"/></el-form-item>
                    <el-form-item label="位置" prop="location"><el-input v-model="editRoomData.location"/></el-form-item>
                    <el-form-item label="容量" prop="capacity">
                        <el-select v-model="editRoomData.capacity">
                            <el-option label="20" value=20 />
                            <el-option label="30" value=30 />
                            <el-option label="40" value=40 />
                            <el-option label="50" value=50 />
                        </el-select>
                    </el-form-item>
                </div>
                <div style="flex: 1">   
                    <el-form-item label="设备" prop="equipment">
                        <el-input
                            v-model="editRoomData.equipment"
                            style="width: 200px"
                            :rows="2"
                            type="textarea"
                            placeholder="Please input"
                        />
                    </el-form-item>
                    <el-form-item label="状态" prop="state">
                        <el-select v-model="editRoomData.state">
                            <el-option label="正常" value="正常" />
                            <el-option label="停用" value="停用" />
                        </el-select>
                    </el-form-item>
                </div>
            </div>
            <el-form-item label="开放时间">
                <div class="example-basic">
                    <el-time-picker v-model="editRoomData.open" placeholder="Open time"  style="margin-right: 8px" format='HH:mm' value-format='HH:mm:00' :default-value="new Date(0, 0, 0, 7, 0, 0)"/>
                    <el-time-picker v-model="editRoomData.close" placeholder="Close time" style="margin-left: 8px" format='HH:mm' value-format='HH:mm:00' :default-value="new Date(0, 0, 0, 7, 0, 0)"/>
                </div>
            </el-form-item>
            <el-row>
            <el-col :span="8">
            </el-col>
            <el-col :span="16">            
            <el-form-item>
                <el-button class="login_btn" type="primary" size="default" @click="editRoom(ruleFormRef2)">确定</el-button>
                <el-button class="login_btn" size="default" @click="dialogVisible2 = false">取消</el-button>
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
    const dialogVisible1 = ref(false)
    const dialogVisible2 = ref(false)
    const ruleFormRef1 = ref<FormInstance>()
    const addRoomData = ref({
        roomname:'',
        location:'',
        capacity:null,
        equipment:'',
        open:'',
        close:'',
        state:''
    })
    const ruleFormRef2 = ref<FormInstance>()
    const editRoomData = ref({
        id:0,
        roomname:'',
        location:'',
        capacity:0,
        equipment:'',
        open:'',
        close:'',
        state:''
    })
    const rules = ({
        roomname: [{required: true, message: '该项不能为空'}],
        location: [{required: true, message: '该项不能为空'}],
        capacity:[{required: true, message: '该项不能为空'}],
        equipment: [{required: true, message: '该项不能为空'}],
        open: [{required: true, message: '该项不能为空'}],
        close: [{required: true, message: '该项不能为空'}],
        state: [{required: true, message: '该项不能为空'}]
    })

    onMounted(async () => {
        getList()
    })

    function getList(){
        axios.get("http://localhost:8080/room/list")
        .then(response => {tableData.value = response.data})
        .catch(error => {console.error("请求出错：", error)})
    }

    function formatter(row) {
        return row.open + " ~ " + row.close
    }

    function toeditRoom(data: { id: number; roomname: string; location: string; capacity: number; equipment: string; open: string; close: string; state: string } | { id: number; roomname: string; location: string; capacity: number; equipment: string; open: string; close: string; state: string }){
        dialogVisible2.value=true,
        editRoomData.value=data
    }

    const addRoom = async (formEl: FormInstance | undefined) => {
        if (!formEl) return
        await formEl.validate((valid, fields) => {
            if (valid) {
                console.log(addRoomData.value)
                axios.post("http://localhost:8080/room/add", addRoomData.value);
                dialogVisible1.value = false;
                ElMessage({ type: 'success', message: '添加成功！' })
                getList();
                getList();
            } else {
                console.log('error submit!', fields)
            }
        })
        }

    const editRoom = async (formEl: FormInstance | undefined) => {
        if (!formEl) return
        await formEl.validate((valid, fields) => {
            if (valid) {
                console.log(editRoomData.value)
                axios.post("http://localhost:8080/room/update", editRoomData.value);
                dialogVisible2.value = false;
                ElMessage({ type: 'success', message: '修改成功！' })
                getList();
                getList();
            } else {
                console.log('error submit!', fields)
            }
        })
        }        


    async function deleteRoom(id: any){
        ElMessageBox.confirm(
            '确定删除会议室吗?',
            'Warning',
            {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
            }
        ).then(() => {
            axios.get(`http://localhost:8080/room/delete?id=${id}`);
            ElMessage({ type: 'success', message: '删除成功！' });
            getList();
            getList();
        }) .catch(() => {
            console.error("删除失败");
        })       
    }

</script>

<style>
    .example-basic .el-date-editor {
        width: 150px;
    }
</style>