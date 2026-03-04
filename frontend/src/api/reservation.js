import request from '@/utils/request'

/**
 * 获取预约容量信息
 * @param {Object} params - 查询参数
 * @param {number} params.projectId - 项目ID
 * @param {string} params.date - 日期
 * @param {string} [params.location] - 地点
 */
export function getCapacityInfo(params) {
  return request({
    url: '/reservation/capacity',
    method: 'get',
    params: {
      projectId: params.projectId,
      date: params.date,
      location: params.location
    }
  })
}

/**
 * 提交预约
 * @param {Object} data - 预约信息
 */
export function submitReservation(data) {
  return request({
    url: '/reservation/add',
    method: 'post',
    data
  })
}

// 获取用户预约列表
export function getReservationList(params) {
  const userInfo = JSON.parse(localStorage.getItem('userInfo'))
  return request({
    url: '/reservation/list',
    method: 'get',
    params: {
      ...params,
      userID: userInfo.userID
    }
  })
}

// 取消预约
export function cancelReservation(id) {
  return request({
    url: `/reservation/delete/${id}`,
    method: 'delete'
  })
} 