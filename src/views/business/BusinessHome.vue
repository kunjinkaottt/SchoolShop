<template>
    <div style="padding: 15px;">
        <!--    搜索-->
        <div>
            <el-input v-model="search" placeholder="输入店铺名或店铺类型查询" style="width: 25% ;margin: 15px" clearable></el-input>
            <el-button type="primary" @click="load" style="background-color: #42b983;border:0"> 查询</el-button>
        </div>
        <!--        表格      :header-cell-style="{textAlign: 'center'}"表头居中对齐   :cell-style="{ textAlign: 'center' }"表格内容居中对齐 -->
        <el-table
                :row-style="rowstyle"
                :header-cell-style="{textAlign: 'center'}"
                :cell-style="{ textAlign: 'center' }"
                :data="BusinessShopList"
                height="704"
                border
                stripe
                style="width: 98%;">
            <el-table-column prop="shopid" label="店铺编号" width="110" sortable></el-table-column>
            <el-table-column prop="photo" label="图片" width="150">
                <template #default="scope">
                    <el-image
                            style="width: 70px; height: 70px;padding: 0"
                            :src="scope.row.photo"
                            :preview-src-list="[scope.row.photo]">
                        <template #error><!-- 没有图片的时候 -->
                            <el-image src="http://localhost:8081/nophoto.png"/>
                        </template>
                    </el-image>
                </template>
            </el-table-column>
            <el-table-column prop="shopname" label="店名" width="180">
                <template #default="scope" >
                    {{(scope.row.shopname===''||scope.row.shopname===null)&&scope.row.status!==3?'(该店铺商家暂未填写)':scope.row.shopname}}
                </template>
            </el-table-column>
            <el-table-column prop="phone" label="联系方式" width="150" sortable></el-table-column>
            <el-table-column prop="shoptype" label="店铺类型" width="150">
                <template #default="scope" >
                    {{(scope.row.shoptype===''||scope.row.shoptype===null)&&scope.row.status!==3?'(商家暂未选择)':scope.row.shoptype}}
                </template>
            </el-table-column>
            <el-table-column prop="status" label="店铺状态" width="150" sortable >
                <template #default="scope" >
                    <label v-if="scope.row.status===1"
                           style="color: #42b983;border: #42b983 1px solid;padding: 1px;border-radius: 10%;">营业</label>
                    <label v-if="scope.row.status===2"
                           style="color: mediumvioletred;border: mediumvioletred 1px solid;padding: 1px;border-radius: 10%">休息</label>
                    <label v-if="scope.row.status===3"
                           style="color: dodgerblue;border: dodgerblue 1px solid;padding: 1px;border-radius: 10%">待租</label>
                </template>
            </el-table-column>
            <el-table-column prop="address" label="地址" ></el-table-column>
            <el-table-column prop="avgGrade" label="平均评分" width="200" sortable>
                <template #default="scope">
                    <label style="color: #888888"
                           v-if="scope.row.avgGrade===null||scope.row.avgGrade===0.0">暂无评分</label>
                    <el-rate
                            v-else
                            v-model="scope.row.avgGrade"
                            disabled
                            show-score
                            text-color="#ff9900">
                    </el-rate>
                </template>
            </el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作"
                    width="150">
                <template #default="scope">
                    <el-button v-if="scope.row.status!==3"
                               @click="handleCheck(scope.row.shopid)"
                               type="primary" size="small"
                               style="background-color: #42b983;border:0">
                        查看
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <div style="margin: 10px 0">
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
            <!--      新增店铺时的弹出对话框-->
            <el-dialog>
<!--                    title="新增店铺"
                    v-model="dialogVisible"
                    width="30%">
                <el-form :model="newshop" lable-width="120px ">
                    <el-form-item label="新店铺的地址">
                        <el-input v-model="newshop.address"></el-input>
                    </el-form-item>
                </el-form>
                <span class="dialog-footer">
                        <el-button @click="dialogVisible = false,newshop={}">取 消</el-button>
                        <el-button type="primary" @click="addNewShop">确 定</el-button>
                    </span>-->
            </el-dialog>
        </div>
    </div>

</template>

<script>
    import request from "../../utils/request";

    export default {
        name: "BusinessHome",
        data(){
            return{
                dialogVisible: false,//可视化 ：关闭
                search: '',
                currentPage: 1,//当前页  默认第1页
                pageSize: 10,//默认每页x条数据
                total: 0,
                BusinessShopList:[],
            }
        },
        created() {
            this.load();
        },
        methods:{
            load() {
                let business = sessionStorage.getItem("user") || "{}"
                if (this.search === '') {   //如果搜索框有值，请求BusinessHome
                    request.get("/BusinessHome/" + this.currentPage + "/" + this.pageSize+"/"+JSON.parse(business).shopid).then(res => {  //搜索框内容空 不模糊查询，直接查询全部
                        this.total = res.data.total
                        this.BusinessShopList = res.data.records
                        console.log("获取的所有Shop和Business列表，自己的店铺置顶", res)
                    })
                } else {                    //搜索框没有值，请求ShopABusinessList
                    request.get("/ShopABusinessList/" + this.currentPage + "/" + this.pageSize + "/" + this.search).then(res => {
                        this.total = res.data.total
                        this.BusinessShopList = res.data.records
                        console.log("获取的所有Business和Shop模糊查询出来的列表", res)
                    })
                }
            },
            handleCheck(shopid){
                //scope.row.shopid 根据店铺id查 跳转到店铺详情界面
                this.$router.push({path:'/business/businessShopDetail', query: {shopid:shopid,witchCard:"ShopDetail"}})
            //    activeTab（是参数名，起什么都可以） 是跳转过去之后指定显示哪个卡片
            },
            handleSizeChange(pageSize) {//  改变每页记录条数
                this.pageSize = pageSize
                this.load()
            },
            handleCurrentChange(currentPage) {//改变当前页码
                this.currentPage = currentPage
                this.load()
            },
            rowstyle({row, rowIndex}){
                if (row.shopid === JSON.parse(sessionStorage.getItem("user")).shopid) {
                    let stylejson = {};
                    stylejson.background = "#f0f9eb";// 背景颜色
                    return stylejson;
                } else {
                    return "";
                }
            }
        }
    }
</script>

<style scoped>


</style>