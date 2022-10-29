<template>
    <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex; background-image:linear-gradient(45deg,dodgerblue,royalblue);">
        <div style="width: 400px; padding-left: 30px ; font-weight: bold; color: white">校 内 店 铺 管 理 系 统 (管理员后台)</div>
        <div style="flex: 1"></div>
        <div style="width: 150px">
            <el-dropdown>
                  <span class="el-dropdown-link" style="color: white">
<!--                      {{form.phone}} -->
                   <i class="el-icon-user-solid"></i>管理员<i class="el-icon-arrow-down el-icon--right"></i>
                  </span>
                <template #dropdown>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item @click="this.dialogVisible=true">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>

            <el-dialog
                    title="提示"
                    v-model="dialogVisible"
                    width="30%">
                <p style="text-align: center">确定退出登录吗？</p>
                <span>
                    <el-button @click="close">取 消</el-button>
                    <el-button type="primary" @click="out">确 定</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Header",
        data() {
            return {
                dialogVisible: false,
                form:{}
            }
        },
        created() {
            let str = sessionStorage.getItem("user") || "{}"
            this.form = JSON.parse(str)
        },
        methods: {
            close(){
                this.dialogVisible=false
                this.$message({
                    type:"info",
                    message:"已取消"
                })
            },
            out() {
                this.dialogVisible=false
                this.$router.replace('/login');     //replace 跳转的路由不能通过浏览器的back返回上一个页面
                sessionStorage.removeItem("user")
                this.$message({
                    type:"success",
                    message:"已退出登录并清除缓存"
                })
            }
        }
    }
</script>

<style scoped>

</style>