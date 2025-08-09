import axios from "axios"
import router from "@/router/index"

const request = axios.create({
    baseURL:"http://localhost:8080",
    timeout:30000
})

request.interceptors.request.use(
    config => {
        config.headers['Content-Type'] = 'application/json;charset=utf-8';
        let user = JSON.parse(localStorage.getItem("information"))
        let token = user.data.token
        config.headers['token'] = token
        return config;
    }, error => {
        console.error('request errors:' + error)
        return Promise.reject(error)
    }
)

request.interceptors.response.use(
    response => {
        let res = response.data;
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        if (res?.code === '401') {
            localStorage.removeItem('information');
            router.push('/Login');
        }
        return res;
    },
    error => {
        console.error('response error:', error);
        return Promise.reject(error);
    }
);


export default request


