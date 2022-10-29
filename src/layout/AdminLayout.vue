<template>
    <div>
            <!--头部-->
            <AdminHeader/>
            <!--主体-->
            <div style="display: flex">
                <!--           侧边栏-->
                <AdminAside/>
                <!--            内容区域       router下的index.js中配置的children子路由 会在router-view展示 -->
                <router-view style="flex: 1"/>
            </div>

        <!--       :show-close="false"隐藏右上角×按钮，:close-on-click-modal="false" 不能通过点击空白处关闭弹窗-->
        <el-dialog
                :show-close="false"
                :close-on-click-modal="false"
                v-model="dialogVisible"
                fullscreen="true"
                center="true">
            <p style="font-size:50px;text-align: center">无权限访问！！!</p>
        </el-dialog>

    </div>
</template>

<script>
    import AdminHeader from "../components/AdminHeader";
    import AdminAside from "../components/AdminAside";
    import router from "../router";

    export default {
        name: "AdminLayout",
        components: {
            AdminHeader,
            AdminAside
        },
        data() {
            return {
                dialogVisible:false,
            }
        },
        created() {
            let userJson = sessionStorage.getItem("user");
            if(!userJson ){  //如果为空，跳转到登陆界面
                router.replace('/login')
            }
            if (JSON.parse(userJson).shopid !== -1){
                this.dialogVisible=true
            }
        },
    }
</script>

<style scoped>

</style>