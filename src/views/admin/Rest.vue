<template>
    <div style="padding: 15px;">
<!--        <el-breadcrumb separator-class="el-icon-arrow-right">&lt;!&ndash; 面包屑 &ndash;&gt;
            <el-breadcrumb-item >店铺管理</el-breadcrumb-item>
            <el-breadcrumb-item>休息的店铺</el-breadcrumb-item>
        </el-breadcrumb>-->
        <!--    搜索-->
        <div>
            <el-input v-model="search" placeholder="输入店铺名或地址查询" style="width: 25% ;margin: 15px" clearable></el-input>
            <el-button type="primary" @click="load"> 查询</el-button>
        </div>
        <!--    表格      :header-cell-style="{textAlign: 'center'}"表头居中对齐   :cell-style="{ textAlign: 'center' }"表格内容居中对齐 -->
        <el-table
                :header-cell-style="{textAlign: 'center'}"
                :cell-style="{ textAlign: 'center' }"
                :data="ShopABusinessList2"
                height="704"
                border
                style="width: 98%">
            <el-table-column prop="shopid" label="店铺编号" width="120" sortable></el-table-column>
            <el-table-column prop="photo" label="图片" width="150">
                <template #default="scope">
                    <el-image
                            style="width: 100px; height: 100px;padding: 0"
                            :src="scope.row.photo"
                            :preview-src-list="[scope.row.photo]">
                        <template #error>
                            <el-image src="http://localhost:8081/nophoto.png"/>
                        </template>
                    </el-image>
                </template>
            </el-table-column>
            <el-table-column prop="shopname" label="店名" width="180">
                <template #default="scope" >
                    {{scope.row.shopname===null?'(该店铺商家暂未填写)':scope.row.shopname}}
                </template>
            </el-table-column>
            <el-table-column prop="truename" label="商家姓名" width="150"></el-table-column>
            <el-table-column prop="phone" label="手机号码" width="150"></el-table-column>
            <el-table-column prop="shoptype" label="店铺类型" width="150" sortable>
                <template #default="scope" >
                    {{scope.row.shopname===null&&scope.row.status!==3?'(商家暂未选择)':scope.row.shopname}}
                </template>
            </el-table-column>
            <el-table-column prop="address" label="地址"></el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作"
                    width="180">
                <template #default="scope">
                    <el-button @click="handleCheck(scope.row.shopid)" type="primary" size="small">
                        查看
                    </el-button>
                    <el-popconfirm
                            confirm-button-text='确认'
                            cancel-button-text='取消'
                            icon="el-icon-info"
                            icon-color="red"
                            title="确定收回该店铺吗？被收回店铺的信息将被清空，请谨慎操作"
                            @confirm="handleRecovery(scope.row.shopid)">
                        <template #reference>
                            <el-button type="danger" size="small">收回店铺</el-button>
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
                    :page-sizes="[5,10,20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<script>
/*    import {ElConfigProvider} from 'element-plus'
    import zhCn from 'element-plus/lib/locale/lang/zh-cn'*/
    import request from "../../utils/request";

    export default {
        name: "Rest",
/*        components: {
            ElConfigProvider
        },*/
        data() {
            return {
                dialogVisible: false,//可视化 ：关闭
                newshop: {},
                // locale: zhCn,
                search: '',
                currentPage: 1,//当前页  默认第1页
                pageSize: 5,//默认每页5条数据
                total: 0,
                ShopABusinessList2: []   //最后面的1表示 查询出店铺状态为1(营业)的店铺
            }
        },
        created() {
            this.load()
        },
        methods: {
            load() {
                    request.get("/ShopManage/" + this.currentPage + "/" + this.pageSize + "/" + 2 + "/" + this.search).then(res => {
                        this.total = res.data.total
                        this.ShopABusinessList2 = res.data.records
                        console.log("获取的shop和business列表", res)
                    })
            },
            handleCheck(shopid) {
                //scope.row.shopid 根据店铺id查 跳转到店铺详情界面
                this.$router.push({path:'/admin/AdminShopDetail', query: {shopid:shopid,witchCard:"ShopDetail"}})
                //    activeTab（是参数名，起什么都可以） 是跳转过去之后指定显示哪个卡片
            },
            handleRecovery(shopid) {    //收回店铺
                request.put("/recoverShop/"+shopid).then(res=>{
                    if (res.code==='0'){
                        this.$message({
                            type: "success",
                            message: "成功收回店铺,可在待租店铺中找到"
                        })
                        this.load();
                    }else {
                        this.$message({
                            type: "error",
                            message: res.msg
                        })
                        this.load();
                    }
                })
                this.load();
            },
            handleSizeChange(pageSize) {        //改变每页记录条数
                this.pageSize = pageSize
                this.load()
            },
            handleCurrentChange(currentPage) {  //改变当前页码
                this.currentPage = currentPage
                this.load()
            },
        }
    }
</script>

<style scoped>

</style>