<template>
  <div class="app-container">

    角色列表

    <div class="search-div">
        <el-form label-width="70px" size="small">
            <el-row>
                <el-col :span="24">
                    <el-form-item label="角色名称">
                        <el-input style="width: 100%;" v-model="searchObj.roleName" placeholder="角色名称"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row style="display: flex;">
                <el-button type="primary" icon="el-icon-search" size="mini" @click="fetchData()">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetData">重置</el-button>
            </el-row>
        </el-form>
    </div>

    <el-table
        v-loading="listLoading"
        :data="list"
        stripe
        border
        style="width: 100%;margin-top: 10px;">

        <el-table-column
            label="序号"
            width="70"
            align="center">
            <template slot-scope="scope">
                {{ (page-1) * limit + scope.$index+1 }}
            </template>
        </el-table-column>

        <el-table-column prop="roleName" label="角色名称" />
        <el-table-column prop="roleCode" label="角色编码" />
        <el-table-column prop="createTime" label="创建时间" />
        <el-table-column label="操作" width="200" align="center">
            <template slot-scope="scope">
                <el-button type="primary" icon="el-icon-edit" size="mini" @click="editRole"></el-button>
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="delRole"></el-button>
            </template>
        </el-table-column>
    </el-table>

    <el-pagination 
        :current-page="page"
        :total="total"
        :page-size="limit"
        style="padding: 30px 0;text-align: center" 
        layout="total,prev,pager,next,jumper"
        @current-change="fetchData"/>

  </div>
</template>

<script>
import {getList} from '@/api/role'
export default {
    data(){
        return{
            listLoading:true,
            list:[],
            total:0,
            page:1,
            limit:3,
            search:{}
        }
    },
    created(){
        this.fetchData();
    },
    methods:{
        fetchData(pageNum = 1){
            this.page = pageNum;
            getList({page:this.page,limit:this.limit,search:this.search}).then(res=>{

            }).catch(e=>{

            })
        }
    }

}
</script>

<style>

</style>