<template>
    <el-tabs v-model="activeTab" type="border-card">
<!--        <el-page-header @back="goBack" content="返回首页" />-->
        <el-tab-pane label="店铺详情" name="ShopDetail" style="height: 80vh">
            <div>
                <el-card style="width: 80%; margin:10px auto;">
                    <!--                    图片-->
                    <el-image
                            style="width: 180px; height: 180px;padding: 0;"
                            :src="ShopDetail.photo"
                            :preview-src-list="[ShopDetail.photo]">
                        <template #error>
                            <img style="width: 180px; height: 180px;" src="http://localhost:8081/nophoto.png"/>
                        </template>
                    </el-image>
                    <!--                    已收藏-->
                    <el-button v-if="ShopDetail.isfavorite===1" type="primary"
                               style="float: right;background-color: darkorange;border: 0;"
                               @click="cancelCollectShop(ShopDetail.fid)"
                               icon="el-icon-star-on"
                               circle>
                    </el-button>
                    <!--                    未收藏    存在收藏过又取消删除的情况0 和未收藏过null-->
                    <el-button v-if="ShopDetail.isfavorite===0||ShopDetail.isfavorite===null" type="primary"
                               style="float: right;background-color: darkorange;border: 0;"
                               @click="CollectShop(ShopDetail.fid,ShopDetail.shopid)"
                               icon="el-icon-star-off"
                               circle>
                    </el-button>
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
                        <el-descriptions-item label="店铺简介:"><i class="el-icon-tickets"></i> {{introduce}}
                        </el-descriptions-item>
                        <el-descriptions-item label="营业时间:"><i class="el-icon-time"></i> {{openinghours}}
                        </el-descriptions-item>
                        <el-descriptions-item label="具体地址:"><i class="el-icon-location-outline"></i>
                            {{ShopDetail.address}}
                        </el-descriptions-item>
                        <el-descriptions-item label="招聘信息:" v-if="ShopDetail.recruit!==null"><i
                                class="el-icon-user"></i> {{ShopDetail.recruit}}
                        </el-descriptions-item>
                    </el-descriptions>
                    <div v-if="ShopDetail.recruit!==null && !memberisstaff" style="text-align: center">
                        <el-button type="primary" @click="apply(ShopDetail.shopid,ShopDetail.bid)"
                                   style="background-color: darkorange;border: 0">申请应聘
                        </el-button>
                    </div>
                </el-card>
            </div>
        </el-tab-pane>

        <el-tab-pane label="店铺评论" name="ShopComment">
            <el-card style="width: 80%; margin: 10px auto">
                <div style="padding: 20px; color: #888">
                    <el-select v-model="grade" placeholder="选择评分">
                        <el-option label="5分" value="5"></el-option>
                        <el-option label="4分" value="4"></el-option>
                        <el-option label="3分" value="3"></el-option>
                        <el-option label="2分" value="2"></el-option>
                        <el-option label="1分" value="1"></el-option>
                    </el-select>
                    <div>
                        <el-input type="textarea" :rows="3" maxlength="128" show-word-limit
                                  placeholder="发一条友善的评论" v-model="commentobject.comment"
                        ></el-input>
                        <div style="text-align: right; padding: 10px">
                            <el-button type="primary" style="background-color:darkorange;border:0" @click="sendComment">留言</el-button>
                        </div>
                    </div>
                </div>
                <div v-if="this.commentList.length===0">
                    <el-empty description="暂无评论"></el-empty>
                </div>
                <div v-else>
                <div style="display: flex; padding: 20px;" v-for="item in commentList">
                    <div style="padding: 0 10px; flex: 5">
                        <div>
                            <b style="font-size: 14px"><b v-if="item.mid===this.mid">(我的评论)</b>{{ item.username }}&nbsp:</b>
                                <el-popconfirm
                                        v-if="item.mid===this.mid"
                                        title="确定删除这条评论吗？"
                                        @confirm="deleteMyComment(item.cid)">
                                    <template #reference>
                                        <el-button   style="float: right"  type="danger"  size="small" >删除  </el-button>
                                    </template>
                                </el-popconfirm>
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
                <!--                    评论 分页-->
                <div class="block">
                    <el-pagination
                            @current-change="commentsCurrentChange"
                            :current-page="commentscurrentPage"
                            :page-count="commentspageCount"
                            layout="prev, pager, next"
                            :total="commentsTotal">
                    </el-pagination>
                </div>
                </div>
            </el-card>
        </el-tab-pane>

        <el-tab-pane label="店铺商品" name="ShopGoods" style="height: 80vh">
            <el-card v-if="this.GoodsList.length===0" style="width: 80%; margin:10px auto" > <el-empty description="店铺暂无商品"></el-empty> </el-card>
            <el-card v-else style="width: 90%; margin: 10px auto">
  <!--    搜索-->
                <el-input v-model="search" placeholder="输入商品名或商品类型查询" style="width: 25% ;margin: 15px" clearable></el-input>
                <el-button style="background-color: darkorange;color: white;border:0" @click="loadGoods"> 查询</el-button>
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
                                    :preview-src-list="[scope.row.img]" >
                                <template #error>
                                    <img src="http://localhost:8081/onimg.png" alt="没有图片">
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
                            label="操作">
                        <template #default="scope">
                            <!--                    未收藏          存在收藏过又取消删除的情况0 和未收藏过null-->
                            <el-button v-if="scope.row.isfavorite!==1" type="primary"
                                       style="background-color: darkorange;border: 0;margin:5%"
                                       @click="CollectGoods(scope.row.fid,scope.row.shopid,scope.row.gid)"
                                       icon="el-icon-star-off"
                                       circle>
                            </el-button>
                            <!--                    已收藏   -->
                            <el-button v-else type="primary"
                                       style="background-color: darkorange;border: 0;margin:5%"
                                       @click="cancelCollectGoods(scope.row.fid)"
                                       icon="el-icon-star-on"
                                       circle>
                            </el-button>

                        </template>
                    </el-table-column>
                </el-table>
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
            </el-card>
        </el-tab-pane>
    </el-tabs>

</template>

<script>
    import request from "../../utils/request";

    let members = sessionStorage.getItem("user") || "{}"

    export default {
        name: "MembersShopDetail",
        data() {
            return {
                activeTab:'ShopDetail',

                ShopDetail: {},
                shopid: 0,
                mid: 0,
                applyobject: {},
                memberisstaff: false,
                favorites: {},
                value1: null,
                grade: '',
                commentList: [],
                commentobject: {comment: ''},

                commentscurrentPage:1,
                commentsTotal:0,
                commentspageCount:0,

                //↓ 该店铺商品
                search: '',
                currentPage: 1,//当前页  默认第1页
                pageSize: 10,//默认每页5条数据
                total: 0,
                GoodsList: [],

            };
        },
        created() {
            this.activeTab = this.$route.query.activeTab
            this.load()
            //判断该普通用户是不是这家店的员工
            request.get("/MemberIsStaff/" + this.mid + "/" + this.shopid).then(res => {
                if (res.code === '0') {
                    this.$message.info(res.msg)
                    this.memberisstaff = true
                }
            })
        },
        methods: {
            load() {
                this.shopid = this.$route.query.shopid
                this.mid = JSON.parse(members).mid
                request.get("/MemberToShopDetail/" + this.mid + "/" + this.shopid).then(res => {   //获取该店铺各项信息 包括该普通用户对店铺的收藏情况
                    this.ShopDetail = res.data[0]
                    console.log(res.data[0])
                })
                this.loadComment()
                this.loadGoods()
            },
            //加载该店铺所有评论
            loadComment(){
                //获取该店铺所有评论内容
                request.get("/MemberToShopDetailCommentList/"+this.commentscurrentPage+ "/" + this.mid + "/" + this.shopid).then(res => {
                    console.log("普通用户店铺评论列表",res)
                    this.commentList = res.data.records
                    this.commentsTotal = res.data.total
                    this.commentspageCount = res.data.pages //总页数
                })
            },
            //加载该店铺所有商品     包括该普通用户对商品的收藏情况
            loadGoods(){
                request.get("/MemberToGoodsList/" + this.currentPage + "/" + this.pageSize + "/" + this.$route.query.shopid + "/" + JSON.parse(members).mid + "/" + this.search).then(res => {
                    this.total = res.data.total
                    this.GoodsList  = res.data.records
                    console.log("获取的MemberToGoodsList列表", res)
                })
            },
//返回首页
            goBack() {
                this.$router.push("/members/MembersHome")
            },
            gettime() {     // 获取当前年月日时分秒
                var date = new Date()
                var year = date.getFullYear();
                var month = date.getMonth() + 1;    //date.getMonth()获取的是0-11 0代表一月
                var day = date.getDate();
                var hours = date.getHours();
                var minutes = date.getMinutes()
                var seconds = date.getSeconds()
                if (month % 10 === month) {
                    month = "0" + month}
                if (day<10) { day = "0" + day  }
                if (hours<10) { hours = "0" + hours  }
                if (minutes<10) { minutes = "0" + minutes  }
                if (seconds<10) { seconds = "0" + seconds  }
                let time = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
                return time;
            },
            //申请应聘
            apply(shopid, bid) {
                let member = JSON.parse(members)
                this.applyobject.mid = member.mid
                this.applyobject.shopid = shopid
                // this.applyobject.applytime = this.gettime()
                this.applyobject.applystate = 0
                this.applyobject.bid = bid
                request.post("/Apply", this.applyobject).then(res => {
                    if (res.code === '0') {
                        this.$message.success("申请成功，等待商家处理")
                    } else {
                        this.$message.error(res.msg)
                    }
                })
            },

            //收藏店铺
            CollectShop(fid, shopid) {
                console.log("点击了收藏 fid = " + fid + ",shopid=" + shopid + ",mid = " + JSON.parse(members).mid)
                this.favorites.fid = fid
                this.favorites.mid = JSON.parse(members).mid
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
            },
            //取消收藏店铺
            cancelCollectShop(fid) {
                console.log("点击了取消收藏 fid：", fid)
                request.put("/cancelCollect/" + fid).then(res => {
                    if (res.code === '0') {
                        this.$message.info("已取消收藏")
                        this.load()
                    } else
                        this.$message.error(res.msg)
                })
            },
            // 发送评论
            sendComment() {
                this.commentobject.mid = this.mid
                this.commentobject.grade = this.grade
                this.commentobject.shopid = this.shopid
                this.commentobject.ctime = this.gettime()
                console.log("评论内容",this.commentobject)
                if (this.grade === '') {
                    this.$message.info("请先选择评分")
                } else {
                    if (this.commentobject.comment === '') {
                        this.$message.error("评论内容不能为空")
                    } else {
                        request.post("/addComment", this.commentobject).then(res => {
                            if (res.code === '0') {
                                this.$message.success("留言成功")
                            }
                            this.commentobject.comment = this.grade = '';  //清空评论内容 和评分
                            this.load()
                        })
                    }
                }
            },
            //删除我的评论
            deleteMyComment(cid){
                console.log("删除的评论编号",cid)
                request.delete("/DeleteMyComment/"+cid).then(res=>{
                    if (res.code==='0'){
                        this.$message.success("删除评论成功")
                        this.load()
                    }else {
                        this.$message.error(res.msg)
                    }
                })
            },
            //收藏商品
            CollectGoods(fid, shopid,gid){
                console.log("点击了收藏 fid = " + fid + ",shopid=" + shopid + ",mid = " + JSON.parse(members).mid +",gid="+gid)
                this.favorites.fid = fid
                this.favorites.mid = JSON.parse(members).mid
                this.favorites.shopid = shopid
                this.favorites.gid = gid
                this.favorites.isfavorite = 1
                    request.post("/collect", this.favorites).then(res => {
                        if (res.code === '0') {
                            this.$message.success("收藏商品成功")
                            this.loadGoods();
                        } else
                            this.$message.error(res.msg)
                    })
                    this.loadGoods()
                this.favorites={}
            },
            //取消收藏商品
            cancelCollectGoods(fid){
                console.log("点击了取消收藏 fid：", fid)
                request.put("/cancelCollect/"+fid).then(res=>{
                    if (res.code==='0'){
                        this.$message.info("已取消该商品收藏")
                        this.loadGoods();
                    }else
                        this.$message.error(res.msg)
                })
            },
            handleSizeChange(pageSize) {//  改变每页记录条数
                this.pageSize = pageSize
                this.loadGoods()
            },
            handleCurrentChange(currentPage) {//改变当前页码
                this.currentPage = currentPage
                this.loadGoods()
            },
            commentsCurrentChange(commentscurrentPage){
                this.commentscurrentPage = commentscurrentPage
                this.loadComment()
            }
        },
        computed:{
            introduce(){
                return this.ShopDetail.introduce === null ? '(该店铺商家暂未填写)' : this.ShopDetail.introduce
            },
            openinghours(){
                return this.ShopDetail.openinghours===null?'(该店铺商家暂未填写)':this.ShopDetail.openinghours
            },

        }
    }
</script>

<style scoped>

</style>