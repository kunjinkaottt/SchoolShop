<template>
    <div>
        <el-menu
                style="width: 200px; min-height: calc(100vh - 50px); "
                :default-active="path"
                router
                class="el-menu-vertical-demo">
<!--       首页-->
            <el-menu-item index="/admin/adminHome">
                <i class="el-icon-s-home"></i> <span slot="title">首页</span>
            </el-menu-item>
            <el-submenu index="1">
                <template #title> <i class="el-icon-s-shop"></i> 店铺管理 </template>
                <el-menu-item index="/admin/open">营业的店铺</el-menu-item>
                <el-menu-item index="/admin/rest">休息的店铺</el-menu-item>
                <el-menu-item index="/admin/rent">待租的店铺</el-menu-item>
                <el-menu-item index="/admin/shoptype">店铺分类</el-menu-item>
            </el-submenu>
            <el-submenu index="2">
                <template #title><i class="el-icon-s-check"></i> 审核管理 </template>
                <el-menu-item index="/admin/examine">未审核的商家申请</el-menu-item>
                <el-menu-item index="/admin/failBusiness">不通过的商家申请</el-menu-item>
            </el-submenu>
            <el-submenu index="3">
                <template #title><i class="el-icon-s-custom"></i>用户管理 </template>
                <el-menu-item index="/admin/adminBusiness">商家账号</el-menu-item>
                <el-menu-item index="/admin/adminMembers">普通用户</el-menu-item>
            </el-submenu>

        </el-menu>
    </div>
</template>

<script>
    export default {
        name: "AdminAside",
        data(){
            return{
                path: this.$route.path  //写在:default-active="path"，当url修改访问路径时，侧边栏对应项目高亮
            }
        }
    }
</script>

<style scoped>

</style>