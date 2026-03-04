import request from '@/utils/request'


// 新增：根据单个疾病ID查询详情
export function getDiseaseDetail(id) {
    return request({
        url: `/disease/${id}`, // 路径参数传递ID
        method: 'get',
        params: { id } //参数查询
    })
}

// 新增：根据多个疾病ID批量查询（支持逗号分隔的ID字符串）
export function getDiseasesByIds(ids) {
    return request({
        url: '/disease/batch',
        method: 'get',
        params: {
            ids: ids // 传递格式如 "7,4,5" 的字符串
        }
    })
}

// 新增：获取diseaseID能被四整除的疾病数据
export function getDiseases() {
    return request({
        url: '/disease/divisible-by-four',
        method: 'get'
    })
}