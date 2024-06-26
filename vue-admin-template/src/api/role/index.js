import request from '@/utils/request';

const base = "/admin/system/sysrole";

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
        url:`${base}/findByRoleId/${id}`,
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

export function getRolesByUserId(userId){
    return request({
        url:`${base}/toAssign/${userId}`,
        method: 'get'
    });
}

export function assignRoles(params){
    return request({
        url:`${base}/doAssign`,
        method: 'post',
        data:params
    });
}

