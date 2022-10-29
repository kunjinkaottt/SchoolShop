<template>
    <div style="padding: 15px;">
<!--        <el-breadcrumb separator-class="el-icon-arrow-right">&lt;!&ndash; 面包屑 &ndash;&gt;
            <el-breadcrumb-item >店铺管理</el-breadcrumb-item>
            <el-breadcrumb-item>待租的店铺</el-breadcrumb-item>
        </el-breadcrumb>-->
        <!--    功能-->
        <div>
            <el-button type="primary" @click="addShop"><i class="el-icon-s-shop"></i> &nbsp新增店铺</el-button>
        <!--    搜索-->
            <el-input v-model="search" placeholder="输入地址查询" style="width: 25% ;margin: 15px" clearable></el-input>
            <el-button type="primary" @click="load"> 查询</el-button>
        </div>
        <!--        表格      :header-cell-style="{textAlign: 'center'}"表头居中对齐   :cell-style="{ textAlign: 'center' }"表格内容居中对齐 -->
        <el-table
                :header-cell-style="{textAlign: 'center'}"
                :cell-style="{ textAlign: 'center' }"
                :data="ShopABusinessList3"
                height="704"
                border
                style="width: 98%">
            <el-table-column
                    type="index"
                    width="50"
                    label="序号">
            </el-table-column>
            <el-table-column prop="shopid" label="店铺编号" width="180" sortable></el-table-column>
            <el-table-column prop="address" label="地址"></el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作"
                    width="180">
                <template #default="scope">
                    <el-button v-if="scope.row.status==3" @click="handleEdit(scope.row)" type="primary" size="small">
                        编辑
                    </el-button>
                    <el-popconfirm
                            v-if="scope.row.status==3"
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
            <!--      新增店铺时的弹出对话框-->
            <el-dialog
                    title="提示" v-model="dialogVisible" width="30%">
                <el-form :model="newshop" lable-width="120px ">
                    <el-form-item label="新店铺的地址">
                        <el-input v-model="newshop.address"></el-input>
                    </el-form-item>
                </el-form>
                <span class="dialog-footer">
                        <el-button @click="dialogVisible = false,newshop={}">取 消</el-button>
                        <el-button type="primary" @click="addNewShop">确 定</el-button>
                    </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import {ElConfigProvider} from 'element-plus'
    import zhCn from 'element-plus/lib/locale/lang/zh-cn'
    import request from "../../utils/request";

    export default {
        name: "Rent",
        components: {
            ElConfigProvider
        },
        data() {
            return {
                dialogVisible: false,//可视化 ：关闭
                newshop: {},
                locale: zhCn,
                search: '',
                currentPage: 1,//当前页  默认第1页
                pageSize: 20,
                total: 0,
                ShopABusinessList3: []
            }
        },
        created() {
            this.load()
        },
        methods: {
            load() {
                    request.get("/RentShopManage/" + this.currentPage + "/" + this.pageSize + "/" + this.search).then(res => {
                        this.total = res.data.total
                        this.ShopABusinessList3 = res.data.records
                        console.log("获取的待租店铺列表", res)
                    })
            },
            addShop() {     //点击新增店铺 按钮
                this.dialogVisible = true;
                this.load()
            },
            addNewShop() {       //点击确认 ——确认添加新店铺
                this.newshop.status = 3   //新增 编辑待租的店铺时 营业状态都先赋值为3——"待租"
                if (this.newshop.shopid) {   //更新
                    console.log("更新", this.newshop.shopid)
                    request.put("/updateNewShop", this.newshop).then(res => {
                        console.log(res)    // 打印看结果  返回  Object code: "0"data: nullmsg: "成功"__proto__: Object
                        if (res.code === '0') {
                            this.$message({
                                type: "success",
                                message: "更新地址成功"
                            })
                            this.load()
                        } else {
                            this.$message({
                                type: "error",
                                message: res.msg
                            })
                        }
                    })
                } else {     //新增
                    console.log("新增", this.newshop.shopid)
                    request.post("/addNewShop", this.newshop).then(res => {
                        console.log(res)    // 打印看结果  返回  Object code: "0"data: nullmsg: "成功"__proto__: Object
                        if (res.code === '0') {
                            this.$message({
                                type: "success",
                                message: "新增店铺成功"
                            })
                            this.load()
                        } else {
                            this.$message({
                                type: "error",
                                message: res.msg
                            })
                        }
                    })
                }
                this.dialogVisible = false
                this.load()
                this.newshop = {}    //清空表单的值
            },
            handleEdit(row) {
                this.newshop = JSON.parse(JSON.stringify(row))  //JSON.parse(JSON.stringify( xxx))深拷贝 跟前面newshop是隔离的 另起一个对象 避免浅拷贝的问题
                this.dialogVisible = true;
                this.load()
            },
            handleDelete(shopid) {
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
                console.log(shopid)
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