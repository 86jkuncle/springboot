import request from '@/utils/request'
import Cookies from 'js-cookie';

const base = "/admin/system/user";

export function login(data) {
  return request({
    url: base+'/login',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: base+'/info',
    method: 'get'
  })
}

export function logout() {
 
  return request({
    url: base+'/logout',
    method: 'post'
  })
}
