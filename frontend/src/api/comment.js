import request from '@/utils/request'

// 获取最新评论列表
export function getLatestComments(params) {
  return request({
    url: '/api/comments/latest',
    method: 'get',
    params
  })
}

// 添加评论
export function addComment(data) {
  // 从localStorage获取用户信息
  const userInfoStr = localStorage.getItem('userInfo')
  const userInfo = JSON.parse(userInfoStr)
  
  console.log('添加评论参数:', data)  // 添加日志
  
  // 验证必要字段
  if (!data.orderID) {
    throw new Error('缺少订单ID')
  }
  if (!data.projectID) {
    throw new Error('缺少项目ID')
  }
  if (!data.rating) {
    throw new Error('缺少评分')
  }
  if (!data.content) {
    throw new Error('缺少评价内容')
  }
  
  const requestData = {
    userID: userInfo.userID,
    orderID: data.orderID,
    projectID: data.projectID,
    comments: data.content,
    commentImage: data.image || null,
    score: data.rating,
    likes: 0
  }
  
  console.log('发送到服务器的评论数据:', requestData)  // 添加日志
  
  return request({
    url: '/comment/add',
    method: 'post',
    data: requestData
  })
}

// 获取评论
export function getComment(orderID) {
  const userInfoStr = localStorage.getItem('userInfo')
  const userInfo = JSON.parse(userInfoStr)
  
  console.log('获取评论参数:', { orderID, userID: userInfo.userID })
  
  return request({
    url: `/comment/getUserOrderByOrderIdAndUserId`,
    method: 'get',
    params: {
      orderID,
      userID: userInfo.userID
    }
  })
}

// 获取指定项目的评论列表
export function getProjectComments(projectId, params) {
  return request({
    url: `/api/comments/project/${projectId}`,
    method: 'get',
    params
  })
}

// 获取评论列表
export function getCommentList(params) {
  return request({
    url: '/comment/list',
    method: 'get',
    params
  })
}

// 获取我的评价列表
export function getMyComments(params) {
  const userInfo = JSON.parse(localStorage.getItem('userInfo'))
  return request({
    url: '/comment/list',
    method: 'get',
    params: {
      ...params,
      userID: userInfo.userID
    }
  })
}

// 根据项目ID获取评论列表
export function getCommentsByProjectId(projectId) {
  return request({
    url: `/comment/projectcommentlist/${projectId}`,
    method: 'get'
  })
}

// 更新评论点赞数
export function updateLikes(commentID) {
  return request({
    url: `/comment/updateLikes`,
    method: 'put',
    params: {
      commentID
    }
  })
}

// 添加评论回复
export function addCommentReply(data) {
  return request({
    url: '/comment/reply/add',
    method: 'post',
    data
  })
} 