<template>
  <div class="app-container">
    <div style="padding: 20px 20px 0 20px">
      授权角色：{{ $route.query.roleName }}
    </div>
    <el-tree
      style="margin: 20px 0"
      ref="tree"
      :data="list"
      @check-change="checkChange"
      node-key="menuId"
      show-checkbox
      :check-strictly="true"
      default-expand-all
      :props="defaultProps"
    />
    <div style="padding: 20px 20px">
      <el-button
        :loading="loading"
        type="primary"
        icon="el-icon-check"
        size="mini"
        @click="save"
        >保存</el-button
      >
      <el-button
        @click="$router.push('/system/sysRole')"
        size="mini"
        icon="el-icon-refresh-right"
        >返回</el-button
      >
    </div>
  </div>
</template>
  <script>
import { toAssign, doAssign } from "@/api/menu";
export default {
  name: "roleAuth",

  data() {
    return {
      loading: false,
      list: [], // 所有
      defaultProps: {
        children: "children",
        label: "menuName",
      },
      parentIds:[],
    };
  },

  created() {
    this.fetchData();
  },

  methods: {
    // cancleChildrenCheckedStatus(arr, initArr = []) {
    //   return arr.reduce((pre, item) => {
    //     pre.push(item.menuId);
    //     if (item.children && item.children.length > 0) {
    //       // 如果当前菜单项有子菜单，则递归调用该函数处理子菜单
    //       pre.push(...this.cancleChildrenCheckedStatus(item.children));
    //     }
    //     return pre;
    //   }, initArr);
    // },
    checkChange(a, b, c) {
     
      if (b === false) {
        //如果当前节点有子集

        if (a.children) {
          //循环子集将他们的选中取消
        //   const checkIds = this.cancleChildrenCheckedStatus(a.children);
        //   checkIds.map(item => {
        //        this.$refs.tree.setChecked(item,false);
        //      })
        a.children.map(item =>{
            this.$refs.tree.setChecked(item.menuId,false);
        })
          //console.log(checkIds);
          
        }
      } else {
        //否则(为选中状态)
        //判断父节点id是否为空
        if (a.parent) {
  
          //如果不为空则将其选中
          this.$refs.tree.setChecked(a.parentId, true);
        }
      }
      //this.list = this.$refs.tree.getCheckedNodes();
    },
    fetchData() {
      const roleId = this.$route.query.roleId;
      toAssign(roleId).then((res) => {
        if (res.status === 200) {
          this.list = res.data;
            console.log(this.list)
          const checkedIds = this.getCheckedIds(this.list);
          //   console.log("getPermissions() checkedIds", checkedIds);
          this.$refs.tree.setCheckedKeys(checkedIds);
        }
      });
    },
    
    getCheckedIds(auths, initArr = []) {
      return auths.reduce((pre, item) => {
        if (item.isSelected) {
          pre.push(item.menuId);
        }
        if (item.children && item.children.length > 0) {
          // 如果当前菜单项有子菜单，则递归调用该函数处理子菜单
          pre.push(...this.getCheckedIds(item.children));
        }
        return pre;
      }, initArr);
    },

    save() {
      const allCheckedNodes = this.$refs.tree.getCheckedNodes(false, true);
      let idList = allCheckedNodes.map((node) => node.menuId);

      let assginMenuVo = {
        roleId: this.$route.query.roleId,
        menuIdList: idList,
      };
      this.loading = true;
      doAssign(assginMenuVo).then((result) => {
        this.loading = false;
        this.$message.success(result.$message || "分配权限成功");
        this.$router.push("/system/sysRole");
      });
    },
  },
};
</script>