<template>
    <div style="padding: 15px;">
        <!--    搜索-->
        <div>
            <el-input v-model="search" placeholder="输入手机号码查询" style="width: 25% ;margin: 15px" clearable></el-input>
            <el-button type="primary" @click="load"> 查询</el-button>
            <el-button style="margin-left: 57%" type="text" @click="dialogVisible = true">说明</el-button>
        </div>
        <!--        表格      :header-cell-style="{textAlign: 'center'}"表头居中对齐   :cell-style="{ textAlign: 'center' }"表格内容居中对齐 -->
        <el-table
                :header-cell-style="{textAlign: 'center'}"
                :cell-style="{ textAlign: 'center' }"
                :data="allbusinesslist"
                height="704"
                border
                style="width: 98%">
            <el-table-column type="expand" >
                <template #default="scope" >
                    <p>密码：{{scope.row.password}}</p>
                </template>
            </el-table-column>
            <el-table-column prop="bid" label="商家编号" sortable></el-table-column>
            <el-table-column prop="truename" label="商家姓名"></el-table-column>
            <el-table-column prop="gender" label="性别" sortable>
                <template #default="scope">
                    <label v-if="scope.row.gender==1">男</label>
                    <label v-if="scope.row.gender==2">女</label>
                </template>
            </el-table-column>
            <el-table-column prop="phone" label="手机号码" sortable></el-table-column>
            <el-table-column prop="state" label="账号状态" sortable >
                <template #default="scope" >
                    <label v-if="scope.row.state==1"
                           style="color: #42b983;border: #42b983 1px solid;padding: 1px;border-radius: 10%;">通过</label>
                    <label v-if="scope.row.state==2"
                           style="color: mediumvioletred;border: mediumvioletred 1px solid;padding: 1px;border-radius: 10%">不通过</label>
                    <label v-if="scope.row.state==0"
                           style="color: dodgerblue;border: dodgerblue 1px solid;padding: 1px;border-radius: 10%">未审核</label>
                </template>
            </el-table-column>
            <el-table-column prop="shopid" label="店铺编号" sortable></el-table-column>
            <el-table-column prop="status" label="店铺状态" sortable >
                <template #default="scope" >
                    <label v-if="scope.row.status==1"
                           style="color: #42b983;border: #42b983 1px solid;padding: 1px;border-radius: 10%;">营业</label>
                    <label v-if="scope.row.status==2"
                           style="color: mediumvioletred;border: mediumvioletred 1px solid;padding: 1px;border-radius: 10%">休息</label>
                    <label v-if="scope.row.status==3"
                           style="color: dodgerblue;border: dodgerblue 1px solid;padding: 1px;border-radius: 10%">待租</label>
                    <label v-if="scope.row.status==null" style="color: red">店铺不存在</label>
                </template>
            </el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作">
                <template #default="scope">
                    <el-popconfirm
                            v-if="scope.row.state==1"
                            confirm-button-text='确认'
                            cancel-button-text='取消'
                            icon="el-icon-info"
                            icon-color="red"
                            title="确定注销吗？该商家的店铺也会转为待租状态"
                            @confirm="cancellation(scope.row.bid,scope.row.shopid)">
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
                <p style="padding-bottom: 15px">1、该页面显示所有的商家账号包括未审核以及不通过的，默认按商家账号状态排序（通过、不通过、未审核），然后是姓名拼音排序。</p>
                <p style="padding-bottom: 15px">2、该页面只有账号状态为通过的商家账号才能有"注销账号的操作按钮"，不通过或未审核的商家账号可以在审核管理中查看并进行相应操作。</p>
                <p style="padding-bottom: 35px">3、该界面除了商家姓名都能手动进行排序，还可以通过搜索框根据手机号码进行模糊查询。</p>
                <p style="padding-bottom: 35px">4、当有些店铺编号对应的店铺已经不存在的，在店铺状态一栏显示店铺不存在。</p>
                <el-button @click="dialogVisible = false">关闭</el-button>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import request from "../../utils/request";

    export default {
        name: "AdminBusiness",
        data(){
            return{
                dialogVisible: false,//可视化 ：关闭
                search: '',
                currentPage: 1,//当前页  默认第1页
                pageSize: 20,//默认每页5条数据
                total: 0,
                allbusinesslist: []
            }
        },
        created() {
            this.load()
        },
        methods:{
            load() {
                    request.get("/AllBusinessList/" + this.currentPage + "/" + this.pageSize + "/" + this.search).then(res => {
                        this.total = res.data.total
                        this.allbusinesslist = res.data.records
                        console.log("获取的所有business列表", res)
                    })
            },
//注销账号
            cancellation(bid,shopid){
                request.put("/recoverShop/"+shopid).then(res1=>{                //收回店铺,商家账号变为2不通过
                    request.delete("/DeleteFailBusiness/"+bid+"/"+shopid).then(res2=>{     //根据bid删除商家账号，条件是商家账号状态为2不通过
                        if(res2.code==='0'){
                            this.$message.success("注销商家账号成功，自动收回其对应店铺")
                            this.load()
                        }
                    })
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