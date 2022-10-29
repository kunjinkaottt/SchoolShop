<template>
    <el-tabs v-model="witchCard" type="border-card">
        <el-page-header @back="goBack" content="返回首页"/>
        <el-tab-pane label="店铺详情" name="ShopDetail">
            <div>
                <el-card style="width: 80%; margin: 10px;">
                    <!--                    图片-->
                    <el-image
                            style="width: 180px; height: 180px;padding: 0;"
                            :src="ShopDetail.photo"
                            :preview-src-list="[ShopDetail.photo]">
                        <template #error>
                            <img style="width: 180px; height: 180px;" src="http://localhost:8081/nophoto.png"/>
                        </template>
                    </el-image>

                    <el-descriptions title="基本信息" :column="2" border style="padding: 1%">
                        <el-descriptions-item>
                            <template #label><i class="el-icon-user"></i> 商家姓名 （商家编号：{{ShopDetail.bid}}）</template>
                            {{ShopDetail.truename}}
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label><i class="el-icon-phone"></i> 联系方式</template>
                            {{ShopDetail.phone}}
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label><i class="el-icon-s-shop"></i> 店铺名称 （店铺编号：{{ShopDetail.shopid}}）</template>
                            {{ShopDetail.shopname===null?'(该店铺商家暂未填写)':ShopDetail.shopname}}
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label><i class="el-icon-menu"></i> 店铺类型</template>
                            <el-tag>{{ShopDetail.shoptype===null?'(该店铺商家暂未填写)':ShopDetail.shoptype}}</el-tag>
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label><i class="el-icon-star-on"/> 平均评分</template>
                            <div style="color: goldenrod">
                                <label style="color: #888888;font-size: small"
                                       v-if="ShopDetail.avgGrade===null||ShopDetail.avgGrade===0.0">暂无评分
                                </label>
                                <div v-else>
                                    <el-rate
                                            v-model="ShopDetail.avgGrade"
                                            disabled
                                            show-score
                                            text-color="#ff9900">
                                    </el-rate>
                                </div>
                            </div>
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label> 店铺状态</template>
                            <div v-if="ShopDetail.status===1" style="color: #42b983 ;font-size: medium  ">营业</div>
                            <div v-if="ShopDetail.status===2" style="color: mediumvioletred">休息</div>
                        </el-descriptions-item>
                    </el-descriptions>
                    <br/>
                    <el-descriptions title="详细信息" :column="1" style="font-size: medium;padding: 1%">
                        <el-descriptions-item label="店铺简介:"><i class="el-icon-tickets"></i>
                            {{ShopDetail.introduce===null?'(该店铺商家暂未填写)':ShopDetail.introduce}}
                        </el-descriptions-item>
                        <el-descriptions-item label="营业时间:"><i class="el-icon-time"></i>
                            {{ShopDetail.openinghours===null?'(该店铺商家暂未填写)':ShopDetail.openinghours}}
                        </el-descriptions-item>
                        <el-descriptions-item label="具体地址:"><i class="el-icon-location-outline"></i>
                            {{ShopDetail.address}}
                        </el-descriptions-item>
                        <el-descriptions-item label="招聘信息:" v-if="ShopDetail.recruit!==null"><i
                                class="el-icon-user"></i> {{ShopDetail.recruit}}
                        </el-descriptions-item>
                    </el-descriptions>
                </el-card>
            </div>
        </el-tab-pane>

        <el-tab-pane label="店铺评论" name="ShopComment">
            <el-card v-if="this.commentList.length===0" style="width: 60%; margin: 10px">
                <el-empty description="暂无评论"></el-empty>
            </el-card>
            <el-card v-else style="width: 60%; margin: 10px">
                <div style="display: flex; padding: 20px;" v-for="item in commentList">
                    <div style="padding: 0 10px; flex: 5">
                        <div style="font-size: 14px">
                            <b>{{item.username }}&nbsp:</b>
                        </div>
                        <div style="padding: 20px 0; color: #888">
                            {{ item.comment }}
                            <div style="color: goldenrod;float:right ">评分：{{item.grade}}</div>
                        </div>
                        <div style="color: #888; font-size: 12px">
                            <span>{{item.ctime}}</span>
                        </div>
                        <hr align=center color=black SIZE=1/>
                    </div>
                </div>
                <!-- 评论 分页-->
                <div class="block">
                    <el-pagination
                            @current-change="commentsCurrentChange"
                            :current-page="commentscurrentPage"
                            :page-count="commentspageCount"
                            layout="prev, pager, next"
                            :total="commentsTotal">
                    </el-pagination>
                </div>
            </el-card>
        </el-tab-pane>

        <el-tab-pane label="店铺商品" name="ShopGoods">
            <!--    搜索-->
            <el-input v-model="search" placeholder="输入商品名或商品类型查询" style="width: 25% ;margin: 15px" clearable></el-input>
            <el-button type="primary" size="small"
                       style="background-color: #42b983;border:0" @click="loadGoods"> 查询
            </el-button>
            <el-card v-if="this.GoodsList.length===0" style="width: 50%; margin: 10px">
                <el-empty description="店铺暂无商品"></el-empty>
            </el-card>
            <el-card v-else style="width: 90%; margin: 10px">
                <!--                该店铺所有商品表格 -->
                <el-table
                        :header-cell-style="{textAlign: 'center'}"
                        :cell-style="{ textAlign: 'center' }"
                        :data="GoodsList"
                        height="560"
                        border
                        style="width: 98%">
                    <el-table-column prop="gid" label="商品编号" width="120px" sortable></el-table-column>
                    <el-table-column prop="img" label="图片">
                        <template #default="scope">
                            <el-image
                                    style="width: 70px; height: 70px;padding: 0"
                                    :src="scope.row.img"
                                    :preview-src-list="[scope.row.img]">
                                <template #error>
                                    <el-image src="http://localhost:8081/noimg.png"></el-image>
                                </template>
                            </el-image>
                        </template>
                    </el-table-column>
                    <el-table-column prop="goodsname" label="商品名称"></el-table-column>
                    <el-table-column prop="price" label="商品价格" sortable>
                        <template #default="scope"><label style="color:darkorange">￥{{scope.row.price}}</label>
                        </template>
                    </el-table-column>
                    <el-table-column prop="goodstype" label="商品类型"></el-table-column>
                </el-table>
                <!--商品 分页-->
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="currentPage"
                        :page-sizes="[5,10,20]"
                        :page-size="pageSize"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="total">
                </el-pagination>
            </el-card>
        </el-tab-pane>
    </el-tabs>

</template>

<script>
    import request from "../../utils/request";

    export default {
        name: "BusinessShopDetail",
        data() {
            return {
                shopid: 0,
                ShopDetail: [],
                witchCard: 'ShopDetail',  //默认显示第一个卡片 店铺详情
                commentList: [],
                total: 0,
                GoodsList: [],
                search: '',
                pageSize: 10,
                currentPage: 1,
                commentscurrentPage: 1,
                commentsTotal: 0,
                commentspageCount: 0,

            }
        },
        created() {
            this.shopid = this.$route.query.shopid  //从load转移过来
            this.witchCard = this.$route.query.witchCard
            this.load()
        },
        methods: {
            load() {
                request.get("/ToShopDetail/" + this.shopid).then(res => {
                    this.ShopDetail = res.data[0]
                    this.ShopDetail.avgGrade = res.data[0].avgGrade
                    console.log("商家》店铺详情", res.data[0])
                })
                this.loadComment()
                this.loadGoods()
            },
            //加载该店铺所有评论
            loadComment() {
                //获取该店铺所有评论内容
                request.get("/CommentList/" + this.commentscurrentPage + "/" + this.shopid).then(res => {
                    this.commentList = res.data.records
                    this.commentsTotal = res.data.total
                    this.commentspageCount = res.data.pages//总页数
                })
            },
            //加载该店铺所有商品
            loadGoods() {
                request.get("/ToGoodsList/" + this.currentPage + "/" + this.pageSize + "/" + this.shopid + "/" + this.search).then(res => {
                    this.total = res.data.total
                    this.GoodsList = res.data.records
                    console.log("获取的ToGoodsList列表", res)
                })
            },
            //返回首页
            goBack() {
                this.$router.push("/business/BusinessHome")
            },
            //  所以商品-改变每页记录条数
            handleSizeChange(pageSize) {
                this.pageSize = pageSize
                this.loadGoods()
            },
            //所以商品-改变当前页码
            handleCurrentChange(currentPage) {
                this.currentPage = currentPage
                this.loadGoods()
            },
            //店铺评论-改变当前页码
            commentsCurrentChange(commentscurrentPage) {
                this.commentscurrentPage = commentscurrentPage
                this.loadComment()
            }
        }
    }
</script>

<style scoped>

</style>