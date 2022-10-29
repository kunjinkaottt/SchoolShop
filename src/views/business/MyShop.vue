<template>
    <div>
        <el-card style="width: 60%; margin: 10px">
            <el-form ref="form" :model="myShop" label-width="80px">
                <el-form-item style="text-align: center" label-width="0">
                    <el-upload
                            class="avatar-uploader"
                            action="http://localhost:8081/uploadFile"
                            :show-file-list="false"
                            :on-success="handlePhotoSuccess"
                            :before-upload="beforeUpload" >
                        <img v-if="myShop.photo" :src="myShop.photo" class="photo">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
                <el-form-item label="店铺编号" style="width:30%">
                    <el-input v-model="myShop.shopid" disabled></el-input>
                </el-form-item>
                <el-form-item label="店铺名称" style="width:30%">
                    <el-input v-model="myShop.shopname"></el-input>
                </el-form-item>
                <el-form-item label="店铺类型" style="width:30%">
                    <el-select v-model="myShop.shoptype" placeholder="请选择店铺类型">
                        <el-option label="餐饮" value="餐饮"></el-option>
                        <el-option label="娱乐" value="娱乐"></el-option>
                        <el-option label="快递" value="快递"></el-option>
                        <el-option label="零售" value="零售"></el-option>
                        <el-option label="服饰" value="服饰"></el-option>
                        <el-option label="理发" value="理发"></el-option>
                        <el-option label="打印" value="打印"></el-option>
                        <el-option label="健身房" value="健身房"></el-option>
                        <el-option label="其他" value="其他"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="店铺状态">
                    <el-radio-group v-model="myShop.status">
                        <el-radio :label="1">营业</el-radio>
                        <el-radio :label="2">休息</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="店铺地址">
                    <el-input v-model="myShop.address" disabled></el-input>
                </el-form-item>
                <el-form-item label="店铺简介">
                    <el-input maxlength="128" show-word-limit v-model="myShop.introduce" type="textarea"
                              rows="3"></el-input>
                </el-form-item>
                <el-form-item label="营业时间">
                    <el-input maxlength="128" show-word-limit v-model="myShop.openinghours" type="textarea"
                              rows="3"></el-input>
                </el-form-item>
                <el-form-item label="招聘信息">
                    <el-input maxlength="128" show-word-limit v-model="myShop.recruit" type="textarea" rows="3"
                              placeholder="输入招聘信息，招聘信息不为空即自动发布招聘，普通用户可以申请应聘，招聘信息清空后则不再接收新的应聘申请。"></el-input>
                </el-form-item>
            </el-form>
            <div style="text-align: center">
                <el-button type="primary" @click="update" style="background-color: #42b983;border:0">保存修改</el-button>
            </div>
        </el-card>

    </div>
</template>

<script>
    import request from "../../utils/request";

    export default {
        name: "BusinessShop",
        data() {
            return {
                myShop: {},
            }
        },
        created() {
            let business = sessionStorage.getItem("user") || "{}"
            console.log(JSON.parse(business).shopid) //JSON.parse()才能通过 .键 取到对象中的 值
            request.get("/MyShop/" + JSON.parse(business).shopid).then(myShop => {
                this.myShop = myShop
            })
        },
        methods: {
            update() {      //点击 保存修改 按钮
                if (this.myShop.shopname===''){ //店铺名为空时不能保存修改
                    this.$message.error('店铺名称不能为空!');
                }else {
                    request.put("/updateMyShop", this.myShop).then(res => {
                        console.log(res)
                        if (res.code === '0') {
                            this.$message.success("保存修改成功")
                        } else {
                            this.$message.error(res.msg)
                            sessionStorage.removeItem("user")
                            this.$router.replace("/login")
                        }
                    })
                }
            },
            handlePhotoSuccess(response) {      //选择图片 成功
                console.log("选择店铺图片 成功",response)
                this.myShop.photo = response   //把图片路径赋值给本地myShop对象中的photo键
                this.$message.success("图片上传成功,未保存")
            },
            beforeUpload(file){     //判断图片是不是小于1M的jpg或png
                const isJPG = file.type === 'image/jpeg';
                const isPNG = file.type === 'image/png';
                const isLt1M = file.size / 1024 / 1024 < 1;

                if (!isJPG&&!isPNG) {
                    this.$message.error('只能上传JPG或PNG格式的图片!');
                }
                else if (!isLt1M) {
                    this.$message.error('上传图片大小不能超过 1MB!');
                }
                return isJPG||isPNG && isLt1M;
            },
        }
    }
</script>

<style scoped>
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 120px;
        height: 120px;
        line-height: 120px;
        text-align: center;
    }

    .photo {
        width: 158px;
        height: 158px;
        display: block;
    }
</style>



























