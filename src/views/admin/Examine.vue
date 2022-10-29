<template>

    <div style="padding: 15px;">
        <!--    搜索-->
        <div>
            <el-button type="primary" @click="load"><i class="el-icon-refresh-right"/> 刷新</el-button>
            <el-input v-model="search" placeholder="输入手机号码查询" style="width: 25% ;margin: 15px" clearable></el-input>
            <el-button type="primary" @click="load"> 查询</el-button>
            <el-button style="margin-left: 57%" type="text" @click="dialogVisible = true">说明</el-button>
        </div>
        <div v-if="examining.length===0">
            <el-card style="width: 96%; margin: 1%" >
                <el-empty description="暂无数据"></el-empty>
            </el-card>
        </div>
        <div v-else>
            <!--        表格      :header-cell-style="{textAlign: 'center'}"表头居中对齐   :cell-style="{ textAlign: 'center' }"表格内容居中对齐 -->
            <el-table
                    :header-cell-style="{textAlign: 'center'}"
                    :cell-style="{ textAlign: 'center' }"
                    :data="examining"
                    height="604"
                    border
                    style="width: 98%">
                <el-table-column prop="bid" label="商家编号" sortable></el-table-column>
                <el-table-column prop="truename" label="商家姓名"></el-table-column>
                <el-table-column prop="gender" label="性别" sortable>
                    <template #default="scope">
                        <label v-if="scope.row.gender==1">男</label>
                        <label v-if="scope.row.gender==2">女</label>
                    </template>
                </el-table-column>
                <el-table-column prop="phone" label="手机号码" sortable></el-table-column>
                <el-table-column prop="shopid" label="申请的店铺编号" sortable></el-table-column>
                <el-table-column prop="address" label="申请店铺的地址" width="650px"></el-table-column>
                <el-table-column
                        fixed="right"
                        label="操作"
                        width="180">
                    <template #default="scope">
                        <el-popconfirm
                                confirm-button-text='确认'
                                cancel-button-text='取消'
                                title="确定通过该商家的注册申请吗？"
                                @confirm="pass(scope.row.bid,scope.row.shopid)">
                            <template #reference>
                                <el-button type="success" size="small">通过</el-button>
                            </template>
                        </el-popconfirm>
                        <el-popconfirm
                                confirm-button-text='确认'
                                cancel-button-text='取消'
                                icon="el-icon-info"
                                icon-color="red"
                                title="确定不通过该商家的注册申请吗？"
                                @confirm="fail(scope.row.bid,scope.row.shopid)">
                            <template #reference>
                                <el-button type="danger" size="small">不通过</el-button>
                            </template>
                        </el-popconfirm>
                    </template>
                </el-table-column>
            </el-table>
            <!--分页-->
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[5,10,20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>
        <div style="margin: 10px 0">
            <!--            说明-->
            <el-dialog
                    title="使用说明" v-model="dialogVisible" width="30%">
                <p style="padding-bottom: 15px">1、该页面是未审核的商家申请界面，默认按商家姓名拼音排序，同名则按店铺地址拼音排序显示。</p>
                <p style="padding-bottom: 15px">
                    2、该页面只显示商家账号状态为"未审核"的商家，如果该申请的待租店铺被删除，则申请该店铺的所有商家以不通过申请处理，自动转移到"不通过的商家申请"界面。</p>
                <p style="padding-bottom: 35px">3、如果有多个商家申请同一家店铺，管理员只能通过其中一个商家，一旦通过一个，其他商家也将自动以不通过处理。</p>
                <el-button @click="dialogVisible = false">关闭</el-button>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import request from "../../utils/request";
    import {ElConfigProvider} from "element-plus";

    export default {
        name: "Examine",
        components: {
            ElConfigProvider
        },
        data() {
            return {
                dialogVisible: false,//可视化 ：关闭
                tangchuang: {},
                search: '',
                currentPage: 1,//当前页  默认第1页
                pageSize: 10,
                total: 0,
                examining: []
            }
        },
        created() {
            this.load()
        },
        methods: {
            load() {
                request.get("/ExamineList/" + this.currentPage + "/" + this.pageSize + "/" + this.search).then(res => {
                    this.total = res.data.total
                    this.examining = res.data.records
                    console.log("获取的ExamineBusinessList列表", res)
                })
            },

            pass(bid, shopid) {  //通过 商家的注册申请
                request.put("/PassBusiness/" + bid + "/" + shopid).then(res => {
                    if (res.code === '0') {
                        this.$message({
                            type: "success",
                            message: "成功通过该商家的注册申请"
                        })
                        this.load()
                    } else {
                        this.$message({
                            type: "error",
                            message: res.msg
                        })
                        this.load()
                    }
                })
                this.load()
            },
            fail(bid, shopid) {   //不通过
                request.put("/FailBusiness/" + bid + "/" + shopid).then(res => {
                    if (res.code === '0') {
                        this.$message({
                            type: "success",
                            message: "拒绝通过该商家的注册申请"
                        })
                        this.load()
                    } else {
                        this.$message({
                            type: "error",
                            message: res.msg
                        })
                        this.load()
                    }
                })
                this.load()
            },
            handleSizeChange(pageSize) {//  改变每页记录条数
                this.pageSize = pageSize
                this.load()
            },
            handleCurrentChange(currentPage) {//改变当前页码
                this.currentPage = currentPage
                this.load()
            },
        }
    }
</script>

<style scoped>

</style>