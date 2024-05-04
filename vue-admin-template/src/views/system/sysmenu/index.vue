<template>
  <div class="app-container">
    菜单列表

    <!-- <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-col :span="24">
            <el-form-item label="菜单名称">
              <el-input
                style="width: 100%"
                v-model="menuName"
                placeholder="菜单名称"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row style="display: flex">
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="search"
            >搜索</el-button
          >
          <el-button icon="el-icon-refresh" size="mini" @click="resetData"
            >重置</el-button
          >
        </el-row>
      </el-form>
    </div> -->

    <div class="tools-div">
      <el-button
        type="success"
        icon="el-icon-plus"
        size="mini"
        @click="addSubMenu"
        >添加</el-button
      >
    </div>

    <el-table
      v-loading="listLoading"
      :data="list"
      style="width: 100%; margin-top: 10px"
      row-key="menuId"
      stripe
      border
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <!-- <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
         {{ (page - 1) * limit + scope.$index + 1 }} 
          {{ scope.row.menuId }}
        </template>
      </el-table-column> -->
      <el-table-column prop="menuName" label="菜单名称" sortable />

      <el-table-column prop="menuType" label="菜单类型" sortable />
      <el-table-column prop="menuSource" label="菜单来源" sortable />
      <el-table-column prop="menuAddr" label="路由地址" sortable />
      <el-table-column prop="component" label="组件路径" sortable />
      <el-table-column prop="perms" label="权限标识" sortable />
      <el-table-column label="菜单图标" sortable>
        <template slot-scope="scope">
          <i :class="scope.row.menuIcon"></i>
        </template>
      </el-table-column>
      <el-table-column prop="menuDesc" label="菜单描述" sortable />
      <el-table-column prop="createTime" label="创建时间" sortable />

      <el-table-column label="操作" width="200" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button
            type="success"
            v-if="scope.row.menuType !== 3"
            icon="el-icon-plus"
            size="mini"
            @click="addSubMenu(scope.row)"
            title="添加下级节点"
          />
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            @click="editMenu(scope.row)"
          ></el-button>
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            @click="delMenu(scope.row)"
          ></el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[10, 50, 100, 200]"
      :page-size="10"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>

    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="40%">
      <el-form
        ref="dataForm"
        :model="sysMenu"
        label-width="150px"
        size="small"
        style="padding-right: 40px"
      >
        <!-- <el-form-item label="父级菜单">
          <el-select v-model="sysMenu.parentId" filterable placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.menuId"
              :label="item.menuName"
              :value="item.menuId"
            >
            </el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item label="上级菜单">
          <el-input v-model="sysMenu.parentName" :disabled="true" />
        </el-form-item>

        <el-form-item label="菜单类型">
          <el-radio-group
            v-model="sysMenu.menuType"
            :disabled="typeDisabled"
            @input="menuTypeRadioChange"
          >
            <el-radio :label="1" :disabled="type0Disabled">目录</el-radio>
            <el-radio :label="2" :disabled="type1Disabled">菜单</el-radio>
            <el-radio :label="3" :disabled="type2Disabled">按钮</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="菜单名称">
          <el-input v-model="sysMenu.menuName" />
        </el-form-item>

        <el-form-item label="图标" v-if="sysMenu.type !== 3">
          <el-select v-model="sysMenu.menuIcon" clearable>
            <el-option
              v-for="item in iconList"
              :key="item.class"
              :label="item.class"
              :value="item.class"
            >
              <span style="float: left">
                <i :class="item.class"></i>
              </span>
              <span style="padding-left: 6px">{{ item.class }}</span>
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="排序">
          <el-input v-model="sysMenu.seq" />
        </el-form-item>

        <el-form-item label="菜单来源">
          <el-radio-group
            v-model="sysMenu.menuSource"
            :disabled="sourceDisabled"
          >
            <el-radio :label="1" :disabled="source0Disabled">内部</el-radio>
            <el-radio :label="2" :disabled="source1Disabled">外链</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item v-if="sysMenu.menuType !== 3">
          <span slot="label">
            <el-tooltip content="访问的路由地址，如：`sysUser`" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
            路由地址
          </span>
          <el-input v-model="sysMenu.menuAddr" placeholder="请输入路由地址" />
        </el-form-item>

        <el-form-item v-if="sysMenu.menuType !== 3">
          <span slot="label">
            <el-tooltip
              content="访问的组件路径，如：`system/user/index`，默认在`views`目录下"
              placement="top"
            >
              <i class="el-icon-question"></i>
            </el-tooltip>
            组件路径
          </span>
          <el-input v-model="sysMenu.component" placeholder="请输入组件路径" />
        </el-form-item>

        <el-form-item v-if="sysMenu.menuType === 3">
          <el-input
            v-model="sysMenu.perms"
            placeholder="请输入权限标识"
            maxlength="100"
          />
          <span slot="label">
            <el-tooltip
              content="控制器中定义的权限字符，如：@PreAuthorize(hasAuthority('bnt.sysRole.list'))"
              placement="top"
            >
              <i class="el-icon-question"></i>
            </el-tooltip>
            权限字符
          </span>
        </el-form-item>

        <el-form-item label="菜单描述">
          <el-input v-model="sysMenu.menuDesc" />
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button
          @click="dialogVisible = false"
          size="small"
          icon="el-icon-refresh-right"
          >取消</el-button
        >
        <el-button @click="saveOrUpdate" size="small" icon="el-icon-check"
          >确定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>
  
  <script>
import { getList, getParentList, add, del, getById, update } from "@/api/menu";
export default {
  data() {
    return {
      listLoading: true,
      list: [],
      total: 0,
      page: 1,
      limit: 10,
      dialogTitle: "",
      currentPage: 1,
      parentNameDisabled: false,
      menuName: "",
      sysMenu: {},
      dialogVisible: false,
      options: [
        {
          menuId: 0,
          menuName: "顶层菜单",
        },
      ],
      typeDisabled: false,
      type0Disabled: false,
      type1Disabled: false,
      type2Disabled: false,
      sourceDisabled: false,
      source0Disabled: false,
      source1Disabled: false,
      iconList: [
        {
          class: "el-icon-s-tools",
        },
        {
          class: "el-icon-s-custom",
        },
        {
          class: "el-icon-setting",
        },
        {
          class: "el-icon-user-solid",
        },
        {
          class: "el-icon-s-help",
        },
        {
          class: "el-icon-phone",
        },
        {
          class: "el-icon-s-unfold",
        },
        {
          class: "el-icon-s-operation",
        },
        {
          class: "el-icon-more-outline",
        },
        {
          class: "el-icon-s-check",
        },
        {
          class: "el-icon-tickets",
        },
        {
          class: "el-icon-s-goods",
        },
        {
          class: "el-icon-document-remove",
        },
        {
          class: "el-icon-warning",
        },
        {
          class: "el-icon-warning-outline",
        },
        {
          class: "el-icon-question",
        },
        {
          class: "el-icon-info",
        },
      ],
    };
  },
  created() {
    this.fetchData();
  },
  methods: {
    editMenu(row) {
      this.dialogTitle = "修改菜单";

      this.sysMenu = Object.assign({}, row);
      this.typeDisabled = true;

      if (this.sysMenu.parentId === 0) {
        this.sysMenu.parentName = "顶层菜单";
        this.dialogVisible = true;
      } else {
        getById(row.parentId).then((res) => {
          if (res.status === 200) {
            this.sysMenu.parentName = res.data.menuName;
            this.dialogVisible = true;
          }
        });
      }
    },
    delMenu(row) {
      if (row.children.length > 0) {
        return this.$message({
          type: "error",
          message: "该菜单下有子节点,无法删除",
        });
      }
      this.$confirm("确定要删除吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        del(row.menuId).then((res) => {
          if (res.status === 200) {
            this.fetchData();
            this.$message({
              type: "success",
              message: "删除成功!",
            });
          }
        });
      });
    },
    addSubMenu(row) {
      this.sysMenu = {};
      this.typeDisabled = false;
      this.dialogTitle = "添加下级节点";
      this.dialogVisible = true;

      //this.sysMenu = Object.assign({}, defaultForm)

      if (row.menuId) {
        this.sysMenu.parentId = row.menuId;
        this.sysMenu.parentName = row.menuName;
        //this.sysMenu.component = 'ParentView'
        if (row.menuType === 3) {
          this.typeDisabled = true;
          this.type0Disabled = true;
          this.type1Disabled = true;
          this.type2Disabled = true;
        }
      } else {
        this.typeDisabled = false;
        this.type0Disabled = false;
        this.type1Disabled = false;
        this.type2Disabled = false;
        this.dialogTitle = "添加目录节点";
        //this.sysMenu.menuType = 1
        this.sysMenu.parentName = "顶层菜单";
        //this.sysMenu.component = 'Layout'
        this.sysMenu.parentId = 0;
        this.type2Disabled = true;
      }
    },
    menuTypeRadioChange(e) {
      //console.log(e)
      console.log(this.sysMenu.menuType);
    },
    saveOrUpdate() {
      if (!this.sysMenu.menuId) {
        console.log("新增");
        this.saveMenu();
      } else {
        console.log("修改");
        this.updateMenu();
      }
    },
    updateMenu() {
      update(this.sysMenu).then((res) => {
        if (res.status === 200) {
          this.dialogVisible = false;
          this.fetchData();
          this.$message({
            message: "修改菜单成功",
            type: "success",
          });
        }
      });
    },
    saveMenu() {
      add(this.sysMenu).then((res) => {
        if (res.status === 200) {
          this.dialogVisible = false;
          this.fetchData();
          this.$message({
            message: "添加菜单成功",
            type: "success",
          });
        }
      });
    },
    add() {
      this.sysMenu = {};
      getParentList().then((res) => {
        if (res.data.length) {
          this.options.push(...res.data);
        }
      });
      this.dialogVisible = true;
    },
    resetData() {
      this.sysMenu = {};
    },
    search() {
      this.fetchData();
    },
    handleSizeChange(val) {
      this.limit = val;
      this.fetchData();
    },
    handleCurrentChange(val) {
      this.fetchData(val);
    },
    fetchData(pageNum = 1) {
      this.page = pageNum;
      getList({
        page: this.page,
        limit: this.limit,
        menuName: this.menuName,
      })
        .then((res) => {
          if (res.status === 200) {
            console.log(res.data.records)
            this.listLoading = false;
            this.list = res.data.records;
            this.total = res.data.total;
            this.currentPage = res.data.current;
          }
        })
        .catch((e) => {});
    },
  },
};
</script>
  
  <style>
</style>