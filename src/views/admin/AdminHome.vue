<template>
    <div style="padding: 15px;">

        <!--    功能-->
        <div>
            <el-button type="primary" @click="addShop"><i class="el-icon-s-shop"></i> &nbsp; 新增店铺</el-button>
        <!--    搜索-->
            <el-input v-model="search" placeholder="输入店铺名或店铺类型查询" style="width: 25% ;margin: 15px" clearable></el-input>
            <el-button type="primary" @click="load"> 查询</el-button>
        </div>
<!--        表格      :header-cell-style="{textAlign: 'center'}"表头居中对齐   :cell-style="{ textAlign: 'center' }"表格内容居中对齐 -->
        <el-table
                :header-cell-style="{textAlign: 'center'}"
                :cell-style="{ textAlign: 'center' }"
                :data="ShopABusinessList"
                height="704"
                border
                stripe
                style="width: 98%">
            <el-table-column prop="shopid" label="店铺编号" width="110" sortable></el-table-column>
            <el-table-column prop="photo" label="图片" width="150">
                <template #default="scope">
                    <el-image
                            style="width: 70px; height: 70px;padding: 0"
                            :src="scope.row.photo"
                            :preview-src-list="[scope.row.photo]" >
                        <template #error>
                            <el-image src="http://localhost:8081/nophoto.png"/>
                        </template>
                    </el-image>
                </template>
            </el-table-column>
            <el-table-column prop="shopname" label="店铺名称" width="180">
                <template #default="scope" >
                    {{(scope.row.shopname===''||scope.row.shopname===null)&&scope.row.status!==3?'(该店铺商家暂未填写)':scope.row.shopname}}
                </template>
            </el-table-column>
            <el-table-column label="商家姓名" width="150">
                    <template #default="scope"  >
                        <el-popover effect="light" trigger="hover" placement="top" v-if="scope.row.status!==3"><!-- 店铺状态不为3 待租，才显示商家姓名和编号 -->
                            <template #default >
                                <p style="width: 300px;">商家编号: {{ scope.row.bid }}</p>
                            </template>
                            <template #reference>
                                <div class="name-wrapper">
                                    <el-tag size="medium">{{ scope.row.truename}}</el-tag>
                                </div>
                            </template>
                        </el-popover>
                    </template>
            </el-table-column>
            <el-table-column prop="phone" label="手机号码" width="150" sortable></el-table-column>
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
            <el-table-column prop="address" label="地址" width="400"></el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作"
                    width="180">
                <template #default="scope">
                    <el-button v-if="scope.row.status!==3" @click="handleCheck(scope.row.shopid)" type="primary" size="small">
                        查看
                    </el-button>
                    <el-popconfirm
                            v-if="scope.row.status!==3"
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
                    <el-button v-if="scope.row.status===3" @click="handleEdit(scope.row)" type="primary" size="small">
                        编辑
                    </el-button>
                    <el-popconfirm
                            v-if="scope.row.status===3"
                            confirm-button-text='确认'
                            cancel-button-text='取消'
                            icon="el-icon-info"
                            icon-color="red"
                            title="确定删除吗？已经申请该店铺的商家账号将自动变成注册未通过"
                            @confirm="handleDelete(scope.row.shopid)">
                        <template #reference>
                            <el-button type="danger" size="small">删除</el-button>
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
                    :page-sizes="[5,10,20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
            <!--      新增店铺时的弹出对话框   :show-close="false"隐藏右上角×按钮，:close-on-click-modal="false" 不能通过点击空白处关闭弹窗-->
            <el-dialog
                    :show-close="false"
                    :close-on-click-modal="false"
                    title="新增/编辑店铺" v-model="dialogVisible" width="30%">
                <el-form :model="newshop" lable-width="120px ">
                    <el-form-item label="店铺地址信息">
                        <el-input v-model="newshop.address"></el-input>
                    </el-form-item>
                </el-form>
                <span class="dialog-footer">
                        <el-button @click="dialogVisible = false,newshop={}">取 消</el-button>
                        <el-button type="primary" @click="NewShop">确 定</el-button>
                    </span>
            </el-dialog>
        </div>
    </div>
</template>

<style>

</style>

<script>
  import request from "../../utils/request";

    export default {
        name: 'AdminHome',
        data() {
            return {
                dialogVisible: false,//可视化 ：关闭
                newshop: {},
                search: '',
                currentPage: 1,//当前页  默认第1页
                pageSize: 10,//默认每页5条数据
                total: 0,
                ShopABusinessList: []
            }
        },
        created() {
            this.load()
        },
        methods: {
            load() {
                    request.get("/ShopABusinessList/" + this.currentPage + "/" + this.pageSize + "/" + this.search).then(res => {
                        this.total = res.data.total
                        this.ShopABusinessList  = res.data.records
                        console.log("获取的shop和business列表", res)
                    })
            },
            addShop() {     //点击新增店铺 按钮
                this.dialogVisible = true;
                this.load()
            },
            NewShop() {       //点击确认 ——添加新店铺 / 更新店铺地址
                this.newshop.status = 3   //新增 编辑待租的店铺时 营业状态都先赋值为3——"待租"
                this.newshop.shopname = this.newshop.address
                request.post("/NewShop", this.newshop).then(res => {
                    console.log(res)    // 打印看结果  返回  Object code: "0"data: nullmsg: "成功"__proto__: Object
                    if (res.code === '0') {
                        this.$message.success(res.msg)
                        this.load()
                    } else {
                        this.$message.error(res.msg)
                    }
                })
                this.dialogVisible = false  // this.load()
                this.newshop = {}    //清空表单的值
            },
            handleCheck(shopid) {
                //scope.row.shopid 根据店铺id查 跳转到店铺详情界面
                this.$router.push({path:'/admin/AdminShopDetail', query: {shopid:shopid,witchCard:"ShopDetail"}})
                //    activeTab（是参数名，起什么都可以） 是跳转过去之后指定显示哪个卡片
            },
            handleRecovery(shopid) {//收回店铺
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
            handleEdit(row) {
                this.newshop = JSON.parse(JSON.stringify(row))  //JSON.parse(JSON.stringify( xxx))深拷贝 跟前面newshop是隔离的 另起一个对象 避免浅拷贝的问题
                this.dialogVisible = true;
                this.load()
            },
            handleDelete(shopid) {      //删除 待租的店铺
                request.delete("/deleteNewShop/" + shopid).then(res => {
                    if (res.code === '0') {
                        this.$message({
                            type: "success",
                            message: "删除成功"
                        })
                    } else {
                        this.$message({
                            type: "error",
                            message: res.msg
                        })
                    }
                    this.load()
                })
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
