<template>
    <div>
        <el-menu
                style="width: 200px; min-height: calc(100vh - 50px); "
                :default-active="path"
                router
                class="el-menu-vertical-demo">
            <!--       首页-->
            <el-menu-item index="/members/membersHome">
                <i class="el-icon-s-home"></i> <span slot="title">首页</span>
            </el-menu-item>
            <el-submenu index="1">
                <template #title> <i class="el-icon-s-comment"></i> 评论管理 </template>
                <el-menu-item index="/members/memberComment">我的评论</el-menu-item>
            </el-submenu>
            <el-submenu index="2">
                <template #title><i class="el-icon-s-check"></i> 应聘管理 </template>
                <el-menu-item index="/members/passApply">已通过的申请</el-menu-item>
                <el-menu-item index="/members/applying">处理中的申请</el-menu-item>
                <el-menu-item index="/members/failApply">不通过的申请</el-menu-item>
            </el-submenu>
            <el-submenu index="3">
                <template #title><i class="el-icon-star-on"></i> 我的收藏 </template>
                <el-menu-item index="/members/favoriteShop">收藏的店铺</el-menu-item>
                <el-menu-item index="/members/favoriteGoods">收藏的商品</el-menu-item>
            </el-submenu>
            <el-menu-item index="/members/membersEdit">
                <i class="el-icon-s-custom"></i> <span slot="title">个人信息管理</span>
            </el-menu-item>
        </el-menu>
    </div>
</template>

<script>
    export default {
        name: "MembersAside",
        data(){
            return{
                path: this.$route.path  //写在:default-active="path"，当url修改访问路径时，侧边栏对应项目高亮
            }
        }
    }
</script>

<style scoped>

</style>