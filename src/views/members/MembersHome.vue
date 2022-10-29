<template>
    <div style="padding: 15px;">
        <!--        轮播图-->
        <el-carousel :interval="3000" type="card" height="200px" style="width: 80% ; margin: 0 auto">
            <el-carousel-item style="background-image: linear-gradient(-90deg,#29bdd9 0%,#276ace 100%);border-radius:20px">
                <div style="display: flex">
                    <div class="shopid">{{this.maxAvgGradeObject.shopid}}</div>
                    <div class="shopname">评分最高店铺：{{this.maxAvgGradeObject.shopname}}</div>
                </div>
                <div class="lunbotu" @click="MembersShopDetail(this.maxAvgGradeObject.shopid)">
                    <el-image class="tushangtu" :src="this.maxAvgGradeObject.photo">
                        <template #error> <el-image src="http://localhost:8081/nophoto.png"/>  </template>
                    </el-image>
                </div>
            </el-carousel-item>
            <el-carousel-item style="background-image:linear-gradient(45deg,rebeccapurple 20%,#67f 60%);border-radius: 20px">
                <div style="display: flex">
                    <div class="shopid"> {{this.maxFavoritesObject.shopid}}</div>
                    <div class="shopname">收藏最多店铺：{{this.maxFavoritesObject.shopname}}</div>
                </div>
                <div class="lunbotu"  @click="MembersShopDetail(this.maxFavoritesObject.shopid)">
                    <el-image class="tushangtu" :src="this.maxFavoritesObject.photo">
                        <template #error>   <el-image src="http://localhost:8081/nophoto.png"/>  </template>
                    </el-image>
                </div>
            </el-carousel-item>
            <el-carousel-item style="background-image: linear-gradient(45deg, #39b54a,#f0f9eb );border-radius:20px">
                <div style="display: flex">
                    <div class="shopid"> {{this.luckyShopObject.shopid}} </div>
                    <div class="shopname">官方推荐店铺：{{this.luckyShopObject.shopname}}</div>
                </div>
                <div class="lunbotu" @click="MembersShopDetail(this.luckyShopObject.shopid)">
                    <el-image class="tushangtu" :src="this.luckyShopObject.photo">
                        <template #error>  <el-image src="http://localhost:8081/nophoto.png"/>  </template>
                    </el-image>
                </div>
            </el-carousel-item>
        </el-carousel>
        <!--   排序  搜索-->
        <div>
            <span v-if="!this.isTable">
                <el-button type="text" @click="sort('status')" style="padding: 0 30px"> 默认排序</el-button>
                <el-button type="text" @click="sort('fcount')" style="padding: 0 30px"> 按收藏排序</el-button>
                <el-button type="text" @click="sort('avgGrade')" style="padding: 0 30px"> 按评分排序</el-button>
                <el-button type="text" @click="sort('shopid')" style="padding: 0 30px"> 按编号排序</el-button>
            </span>
            <el-input v-model="search" placeholder="输入店铺名或店铺类型查询" style="width: 25% ;margin: 15px;padding-left: 10px"
                      clearable></el-input>
            <el-button type="primary" @click="load" style="background-color: darkorange; border:0"> 查询</el-button>
            <el-button type="text" @click="this.isTable = !this.isTable" style="float:right;padding-right: 30px">
                切换显示格式
            </el-button>
        </div>
        <!--        前台 卡片 样式-->
        <div v-if="!this.isTable" style="margin: 10px 0">
            <el-row :gutter="10">
                <el-col :span="8" v-for="(item,index) in MembersHomeList" :key="item.shopid">
                    <el-card :body-style="{ padding: '10px' }" style="width: 92%;height: 95%">
                        <div style="border:1px dashed #999">
                            <div style="float: right;color: #999999;font-size: smaller;padding: 2px 10px">
                                收藏量{{item.fcount}}
                            </div>
                            <div style="font-size: smaller;color: #888888;padding: 5px">{{item.shopid}}</div>
                            <!-- 店铺编号 -->
                            <div v-if="item.status!==3">
                                <!--                        未收藏  点击触发收藏-->
                                <el-button v-if="item.isfavorite!==1"
                                           @click="CollectShop(item.fid,item.shopid)"
                                           type="primary" size="small" circle
                                           style="background-color: darkorange;border:0;float: right;margin-right: 20px">
                                    <i class="el-icon-star-off"></i>
                                </el-button>
                                <!--                        已收藏  点击触发取消收藏-->
                                <el-button v-else
                                           @click="cancelCollect(item.fid)"
                                           type="primary" size="small" circle
                                           style="background-color: darkorange;border:0;float: right;margin-right: 20px">
                                    <i class="el-icon-star-on"></i>
                                </el-button>
                            </div>
                            <!--                            图片-->
                            <el-image class="photo" :src="item.photo" :preview-src-list="[item.photo]">
                                <template #error>
                                    <el-image src="http://localhost:8081/nophoto.png"/>
                                </template>
                            </el-image>
                        </div>
                        <div style="padding: 14px;">
                            <div style="display: flex;">
                                <span style="flex: 10"> {{(item.shopname===''||item.shopname===null)&&item.status!==3?'(暂无店铺名)':item.shopname}}</span>
                                <div style="flex: 2;float:right">
                                    <label v-if="item.status===1"
                                           style="color: #42b983;border: #42b983 1px solid;padding: 1px;border-radius: 5px;">营业中</label>
                                    <label v-if="item.status===2"
                                           style="color: mediumvioletred;border: mediumvioletred 1px solid;padding: 1px;border-radius: 5px">休息中</label>
                                    <label v-if="item.status===3"
                                           style="color: dodgerblue;border: dodgerblue 1px solid;padding: 1px;border-radius: 5px">待租</label>
                                </div>
                            </div>
                            <div style="display:flex;padding-top: 10px">
                                <div style="padding: 5px;flex:8">
                                    <label style="color: #888888;font-size: small"
                                           v-if="item.avgGrade===null||item.avgGrade===0.0">暂无评分</label>
                                    <div v-else>
                                        <el-rate
                                                v-model="item.avgGrade"
                                                disabled
                                                show-score
                                                text-color="#ff9900">
                                        </el-rate>
                                    </div>
                                </div>
                                <div style="font-size: small;flex:3">店铺类型：{{item.shoptype}}</div>
                            </div>
                            <div style="display: flex; padding-top: 10px;">
                                <i class="el-icon-location-outline"></i><span class="address"> {{ item.address }}</span>
                                <div v-if="item.status!==3">
                                    <el-button style="flex: 1"
                                               type="primary"
                                               size="small"
                                               @click="MembersShopDetail(item.shopid)">查看
                                    </el-button>
                                </div>
                            </div>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </div>
        <!--        表格      :header-cell-style="{textAlign: 'center'}"表头居中对齐   :cell-style="{ textAlign: 'center' }"表格内容居中对齐 -->
        <el-table
                v-else
                :header-cell-style="{textAlign: 'center'}"
                :cell-style="{ textAlign: 'center' }"
                :data="MembersHomeList"
                height="704"
                stripe
                style="width: 98%;">
            <el-table-column prop="shopid" label="店铺编号" width="110" sortable></el-table-column>
            <el-table-column prop="photo" label="图片" width="150">
                <template #default="scope">
                    <el-image
                            style="width: 70px; height: 70px;padding: 0"
                            :src="scope.row.photo"
                            :preview-src-list="[scope.row.photo]">
                        <template #error>
                            <el-image src="http://localhost:8081/nophoto.png"/>
                        </template>
                    </el-image>
                </template>
            </el-table-column>
            <el-table-column prop="shopname" label="店名" width="180">
                <template #default="scope">
                    {{(scope.row.shopname===''||scope.row.shopname===null)&&scope.row.status!==3?'(该店铺商家暂未填写)':scope.row.shopname}}
                </template>
            </el-table-column>
            <el-table-column prop="shoptype" label="店铺类型" width="140">
                <template #default="scope">
                    {{(scope.row.shoptype===''||scope.row.shoptype===null)&&scope.row.status!==3?'(商家暂未选择)':scope.row.shoptype}}
                </template>
            </el-table-column>
            <el-table-column prop="status" label="店铺状态" width="120" sortable>
                <template #default="scope">
                    <label v-if="scope.row.status==1"
                           style="color: #42b983;border: #42b983 1px solid;padding: 1px;border-radius:5px;">营业</label>
                    <label v-if="scope.row.status==2"
                           style="color: mediumvioletred;border: mediumvioletred 1px solid;padding: 1px;border-radius:5px">休息</label>
                    <label v-if="scope.row.status==3"
                           style="color: dodgerblue;border: dodgerblue 1px solid;padding: 1px;border-radius:5px">待租</label>
                </template>
            </el-table-column>
            <el-table-column prop="address" label="地址"></el-table-column>
            <el-table-column prop="avgGrade" label="平均评分" width="180" sortable>
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
            <el-table-column prop="fcount" label="收藏量" width="80">
                <template #default="scope">
                    {{scope.row.fcount}}
                </template>
            </el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作"
                    width="160">
                <template #default="scope">
                    <div v-if="scope.row.status!==3">
                        <el-button
                                @click="MembersShopDetail(scope.row.shopid)"
                                type="primary" size="small"
                                style="background-color: darkorange;border:0">
                            查看
                        </el-button>
                        <!--                        未收藏  点击触发收藏-->
                        <el-button v-if="scope.row.isfavorite!==1"
                                   @click="CollectShop(scope.row.fid,scope.row.shopid)"
                                   type="primary" size="small"
                                   style="background-color: darkorange;border:0"
                                   icon="el-icon-star-off"
                                   circle>
                        </el-button>
                        <!--                        已收藏  点击触发取消收藏-->
                        <el-button v-else
                                   @click="cancelCollect(scope.row.fid)"
                                   type="primary" size="small"
                                   style="background-color: darkorange;border:0"
                                   icon="el-icon-star-on"
                                   circle>
                        </el-button>
                    </div>
                </template>
            </el-table-column>
        </el-table>
        <div style="margin: 10px 0">
            <!--分页-->
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[9,18,36]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    import request from "../../utils/request";

    export default {
        name: "MembersHome",
        data() {
            return {
                dialogVisible: false,//可视化 ：关闭
                search: '',
                currentPage: 1,//当前页  默认第1页
                pageSize: 9,//默认每页x条数据
                total: 0,
                MembersHomeList: [],
                favorites: {},
                members: {},
                isTable: false,
                sortItem: 'status',
                carouselList: [],
                maxAvgGradeObject: {},
                maxFavoritesObject: {},
                luckyShopObject: {},

            }
        },
        created() {
            let members = sessionStorage.getItem("user") || "{}"
            this.members = JSON.parse(members)
            request.get("/MembersHome2/" + this.currentPage + "/" + this.pageSize + "/" + this.members.mid + "/" + this.sortItem + "/" + this.search).then(res => {
                this.total = res.data.total
                this.MembersHomeList = res.data.records
                //轮播图——最多收藏
                let maxFavoritesItem = res.data.records[0].fcount
                this.maxFavoritesObject = res.data.records[0]
                for (let i = 0; i < this.pageSize; i++) {
                    if (res.data.records[i].fcount > maxFavoritesItem) {
                        maxFavoritesItem = res.data.records[i].fcount
                        this.maxFavoritesObject = res.data.records[i]
                    }
                }
                console.log("轮播图——最多收藏", this.maxFavoritesObject)
                //轮播图——最高评分
                let maxAvgGradeItem = res.data.records[0].avgGrade
                this.maxAvgGradeObject = res.data.records[0]
                for (let i = 0; i < this.pageSize; i++) {
                    if (res.data.records[i].avgGrade > maxAvgGradeItem) {
                        maxAvgGradeItem = res.data.records[i].avgGrade
                        this.maxAvgGradeObject = res.data.records[i]
                    }
                }
                console.log("轮播图——最高评分", this.maxAvgGradeObject)
                //   轮播图——官方推荐
                for (let i = 0; i < this.pageSize; i++) {
                    if (res.data.records[i].status !== 3
                        && res.data.records[i].shopid !== this.maxFavoritesObject.shopid
                        && res.data.records[i].shopid !== this.maxAvgGradeObject.shopid
                        && res.data.records[i].avgGrade !== this.maxAvgGradeObject.avgGrade
                        && res.data.records[i].fcount !== this.maxFavoritesObject.fcount) {
                        this.luckyShopObject = res.data.records[i]
                    }
                }
                console.log("轮播图——官方推荐", this.luckyShopObject)
            })
        },
        methods: {
            load() {
                request.get("/MembersHome2/" + this.currentPage + "/" + this.pageSize + "/" + this.members.mid + "/" + this.sortItem + "/" + this.search).then(res => {
                    this.total = res.data.total
                    this.MembersHomeList = res.data.records
                    console.log("获取列表", res)
                })
            },
            sort(sortItem) {
                this.sortItem = sortItem
                this.load()
            },
            MembersShopDetail(shopid) {
                //row.shopid 根据店铺id查 跳转到店铺详情界面
                this.$router.push({
                    name: 'membersShopDetail',
                    query: {shopid: shopid, activeTab: "ShopDetail"}//    activeTab（是参数名，起什么都可以） 是跳转过去之后指定显示哪个卡片
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
            //收藏店铺
            CollectShop(fid, shopid) {
                console.log("点击了收藏 fid = " + fid + ",shopid=" + shopid + ",mid = " + this.members.mid)
                this.favorites.fid = fid
                this.favorites.mid = this.members.mid
                this.favorites.shopid = shopid
                this.favorites.gid = 0
                this.favorites.isfavorite = 1
                console.log(this.favorites)
                request.post("/collect", this.favorites).then(res => {
                    if (res.code === '0') {
                        this.$message.success("收藏成功")
                        this.load();
                    } else
                        this.$message.error(res.msg)
                })
                this.load();
            },
            //取消收藏
            cancelCollect(fid) {
                console.log("点击了取消收藏 fid：", fid)
                request.put("/cancelCollect/" + fid).then(res => {
                    res.code==='0' ? this.$message.info("已取消收藏") : this.$message.error(res.msg)
                    this.load()
                    // if (res.code === '0') {
                    //     this.$message.info("已取消收藏")
                    //     this.load();
                    // } else
                    //     this.$message.error(res.msg)
                })
                this.load();
            },

        }
    }
</script>

<!-- ↓scoped 局部的，让里面的样式不会被其他组件的同class名的样式覆盖掉。 -->
<style scoped>
    .el-carousel__item h3 {

        color: #475669;
        font-size: 14px;
        opacity: 0.75;
        line-height: 200px;
        margin: 0;
    }
    .photo {
        display: block;
        width: 25%;
        height: 25%;
        padding-left: 10px
    }
    .address {
        flex: 8;
        font-size: 13px;
        color: #999;
    }
    .shopid{
    color: #999999;
    font-size: smaller;
    padding: 10px 0  0 10px
}
    .shopname{
        padding-left: 170px;font-size: large;
    }
    .lunbotu{
        padding:10px  0 0 200px;
    }
    .tushangtu{
        width:150px;height:150px
    }
</style>