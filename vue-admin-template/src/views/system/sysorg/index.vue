<template>
  <div class="app-container">
    机构列表

    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-col :span="24">
            <el-form-item label="机构名称">
              <el-input
                style="width: 100%"
                v-model="orgName"
                placeholder="机构名称"
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
      style="width: 100%; margin-top: 10px"
      row-key="orgId"
      stripe
      border
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="orgName" label="机构名称" sortable>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" sortable>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            @click="editOrg(scope.row.orgId)"
          ></el-button>
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            @click="delOrg(scope.row)"
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
        :model="sysOrg"
        label-width="150px"
        size="small"
        style="padding-right: 40px"
      >
        <el-form-item label="机构名称">
          <el-input v-model="sysOrg.orgName" />
        </el-form-item>

        <el-form-item label="所属机构">
          <el-select v-model="sysOrg.parentId" filterable placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.orgId"
              :label="item.orgName"
              :value="item.orgId"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="排序">
          <el-input v-model="sysOrg.seq" />
        </el-form-item>

        <el-form-item label="机构描述">
          <el-input v-model="sysOrg.orgDesc" />
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
import { getList, getParentList, add, getById, update, del } from "@/api/org";
export default {
  data() {
    return {
      listLoading: true,
      list: [],
      total: 0,
      page: 1,
      limit: 10,
      currentPage: 1,
      orgName: "",
      options: [
        {
          orgId: 0,
          orgName: "顶层机构",
        },
      ],
      value: "",
      sysOrg: {},
      dialogVisible: false,
    };
  },
  created() {
    this.fetchData();
  },

  methods: {
    search(){
        this.fetchData();
    },
    handleSizeChange(val) {
      this.limit = val;
      this.fetchData();
    },
    handleCurrentChange(val) {
      this.fetchData(val);
    },
    saveOrUpdate() {
      if (!this.sysOrg.orgId) {
        this.saveOrg();
      } else {
        this.updateOrg();
      }
    },
    updateOrg() {
      update(this.sysOrg).then((res) => {
        if (res.status === 200) {
          this.dialogVisible = false;
          this.fetchData();
          this.$message({
            message: "修改机构成功",
            type: "success",
          });
        }
      });
    },
    saveOrg() {
      add(this.sysOrg).then((res) => {
        if (res.status === 200) {
          this.dialogVisible = false;
          this.fetchData();
          this.$message({
            message: "添加机构成功",
            type: "success",
          });
        }
      });
    },
    delOrg(row) {
      if (row.children) {
        return this.$message({
          type: "error",
          message: "该机构下有子节点,无法删除",
        });
      }
      this.$confirm("确定要删除吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        del(row.orgId).then((res) => {
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
    editOrg(id) {
      this.options = [
        {
          orgId: 0,
          orgName: "顶层机构",
        },
      ];
      getById(id).then((res) => {
        this.sysOrg = res.data;
        getParentList().then((resp) => {
          if (resp.data.length) {
            this.options.push(...resp.data);
           
            this.dialogVisible = true;
          }
        });
      });
    },
    add() {
      this.sysOrg = {};
      getParentList().then((res) => {
        if (res.data.length) {
          this.options.push(...res.data);
        }
      });
      this.dialogVisible = true;
    },
    resetData() {this.orgName = ''},
    fetchData(pageNum = 1) {

      this.page = pageNum;
      getList({ page: this.page, limit: this.limit, orgName: this.orgName })
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