import Vue from 'vue'
import Router from 'vue-router'
import Home from '../components/Home'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Default',
      redirect: '/home',
      component: Home
    },
    {
      // home页面并不需要被访问，只是作为其它组件的父组件
      path: '/home',
      name: 'Home',
      component: Home,
      redirect: '/index',
      children: [
        {
          path: '/index',
          name: 'AppIndex',
          component: () => import('../components/home/AppIndex')
        },
        {
          path: '/jotter',
          name: 'Jotter',
          component: () => import('../components/jotter/Articles')
        },
        {
          path: '/jotter/article',
          name: 'Article',
          component: () => import('../components/jotter/ArticleDetails')
        },
        {
          path: '/jotter/Audit_ArticleDetails',
          name: 'Article',
          component: () => import('../components/jotter/Audit_ArticleDetails')
        },
        {
          path: '/jotter/reexamine_ArticleDetails',
          name: 'reexamine',
          component: () => import('../components/jotter/reexamine_ArticleDetails')
        },
        {
          path: '/jotter/last_ArticleDetails',
          name: 'last',
          component: () => import('../components/jotter/last_ArticleDetails')
        },
        {
          path: '/jotter/admin_ArticleDetails',
          name: 'admin_audit_readonly',
          component: () => import('../components/jotter/admin_Audit_ArticleDetails')
        },
        {
          path: '/admin/content/editor',
          name: 'Editor',
          component: () => import('../components/admin/content/ArticleEditor'),
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/admin/content/add_editor',
          name: 'add_editor',
          component: () => import('../components/admin/content/EditArticlesForm'),
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/library',
          name: 'Library',
          component: () => import('../components/library/LibraryIndex')
        }
      ]
    },
    {
      path: '/news',
      name: 'news',
      component: () => import('../components/news/AppIndex')
    },
    {
      path: '/news/query',
      name: 'query_news',
      component: () => import('../components/news/query_AppIndex')
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('../components/Login')
    },
    {
      path: '/register',
      name: 'Register',
      component: () => import('../components/Register')
    },
    {
      path: '/admin',
      name: 'Admin',
      component: () => import('../components/admin/AdminIndex'),
      meta: {
        requireAuth: true
      },
      children: [
        {
          path: '/admin/dashboard',
          name: 'Dashboard',
          component: () => import('../components/admin/dashboard/admin/index'),
          meta: {
            requireAuth: true
          }
        },
        {
          //文章审核
          path: '/admin/audit/audit',
          name: 'audit',
          component: () => import('../components/admin/audit/ArticleAudit'),
          meta: {
            requireAuth: true
          }
        },
        {
          //文章复审
          path: '/admin/audit/reexamine_audit',
          name: 'reexamine_audit',
          component: () => import('../components/admin/audit/reex_ArticleAudit'),
          meta: {
            requireAuth: true
          }
        },
        {
          //文章终审
          path: '/admin/audit/last_audit',
          name: 'last_audit',
          component: () => import('../components/admin/audit/last_ArticleAudit'),
          meta: {
            requireAuth: true
          }
        },
        {
          //文章审核-管理员查看（无审核权）
          path: '/admin/content/admin_audit',
          name: 'admin_audit',
          component: () => import('../components/admin/content/admin_ArticleAudit'),
          meta: {
            requireAuth: true
          }
        },
        {
          //文章栏目管理
          path: '/admin/content/banner',
          name: 'BannerManagement',
          component: () => import('../components/admin/content/BannerManagement'),
          meta: {
            requireAuth: true
          }
        },
        {
          //编辑文章
          path: '/admin/content/article',
          name: 'ArticleManagement',
          component: () => import('../components/admin/content/ArticleManagement'),
          meta: {
            requireAuth: true
          }
        },
        {
          //文章操作明细
          path: '/admin/content/operate',
          name: 'OperateManagement',
          component: () => import('../components/admin/content/OperateManagement'),
          meta: {
            requireAuth: true
          }
        },
        {
          //用户信息
          path: '/admin/user/profile',
          name: 'Profile',
          component: () => import('../components/admin/user/UserProfile'),
          meta: {
            requireAuth: true
          }
        },
        {
          //个人用户信息
          path: '/admin/user/change_UserProfile',
          name: 'change_UserProfile',
          component: () => import('../components/admin/user/change_UserProfile'),
          meta: {
            requireAuth: true
          }
        },
        {
          //学院基本信息
          path: '/admin/news_manager',
          name: 'College',
          component: () => import('../components/admin/news_manager/College_index'),
          meta: {
            requireAuth: true
          }
        },
        {
          //角色配置
          path: '/admin/user/role',
          name: 'Role',
          component: () => import('../components/admin/user/Role'),
          meta: {
            requireAuth: true
          }
        },
        {
          //新闻业务流程
          path: '/admin/news_manager/process',
          name: 'Process',
          component: () => import('../components/admin/news_manager/Process_index'),
          meta: {
            requireAuth: true
          }
        }
      ]
    },
    {
      path: '*',
      component: () => import('../components/pages/Error404')
    }
  ]
})

// 用于创建默认路由
export const createRouter = routes => new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Default',
      redirect: '/home',
      component: Home
    },
    {
      // home页面并不需要被访问，只是作为其它组件的父组件
      path: '/home',
      name: 'Home',
      component: Home,
      redirect: '/index',
      children: [
        {
          path: '/index',
          name: 'AppIndex',
          component: () => import('../components/home/AppIndex')
        },
        {
          path: '/jotter',
          name: 'Jotter',
          component: () => import('../components/jotter/Articles')
        },
        {
          path: '/jotter/article',
          name: 'Article',
          component: () => import('../components/jotter/ArticleDetails')
        },
        {
          path: '/jotter/allarticle',
          name: 'allarticle',
          component: () => import('../components/jotter/allArticleDetails')
        },
        {
          path: '/admin/content/editor',
          name: 'Editor',
          component: () => import('../components/admin/content/ArticleEditor'),
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/library',
          name: 'Library',
          component: () => import('../components/library/LibraryIndex')
        }
      ]
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('../components/Login')
    },
    {
      path: '/register',
      name: 'Register',
      component: () => import('../components/Register')
    },
    {
      path: '/admin',
      name: 'Admin',
      component: () => import('../components/admin/AdminIndex'),
      meta: {
        requireAuth: true
      },
      children: [
        {
          path: '/admin/dashboard',
          name: 'Dashboard',
          component: () => import('../components/admin/dashboard/admin/index'),
          meta: {
            requireAuth: true
          }
        }
      ]
    },
    {
      path: '*',
      component: () => import('../components/pages/Error404')
    }
  ]
})
