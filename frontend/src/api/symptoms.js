import request from '@/utils/request'

// 获取症状
export function getSymptoms(params) {
    return request({
        url: '/symptoms/search',
        method: 'get',
        params
    })
}

export function getAllSymptoms() {
    return request({
        url: '/symptoms/all',
        method: 'get'
    });
}