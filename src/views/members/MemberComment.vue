<template>
    <el-card style="width: 70%; margin: 3%" v-if="MyCommentList.length===0">
        <el-empty description="你暂无评论记录"></el-empty>
    </el-card>
    <div v-else style="padding: 15px;">
        <!--    搜索-->
        <div>
            <el-input v-model="search" placeholder="输入评论内容查询" style="width: 25% ;margin: 15px" clearable></el-input>
            <el-button type="primary" @click="load" style="background-color: darkorange; border:0"> 查询</el-button>
        </div>
        <!--        表格      :header-cell-style="{textAlign: 'center'}"表头居中对齐   :cell-style="{ textAlign: 'center' }"表格内容居中对齐 -->
        <el-table
                :header-cell-style="{textAlign: 'center'}"
                :cell-style="{ textAlign: 'center' }"
                :data="MyCommentList"
                height="704"
                style="width: 98%;">
            <el-table-column prop="shopid" label="店铺编号" width="120" sortable></el-table-column>
            <!--<el-table-column prop="photo" label="图片" width="150">
                <template #default="scope">
                    <el-image
                            style="width: 70px; height: 70px;padding: 0"
                            :src="scope.row.photo"
                            :preview-src-list="[scope.row.photo]">
                        <template #error>
                            没有图片
                        </template>
                    </el-image>
                </template>
            </el-table-column>-->
            <el-table-column prop="shopname" label="店铺名称" >
                <template #default="scope">
                <label>{{scope.row.shopname===null?'(该店铺商家暂未填写)':scope.row.shopname}}</label></template>
            </el-table-column>
            <el-table-column prop="comment" label="评论内容"  width="550"></el-table-column>
            <el-table-column prop="grade" label="评分" sortable="">
                <template #default="scope">
                    <label style="color:darkorange ">{{scope.row.grade}}分</label>
                </template>
            </el-table-column>
            <el-table-column prop="ctime" label="评论时间" width="280" sortable></el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作"
                    width="180">
                <template #default="scope">
                        <el-button
                                @click="ToShopDetail(scope.row.shopid)"
                                type="primary" size="small"
                                style="background-color: darkorange;border:0">
                            查看
                        </el-button>
                        <el-popconfirm
                                title="确定删除这条评论吗？"
                                @confirm="deleteMyComment(scope.row.cid)">
                            <template #reference>
                                <el-button type="danger"  size="small" >删除  </el-button>
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
        </div>
    </div>
</template>

<script>
    import request from "../../utils/request";
    let members = sessionStorage.getItem("user") || "{}"

    export default {
        name: "MemberComment",
        data(){
            return{
                MyCommentList:[],
                total: 0,
                currentPage:1,
                pageSize:10,
                search:'',

            }
        },
        created() {
            this.load()
        },
        methods:{
            load(){
                request.get("/MyCommentList/"+this.currentPage+"/"+this.pageSize+"/"+JSON.parse(members).mid+"/"+this.search).then(res=>{
                    this.total = res.data.total
                    this.MyCommentList = res.data.records
                })
            },
            //跳转到详情界面
            ToShopDetail(shopid) {
                //row.shopid 根据店铺id查 跳转到店铺详情界面
                this.$router.push({ path: '/members/membersShopDetail', query: {shopid:shopid,activeTab:"ShopComment"} })
            },
            //删除评论
            deleteMyComment(cid){
                request.delete("/DeleteMyComment/"+cid).then(res=>{
                    if (res.code==='0'){
                        this.$message.success("删除成功")
                        this.load()
                    }else {
                        this.$message.error(res.msg)
                    }
                })
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