<template>
  <div class="app-container">
    角色列表

    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-col :span="24">
            <el-form-item label="角色名称">
              <el-input
                style="width: 100%"
                v-model="roleName"
                placeholder="角色名称"
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
    </div>

    <div class="tools-div">
      <el-button type="success" icon="el-icon-plus" size="mini" @click="add"
        >添加</el-button
      >
    </div>

    <el-table
      v-loading="listLoading"
      :data="list"
      stripe
      border
      style="width: 100%; margin-top: 10px"
    >
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="roleName" label="角色名称" />
      <el-table-column prop="roleCode" label="角色编码" />
      <el-table-column prop="roleDesc" label="角色描述" />
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            @click="editRole(scope.row.roleId)"
          ></el-button>
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            @click="delRole(scope.row.roleId)"
          ></el-button>
          <el-button
            type="warning"
            icon="el-icon-baseball"
            size="mini"
            @click="showAssignAuth(scope.row)"
            title="分配权限"
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

    <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="40%">
      <el-form
        ref="dataForm"
        :model="sysRole"
        label-width="150px"
        size="small"
        style="padding-right: 40px"
      >
        <el-form-item label="角色名称">
          <el-input v-model="sysRole.roleName" />
        </el-form-item>

        <el-form-item label="角色编码">
          <el-input v-model="sysRole.roleCode" />
        </el-form-item>

        <el-form-item label="排序">
          <el-input v-model="sysRole.seq" />
        </el-form-item>

        <el-form-item label="角色描述">
          <el-input v-model="sysRole.roleDesc" />
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
import {
  getList,
  add,
  getById,
  update,
  del
} from "@/api/role";
export default {
  data() {
    return {
      listLoading: true,
      list: [],
      roleName: "",
      total: 0,
      page: 1,
      limit: 3,
      currentPage:1,
      sysRole:{},
      dialogVisible:false
    };
  },
  created() {
    this.fetchData();
  },
  methods: {
    showAssignAuth(row){
      this.$router.push({path:`/system/assignAuth?roleId=${row.roleId}&roleName=${row.roleName}`});
    },
    saveOrUpdate() {
      if (!this.sysRole.roleId) {
        this.saveRole();
      } else {
        this.updateRole();
      }
    },
    saveRole() {
      add(this.sysRole).then((res) => {
        if (res.status === 200) {
          this.dialogVisible = false;
          this.fetchData();
          this.$message({
            message: "添加角色成功",
            type: "success",
          });
        }
      });
    },
    updateRole() {
      update(this.sysRole).then((res) => {
        if (res.status === 200) {
          this.dialogVisible = false;
          this.fetchData();
          this.$message({
            message: "修改角色成功",
            type: "success",
          });
        }
      });
    },
    add() {
      this.sysRole = {};
      this.dialogVisible = true;
    },
    editRole(id) {
      getById(id).then((res) => {
        this.sysRole = res.data;
        this.dialogVisible = true;
      });
    },
    delRole(id) {
      this.$confirm("确定要删除吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        del(id).then((res) => {
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
    resetData(){
        this.roleName = ''
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
      getList({ page: this.page, limit: this.limit, roleName: this.roleName })
        .then((res) => {
          if (res.status === 200) {
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