<template>
    <div>
        <el-row class="tac">
            <el-col :span="3">
            <div style="text-align: center;">
                <h3 class="mb-2">会议室管理系统</h3>
            </div>
            
            <el-menu
                router
                :default-active="$route.path"
                class="el-menu-vertical-demo"
                :unique-opened="true"
                @open="handleOpen"
                @close="handleClose">
                
                <el-menu-item index="/PlatformU/Inquire">
                    <template #title>
                        <el-icon><House /></el-icon>
                        <router-link to="/PlatformU/Inquire">会议室查询</router-link>
                    </template>
                </el-menu-item> 

                <el-sub-menu index="1">
                    <template #title>
                        <el-icon><Setting /></el-icon>
                        <span>会议室预约</span>
                    </template>
                    <el-menu-item index="/PlatformU/Request">
                        <router-link to="/PlatformU/Request">添加申请</router-link>
                    </el-menu-item>                                        
                    <el-menu-item index="/PlatformU/MyRequest">
                        <router-link to="/PlatformU/MyRequest">我的申请</router-link>
                    </el-menu-item>
                </el-sub-menu>

                <el-sub-menu index="3">
                    <template #title>
                        <el-icon><ChatLineSquare /></el-icon>
                        <span>我的信息</span>
                    </template>
                    <el-menu-item index="/PlatformU/SendEmail">
                        <router-link to="/PlatformU/SendEmail">发送信息</router-link>
                    </el-menu-item>
                    <el-menu-item index="/PlatformU/ReceiveEmail">
                        <router-link to="/PlatformU/ReceiveEmail">收到信息</router-link>
                    </el-menu-item>
                </el-sub-menu>                   
            </el-menu>
            </el-col>
            <el-col :span="21"  style="width: 100%;">
                <el-container>
                    <el-header>
                        <el-breadcrumb separator="/" style="cursor: default;">
                            <el-breadcrumb-item v-for="(item,index) in $route.matched" 
                            :key="index"
                            :to="{path:item.path}"
                            >{{item.meta.title}}</el-breadcrumb-item>
                        </el-breadcrumb>
                        <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end;">
                            <el-dropdown @command="handleCommand">
                                <div style="display: flex;align-items: center;cursor: default;outline: none;">
                                    <el-icon :size="20" style="margin: 5px;"><Avatar /></el-icon>
                                    <span class="el-dropdown-link">
                                        {{username}}<el-icon class="el-icon--right"><arrow-down /></el-icon>
                                    </span>
                                </div>
                                <template #dropdown>
                                    <el-dropdown-menu>
                                        <el-dropdown-item command="a" @click="quit()">退出登录</el-dropdown-item>
                                        <el-dropdown-item command="b" @click="logout()">注销账号</el-dropdown-item>
                                    </el-dropdown-menu>
                                </template>

                            </el-dropdown>
                        </div>
                    </el-header>
                    <el-main>
                        <router-view></router-view>  
                    </el-main>
                </el-container>
            </el-col>
        </el-row>
    </div>
</template>

<script lang="ts" setup>
    import { ElMessage, ElMessageBox } from 'element-plus'
    import {House, Setting, Avatar, ChatLineSquare, ArrowDown} from '@element-plus/icons-vue'
    import { useRouter } from 'vue-router'
    import axios from 'axios'

    let $router = useRouter()
    const user = JSON.parse(localStorage.getItem("information"))
    const username = user.data.username
    const handleOpen = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
    }

    const handleClose = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
    }

    const handleCommand = (command: string | number | object) => {
    ElMessage(`click on item ${command}`)
    }

    function quit() {
        localStorage.removeItem('information');
        ElMessage({ type: 'success', message: '退出成功！' });
        $router.push('/Login/Password');    
    }

    async function logout() {
        ElMessageBox.confirm(
            '确定注销账号吗?',
            'Warning',
            {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }  
        ) .then(() => {
            axios.get(`http://localhost:8080/role/delete?id=${user.data.id}`);
            ElMessage({ type: 'success', message: '注销成功！' });
            localStorage.removeItem('information');
            $router.push('/Login');            
        }) .catch(() => {
            console.error("删除失败");
        })       
    }    

</script>

<style>
    .el-menu-vertical-demo {
        height: 100%;
        margin: 10px;
    }

    a {
        text-decoration: none;
        color: #000;
    }

    router-link-active {
        text-decoration: none;
    }

    .el-menu-item.is-active {
        background-color: rgb(235.9,245.3,255) !important;
        color: #000;
    }

    .el-header{
        display: flex;
        align-items: center;
        border-bottom: 1px solid #dbdbdb;
    }

    .example-showcase .el-dropdown-link {
        color: var(--el-color-primary);
        display: flex;
        align-items: center;
    }
    

</style>