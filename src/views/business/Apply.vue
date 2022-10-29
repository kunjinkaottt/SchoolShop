<template>
    <div>
        <el-button type="primary" @click="load" style="margin:1% 0 0 1%;background-color: #42b983; border:0">
            <i class="el-icon-refresh"></i> 刷新</el-button>
    <el-card v-if="this.applyList.length===0" style=" margin: 3%" >
        <el-empty description="没有申请记录"></el-empty>
    </el-card>
    <div v-else style="padding: 15px">
        <!--        表格      :header-cell-style="{textAlign: 'center'}"表头居中对齐   :cell-style="{ textAlign: 'center' }"表格内容居中对齐 -->
        <el-table
                :header-cell-style="{textAlign: 'center'}"
                :cell-style="{ textAlign: 'center' }"
                :data="applyList"
                height="704"
                border
                style="width: 98%">
            <el-table-column
                    type="index"
                    width="50"
                    label="序号">
            </el-table-column>
            <el-table-column prop="mid" label="用户编号"></el-table-column>
            <el-table-column prop="memberTruename" label="申请人姓名"></el-table-column>
            <el-table-column prop="gender" label="性别" sortable>
                <template #default="scope">
                    <label v-if="scope.row.gender==1">男</label>
                    <label v-if="scope.row.gender==2">女</label>
                </template>
            </el-table-column>
            <el-table-column prop="mphone" label="手机号码" ></el-table-column>
            <el-table-column prop="applytime" label="申请时间" width="600px" sortable></el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作">
                <template #default="scope">
                    <el-popconfirm
                            confirm-button-text='确认'
                            cancel-button-text='取消'
                            title="确定录用该用户为员工吗？"
                            @confirm="handlePassApply(scope.row.mid)">
                        <template #reference>
                            <el-button type="success" size="small">通过</el-button>
                        </template>
                    </el-popconfirm>
                    <el-popconfirm
                            confirm-button-text='确认'
                            cancel-button-text='取消'
                            icon="el-icon-info"
                            icon-color="red"
                            title="确定不通过吗？"
                            @confirm="handleFailApply(scope.row.mid)">
                        <template #reference>
                            <el-button type="danger" size="small">不通过</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <!--分页-->
        <div style="margin: 10px 0">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[10,15,20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>
    </div>
    </div>
</template>

<script>
    import request from "../../utils/request";
let business = sessionStorage.getItem("user")||{}
    export default {
        name: "Apply",
        data(){
            return{
                applyList:[],
                dialogVisible: false,//可视化 ：关闭
                currentPage: 1,
                pageSize: 5,
                total: 0,

            }
        },
        created() {
            this.load();
        },
        methods:{
            load(){
                let business = sessionStorage.getItem("user")||"{}"
                console.log("缓存中的商家登录信息"+JSON.parse(business).shopid)
                    request.get("/AllApplyList/"+this.currentPage+"/"+this.pageSize+"/" +JSON.parse(business).shopid).then(res=>{
                        this.total = res.data.total
                        this.applyList = res.data.records
                        console.log("获取该店铺的应聘申请apply列表",res.data)
                    })
            },
            handlePassApply(mid){
                request.put("/Passapply/"+mid+"/"+JSON.parse(business).shopid+"/"+JSON.parse(business).bid).then(res=>{
                    if(res.code==='0'){
                        this.$message.success("成功通过该应聘申请")
                        this.load()
                    }else {
                        this.$message.error(res.msg)
                    }
                })
                this.load()
            },
            handleFailApply(mid){
                request.put("/Failapply/"+mid+"/"+JSON.parse(business).shopid).then(res=>{
                    if(res.code==='0'){
                        this.$message({
                            type:"success",
                            message:"成功拒绝该应聘申请"
                        })
                        this.load()
                    }else {
                        this.$message({
                            type:"error",
                            message:res.msg
                        })
                    }
                })
                this.load()
            },

            //  改变每页记录条数
            handleSizeChange(pageSize) {
                this.pageSize = pageSize
                this.load()
            },
            //改变当前页码
            handleCurrentChange(currentPage) {
                this.currentPage = currentPage
                this.load()
            },
        }
    }
</script>

<style scoped>

</style>