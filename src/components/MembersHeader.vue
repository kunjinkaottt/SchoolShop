<template>
    <div style="height: 61px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex; background-color:darkorange ">
        <div style="width: 300px; padding-left: 30px ; font-weight: bold; color: white">校 内 店 铺 管 理 系 统 (前台界面)</div>
        <div style="flex: 1">
            <el-menu :default-active="path"
                     style="align-items: center"
                     router
                     mode="horizontal" >
                <el-menu-item index="/members/membersHome"><i class="el-icon-s-home"></i> 首页</el-menu-item>
                <el-menu-item index="/members/memberComment"><i class="el-icon-s-comment"></i>我的评论</el-menu-item>
                <el-menu-item index="/members/memberApply"><i class="el-icon-s-check"></i>应聘管理</el-menu-item>
                <el-menu-item index="/members/favorites"><i class="el-icon-star-on"></i>我的收藏</el-menu-item>
                <el-menu-item index="/members/membersEdit"><i class="el-icon-s-custom"></i> <span slot="title">个人信息管理</span></el-menu-item>
            </el-menu>
        </div>
        <div style="width: 220px;padding-left: 20px">
            <el-dropdown>
                  <span class="el-dropdown-link" style="color: white">
                   <i class="el-icon-user-solid"></i>普通用户：{{form.username}}<i class="el-icon-arrow-down el-icon--right"></i>
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
        data() {
            return {
                dialogVisible: false,
                form:{},
                path: this.$route.path,  //写在:default-active="path"，当url修改访问路径时，侧边栏对应项目高亮

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
                this.$router.replace('/login');
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