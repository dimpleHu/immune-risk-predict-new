/**
 * 日期格式化
 * @param {(Object|string|number)} time
 * @param {string} pattern
 * @returns {string}
 */
export function parseTime(time, pattern) {
  if (arguments.length === 0 || !time) {
    return null
  }
  let format = pattern || 'yyyy-MM-dd HH:mm:ss'
  let date
  if (typeof time === 'object') {
    date = time
  } else {
    if ((typeof time === 'string') && (/^[0-9]+$/.test(time))) {
      time = parseInt(time)
    } else if (typeof time === 'string') {
      time = time.replace(new RegExp(/-/gm), '/')
    }
    if ((typeof time === 'number') && (time.toString().length === 10)) {
      time = time * 1000
    }
    date = new Date(time)
  }
  const formatObj = {
    'y+': date.getFullYear(),
    'M+': date.getMonth() + 1,
    'd+': date.getDate(),
    'H+': date.getHours(),
    'm+': date.getMinutes(),
    's+': date.getSeconds()
  }
  for (const key in formatObj) {
    if (new RegExp(`(${key})`).test(format)) {
      const str = formatObj[key].toString()
      const match = RegExp.$1
      format = format.replace(match, (match.length === 1) ? str : padLeftZero(str))
    }
  }
  return format
}

/**
 * 左侧补零
 * @param {string} str
 * @returns {string}
 */
function padLeftZero(str) {
  return ('00' + str).substr(str.length)
}

/**
 * 格式化时间段
 * @param {string} timeSlot 时间段字符串，格式如：09:00-10:00
 * @returns {string}
 */
export function formatTimeSlot(timeSlot) {
  if (!timeSlot) return ''
  const [start, end] = timeSlot.split('-')
  return `${start || ''} - ${end || ''}`
}

/**
 * 检查对象是否为空
 * @param {Object} obj
 * @returns {boolean}
 */
export function isEmptyObject(obj) {
  return obj === null || obj === undefined || Object.keys(obj).length === 0
}

/**
 * 格式化金额
 * @param {number} amount
 * @returns {string}
 */
export function formatAmount(amount) {
  if (typeof amount !== 'number') {
    return '0.00'
  }
  return amount.toFixed(2)
}

/**
 * 生成随机字符串
 * @param {number} length
 * @returns {string}
 */
export function randomString(length = 32) {
  const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'
  let result = ''
  for (let i = 0; i < length; i++) {
    result += chars.charAt(Math.floor(Math.random() * chars.length))
  }
  return result
}

/**
 * 深拷贝对象
 * @param {Object} source
 * @returns {Object}
 */
export function deepClone(source) {
  if (!source && typeof source !== 'object') {
    throw new Error('error arguments', 'deepClone')
  }
  const targetObj = source.constructor === Array ? [] : {}
  Object.keys(source).forEach(keys => {
    if (source[keys] && typeof source[keys] === 'object') {
      targetObj[keys] = deepClone(source[keys])
    } else {
      targetObj[keys] = source[keys]
    }
  })
  return targetObj
}

/**
 * 防抖函数
 * @param {Function} func
 * @param {number} wait
 * @param {boolean} immediate
 * @returns {Function}
 */
export function debounce(func, wait, immediate) {
  let timeout
  return function() {
    const context = this
    const args = arguments
    const later = function() {
      timeout = null
      if (!immediate) func.apply(context, args)
    }
    const callNow = immediate && !timeout
    clearTimeout(timeout)
    timeout = setTimeout(later, wait)
    if (callNow) func.apply(context, args)
  }
}

/**
 * 节流函数
 * @param {Function} func
 * @param {number} wait
 * @returns {Function}
 */
export function throttle(func, wait) {
  let previous = 0
  return function() {
    const now = Date.now()
    const context = this
    const args = arguments
    if (now - previous > wait) {
      func.apply(context, args)
      previous = now
    }
  }
} 