import store from '@/store'

const menu = {
    list() {
        return [{"backMenu":[
                {"child":[
                        {"buttons":["新增","查看","修改","删除"],"menu":"服务分类","tableName":"service-category"},
                        {"buttons":["新增","查看","修改","删除"],"menu":"服务信息","tableName":"service-info"},
                        {"buttons":["新增","查看","修改","删除"],"menu":"预约管理","tableName":"appointment"},
                        {"buttons":["新增","查看","修改","删除"],"menu":"评价管理","tableName":"review"}
                    ],"menu":"服务管理"},
                {"child":[
                        {"buttons":["新增","查看","修改","删除"],"menu":"通知管理","tableName":"notice"},
                        {"buttons":["新增","查看","修改","删除"],"menu":"权限管理","tableName":"permission"},
                        {"buttons":["新增","查看","修改","删除"],"menu":"角色管理","tableName":"role"},
                        {"buttons":["新增","查看","修改","删除","角色授权"],"menu":"用户管理","tableName":"users"},
                        {"buttons":["新增","查看","修改","删除"],"menu":"系统配置","tableName":"config"}
                    ],"menu":"系统管理"}
            ], "hasBackLogin":"是","hasBackRegister":"否", "hasFrontLogin":"否","hasFrontRegister":"否","roleName":"管理员","tableName":"users"},
        ]
    }
}

export function filterMenuByPermissions(menuList) {
  if (!menuList || !menuList.length) return []

  return menuList.filter(menu => {
    // Check if user has required permissions
    if (menu.permission) {
      const userPermissions = store.state.permissions
      if (!userPermissions.includes(menu.permission)) {
        return false
      }
    }

    // Recursively filter children
    if (menu.children && menu.children.length) {
      menu.children = filterMenuByPermissions(menu.children)
      // Only show menu if it has visible children
      return menu.children.length > 0
    }

    return true
  })
}

export function findMenuByPath(menuList, path) {
  if (!menuList || !menuList.length) return null

  for (const menu of menuList) {
    if (menu.path === path) return menu
    if (menu.children && menu.children.length) {
      const found = findMenuByPath(menu.children, path)
      if (found) return found
    }
  }
  return null
}

export function generateBreadcrumb(menuList, currentPath) {
  const breadcrumb = []
  let current = findMenuByPath(menuList, currentPath)

  while (current) {
    breadcrumb.unshift(current)
    current = current.parent
  }

  return breadcrumb
}

export default menu;
