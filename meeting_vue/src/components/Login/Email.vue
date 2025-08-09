<template>
    <div  class="login-page">
        <el-form label-width="auto">
            <el-form-item label="邮箱">
                <el-input v-model="email" />
            </el-form-item>
            <div style="display: flex;">
                <div style="flex: 1">
                    <el-form-item label="验证码">
                        <el-input v-model="sidentifyMode" />
                    </el-form-item>
                </div>
                <div style="flex: 1">
                    <el-button @click="send()">发送验证码</el-button>
                </div>
            </div>
            <el-button class="button1" type="primary" @click="resend()">确定</el-button>
            <el-button class="button1" @click="back()">取消</el-button>
        </el-form>
    </div>
</template>

<script lang="ts" setup>
    import {ref,toRaw} from 'vue'
    import axios from 'axios'
    import { ElMessage, FormInstance, ElMessageBox  } from 'element-plus'
    import { useRouter } from 'vue-router'

    let $router = useRouter()
    const email = ref('')
    const sidentifyMode = ref('')
    const code = ref('')
    const role = ref(null)

    function send() {
        if (!email) {ElMessage({ type: 'error', message: '邮箱不能为空！' })}
        else {
            axios.get(`http://localhost:8080/sendmail?mail=${email.value}`)
            .then(response => {code.value = response.data.code
                                role.value = response.data.role
                                ElMessage({ type: 'success', message: '发送成功！' })})
            .catch(error => {console.error("请求出错：", error);ElMessage({ type: 'error', message: '发送失败！' })})
        }
    }

    function resend() {
        if (!sidentifyMode.value) {ElMessage({ type: 'error', message: '验证码不能为空！' })}
        else if (sidentifyMode.value !=code.value) {ElMessage({ type: 'error', message: '验证码错误！' })}
        else {
            const dataToStore = {
                code: "200",
                data: role.value,
                msg: "请求成功",
                expires: new Date().getTime() + 3600000
            };
            localStorage.setItem("information", JSON.stringify(dataToStore));
            console.log(dataToStore)
            ElMessage({ type: 'success', message: '登录成功！' })
            if (dataToStore.data.role == 'user'){
                $router.push('/PlatformU/Inquire');
            }else if (dataToStore.data.role == 'manager'){
                $router.push('/PlatformM/Inquire');
            }else {
                $router.push('/PlatformS/Statistic');
            } 
        }
    }

    function back() {
        $router.push('/Login/Password')
    }
</script>

<style scoped>
.button1 {
    width: 130px;
    margin: 8px;
}
</style>