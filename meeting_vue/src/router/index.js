
import { createRouter, createWebHistory } from 'vue-router'

// 公共组件
import Login from '@/components/Login/Login.vue'
import Email from '@/components/Login/Email.vue'
import Password from '@/components/Login/Password.vue'

// 用户平台组件
const PlatformU = () => import('@/components/PlatformU/PlatformU.vue')
const Inquire = () => import('@/components/PlatformU/Inquire.vue')
const Request = () => import('@/components/PlatformU/Request.vue')
const MyRequest = () => import('@/components/PlatformU/MyRequest.vue')
const SendEmail = () => import('@/components/PlatformU/SendEmail.vue')
const ReceiveEmail = () => import('@/components/PlatformU/ReceiveEmail.vue')

// 管理员平台组件
const PlatformM = () => import('@/components/PlatFormM/PlatformM.vue')
const MeetingList = () => import('@/components/PlatFormM/MeetingList.vue')
const Review = () => import('@/components/PlatFormM/Review.vue')
const Booking = () => import('@/components/PlatFormM/Booking.vue')

// 超级管理员平台组件
const PlatformS = () => import('@/components/PlatFormS/PlatformS.vue')
const RoleEdit = () => import('@/components/PlatFormS/RoleEdit.vue')
const RoomEdit = () => import('@/components/PlatFormS/RoomEdit.vue')
const Statistic = () => import('@/components/PlatFormS/Statistic.vue')

const routes = [
    {
        path: '/',
        redirect: '/login',
        meta: { noAuth: true }
    },
    {
        path: '/login',
        component: Login,
        meta: { noAuth: true },
        children: [
            { path: 'password', component: Password ,meta: { noAuth: true }},
            { path: 'email', component: Email, meta: { noAuth: true }}
        ]
    },
    {
        path: '/platformU',
        component: PlatformU,
        meta: { title: "首页", roles: ['user'] },
        children: [
            { path: 'inquire', component: Inquire, meta: { title: "会议室查询" } },
            { path: 'request', component: Request, meta: { title: "申请会议室" } },
            { path: 'myRequest', component: MyRequest, meta: { title: "我的申请" } },
            { path: 'sendEmail', component: SendEmail, meta: { title: "发送信息" } },
            { path: 'receiveEmail', component: ReceiveEmail, meta: { title: "收到信息" } }
        ]
    },
    {
        path: '/platformM',
        component: PlatformM,
        meta: { title: "首页", roles: ['manager'] },
        children: [
            { path: 'inquire', component: Inquire, meta: { title: "会议室查询" } },
            { path: 'meetingList', component: MeetingList, meta: { title: "部门会议列表" } },
            { path: 'review', component: Review, meta: { title: "待审核" } },
            { path: 'booking', component: Booking, meta: { title: "预约会议" } },
            { path: 'sendEmail', component: SendEmail, meta: { title: "发送信息" } },
            { path: 'receiveEmail', component: ReceiveEmail, meta: { title: "收到信息" } }
        ]
    },
    {
        path: '/platformS',
        component: PlatformS,
        meta: { title: "首页", roles: ['system'] },
        children: [
            { path: 'statistic', component: Statistic, meta: { title: "预约日志与统计" } },
            { path: 'roleEdit', component: RoleEdit, meta: { title: "管理用户" } },
            { path: 'roomEdit', component: RoomEdit, meta: { title: "管理会议室" } },
            { path: 'sendEmail', component: SendEmail, meta: { title: "发送信息" } },
            { path: 'receiveEmail', component: ReceiveEmail, meta: { title: "收到信息" } }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    if (to.meta.title) {
        document.title = to.meta.title
    }

    if (to.meta.noAuth) return next()

    const storedInfo = JSON.parse(localStorage.getItem('information'))
    
    if (!storedInfo || new Date().getTime() >= storedInfo.expires) {
        localStorage.removeItem('information')
        ElMessage({ type: 'error', message: '登录过期' })
        return next('/login/password')
    }

    if (to.meta.roles && !to.meta.roles.includes(storedInfo.data.role)) {
        ElMessage({ type: 'error', message: '角色错误' })
        return next('/login/password')
    }

    next()
})

export default router
