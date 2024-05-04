<template>
  <div class="app-container">
    用户列表

    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-col :span="24" style="display: flex">
            <el-form-item label="用户名称">
              <el-input
                v-model="sysUser.userName"
                placeholder="用户名称"
              ></el-input>
            </el-form-item>

            <el-form-item label="用户手机">
              <el-input
                v-model="sysUser.phone"
                placeholder="用户名称"
              ></el-input>
            </el-form-item>

            <el-form-item label="用户类型">
              <el-select v-model="sysUser.type" filterable placeholder="请选择">
                <el-option
                  v-for="item in typeArr"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="所属机构">
              <el-select
                v-model="sysUser.orgId"
                filterable
                placeholder="请选择"
              >
                <el-option
                  v-for="item in options"
                  :key="item.orgId"
                  :label="item.orgName"
                  :value="item.orgId"
                >
                </el-option>
              </el-select>
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

      <el-table-column prop="userName" label="用户名称" />
      <el-table-column prop="orgId" label="所属机构" />
      <el-table-column prop="type" label="用户类型" />
      <el-table-column prop="phone" label="用户手机号" />
      <el-table-column label="状态">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.userStatus"
            :active-value="1"
            :inactive-value="2"
            @change="switchStatus(scope.row)"
          >
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="userDesc" label="描述" />
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            title="编辑"
            @click="editUser(scope.row.userId)"
          ></el-button>
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            title="删除"
            @click="delUser(scope.row.userId)"
          ></el-button>
          <el-button
            type="success"
            icon="el-icon-setting"
            size="mini"
            title="授权"
            @click="showAssignRole(scope.row)"
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
        :model="sysUser"
        label-width="150px"
        size="small"
        style="padding-right: 40px"
      >
        <el-form-item label="用户名称">
          <el-input v-model="sysUser.userName" />
        </el-form-item>

        <el-form-item label="用户手机">
          <el-input v-model="sysUser.phone" />
        </el-form-item>

        <el-form-item label="所属机构">
          <el-select v-model="sysUser.orgId" filterable placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.orgId"
              :label="item.orgName"
              :value="item.orgId"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="用户类型">
          <el-select v-model="sysUser.type" filterable placeholder="请选择">
            <el-option
              v-for="item in typeArr"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="用户描述">
          <el-input v-model="sysUser.userDesc" />
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

    <el-dialog title="分配角色" :visible.sync="dialogRoleVisible">
      <el-form label-width="80px">
        <el-form-item label="用户名">
          <el-input disabled :value="sysUser.userName"></el-input>
        </el-form-item>

        <el-form-item label="角色列表">
          <el-checkbox
            :indeterminate="isIndeterminate"
            v-model="checkAll"
            @change="handleCheckAllChange"
            >全选</el-checkbox
          >
          <div style="margin: 15px 0"></div>
          <el-checkbox-group
            v-model="userRoleIds"
            @change="handleCheckedChange"
          >
            <el-checkbox
              v-for="role in allRoles"
              :key="role.roleId"
              :label="role.roleId"
              >{{ role.roleName }}</el-checkbox
            >
          </el-checkbox-group>
        </el-form-item>
      </el-form>

      <div slot="footer">
        <el-button type="primary" @click="assignRole" size="small"
          >授权</el-button
        >
        <el-button @click="dialogRoleVisible = false" size="small"
          >取消</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>
  
  <script>
import {
  getList,
  add,
  getById,
  update,
  del,
  userStatus,
} from "@/api/user/index";
import { getRolesByUserId, assignRoles } from "@/api/role";
import { getAllList } from "@/api/org";
export default {
  data() {
    return {
      typeArr: [
        { value: 1, label: "自有人员" },
        { value: 2, label: "管理人员" },
        { value: 3, label: "普通用户" },
      ],
      options: [],
      listLoading: true,
      dialogVisible: false,
      list: [],
      total: 0,
      currentPage: 1,
      page: 1,
      limit: 3,
      sysUser: {},
      dialogRoleVisible: false,
      checkAll: false,
      isIndeterminate: false,
      userRoleIds: [],
      allRoles: [],
    };
  },
  created() {
    this.fetchData();
    this.allOrg();
  },
  methods: {
    handleCheckedChange(value) {
      const { userRoleIds, allRoles } = this;
      this.checkAll =
        userRoleIds.length == allRoles.length && allRoles.length > 0;
      this.isIndeterminate =
        userRoleIds.length > 0 && userRoleIds.length < allRoles.length;
    },
    handleCheckAllChange(value) {
      this.userRoleIds = value ? this.allRoles.map((item) => item.roleId) : [];
      this.isIndeterminate = false;
    },
    showAssignRole(row) {
      this.sysUser = row;
      getRolesByUserId(row.userId).then((res) => {
        if (res.status === 200) {
          this.userRoleIds = res.data.userRoleIds;
          this.allRoles = res.data.allRoles;
          this.checkAll = this.userRoleIds.length === this.allRoles.length;
          this.isIndeterminate =
            this.userRoleIds.length > 0 &&
            this.userRoleIds.length < this.allRoles.length;
          this.dialogRoleVisible = true;
        }
      });
    },
    assignRole() {
      let assginRoleDTO = {
        userId: this.sysUser.userId,
        roleIdList: this.userRoleIds,
      };

      assignRoles(assginRoleDTO).then((res) => {
        if (res.status === 200) {
          this.$message({
            message: "授权成功",
            type: "success",
          });
          this.dialogRoleVisible = false;
          this.fetchData(this.page);
        }
      });
    },
    switchStatus(row) {
      userStatus(row.userId, row.userStatus).then((res) => {
        if (res.status === 200 && row.userStatus === 1) {
          this.$message({
            message: "启用成功",
            type: "success",
          });
        } else if (res.status === 200 && row.userStatus === 2) {
          this.$message({
            message: "禁用成功",
            type: "success",
          });
        }
      });
    },
    saveOrUpdate() {
      if (!this.sysUser.userId) {
        this.saveUser();
      } else {
        this.updateUser();
      }
    },
    saveUser() {
      add(this.sysUser).then((res) => {
        if (res.status === 200) {
          this.dialogVisible = false;
          this.fetchData();
          this.$message({
            message: "添加用户成功",
            type: "success",
          });
        }
      });
    },
    updateUser() {
      update(this.sysUser).then((res) => {
        if (res.status === 200) {
          this.dialogVisible = false;
          this.fetchData();
          this.$message({
            message: "修改用户成功",
            type: "success",
          });
        }
      });
    },
    add() {
      this.sysUser = {};
      this.dialogVisible = true;
    },
    delUser(id) {
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
    resetData() {
      this.sysUser = {};
    },
    allOrg() {
      getAllList().then((res) => {
        if (res.status === 200) {
          this.options = res.data;
        }
      });
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
    editUser(id) {
      getById(id).then((res) => {
        this.sysUser = res.data;
        this.dialogVisible = true;
      });
    },
    fetchData(pageNum = 1) {
      this.page = pageNum;
      getList({
        page: this.page,
        limit: this.limit,
        orgId: this.sysUser.orgId,
        phone: this.sysUser.phone,
        userName: this.sysUser.userName,
        type: this.sysUser.type,
      })
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