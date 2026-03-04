// 引入项目已有的axios实例（与预测记录接口保持一致）
import request from '@/utils/request'

/**
 * 普通对话接口（一次性获取完整回复）
 * @param {Object} params - 请求参数
 * @param {string} params.message - 用户输入的提问内容（必填）
 * @returns {Promise} - 返回后端响应的完整文本回复
 */
export function deepSeekChat(params) {
    return request({
        url: '/deepseekApi/chat',
        method: 'post',
        params // 对应后端 @RequestParam 接收参数
    })
}

/**
 * 流式对话接口（实时分段返回回复，适合展示"打字"效果）
 * @param {Object} params - 请求参数
 * @param {string} params.message - 用户输入的提问内容（必填）
 * @returns {Promise} - 返回 SSE 事件流（需前端特殊处理流式数据）
 * @description 注意：此接口返回的是文本事件流，前端需用 EventSource 或 axios 流式拦截器处理
 */
export function deepSeekChatStream(params) {
    return request({
        url: '/deepseekApi/chatStream',
        method: 'post',
        params, // 对应后端 @RequestParam 接收参数
        responseType: 'stream', // 关键配置：告知axios以"流"形式接收响应
        headers: {
            'Accept': 'text/event-stream' // 声明接收 SSE 事件流格式
        }
    })
}