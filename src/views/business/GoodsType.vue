<template>
    <div style="padding: 10px">
        <el-card>
            <div id="goodsChart" :style="{width: '1200px', height: '600px'}"></div>
        </el-card>
   <!--     <el-card v-else>
            <el-empty description="暂无商品"></el-empty>
        </el-card>-->
    </div>
</template>

<script>
    import request from "../../utils/request";

    let business = sessionStorage.getItem("user")
    export default {
        name: "GoodsType",
        data() {
            return {
                GoodsTypeList: [],
            }
        },
        mounted() {//页面渲染完成时则行
            this.drawLine();
        },
        methods: {
            drawLine() {
                // 基于准备好的dom，初始化echarts实例
                let goodsChart = this.$root.echarts.init(document.getElementById('goodsChart'))
                let option = {
                    title: {
                        text: '各商品类型对应数量统计图',
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
                request.get("/goodstype/" + JSON.parse(business).shopid).then(res => {
                    if (res.code === '0') {
                        this.GoodsTypeList = res.data
                        res.data.forEach(item => {
                            option.series[0].data.push({name: item.goodstype, value: item.count})
                        })
                        // 绘制图表
                        goodsChart.setOption(option);
                    }
                })

            },
        },
    }
</script>

<style scoped>

</style>