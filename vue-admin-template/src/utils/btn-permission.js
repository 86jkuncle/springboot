

import store from '@/store'

//判断当前用户是否有此按钮权限
//permission,权限字符串 btn:sysRole:add
export default function hasPermission(permission) {
  //获取当前用户所有按钮权限
  const buttons = store.getters.buttons;
  //判断当前用户是否有此按钮权限
  return buttons.indexOf(permission) !== -1;
}