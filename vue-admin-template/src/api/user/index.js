import request from '@/utils/request';

const base = "/admin/system/sysuser";

export function getList(params){
    return request({
        url:`${base}/list`,
        method:'get',
        params
    });
}

export function add(params){
    return request({
        url:base+'/add',
        method:'post',
        data:params
    });
}

export function getById(id){
    return request({
        url:`${base}/findByUserId/${id}`,
        method:'get'
    });
}

export function update(params){
    return request({
        url:`${base}/edit`,
        method:'post',
        data:params
    });
}

export function del(id){
    return request({
        url:`${base}/del/${id}`,
        method:'delete'
    });
}

export function userStatus(id,status){
    return request({
        url:`${base}/status/${id}/${status}`,
        method:'get'
    });
}