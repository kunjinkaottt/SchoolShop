<template>

    <el-tabs v-model="activeTab" type="border-card">
        <el-tab-pane label="收藏的店铺" name="favoriteShop" >
            <el-card v-if="this.FavoriteShopList.length===0" style=" margin: 3%" >
                <el-empty description="暂无收藏"></el-empty>
            </el-card>
            <div v-else style="padding: 15px;">
                <!--    功能-->
                <div>
                    <!--    搜索-->
                    <el-input v-model="fsSearch" placeholder="输入店铺名或店铺类型查询" style="width: 25% ;margin: 15px" clearable></el-input>
                    <el-button type="primary" style="background-color:darkorange;border:0" @click="loadFavoritesShop"> 查询</el-button>
                </div>
                <!--        表格      :header-cell-style="{textAlign: 'center'}"表头居中对齐   :cell-style="{ textAlign: 'center' }"表格内容居中对齐 -->
                <el-table
                        :header-cell-style="{textAlign: 'center'}"
                        :cell-style="{ textAlign: 'center' }"
                        :data="FavoriteShopList">
                    <el-table-column prop="shopid" label="店铺编号" width="120" sortable/>
                    <el-table-column prop="shopname" label="店铺名称" >
                        <template #default="scope">
                            <label v-if="scope.row.shopname===null||scope.row.shopname===''" style="color: #888888;font-size: small">(该店铺商家暂未填写)</label>
                            <label v-else>{{scope.row.shopname}}</label>
                        </template>
                    </el-table-column>
                    <el-table-column prop="photo" label="图片" >
                        <template #default="scope">
                            <el-image
                                    style="width: 70px; height: 70px;padding: 0"
                                    :src="scope.row.photo"
                                    :preview-src-list="[scope.row.photo]" >
                                <template #error>
                                    <el-image src="http://localhost:8081/nophoto.png" />
                                </template>
                            </el-image>
                        </template>
                    </el-table-column>
                    <el-table-column label="商家姓名" >
                        <template #default="scope"  >
                            <el-popover effect="light" trigger="hover" placement="top" >
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
                    <el-table-column prop="phone" label="商家手机"  sortable></el-table-column>
                    <el-table-column prop="shoptype" label="店铺类型" >
                        <template #default="scope">
                            <label v-if="scope.row.shoptype===null||scope.row.shoptype===''" style="color: #888888;font-size: small">(该店铺商家暂未选择)</label>
                            <label v-else>{{scope.row.shoptype}}</label>
                        </template>
                    </el-table-column>
                    <el-table-column prop="status" label="店铺状态"  sortable >
                        <template #default="scope" >
                            <label v-if="scope.row.status==1"
                                   style="color: #42b983;border: #42b983 1px solid;padding: 1px;border-radius: 10%;">营业</label>
                            <label v-if="scope.row.status==2"
                                   style="color: mediumvioletred;border: mediumvioletred 1px solid;padding: 1px;border-radius: 10%">休息</label>
                            <label v-if="scope.row.status==3"
                                   style="color: dodgerblue;border: dodgerblue 1px solid;padding: 1px;border-radius: 10%">待租</label>
                        </template>
                    </el-table-column>
                    <el-table-column prop="address" label="地址" width="360"></el-table-column>
                    <el-table-column
                            fixed="right"
                            label="操作"
                            width="180">
                        <template #default="scope">
                            <el-button @click="handleCheck(scope.row.shopid)" type="primary" size="small" style="background-color: darkorange;border:0">
                                查看
                            </el-button>
                            <!--                        已收藏  点击触发取消收藏-->
                            <el-button v-if="this.fsIsFavoriteList[scope.$index]===1"
                                       @click="cancelCollectShop(scope.row.fid,scope.$index)"
                                       type="primary" size="small"
                                       style="background-color: darkorange;border:0"
                                       circle>
                                <i class="el-icon-star-on"></i>
                            </el-button>
                            <!--                        未收藏  点击触发(再次)收藏-->
                            <el-button v-if="this.fsIsFavoriteList[scope.$index]===0"
                                       @click="CollectShop(scope.row.fid,scope.$index)"
                                       type="primary" size="small"
                                       style="background-color: darkorange;border:0"
                                       circle>
                                <i class="el-icon-star-off"></i>
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div style="margin: 10px 0">
                    <!--分页-->
                    <el-pagination
                            @size-change="fsSizeChange"
                            @current-change="fsCurrentChange"
                            :current-page="fsCurrentPage"
                            :page-sizes="[5,10,20]"
                            :page-size="fsPageSize"
                            layout="total, sizes, prev, pager, next, jumper"
                            :total="fsTotal">
                    </el-pagination>
                </div>
            </div>
        </el-tab-pane>

        <el-tab-pane label="收藏的商品" name="favoriteGoods" >
            <el-card v-if="this.FavoriteGoodsList.length===0" style=" margin: 3%" >
                <el-empty description="暂无收藏"></el-empty>
            </el-card>
            <div v-else style="padding: 15px;">
                <!--    功能-->
                <div>
                    <!--    搜索-->
                    <el-input v-model="fgSearch" placeholder="输入商品名或商品类型查询" style="width: 25% ;margin: 15px" clearable></el-input>
                    <el-button type="primary" style="background-color:darkorange;border:0" @click="loadFavoritesGoods"> 查询</el-button>
                </div>
                <!--        表格      :header-cell-style="{textAlign: 'center'}"表头居中对齐   :cell-style="{ textAlign: 'center' }"表格内容居中对齐 -->
                <el-table
                        :header-cell-style="{textAlign: 'center'}"
                        :cell-style="{ textAlign: 'center' }"
                        :data="FavoriteGoodsList">
                    <el-table-column prop="shopid" label="店铺编号" width="120" sortable/>
                    <el-table-column prop="shopname" label="店铺名称" >
                        <template #default="scope">
                            <label v-if="scope.row.shopname===null" style="color: #888888;font-size: small">(该店铺商家暂未填写)</label>
                            <label v-else>{{scope.row.shopname}}</label>
                        </template>
                    </el-table-column>
                    <el-table-column prop="img" label="图片" >
                        <template #default="scope">
                            <el-image
                                    style="width: 70px; height: 70px;padding: 0"
                                    :src="scope.row.img"
                                    :preview-src-list="[scope.row.img]" >
                                <template #error>
                                    没有图片
                                </template>
                            </el-image>
                        </template>
                    </el-table-column>
                    <el-table-column label="商品名称" >
                        <template #default="scope"  >
                            <el-popover effect="light" trigger="hover" placement="top" >
                                <template #default >
                                    <p style="width: 300px;">商品编号: {{ scope.row.gid }}</p>
                                </template>
                                <template #reference>
                                    <div class="name-wrapper">
                                        <el-tag size="medium">{{ scope.row.goodsname}}</el-tag>
                                    </div>
                                </template>
                            </el-popover>
                        </template>
                    </el-table-column>
                    <el-table-column prop="goodstype" label="商品类型" ></el-table-column>
                    <!--            <el-table-column prop="address" label="地址" width="400"></el-table-column>-->
                    <el-table-column
                            fixed="right"
                            label="操作"
                            width="180">
                        <template #default="scope">
                            <el-button @click="handleCheck(scope.row.shopid)" type="primary" size="small" style="background-color: darkorange;border:0">
                                查看
                            </el-button>
                            <!--                        已收藏  点击触发取消收藏-->
                            <el-button v-if="this.fgIsFavoriteList[scope.$index]===1"
                                       @click="cancelCollectGoods(scope.row.fid,scope.$index)"
                                       type="primary" size="small"
                                       style="background-color: darkorange;border:0"
                                       circle>
                                <i class="el-icon-star-on"></i>
                            </el-button>
                            <!--                        未收藏  点击触发(再次)收藏-->
                            <el-button v-if="this.fgIsFavoriteList[scope.$index]===0"
                                       @click="collectGoodsAgain(scope.row.fid,scope.$index)"
                                       type="primary" size="small"
                                       style="background-color: darkorange;border:0"
                                       circle>
                                <i class="el-icon-star-off"></i>
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div style="margin: 10px 0">
                    <!--分页-->
                    <el-pagination
                            @size-change="fgSizeChange"
                            @current-change="fgCurrentChange"
                            :current-page="fgCurrentPage"
                            :page-sizes="[5,10,20]"
                            :page-size="fgPageSize"
                            layout="total, sizes, prev, pager, next, jumper"
                            :total="fgTotal">
                    </el-pagination>
                </div>
            </div>
        </el-tab-pane>
    </el-tabs>
</template>

<script>
    import request from "../../utils/request";

let member = sessionStorage.getItem("user") || {}

    export default {
        name: "Favorites",
        data(){
            return{
                activeTab:'favoriteShop',

                //收藏的店铺
                fsCurrentPage:1,
                fsPageSize:10,
                fsTotal:0,
                fsSearch:'',
                FavoriteShopList:[],
                fsIsFavoriteList:[],
            //    收藏的商品
                fgCurrentPage:1,
                fgPageSize:10,
                fgTotal:0,
                fgSearch:'',
                FavoriteGoodsList:[],
                fgIsFavoriteList:[],

            }
        },
        created() {
            this.loadFavoritesShop()
            this.loadFavoritesGoods()
        },
        methods:{
            loadFavoritesShop(){
                request.get("/FavoriteShopList/"+this.fsCurrentPage+"/"+this.fsPageSize+"/"+JSON.parse(member).mid+"/"+this.fsSearch).then(res=>{
                    this.fsTotal = res.data.total
                    this.FavoriteShopList = res.data.records
                    console.log("获取的店铺收藏列表",this.FavoriteShopList)
                    for(let i =0;i<res.data.records.length;i++){//获取表格每行的收藏情况 一开始都是1收藏
                        this.fsIsFavoriteList[i] = 1
                    }
                })
            },
            loadFavoritesGoods(){
                request.get("/FavoriteGoodsList/"+this.fgCurrentPage+"/"+this.fgPageSize+"/"+JSON.parse(member).mid+"/"+this.fgSearch).then(res=>{
                    this.fgTotal = res.data.total
                    this.FavoriteGoodsList = res.data.records
                    console.log("获取的商品收藏列表",this.FavoriteGoodsList)
                    for(let i =0;i<res.data.records.length;i++){//获取表格每行的收藏情况 一开始都是1收藏
                        this.fgIsFavoriteList[i] = 1
                    }
                })
            },
//收藏的店铺-取消收藏
            cancelCollectShop(fid,index){
                console.log("收藏的店铺-取消收藏",fid)
                this.fsIsFavoriteList[index] = 0
                request.put("/cancelCollect/"+fid).then(res=>{
                    if (res.code==='0'){
                        this.$message.info("已取消收藏")
                    }else
                        this.$message.error(res.msg)
                })
            },
//收藏的店铺-(再次)收藏
            CollectShop(fid,index){
                console.log("收藏的店铺-(再次)收藏",fid)
              this.fsIsFavoriteList[index] = 1
                request.put("/collectAgain/"+fid).then(res=>{
                    if (res.code==='0'){
                        this.$message.success("收藏成功")
                    }else
                        this.$message.error(res.msg)
                })
            },

//收藏的商品-取消收藏
            cancelCollectGoods(fid,index){
                console.log("收藏的商品-取消收藏",fid)
                this.fgIsFavoriteList[index] = 0
                request.put("/cancelCollect/"+fid).then(res=>{
                    if (res.code==='0'){
                        this.$message.info("已取消收藏")
                    }else
                        this.$message.error(res.msg)
                })
            },
//收藏的商品-(再次)收藏
            collectGoodsAgain(fid,index){
                console.log("收藏的商品-(再次)收藏",fid)
                this.fgIsFavoriteList[index] = 1
                request.put("/collectAgain/"+fid).then(res=>{
                    if (res.code==='0'){
                        this.$message.success("收藏成功")
                    }else
                        this.$message.error(res.msg)
                })
            },
//查看
            handleCheck(shopid){
              this.$router.push({path: '/members/membersShopDetail', query: {shopid:shopid,activeTab:"ShopDetail"} })
            },
//  收藏的店铺-改变每页记录条数
            fsSizeChange(pageSize){
                this.fsPageSize  = pageSize
                this.loadFavoritesShop()
            },
//  收藏的店铺-改变当前页码
            fsCurrentChange(currentPage){
                this.fsCurrentPage  = currentPage
                this.loadFavoritesShop()
            },

//  收藏的商品-改变每页记录条数
            fgSizeChange(pageSize){
                this.fgPageSize  = pageSize
                this.loadFavoritesGoods()
            },
//  收藏的商品-改变当前页码
            fgCurrentChange(currentPage){
                this.fgCurrentPage  = currentPage
                this.loadFavoritesGoods()
            }
    }

}
</script>

<style scoped>

    .el-table{
        height: 100% !important;
    }

</style>