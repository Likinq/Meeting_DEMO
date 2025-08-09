<template>
    <div style="display: flex;">
    <div style="flex: 1">    
    <el-form 
        label-width="auto"
        ref="ruleFormRef3"
        style="width: 700px;"
        :model="selectRoleData">
        <el-select v-model="selectRoleData.department" style="width: 150px;margin: 16px;" placeholder="部门">
            <el-option label="-无-" value='' />
            <el-option label="部门1" value="部门1" />
            <el-option label="部门2" value="部门2" />
            <el-option label="部门3" value="部门3" />
        </el-select>

        <el-select v-model="selectRoleData.role" style="width: 150px;margin: 16px;" placeholder="角色">
            <el-option label="-无-" value='' />
            <el-option label="用户" value="user" />
            <el-option label="部门管理员" value="manager" />
            <el-option label="系统管理员" value="system" />
        </el-select>
    </el-form>
    </div>
    <div style="flex: 1">
    <el-button type="primary" @click="togetList" style="margin: 16px;">点击查询</el-button>
    <el-button @click="dialogVisible1 = true" style="margin: 16px;">添加用户</el-button>
    </div>
    </div>


    <el-table :data="tableData" style="width: 100%" :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
        <el-table-column prop="id" label="ID" width="130" />
        <el-table-column prop="role" label="角色" width="130" :formatter="formatter" />
        <el-table-column prop="username" label="用户名" width="130" />
        <el-table-column prop="password" label="密码" width="130" />
        <el-table-column prop="department" label="部门" width="130" />
        <el-table-column prop="mailbox" label="邮箱" width="230" />
        <el-table-column prop="state" label="状态" width="130" />
        <el-table-column prop="change" label="操作">
        <template #default="scope">
            <el-button type="primary" :icon="Edit" circle @click="toeditRole(scope.row)" />
            <el-button type="danger" :icon="Delete" circle @click="deleteRole(scope.row.id)"/>
        </template>
        </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible1" title="注册" width="500">
        <el-form 
            label-width="70px" 
            ref="ruleFormRef1"
            :model="addRoleData"
            :rules="rules"
            height="500"
        >
        <div style="display: flex;">
            <div style="flex: 1">
                <el-form-item label="账号" prop="username">
                    <el-input v-model="addRoleData.username"/>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="addRoleData.password"/>
                </el-form-item>
                <el-form-item label="状态" prop="state">
                <el-select v-model="addRoleData.state">
                        <el-option label="正常" value="正常" />
                        <el-option label="停用" value="停用" />
                </el-select>                    
                </el-form-item>
            </div>
            <div style="flex: 1">   
                <el-form-item label="部门" prop="department">
                <el-select v-model="addRoleData.department">
                    <el-option label="部门1" value="部门1" />
                    <el-option label="部门2" value="部门2" />
                    <el-option label="部门3" value="部门3" />
                </el-select>
                </el-form-item>
                <el-form-item label="角色" prop="role">
                <el-select v-model="addRoleData.role">
                    <el-option label="用户" value="user" />
                    <el-option label="部门管理员" value="manager" />
                    <el-option label="系统管理员" value="system" />
                </el-select>
                </el-form-item>
                <el-form-item label="邮箱" prop="mailbox">
                    <el-input v-model="addRoleData.mailbox"/>
                </el-form-item>
            </div>
        </div>
        <el-row>
            <el-col :span="5">
            </el-col>
            <el-col :span="19">
            <el-form-item>
            <el-button class="login_btn" type="primary" size="default" @click="addRole(ruleFormRef1)">注册</el-button>
            <el-button class="login_btn" size="default" @click="dialogVisible1 = false">返回</el-button>
            </el-form-item>
            </el-col>
        </el-row>
        </el-form>
    </el-dialog>

    <el-dialog v-model="dialogVisible2" title="编辑用户" width="500">
        <el-form 
            label-width="70px"
            ref="ruleFormRef2"
            :model="editRoleData"
            :rules="rules">
            <div style="display: flex;">
                <div style="flex: 1">
                    <el-form-item label="账号" prop="username">
                        <el-input v-model="editRoleData.username"/>
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input v-model="editRoleData.password"/>
                    </el-form-item>
                    <el-form-item label="状态" prop="state">
                    <el-select v-model="editRoleData.state">
                        <el-option label="正常" value="正常" />
                        <el-option label="停用" value="停用" />
                    </el-select>                    
                    </el-form-item>
                </div>
                <div style="flex: 1">   
                    <el-form-item label="部门" prop="department">
                    <el-select v-model="editRoleData.department">                        
                        <el-option label="部门1" value="部门1" />
                        <el-option label="部门2" value="部门2" />
                        <el-option label="部门3" value="部门3" />
                    </el-select>
                    </el-form-item>
                    <el-form-item label="角色" prop="role">
                    <el-select v-model="editRoleData.role">
                        <el-option label="用户" value="user" />
                        <el-option label="部门管理员" value="manager" />
                        <el-option label="系统管理员" value="system" />
                    </el-select>
                    </el-form-item>
                    <el-form-item label="邮箱" prop="mailbox">
                        <el-input v-model="editRoleData.mailbox"/>
                    </el-form-item>
                </div>
            </div>
            <el-row>
            <el-col :span="5">
            </el-col>
            <el-col :span="19">          
            <el-form-item>
                <el-button class="login_btn" type="primary" size="default" @click="editRole(ruleFormRef2)">确定</el-button>
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
    const addRoleData = ref({
        role:'',
        username:'',
        password:'',
        password2:'',
        department:'',
        mailbox:'',
        state:''
    })
    const ruleFormRef2 = ref<FormInstance>()
    const editRoleData = ref({
        id:0,
        role:'',
        username:'',
        password:'',
        department:'',
        mailbox:'',
        state:''
    })
    const ruleFormRef3 = ref<FormInstance>()
    const selectRoleData = ref({
        role:'',
        department:''
    })    
    const rules = ({
        role: [{required: true, message: '该项不能为空'}],
        username: [{required: true, message: '该项不能为空'}],
        password:[{required: true, message: '该项不能为空'}],
        department: [{required: true, message: '该项不能为空'}],
        mailbox: [{required: true, message: '该项不能为空'}],
        state: [{required: true, message: '该项不能为空'}]
    })

    function formatter(row) {
        if (row.role=="user"){return "用户"}
        else if (row.role=="manager"){return "部门管理员"}
        else {return "系统管理员"}
    }

    onMounted(async () => {
        getList()
    })

    function togetList(){
        if (selectRoleData.value.department == '' && selectRoleData.value.role == '') {getList();}
        else if (selectRoleData.value.department == '' && selectRoleData.value.role != '') {getListByRo(selectRoleData.value.role);}
        else if (selectRoleData.value.department != '' && selectRoleData.value.role == '') {getListByDe(selectRoleData.value.department);}
        else {getListByRoDe(selectRoleData.value.role,selectRoleData.value.department)}
    }

    function getList(){
        axios.get("http://localhost:8080/role/list")
        .then(response => {tableData.value = response.data})
        .catch(error => {console.error("请求出错：", error)})
    }

    function getListByRo(role) {
        axios.get(`http://localhost:8080/role/rolist?role=${role}`)
        .then(response => {tableData.value = response.data})
        .catch(error => {console.error("请求出错：", error)})
    }

    function getListByDe(department) {
        axios.get(`http://localhost:8080/role/delist?department=${department}`)
        .then(response => {tableData.value = response.data})
        .catch(error => {console.error("请求出错：", error)})
    }

    function getListByRoDe(role,department) {
        axios.get(`http://localhost:8080/role/rodelist?role=${role}&department=${department}`)
        .then(response => {tableData.value = response.data})
        .catch(error => {console.error("请求出错：", error)})
    }

    function toeditRole(data){
        dialogVisible2.value=true,
        editRoleData.value=data
    }

    const addRole = async (formEl: FormInstance | undefined) => {
        if (!formEl) return
        await formEl.validate((valid, fields) => {
            if (valid) {
                console.log(addRoleData.value)
                axios.post("http://localhost:8080/role/add", addRoleData.value);
                dialogVisible1.value = false;
                ElMessage({ type: 'success', message: '添加成功！' })
                getList();
                getList()
            } else {
                console.log('error submit!', fields)
            }
        })
        }

    const editRole = async (formEl: FormInstance | undefined) => {
        if (!formEl) return
        await formEl.validate((valid, fields) => {
            if (valid) {
                console.log(editRoleData.value)
                axios.post("http://localhost:8080/role/update", editRoleData.value);
                dialogVisible2.value = false;
                ElMessage({ type: 'success', message: '修改成功！' });
                getList();
                getList()
            } else {
                console.log('error submit!', fields)
            }
        })
        }        


    async function deleteRole(id: any){
        ElMessageBox.confirm(
            '确定删除该用户吗?',
            'Warning',
            {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
            }
        ).then(() => {
            axios.get(`http://localhost:8080/role/delete?id=${id}`);
            ElMessage({ type: 'success', message: '删除成功！' });
            getList();
            getList();
        }) .catch(() => {
            console.error("删除失败");
        })       
    }    
</script>