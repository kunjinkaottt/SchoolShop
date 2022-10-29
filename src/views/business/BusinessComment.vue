<template>
    <div style="margin-top: 10px; margin-bottom: 80px">
        <el-card v-if="this.commentList.length===0" style="width: 60%; margin: 10px" > <el-empty description="暂无评论"></el-empty> </el-card>
        <el-card v-else style="width: 60%; margin: 10px" >
<!--            <div style="padding: 20px; color: #888">
                <div>
                    <el-input type="textarea" :rows="3" v-model="entity.content"></el-input>
                    <div style="text-align: right; padding: 10px"><el-button type="primary" @click="save">留言</el-button></div>
                </div>
            </div>-->
            <div style="display: flex; padding: 20px" v-for="item in commentList" :key="item.cid">
                <div style="padding: 0 10px; flex: 5">
                    <div><b style="font-size: 14px">{{ item.username }}&nbsp:</b></div>
                    <div style="padding: 10px 0; color: #888">
                        {{ item.comment }}
                        <div style="color: goldenrod;float:right ">评分：{{item.grade}}</div>
                    </div>

                    <div style="color: #888; font-size: 12px">
                        <span>评论时间 : {{item.ctime}}</span>
                    </div>
                    <hr align=center color=black SIZE=1/>
                </div>
            </div>
<!--            分页-->
            <div class="block">
                <el-pagination
                        @current-change="handlecurrentChange"
                        :current-page="currentPage"
                        :page-count="pageCount"
                        layout="prev, pager, next"
                        :total="total">
                </el-pagination>
            </div>

        </el-card>
    </div>
</template>

<script>
    import request from "../../utils/request";

    export default {
        name: "BusinessComment",
        data(){
            return{
                commentList:[],
                currentPage:1,
                pageCount:0,
                total:0,

            }
        },
        created() {
            this.load();
        },
        methods:{
            load(){
                let business = sessionStorage.getItem("user") || "{}"
                    request.get("/MyShopCommentList/" + this.currentPage+ "/" +JSON.parse(business).shopid).then(res => {
                        this.pageCount = res.data.pages //总页数
                        this.commentList = res.data.records
                        this.total = res.data.total //总条数
                        console.log("获取该店铺所有评论comment列表", res)
                    })
            },
            handlecurrentChange(currentPage){
                this.currentPage = currentPage
                this.load()
            }
        }
    }
</script>

<style scoped>

</style>