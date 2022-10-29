import { createRouter, createWebHistory } from 'vue-router'
import AdminLayout from '../layout/AdminLayout.vue'
import BusinessLayout from  '../layout/BusinessLayout.vue'
import MembersLayout from "../layout/MembersLayout";

const routes = [
  {//登录
    path: '/',
    redirect: "/login" //访问根目录时 默认访问login界面
  },
  {//管理员
    path: '/admin',
    name: 'AdminLayout',
    component: AdminLayout,
    redirect:"/admin/adminHome",//访问localhost:8080/admin时 默认访问/admin/adminHome
    children:[
      {//首页
        path:'adminHome',
        name:'AdminHome',
        component: ()=> import("../views/admin/AdminHome"),
      },
      {//营业店铺
        path:'open',
        name:'Open',
        component: ()=> import("../views/admin/Open"),
      },
      {//休息店铺
        path:'rest',
        name:'Rest',
        component: ()=> import("../views/admin/Rest"),
      },
      {//待租店铺
        path:'rent',
        name:'Rent',
        component: ()=> import("../views/admin/Rent"),
      },
      {//店铺分类
        path:'shoptype',
        name:'Shoptype',
        component: ()=> import("../views/admin/ShopType"),
      },
      {//未审核的商家申请
        path:'examine',
        name:'Examine',
        component: ()=> import("../views/admin/Examine"),
      },
      {//不通过的商家申请
        path:'failBusiness',
        name:'FailBusiness',
        component: ()=> import("../views/admin/FailBusiness"),
      },
      {//商家账号
        path:'adminBusiness',
        name:'AdminBusiness',
        component: ()=> import("../views/admin/AdminBusiness"),
      },
      {//普通用户
        path:'adminMembers',
        name:'AdminMembers',
        component: ()=> import("../views/admin/AdminMembers"),
      },
      {//查看店铺详情
        path:'adminShopDetail',
        name:'AdminShopDetail',
        component: ()=> import("../views/admin/AdminShopDetail"),
      }
    ]
  },
  {//商家
    path: '/business',
    name: 'BusinessLayout',
    component: BusinessLayout,
    redirect:"/business/businessHome",  //访问localhost:8080/business时  默认访问/business/businessHome
    children:[
      {//首页
        path:'businessHome',
        name:'BusinessHome',
        component: ()=> import("../views/business/BusinessHome"),
      },
      {//我的店铺
        path:'myShop',
        name:'MyShop',
        component: ()=> import("../views/business/MyShop"),
      },
      {//店铺详情
        path:'businessShopDetail',//url中的名
        name:'BusinessShopDetail',
        component: ()=> import("../views/business/BusinessShopDetail"),//文件路径
      },
      {//店铺评论
        path:'businessComment',
        name:'BusinessComment',
        component: ()=> import("../views/business/BusinessComment"),
      },
      {//员工信息
        path:'staff',
        name:'Staff',
        component: ()=> import("../views/business/Staff"),
      },
      {//应聘申请
        path:'apply',
        name:'Apply',
        component: ()=> import("../views/business/Apply"),
      },
      {//编辑信息
        path:'businessEdit',
        name:'BusinessEdit',
        component: ()=> import("../views/business/BusinessEdit"),
      },
      {//所有商品
        path:'goods',
        name:'Goods',
        component: ()=> import("../views/business/Goods"),
      },
      {//商品分类
        path:'goodstype',
        name:'Goodstype',
        component: ()=> import("../views/business/GoodsType"),
      },
      {//修改密码
        path:'businessPassword',
        name:'BusinessPassword',
        component: ()=> import("../views/business/BusinessPassword"),
      },
    ]
  },
  {//普通用户
    path: '/members',
    name: 'MembersLayout',
    component: MembersLayout,
    redirect: "/members/membersHome",
    children: [
      {//首页
        path: 'membersHome',
        name: 'MembersHome',
        component: () => import("../views/members/MembersHome")
      },
      {//（普通用户的）店铺详情
        path: 'membersShopDetail',
        name: 'membersShopDetail',
        component: () => import("../views/members/MembersShopDetail")
      },
      {//我的评论
        path: 'memberComment',
        name: 'MemberComment',
        component: () => import("../views/members/MemberComment")
      },
      {//应聘申请
        path: 'memberApply',
        name: 'MemberApply',
        component: () => import("../views/members/MemberApply")
      },
      {//收藏的店铺
        path: 'favorites',
        name: 'Favorites',
        component: () => import("../views/members/Favorites")
      },
      {//编辑信息
        path: 'membersEdit',
        name: 'MembersEdit',
        component: () => import("../views/members/MembersEdit")
      }
    ]
  },
  {//登录
    path: '/Login',
    name: 'Login',
    component: () => import("../views/Login"),
  },
  {//注册
    path: '/register',
    name: 'Register',
    component: () => import("../views/Register")
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// 路由

export default router
