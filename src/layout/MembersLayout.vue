<template>
    <div>
            <!--头部-->
            <MembersHeader/>
            <!--主体-->
            <div class="layout">
                <!--           侧边栏-->
<!--                <MembersAside/>-->
                <!--            内容区域-->
                <router-view style="width: 1500px ;margin: 0 auto"/>
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

    import MembersHeader from "../components/MembersHeader";
    import router from "../router";

    export default {
        name: "MembersLayout", // 跟文件名保持一致
        components: {
            MembersHeader
        },
        data() {
            return {
                dialogVisible:false
            }
        },
        created() {
            let userJson = sessionStorage.getItem("user");
            console.log("user:",userJson)
            if(!userJson){  //如果为空，跳转到登陆界面
                router.replace('/login')
            }
            console.log(JSON.parse(userJson).username)
            if (!JSON.parse(userJson).username){
                this.dialogVisible=true
            }
        }
    }
</script>

<style scoped>
.layout{
    display: flex;
    /*background-image: linear-gradient(45deg,darkorange,lightskyblue,#42b983);*/
}
</style>