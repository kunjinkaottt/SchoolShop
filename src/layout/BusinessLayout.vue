<template>
    <div>
            <!--头部-->
            <BusinessHeader/>
            <!--主体-->
            <div style="display: flex">
                <!--           侧边栏-->
                <BusinessAside/>
                <!--            内容区域-->
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
    import BusinessHeader from "../components/BusinessHeader";
    import BusinessAside from "../components/BusinessAside";
    import router from "../router";

    export default {
        name: "BusinessLayout",
        components: {
            BusinessAside,
            BusinessHeader
        },
        data() {
            return {
                dialogVisible:true
            }
        },
        created() {
            let userJson = sessionStorage.getItem("user");
            console.log("user:",userJson)
            if(!userJson){  //如果为空，跳转到登陆界面
                router.replace('/login')
            }if (JSON.parse(userJson).shopid>0){
                this.dialogVisible=false
            }
        }
    }
</script>

<style scoped>

</style>