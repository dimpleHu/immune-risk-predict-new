import request from '@/utils/request'

// 获取飞行项目列表
export function getFlightProjects(params) {
  return request({
    url: '/flightProject/list',
    method: 'get',
    params
  })
}

// 获取飞行项目详情
export function getProjectDetail(projectId) {
  return request({
    url: `/flightProject/detail/${projectId}`,
    method: 'get'
  })
}

// 获取热门飞行项目
export function getHotProjects(params) {
  return request({
    url: '/flightProject/hot',
    method: 'get',
    params
  })
} 