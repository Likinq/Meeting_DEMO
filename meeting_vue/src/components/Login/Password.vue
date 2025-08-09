<template>
    <div class="login-page"> 
        <el-form label-width="auto">
            <el-form-item label="账号">
                <el-input v-model="formLabelAlign.username" />
            </el-form-item>
            <el-form-item label="密码">
                <el-input v-model="formLabelAlign.password" show-password/>
            </el-form-item>
            <div style="display: flex;">
                <div style="flex: 1">
                    <el-form-item label="验证码">
                        <el-input v-model="sidentifyMode" />
                    </el-form-item>
                </div>
                <!-- 使用验证码组件 -->
                <div class="code" @click="refreshCode" style="flex: 1">
                    <SIdentify :identifyCode="identifyCode"></SIdentify>
                </div>
            </div>
            <el-row>
              <el-col :span="4">
              </el-col>
              <el-col :span="20">
                <el-form-item>
                  <el-button class="login_btn" type="primary" size="default" @click="login">登录</el-button>
                  <p></p>
                  <el-button class="login_btn" size="default" @click="dialogVisible = true">注册</el-button>
                </el-form-item>
              </el-col>
            </el-row>
            <router-link to="/Login/Email" style="color: black;font-size: 15px;text-decoration: underline;">邮箱验证码登录</router-link>

            <el-dialog v-model="dialogVisible" title="注册" width="500">
              <el-form 
                  label-width="auto" 
                  ref="ruleFormRef"
                  :model="formLabelAlign1"
                  :rules="rules"
                  height="500"
              >
                <div style="display: flex;">
                  <div style="flex: 1">
                      <el-form-item label="账号" prop="username">
                          <el-input v-model="formLabelAlign1.username"/>
                      </el-form-item>
                      <el-form-item label="密码" prop="password">
                          <el-input v-model="formLabelAlign1.password" show-password/>
                      </el-form-item>
                      <el-form-item label="再次输入密码" prop="password2">
                          <el-input v-model="formLabelAlign1.password2" show-password/>
                      </el-form-item>
                  </div>
                  <div style="flex: 1">   
                      <el-form-item label="部门" prop="department">
                        <el-select v-model="formLabelAlign1.department">
                            <el-option label="部门1" value="部门1" />
                            <el-option label="部门2" value="部门2" />
                            <el-option label="部门3" value="部门3" />
                        </el-select>
                      </el-form-item>
                      <el-form-item label="角色" prop="role">
                        <el-select v-model="formLabelAlign1.role">
                            <el-option label="用户" value="user" />
                            <el-option label="部门管理员" value="manager" />
                            <el-option label="系统管理员" value="system" />
                        </el-select>
                      </el-form-item>
                      <el-form-item label="邮箱" prop="mailbox">
                          <el-input v-model="formLabelAlign1.mailbox"/>
                      </el-form-item>
                  </div>
                </div>
                <el-row>
                  <el-col :span="6">
                  </el-col>
                  <el-col :span="18">
                  <el-form-item>
                    <el-button class="login_btn" type="primary" size="default" @click="enroll(ruleFormRef)">注册</el-button>
                    <el-button class="login_btn" size="default" @click="dialogVisible = false">返回</el-button>
                  </el-form-item>
                  </el-col>
                </el-row>
              </el-form>
            </el-dialog>
        </el-form>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, reactive } from 'vue'
import type { FormItemProps, FormRules, FormInstance } from 'element-plus'
import { ElMessage } from 'element-plus'
import SIdentify from '@/components/Login/Sidentify.vue'
import { useRouter } from 'vue-router'
import axios from 'axios'


const dialogVisible = ref(false)
const formLabelAlign = ref({
  username: '',
  password: ''
})

//验证码
//获取路由器
let $router = useRouter()
let sidentifyMode = ref('') //输入框验证码
let identifyCode = ref('') //图形验证码
let identifyCodes = ref('1234567890abcdefjhijklinopqrsduvwxyz') //验证码出现的数字和字母
 
//组件挂载
onMounted(() => {
  identifyCode.value = ''
  makeCode(identifyCodes.value, 4)
})
 
// 生成随机数
const randomNum = (min, max) => {
  max = max + 1
  return Math.floor(Math.random() * (max - min) + min)
}
// 随机生成验证码字符串
const makeCode = (o, l) => {
  for (let i = 0; i < l; i++) {
    identifyCode.value += o[randomNum(0, o.length)]
  }
}
// 更新验证码
const refreshCode = () => {
  identifyCode.value = ''
  makeCode(identifyCodes.value, 4)
}
//登录
async function login() {
    //验证验证码不为空
    if (!sidentifyMode.value) {
        ElMessage({ type: 'error', message: '验证码不能为空！' })
        return
    }
    //验证验证码是否正确
    if (sidentifyMode.value != identifyCode.value) {
        ElMessage({ type: 'error', message: '验证码错误' })
        refreshCode()
        return
    } 
    else {
        try {
            const response = await axios.post("http://localhost:8080/role/login", formLabelAlign.value, {
            headers: {
                'Content-Type': 'application/json',
            },
            });
            console.log(response.data)
            if (response.data.code == "200" && response.data.data.state == "正常") {
                console.log('登录成功:', response.data);
                ElMessage({ type: 'success', message: '登录成功！' })
                if (response.data.data.role == 'user'){
                  $router.push('/PlatformU/Inquire');
                }else if (response.data.data.role == 'manager'){
                  $router.push('/PlatformM/Inquire');
                }else {
                  $router.push('/PlatformS/Statistic');
                }                 
                //localStorage.setItem("information",JSON.stringify(response.data))
                const dataToStore = {
                    code: response.data.code,
                    data: response.data.data,
                    msg: response.data.msg,
                    expires: new Date().getTime() + 3600000
                };
                localStorage.setItem("information", JSON.stringify(dataToStore));
            }else {
                ElMessage.error(response.data.message || "账号或密码错误");
            }
        } catch (error) {
            console.error("登录失败：", error);
        }    
    }
}

const formLabelAlign1 = reactive<RuleForm>({
    username: '',
    password: '',
    password2: '',
    department: '',
    role:'',
    mailbox:'',
    state:'正常'

})
interface RuleForm {
    username: String,
    password: String,
    password2: String,
    department:String,
    role:String,
    mailbox:String,
    state:String
}
const ruleFormRef = ref<FormInstance>()
const validatePass = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('该项不能为空'))
  } else if (value !== formLabelAlign1.password) {
    callback(new Error("两次密码不一致"))
  } else {
    callback()
  }
}
const rules = reactive<FormRules<RuleForm>>({
    username: [{required: true, message: '该项不能为空'}],
    password: [{required: true, message: '该项不能为空'}],
    password2:[{ validator: validatePass, trigger: 'blur' }],
    department:[{required: true, message: '该项不能为空'}],
    role: [{required: true, message: '该项不能为空'}],
    mailbox: [{required: true, message: '该项不能为空'}]
})
const enroll = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
        const response = axios.post("http://localhost:8080/role/add", formLabelAlign1);
        response.then((res) => {
          if (res.data.code == "200"){ElMessage({ type: 'success', message: '注册成功！' });dialogVisible.value = false;}
          else {ElMessage({ type: 'error', message: '注册失败！' })}
          console.log(res);
        }).catch((err) => {
            console.error(err);
        });        
    } else {
        console.log('error submit!', fields)
    }
  })
}


</script>

<style scoped>
.el-button {
    width: 130px;
    margin: 8px;
}

</style>