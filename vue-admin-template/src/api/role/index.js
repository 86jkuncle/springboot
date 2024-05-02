import request from '@/utils/request';

const base = "/admin/system/sysrole";

export function getList(params){
    return request({
        url:base+'/list',
        method:'get',
        params
    });
}