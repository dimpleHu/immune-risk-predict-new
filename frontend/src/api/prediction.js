// import request from '@/utils/request'
//
// // 新增预测记录
// export function addPrediction(data) {
//     return request({
//         url: '/predictions/add',
//         method: 'post',
//         data
//     })
// }
//
// // 根据ID查询预测记录
// export function getPredictionList(predictionId) {
//     return request({
//         url: `/history/userlist`,
//         method: 'get'
//     })
// }
//
// // 根据ID查询预测记录
// export function getPredictionById(predictionId) {
//     return request({
//         url: `/predictions/${predictionId}`,
//         method: 'get'
//     })
// }
//
// // 根据用户ID查询预测记录列表
// export function getPredictionsByUserId(userId) {
//     return request({
//         url: `/predictions/user/${userId}`,
//         method: 'get'
//     })
// }
//
// // 更新预测记录
// export function updatePrediction(data) {
//     return request({
//         url: '/predictions',
//         method: 'put',
//         data
//     })
// }
//
// // 根据ID删除预测记录
// export function removePredictionById(predictionId) {
//     return request({
//         url: `/predictions/${predictionId}`,
//         method: 'delete'
//     })
// }
//
//
//
//

// 引入axios实例
import request from '@/utils/request'
import prediction from "@/views/prediction/index.vue";

/**
 * 获取用户的预测记录列表（带分页和筛选）
 * @param {Object} params - 查询参数
 * @param {number} params.page - 页码，默认1
 * @param {number} params.limit - 每页条数，默认10
 * @param {number} params.userId - 用户ID
 * @param {string} [params.startDate] - 开始日期
 * @param {string} [params.endDate] - 结束日期
 * @returns {Promise} - 返回请求的Promise对象
 */
export function getUserPredictionList(params) {
    return request({
        url: '/predictions/userlist',
        method: 'get',
        params
    })
}

/**
 * 根据ID获取预测记录详情
 * @param {number} id - 预测记录ID
 * @returns {Promise} - 返回请求的Promise对象
 */
// export function getPredictionById(id) {
//     return request({
//         url: `/predictions/detail/${id}`,
//         method: 'get'
//     })
// }
export function getPredictionById(id){
    return request({
        url: `/predictions/detail/${id}`,
        method: 'get'
    })
}

/**
 * 根据用户ID获取所有预测记录
 * @param {number} userId - 用户ID
 * @returns {Promise} - 返回请求的Promise对象
 */
export function getPredictionsByUserId(userId) {
    return request({
        url: `/predictions/user/${userId}`,
        method: 'get'
    })
}

/**
 * 新增预测记录
 * @param {Object} data - 预测记录数据
 * @param {number} data.userId - 用户ID
 * @param {number} data.age - 年龄
 * @param {number} data.sex - 性别（0-女，1-男）
 * @param {string} data.symptomsID - 症状ID，多个用逗号分隔
 * @param {string} [data.otherInfo] - 其他信息
 * @returns {Promise} - 返回请求的Promise对象
 */
export function addPrediction(data) {
    return request({
        url: '/predictions/add',
        method: 'post',
        data
    })
}


/**
 * 更新预测记录
 * @param {Object} data - 预测记录数据
 * @param {number} data.predictionID - 预测记录ID
 * @param {number} [data.age] - 年龄
 * @param {number} [data.sex] - 性别（0-女，1-男）
 * @param {string} [data.symptomsID] - 症状ID，多个用逗号分隔
 * @returns {Promise} - 返回请求的Promise对象
 */
export function updatePrediction(data) {
    return request({
        url: '/predictions',
        method: 'put',
        data
    })
}

/**
 * 根据ID删除预测记录
 * @param {number} id - 预测记录ID
 * @returns {Promise} - 返回请求的Promise对象
 */
export function removePredictionById(id) {
    return request({
        url: `/predictions/${id}`,
        method: 'delete'
    })
}
