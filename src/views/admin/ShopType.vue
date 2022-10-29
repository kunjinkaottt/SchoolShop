<template>
    <div style="padding: 10px">
<!--        <el-breadcrumb style="padding-bottom: 10px" separator-class="el-icon-arrow-right">&lt;!&ndash; 面包屑 &ndash;&gt;
            <el-breadcrumb-item >店铺管理</el-breadcrumb-item>
            <el-breadcrumb-item>店铺分类</el-breadcrumb-item>
        </el-breadcrumb>-->
        <el-card>
            <div id="myChart" :style="{width: '1200px', height: '600px'}"></div>
        </el-card>
        <el-card>
            <div id="statusChart" :style="{width: '1200px', height: '600px'}"></div>
        </el-card>
    </div>
</template>

<script>
    import request from "../../utils/request";

    export default {
        name: "ShopType",
        data(){
            return{

            }
        },
        mounted() {//页面渲染完成时则行
            this.drawLine();
        },
        methods:{
            drawLine(){
            // 基于准备好的dom，初始化echarts实例
                let myChart = this.$root.echarts.init(document.getElementById('myChart'))
                let statusChart = this.$root.echarts.init(document.getElementById('statusChart'))
                let option = {
                    title: {
                        text: '各店铺类型对应数量统计图',
                        // subtext: '虚拟数据',
                        left: 'left'
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        trigger: 'item'
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            mark: {show: true},
                            dataView: {show: true, readOnly: false},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    series: [
                        {
                            name: '各店铺类型对应数量',
                            type: 'pie',
                            radius: [50, 250],
                            center: ['50%', '50%'],
                            roseType: 'area',
                            itemStyle: {
                                borderRadius: 8
                            },
                            data: []
                        }
                    ]
                }
                let option2 = {
                    title: {
                        text: '各店铺状态对应数量统计图',
                        // subtext: '虚拟数据',
                        left: 'left'
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        trigger: 'item'
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            mark: {show: true},
                            dataView: {show: true, readOnly: false},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    series: [
                        {
                            name: '各店铺状态对应数量',
                            type: 'pie',
                            radius: [50, 250],
                            center: ['50%', '50%'],
                            roseType: 'area',
                            itemStyle: {
                                borderRadius: 8
                            },
                            data: []
                        }
                    ]
                }
                request.get("/shoptype").then(res => {
                    if (res.code === '0') {
                        res.data.forEach(item => {
                            option.series[0].data.push({name: item.shoptype, value: item.count})
                        })
                        // 绘制图表
                        myChart.setOption(option);
                    }
                })
                request.get("/statustype").then(res2=>{
                    if (res2.code === '0') {
                        res2.data.forEach(item => {
                            option2.series[0].data.push({name: item.status===1?'营业': item.status===2?'休息':'待租', value: item.count})
                        })
                        statusChart.setOption(option2);
                    }
                })
            },

        }
    }
</script>

<style scoped>

</style>