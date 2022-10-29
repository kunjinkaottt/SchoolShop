<template>
    <div style="padding: 15px;">
        <!--    搜索-->
        <div>
            <el-input v-model="search" placeholder="输入手机号码查询" style="width: 25% ;margin: 15px" clearable></el-input>
            <el-button type="primary" @click="load()"> 查询</el-button>
            <el-button style="margin-left: 57%" type="text" @click="dialogVisible = true">说明</el-button>
        </div>
        <!--        表格      :header-cell-style="{textAlign: 'center'}"表头居中对齐   :cell-style="{ textAlign: 'center' }"表格内容居中对齐 -->
        <el-table
                :header-cell-style="{textAlign: 'center'}"
                :cell-style="{ textAlign: 'center' }"
                :data="allmembers"
                height="704"
                border
                style="width: 98%"
                :default-sort="{ prop: 'mid', order: 'ascending' }">    <!-- 默认mid升序排序 -->
            <el-table-column type="expand" >
                <template #default="scope" >
                    <p v-if="scope.row.shopid!==0">在职店铺编号: {{ scope.row.shopid }}</p>
                    <p v-if="scope.row.shopid!==0">在职店铺名称: {{ scope.row.shopname }}</p>
                    <p v-if="scope.row.shopid!==0">在职店铺商家编号: {{ scope.row.bid }}</p>
                    <p v-if="scope.row.shopid!==0">在职店铺商家姓名: {{ scope.row.businessName }}</p>
                    <p v-if="scope.row.shopid!==0">在职店铺地址: {{ scope.row.address }}</p>
<!--                    <hr align=center style="width:200px" color=#987cb9 SIZE=1/>-->
                    <p>该账号的密码：{{scope.row.password}}</p>
                </template>
            </el-table-column>
            <el-table-column prop="mid" label="用户编号" sortable></el-table-column>
          <!--  <el-table-column label="在职店铺编号" >
                <template #default="scope" >
                    <el-popover effect="light" trigger="hover" placement="top" v-if="scope.row.shopid!==0">
                        <template #default >
                        <p style="width: 300px;">店铺名称: {{ scope.row.shopname }}</p>
                        <p style="width: 300px;">商家编号: {{ scope.row.bid }}</p>
                        <p style="width: 300px;">商家姓名: {{ scope.row.businessName }}</p>
                        </template>
                        <template #reference>
                        <div class="name-wrapper">
                            <el-tag size="medium">{{ scope.row.shopid }}</el-tag>
                        </div>
                        </template>
                    </el-popover>
                </template>
            </el-table-column>-->
            <el-table-column prop="truename" label="真实姓名"></el-table-column>
            <el-table-column prop="gender" label="性别" sortable>
                <template #default="scope">
                    <label v-if="scope.row.gender==1">男</label>
                    <label v-if="scope.row.gender==2">女</label>
                </template>
            </el-table-column>
            <el-table-column prop="phone" label="手机号码" sortable></el-table-column>
            <el-table-column prop="username" label="用户名" sortable ></el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作">
                <template #default="scope">
                    <el-popconfirm
                            confirm-button-text='确认'
                            cancel-button-text='取消'
                            icon="el-icon-info"
                            icon-color="red"
                            title="确定注销吗？该用户的所有信息将被删除"
                            @confirm="cancellation(scope.row.mid)">
                        <template #reference>
                            <el-button type="danger" size="small">注销账号</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <div style="margin: 10px 0">
            <!--分页-->
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[10,20,40]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
            <!--            说明-->
            <el-dialog
                    title="使用说明" v-model="dialogVisible" width="30%">
                <p style="padding-bottom: 15px">1、该页面显示所有的普通用户账号，默认按用户编号顺序排序，取消选择之后按姓名拼音排序，另外性别，手机号码，用户名也可以排序。</p>
                <p style="padding-bottom: 15px">2、该页面表格左边的箭头可以看到该用户的密码，如果该用户是某个店铺的在职员工，则显示其在职店铺编号、店铺名、商家编号、商家姓名和店铺地址。</p>
                <p style="padding-bottom: 35px">3、"注销账号"操作可以将表格中用户删除，同时对应店铺的员工表中对应员工记录以及该用户在申请表中的记录也会被删除</p>
<!--                <p style="padding-bottom: 35px">4、表格中的在职店铺编号，如果用户是某店铺员工则显示店铺编号否则为空，把鼠标悬停在店铺编号上可以看到店铺名，商家编号和姓名。</p>-->
                <el-button @click="dialogVisible = false">关闭</el-button>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import request from "../../utils/request";

    export default {
        name: "AdminMembers",
        data(){
            return{
                dialogVisible: false,//可视化 ：关闭
                search: '',
                currentPage: 1,//当前页  默认第1页
                pageSize: 20,//默认每页5条数据
                total: 0,
                allmembers: []
            }
        },
        created() {
            this.load()
        },
        methods:{
            load() {
                    request.get("/AllMembers/" + this.currentPage + "/" + this.pageSize + "/" + this.search).then(res => {
                        this.total = res.data.total
                        this.allmembers = res.data.records
                        console.log("获取的所有members列表", res)
                    })
            },

        //注销账号
            cancellation(mid){
                request.delete("/DeleteMembers/"+mid).then(res=>{
                    if(res.code==='0'){
                        this.$message({
                            type:"success",
                            message:"注销成功，其收藏、评论记录等信息自动删除"
                        })
                    }
                    this.load()
                })
                this.load()
            },
        //  改变每页记录条数
            handleSizeChange(pageSize) {
                this.pageSize = pageSize
                this.load()
            },
            handleCurrentChange(currentPage) {//改变当前页码
                this.currentPage = currentPage
                this.load()
            }
        }
    }
</script>

<style scoped>

</style>