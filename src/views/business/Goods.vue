<template>
    <div style="padding: 15px;">
        <!--    功能-->
        <div>
            <el-button style="background-color: #42b983;color: white;border:0" @click="addGoods"><i class="el-icon-goods"></i> &nbsp新增商品</el-button>
            <!--    搜索-->
            <el-input v-model="search" placeholder="输入商品名或商品类型查询" style="width: 25% ;margin: 15px" clearable></el-input>
            <el-button style="background-color: #42b983;color: white;border:0" @click="load"> 查询</el-button>
        </div>
        <!--        表格      :header-cell-style="{textAlign: 'center'}"表头居中对齐   :cell-style="{ textAlign: 'center' }"表格内容居中对齐 -->
        <el-card v-if="this.GoodsList.length===0">
                <el-empty description="暂无商品"></el-empty>
        </el-card>
        <el-table
                v-else
                :header-cell-style="{textAlign: 'center'}"
                :cell-style="{ textAlign: 'center' }"
                :data="GoodsList"
                height="704"
                border
                style="width: 98%">
            <el-table-column prop="gid" label="商品编号" width="120px" sortable></el-table-column>
            <el-table-column prop="img" label="图片">
                <template #default="scope">
                    <el-image
                            style="width: 70px; height: 70px;padding: 0"
                            :src="scope.row.img"
                            :preview-src-list="[scope.row.img]" >
                        <template #error>
                            <img src="http://localhost:8081/noimg.png" >
                        </template>
                    </el-image>
                </template>
            </el-table-column>
            <el-table-column prop="goodsname" label="商品名称" ></el-table-column>
            <el-table-column prop="price" label="商品价格" sortable>
                <template #default="scope">  <label style="color:darkorange" >￥{{scope.row.price}}</label></template>
            </el-table-column>
            <el-table-column prop="goodstype" label="商品类型" ></el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作">
                <template #default="scope">
                    <el-button @click="handleEdit(scope.row)" type="primary" size="small">
                        编辑
                    </el-button>
                    <el-popconfirm
                            confirm-button-text='确认'
                            cancel-button-text='取消'
                            icon="el-icon-info"
                            icon-color="red"
                            title="确定下架该商品吗？"
                            @confirm="handleDelete(scope.row)">
                        <template #reference>
                            <el-button type="danger" size="small">下架</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <div style="margin: 10px 0">
            <!--分页-->
            <el-pagination
                    v-if="this.GoodsList.length!==0"
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[5,10,20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
            <!--      新增商品时的弹出对话框  :show-close="false"隐藏右上角×按钮，:close-on-click-modal="false" 不能通过点击空白处关闭弹窗-->
            <el-dialog
                    :show-close="false"
                    :close-on-click-modal="false"
                    title="新增/编辑商品" v-model="dialogVisible" width="30%">
                <el-form :rules="rules"
                         ref="form"
                         :model="oneGoods" lable-width="120px " >
<!--                    商品编号自动生成，店铺编号在缓存中取出 所以不用填写  -->
<!--                商品图片上传-->
                        <el-upload
                                class="uploadcss"
                                action="http://localhost:8081/uploadFile"
                                :show-file-list="false"
                                :on-success="handleImgSuccess"
                                :before-upload="beforeUpload">
                            <img v-if="oneGoods.img" :src="oneGoods.img" style="width: 180px;height:180px">
                            <i v-else class="plusicon">点击添加图片</i>
                        </el-upload>
                    <el-form-item label="商品名称" prop="goodsname">
                        <el-input v-model="oneGoods.goodsname" placeholder="请输入商品名称" clearable/>
                    </el-form-item>
                    <el-form-item label="商品价格" prop="price">
                        <el-input v-model="oneGoods.price" placeholder="请输入商品价格" clearable/>
                    </el-form-item>
                    <el-form-item label="商品类型" prop="goodstype" >
                        <el-input v-model="oneGoods.goodstype" placeholder="请输入商品类型" clearable />
                    </el-form-item>
                </el-form>
                <span class="dialog-footer">
                        <el-button @click="oneGoods={},dialogVisible = false">取 消</el-button>
                        <el-button type="primary" @click="addNewGoods">确 定</el-button>
                    </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import request from "../../utils/request";
    let business = sessionStorage.getItem("user") || "{}"

    export default {
        name: "Goods",
        data() {
            return {
                dialogVisible: false,//可视化 ：关闭
                oneGoods: {},
                search: '',
                currentPage: 1,//当前页  默认第1页
                pageSize: 10,//默认每页5条数据
                total: 0,
                GoodsList: [],
                rules: {
                    price: [
                        {required: true, message: '请输入商品价格', trigger: 'blur'},
                    ],
                    goodsname: [
                        {required: true, message: '请输入商品名称', trigger: 'blur'},
                        {min: 1, max: 16, message: '商品名称过长', trigger: 'blur'}
                    ],
                    goodstype: [
                        {required: true, message: '请输入商品类型', trigger: 'blur'},
                        {min: 1, max: 16, message: '商品类型名称过长', trigger: 'blur'}
                    ],
                }
            }
        },
        created() {
            this.load()
        },
        methods: {
            load() {
                request.get("/GoodsList/" + this.currentPage + "/" + this.pageSize + "/" +JSON.parse(business).shopid + "/" + this.search).then(res => {
                    this.total = res.data.total
                    this.GoodsList  = res.data.records
                    console.log("获取的GoodsList列表", res)
                })
            },
            addGoods() {     //点击新增店铺 按钮
                this.dialogVisible = true;
                this.load()
            },
            addNewGoods() {       //点击确认 ——确认添加新商品
                this.$refs['form'].validate((valid)=> {
                    if (valid) { //如果输入框的内容合法 才会发送请求

                        if (isNaN(this.oneGoods.price)){    //isNaN方法：!!!不是数字返回true!!!
                            this.$message({ type: "error", message: "输入的价格格式不对" })
                        }else{//是数字
                        if (this.oneGoods.gid) {   //gid存在——更新
                            request.put("/updateGoods", this.oneGoods).then(res => {
                                if (res.code === '0') {
                                    this.$message.success("商品信息更新成功")
                                    this.load()
                                } else {
                                    this.$message.error(res.msg)
                                }
                            })
                        } else {     //gid不存在——新增
                            console.log("新增", this.oneGoods.gid)
                            this.oneGoods.shopid = JSON.parse(business).shopid  //外键shopid赋值到商品对象
                            request.post("/addNewGoods", this.oneGoods).then(res => {
                                if (res.code === '0') {
                                    this.$message.success("新增商品成功")
                                    this.load()
                                } else {
                                    this.$message.error(res.msg)
                                    sessionStorage.removeItem("user")
                                    this.$router.replace("/login")
                                }
                            })
                        }
                        this.dialogVisible = false
                        this.load()
                        this.oneGoods = {}    //清空表单的值
                        }

                    }
                })

            },

            handleEdit(row) {   //编辑商品信息
                this.oneGoods = JSON.parse(JSON.stringify(row))  //JSON.parse(JSON.stringify( xxx))深拷贝 跟前面newshop是隔离的 另起一个对象 避免浅拷贝的问题
                this.dialogVisible = true;
                this.load()
            },

            handleDelete(row) {      //下架商品
                this.oneGoods = row
                this.oneGoods.isdelete = 1
                request.put("/deleteGoods" ,this.oneGoods ).then(res => {
                    if (res.code === '0') {
                        this.$message.success("商品下架成功")
                    } else {
                        this.$message.error(res.msg)
                    }
                    this.load()
                })
                this.oneGoods ={}
            },

            handleImgSuccess(res){  //res是请求upload得到的retrun 的值
                console.log(res)
                this.oneGoods.img = res;
            },
         /*   InputChange(e){
                    console.log(e.target.value.match(/^\d*(\.?\d{0,2})/g)[0],6666)
                    this.oneGoods.price = e.target.value.match(/^\d*(\.?\d{0,2})/g)[0] || null;
            },*/
            //判断图片是不是小于1M的jpg或png
            beforeUpload(file){
                const isJPG = file.type === 'image/jpeg';
                const isPNG = file.type === 'image/png';
                const isLt1M = file.size / 1024 / 1024 < 1;

                if (!isJPG&&!isPNG) {
                    this.$message.error('只能上传JPG或PNG格式图片!');
                }
                else if (!isLt1M) {
                    this.$message.error('上传图片大小不能超过 1MB!');
                }
                return isJPG||isPNG && isLt1M;
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

<style>
.uploadcss{
    text-align:center;
    padding: 5%;
    margin-left: 5%;
}
.plusicon{
    text-align: center;
    width: 100px;
    height: 100px;
    justify-content: center;
    align-items: center;
    border: #8c939d 1px dashed;
}

</style>