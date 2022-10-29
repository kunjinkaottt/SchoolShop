<template>
<!--    配置国际化 -->
    <el-config-provider :locale="locale">

            <router-view/>

    </el-config-provider>
</template>

<script>

    import { ElConfigProvider } from 'element-plus'
    import zhCn from 'element-plus/lib/locale/lang/zh-cn'//设置为中文

    export default {
        name: 'App',
        components: {
            ElConfigProvider,
        },
        data() {
            return {
                locale: zhCn
            }
        }
    }
</script>
