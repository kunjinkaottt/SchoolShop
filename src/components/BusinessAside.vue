<template>
    <div>
        <el-menu
                style="width: 200px; min-height: calc(100vh - 50px)"
                :default-active="path"
                router
                class="el-menu-vertical-demo">
            <!--       首页-->
            <el-menu-item index="/business/businessHome">
                <i class="el-icon-s-home"></i> <span slot="title">首页</span>
            </el-menu-item>
            <el-submenu index="1">
                <template #title> <i class="el-icon-s-shop"></i> 店铺管理 </template>
                <el-menu-item index="/business/myShop">我的店铺</el-menu-item>
                <el-menu-item index="/business/businessComment">店铺评论</el-menu-item>
            </el-submenu>
            <el-submenu index="2">
                <template #title><i class="el-icon-s-check"></i> 员工管理 </template>
                <el-menu-item index="/business/staff">员工信息</el-menu-item>
                <el-menu-item index="/business/apply">应聘申请</el-menu-item>
            </el-submenu>
            <el-submenu index="3">
                <template #title><i class="el-icon-s-goods"></i> 商品管理 </template>
                <el-menu-item index="/business/goods">所有商品</el-menu-item>
                <el-menu-item index="/business/goodstype">商品分类</el-menu-item>
            </el-submenu>
            <el-menu-item index="/business/businessEdit">
                <i class="el-icon-s-custom"></i> <span slot="title">个人信息管理</span>
            </el-menu-item>
        </el-menu>
    </div>
</template>

<script>
    export default {
        name: "BusinessAside",
        data(){
            return{
                path: this.$route.path
            }
        }
    }
</script>

<style scoped>

</style>