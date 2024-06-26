 import router from './router'
 import store from './store'
 import {
   Message
 } from 'element-ui'
 import NProgress from 'nprogress' // progress bar
 import 'nprogress/nprogress.css' // progress bar style
 import {
   getToken
 } from '@/utils/auth' // get token from cookie
 import getPageTitle from '@/utils/get-page-title'

 import Layout from '@/layout'
 import ParentView from '@components/ParentView'
 const _import = require('./router/_import_' + process.env.NODE_ENV)

 NProgress.configure({
   showSpinner: false
 }) // NProgress Configuration

 const whiteList = ['/login'] // no redirect whitelist

 router.beforeEach(async (to, from, next) => {
   // start progress bar
   NProgress.start()

   // set page title
   document.title = getPageTitle(to.meta.title)

   // determine whether the user has logged in
   const hasToken = getToken()

   if (hasToken) {
     if (to.path === '/login') {
       // if is logged in, redirect to the home page
       next({
         path: '/'
       })
       NProgress.done()
     } else {
       const hasGetUserInfo = store.getters.name
       if (hasGetUserInfo) {
         next()
       } else {
         try {
           // get user info
           await store.dispatch('user/getInfo')

           if (store.getters.menus.length < 1) {
             global.antRouter = []
             next()
           }
           //过滤路由
           const menus = filterAsyncRouter(store.getters.menus)
           console.log(menus)
           //动态添加路由
           router.addRoutes(menus)
           //将路由数据传递给全局变量,做侧边栏菜单渲染
           global.antRouter = menus

           next({
             ...to,
             replace: true
           })
         } catch (error) {
           // remove token and go to login page to re-login
           await store.dispatch('user/resetToken')
           Message.error(error || 'Has Error')
           next(`/login?redirect=${to.path}`)
           NProgress.done()
         }
       }
     }
   } else {
     /* has no token*/

     if (whiteList.indexOf(to.path) !== -1) {
       // in the free login whitelist, go directly
       next()
     } else {
       // other pages that do not have permission to access are redirected to the login page.
       next(`/login?redirect=${to.path}`)
       NProgress.done()
     }
   }
 })

 router.afterEach(() => {
   // finish progress bar
   NProgress.done()
 })

 //遍历后台传来的路由字符串,转换为组件对象
 function filterAsyncRouter(asyncRouterMap) {
   const accessedRouters = asyncRouterMap.filter(route => {
     if (route.component) {
       if (route.component === 'Layout') { //Layout组件特殊处理
         route.component = Layout
       } else if (route.component === 'ParentView') {
         route.component = ParentView
       } else {
         try {
           route.component = _import(route.component) //导入组件
         } catch (error) {
           debugger
           console.log(error)
           router.component = _import('dashboard/index') //导入组件
         }

       }
     }
     if (route.children && route.children.length > 0) {
       route.children = filterAsyncRouter(route.children)
     }else{
      delete route.children
     }
     return true
   })
   return accessedRouters
 }
