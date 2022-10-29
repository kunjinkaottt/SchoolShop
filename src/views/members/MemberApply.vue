<template>

    <el-tabs v-model="activeTab" type="border-card">
<!--        <el-page-header @back="goBack" content="返回首页" />-->
        <el-tab-pane label="已通过的申请" name="passApply" >
            <el-button type="primary" @click="loadPassApply" style="margin:1% 0 0 1%;background-color: darkorange; border:0">
                <i class="el-icon-refresh"></i> 刷新</el-button>
            <el-card v-if="this.MemberPassApplyList.length===0" style="margin: 3%" >
                <el-empty description="你暂无通过的申请应聘"></el-empty>
            </el-card>
            <div v-else style="padding: 15px;">
                <!--    搜索-->
                <div>
                    <el-input v-model="passSearch" placeholder="输入店铺名或地址查询" style="width: 25% ;margin: 15px" clearable></el-input>
                    <el-button type="primary" @click="loadPassApply" style="background-color: darkorange;border:0"> 查询</el-button>
                </div>
                <!--        表格      :header-cell-style="{textAlign: 'center'}"表头居中对齐   :cell-style="{ textAlign: 'center' }"表格内容居中对齐 -->
                <el-table
                        :header-cell-style="{textAlign: 'center'}"
                        :cell-style="{ textAlign: 'center' }"
                        :data="MemberPassApplyList">
                    <el-table-column prop="shopid" label="店铺编号" width="100" sortable></el-table-column>
                    <el-table-column prop="shopname" label="店铺名称" ></el-table-column>
                    <el-table-column prop="businessTruename" label="商家姓名" ></el-table-column>
                    <el-table-column prop="bphone" label="商家手机号码" ></el-table-column>
                    <el-table-column prop="address" label="店铺地址" width="500"></el-table-column>
                    <el-table-column prop="applytime" label="申请时间"  sortable></el-table-column>
                    <el-table-column
                            fixed="right"
                            label="操作"
                            width="120">
                        <template #default="scope">
                            <el-button @click="ToShopDetail(scope.row.shopid)" type="primary" style="background-color: darkorange;border:0" size="small">
                                查看店铺详情
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <!--分页-->
                <div style="margin: 10px 0">
                    <el-pagination
                            @size-change="passSizeChange"
                            @current-change="passCurrentChange"
                            :current-page="passCurrentPage"
                            :page-sizes="[10,15,20]"
                            :page-size="passPageSize"
                            layout="total, sizes, prev, pager, next, jumper"
                            :total="passTotal">
                    </el-pagination>
                </div>
            </div>
        </el-tab-pane>

        <el-tab-pane label="处理中的申请" name="applying" >
            <el-card v-if="this.MemberApplyingList.length===0" style="margin: 3%" >
                <el-empty description="你暂无申请应聘的记录"></el-empty>
            </el-card>
            <div v-else style="padding: 15px;">
                    <!--    搜索-->
                    <div>
                        <el-input v-model="applyingSearch" placeholder="输入店铺名或地址查询" style="width: 25% ;margin: 15px" clearable></el-input>
                        <el-button type="primary" @click="loadApplying" style="background-color: darkorange;border:0"> 查询</el-button>
                    </div>
                    <!--        表格      :header-cell-style="{textAlign: 'center'}"表头居中对齐   :cell-style="{ textAlign: 'center' }"表格内容居中对齐 -->
                    <el-table
                            :header-cell-style="{textAlign: 'center'}"
                            :cell-style="{ textAlign: 'center' }"
                            :data="MemberApplyingList"
                            ref="table">
                        <el-table-column prop="shopid" label="店铺编号" width="120" sortable></el-table-column>
                        <el-table-column prop="shopname" label="店铺名称" ></el-table-column>
                        <el-table-column prop="businessTruename" label="商家姓名" ></el-table-column>
                        <el-table-column prop="bphone" label="商家手机号码" ></el-table-column>
                        <el-table-column prop="address" label="店铺地址" width="300"></el-table-column>
                        <el-table-column prop="applytime" label="申请时间" sortable></el-table-column>
                        <el-table-column
                                fixed="right"
                                label="操作" >
                            <template #default="scope">
                                <el-button @click="ToShopDetail(scope.row.shopid)" type="primary" style="background-color: darkorange;border:0" size="small">
                                    查看店铺
                                </el-button>
                                <el-popconfirm
                                        title="确定取消该申请吗？"
                                        @confirm="cancelApply(scope.row.applyid)">
                                    <template #reference>
                                        <el-button style="float: right"  type="danger"  size="small" >取消申请</el-button>
                                    </template>
                                </el-popconfirm>
                            </template>
                        </el-table-column>
                    </el-table>
                    <!--分页-->
                    <div style="margin: 10px 0">
                        <el-pagination
                                @size-change="applyingSizeChange"
                                @current-change="applyingCurrentChange"
                                :current-page="applyingCurrentPage"
                                :page-sizes="[10,15,20]"
                                :page-size="applyingPageSize"
                                layout="total, sizes, prev, pager, next, jumper"
                                :total="applyingTotal">
                        </el-pagination>
                    </div>
                </div>
        </el-tab-pane>

        <el-tab-pane label="不通过的申请" name="failApply" >
            <el-button type="primary" @click="loadFailApply" style="margin:1% 0 0 1%;background-color: darkorange; border:0">
                <i class="el-icon-refresh"></i> 刷新</el-button>
            <el-card v-if="this.MemberFailApplyList.length===0" style="margin: 3%" >
                <el-empty description="你没有不通过的应聘申请"></el-empty>
            </el-card>
            <div v-else style="padding: 15px;">
                <!--    搜索-->
                <div>
                    <el-input v-model="failSearch" placeholder="输入店铺名或地址查询" style="width: 25% ;margin: 15px" clearable></el-input>
                    <el-button type="primary" @click="loadFailApply" style="background-color: darkorange;border:0"> 查询</el-button>
                </div>
                <!--        表格      :header-cell-style="{textAlign: 'center'}"表头居中对齐   :cell-style="{ textAlign: 'center' }"表格内容居中对齐 -->
                <el-table
                        :header-cell-style="{textAlign: 'center'}"
                        :cell-style="{ textAlign: 'center' }"
                        :data="MemberFailApplyList">
                    <el-table-column prop="shopid" label="店铺编号" width="120" sortable></el-table-column>
                    <el-table-column prop="shopname" label="店铺名称" ></el-table-column>
                    <el-table-column prop="businessTruename" label="商家姓名" ></el-table-column>
                    <el-table-column prop="bphone" label="商家手机号码" ></el-table-column>
                    <el-table-column prop="address" label="店铺地址" width="300"></el-table-column>
                    <el-table-column prop="applytime" label="申请时间" sortable></el-table-column>
                    <el-table-column
                            fixed="right"
                            label="操作">
                        <template #default="scope">
                            <el-button @click="ToShopDetail(scope.row.shopid)" type="primary" style="background-color: darkorange;border:0" size="small">
                                查看店铺详情
                            </el-button>
                            <el-popconfirm
                                    title="确定删除这条申请记录吗？"
                                    @confirm="deleteFailApply(scope.row.applyid)">
                                <template #reference>
                                    <el-button   style="float: right"  type="danger"  size="small" >删除</el-button>
                                </template>
                            </el-popconfirm>
                        </template>
                    </el-table-column>
                </el-table>
                <!--分页-->
                <div style="margin: 10px 0">
                    <el-pagination
                            @size-change="failSizeChange"
                            @current-change="failCurrentChange"
                            :current-page="failCurrentPage"
                            :page-sizes="[10,15,20]"
                            :page-size="failPageSize"
                            layout="total, sizes, prev, pager, next, jumper"
                            :total="failTotal">
                    </el-pagination>
                </div>
            </div>
        </el-tab-pane>

<!--        <el-tab-pane label="测试" name="test" >
            测试一下  el-tab-pane 写几个就有几个Tab 可以切换显示
        </el-tab-pane>-->
    </el-tabs>

</template>

<script>
    import request from "../../utils/request";

    let members = sessionStorage.getItem("user") || "{}"

    export default {
        name: "MemberApply",
        data(){
            return{
                activeTab:'passApply',
            // 申请中
                MemberApplyingList:[],
                applyingCurrentPage:1,
                applyingPageSize:5,
                applyingSearch:'',
                applyingTotal:0,
            // 已通过
                MemberPassApplyList:[],
                passCurrentPage:1,
                passPageSize:10,
                passSearch:'',
                passTotal:0,
            // 不通过
                MemberFailApplyList:[],
                failCurrentPage:1,
                failPageSize:10,
                failSearch:'',
                failTotal:0,
            }
        },
        created() {
            this.loadApplying()
            this.loadPassApply()
            this.loadFailApply()
        },
       /* mounted(){
            this.$nextTick(() => {
                // 初始化表格
                this.$refs.table.doLayout();
            })
        },*/
        methods:{
            loadApplying(){ //获取处理中的申请
                request.get("/MemberApplyList/"+this.applyingCurrentPage+"/"+this.applyingPageSize+"/0/"+JSON.parse(members).mid+"/"+this.applyingSearch).then(res=>{
                    console.log(res.data.records)
                    this.applyingTotal = res.data.total
                    this.MemberApplyingList = res.data.records
                })
            },
            loadPassApply(){ //获取申请通过的记录
                request.get("/MemberApplyList/"+this.passCurrentPage+"/"+this.passPageSize+"/1/"+JSON.parse(members).mid+"/"+this.passSearch).then(res=>{
                    this.passTotal = res.data.total
                    this.MemberPassApplyList = res.data.records
                })
            },
            loadFailApply(){ //获取申请不通过的记录
                request.get("/MemberApplyList/"+this.failCurrentPage+"/"+this.failPageSize+"/2/"+JSON.parse(members).mid+"/"+this.failSearch).then(res=>{
                    this.failTotal = res.data.total
                    this.MemberFailApplyList = res.data.records
                })
            },

        // 跳转到店铺详情界面
            ToShopDetail(shopid){
                this.$router.push({ path: '/members/membersShopDetail', query: {shopid:shopid,activeTab:"ShopDetail"} })
            },
            //取消申请
            cancelApply(applyid){
                request.delete("/CancelApply/"+applyid).then(res=>{
                    console.log("cancel apply result:",res)
                    if(res.code==='0'){//结果是Boolean值 删除成功返回true
                        this.$message.info("取消申请成功")
                        this.loadApplying()
                    }else{
                        this.$message.error(res.msg)
                    }
                })
                this.loadApplying()
            },
            //删除申请记录
            deleteFailApply(applyid){
                request.delete("/DeleteFailApply/"+applyid).then(res=>{
                    if (res.code==='0'){
                        this.$message.success("删除成功")
                        this.loadFailApply()
                    }else
                        this.$message.error(res.msg)
                })
            },
            //  申请中-改变每页记录条数
            applyingSizeChange(pageSize) {
                this.applyingPageSize = pageSize
                this.loadApplying()
            },
            //  申请中-改变当前页码
            applyingCurrentChange(currentPage) {
                this.applyingCurrentPage = currentPage
                this.loadApplying()
            },
            //  已通过-改变每页记录条数
            passSizeChange(pageSize) {
                this.passPageSize = pageSize
                this.loadPassApply()
            },
            //  已通过-改变当前页码
            passCurrentChange(currentPage) {
                this.passCurrentPage = currentPage
                this.loadPassApply()
            },
            //  不通过-改变每页记录条数
            failSizeChange(pageSize) {
                this.failPageSize = pageSize
                this.loadFailApply()
            },
            //  不通过-改变当前页码
            failCurrentChange(currentPage) {
                this.failCurrentPage = currentPage
                this.loadFailApply()
            },
        }
    }
</script>

<style scoped>
    .el-table{
        height: 100% !important;
    }
</style>