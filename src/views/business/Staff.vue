<template>
    <el-card v-if="this.staffList.length===0" style=" margin: 3%">
        <el-empty description="暂无员工数据"></el-empty>
    </el-card>
    <div v-else style="padding: 15px;">
        <!--    搜索-->
        <div>
            <el-input v-model="search" placeholder="输入员工姓名或其手机号码查询" style="width: 25% ;margin: 15px"
                      clearable></el-input>
            <el-button type="primary" @click="load" style="background-color: #42b983;border:0"> 查询</el-button>
        </div>
        <!--        表格      :header-cell-style="{textAlign: 'center'}"表头居中对齐   :cell-style="{ textAlign: 'center' }"表格内容居中对齐 -->
        <el-table
                :header-cell-style="{textAlign: 'center'}"
                :cell-style="{ textAlign: 'center' }"
                :data="staffList"
                height="704"
                border
                style="width: 98%">
            <el-table-column
                    type="index"
                    width="50"
                    label="序号">
            </el-table-column>
            <el-table-column prop="mid" label="用户编号"></el-table-column>
            <el-table-column prop="truename" label="员工姓名"></el-table-column>
            <el-table-column prop="gender" label="性别" sortable>
                <template #default="scope">
                    <label v-if="scope.row.gender==1">男</label>
                    <label v-if="scope.row.gender==2">女</label>
                </template>
            </el-table-column>
            <el-table-column prop="phone" label="手机号码"></el-table-column>
            <el-table-column prop="create_time" label="入职时间" width="600px" sortable></el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作">
                <template #default="scope">
                    <!--                    <el-button @click="handleCheck(scope.row)" type="primary" size="small">
                                            查看
                                        </el-button>-->
                    <el-popconfirm
                            confirm-button-text='确认'
                            cancel-button-text='取消'
                            icon="el-icon-info"
                            icon-color="red"
                            title="确定移除该员工吗？"
                            @confirm="handleRemoveStaff(scope.row.staffid,scope.row.mid)">
                        <template #reference>
                            <el-button type="danger" size="small">移除</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

    </div>

</template>

<script>
    import request from "../../utils/request";

    export default {
        name: "Staff",
        data() {
            return {
                staffList: [],
                search: '',
                dialogVisible: false,//可视化 ：关闭
                shopid:0,

            }
        },
        created() {
            this.load();
        },
        methods: {
            load() {
                let business = sessionStorage.getItem("user") || "{}"
                this.shopid = JSON.parse(business).shopid
                request.get("/MystaffList/" + this.shopid + "/" + this.search).then(res => {
                    this.staffList = res
                })
            },
            handleRemoveStaff(staffid, mid) {     //移除员工
                request.delete("/RemoveStaff/" + staffid + "/" + mid + "/" + this.shopid ).then(res => {
                    if (res.code === '0') {
                        this.$message.success("成功移除该员工")
                        this.load()
                    } else {
                        this.$message.error(res.msg)
                    }
                })
                this.load()
            }
        }
    }
</script>

<style scoped>

</style>