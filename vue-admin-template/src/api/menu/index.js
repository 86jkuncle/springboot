import request from '@/utils/request';

const base = "/admin/system/sysmenu";

export function getList(params){
    return request({
        url:base+'/list',
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

export function del(id){
    return request({
        url:`${base}/del/${id}`,
        method:'delete'
    });
}

export function getById(id){
    return request({
        url:`${base}/findById/${id}`,
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

export function toAssign(roleId){
    return request({
        url: `${base}/toAssign/${roleId}`,
        method:'get'
    });
}

export function doAssign(params){
    return request({
        url:`${base}/doAssign`,
        method: 'post',
        data:params
    });
}