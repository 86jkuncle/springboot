import request from '@/utils/request';

const base = "/admin/system/sysorg";

export function getAllList(){
    return request({
        url: `${base}/listAll`,
        method:'get'
    });
}

export function getList(params){
    return request({
        url: `${base}/list`,
        method:'get',
        params
    });
}

export function getParentList(params){
    return request({
        url:base+'/listParent',
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
        url:`${base}/findByOrgId/${id}`,
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