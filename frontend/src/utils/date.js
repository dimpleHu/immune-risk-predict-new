/**
 * 日期工具类 - 提供日期格式化和计算功能
 */

/**
 * 格式化日期时间
 * @param {Date|string|number} date 日期对象、时间戳或日期字符串
 * @param {string} format 格式化模式，例如 'YYYY-MM-DD HH:mm:ss'
 * @returns {string} 格式化后的日期字符串
 */
export function formatDate(date, format = 'YYYY-MM-DD HH:mm:ss') {
  if (!date) return '';
  
  // 转换日期对象
  const dateObj = typeof date === 'object' ? date : new Date(date);
  
  // 检查日期是否有效
  if (isNaN(dateObj.getTime())) {
    console.warn('Invalid date:', date);
    return '';
  }
  
  const year = dateObj.getFullYear();
  const month = padZero(dateObj.getMonth() + 1);
  const day = padZero(dateObj.getDate());
  const hours = padZero(dateObj.getHours());
  const minutes = padZero(dateObj.getMinutes());
  const seconds = padZero(dateObj.getSeconds());
  
  return format
    .replace(/YYYY/g, year)
    .replace(/YY/g, String(year).slice(2))
    .replace(/MM/g, month)
    .replace(/M/g, String(dateObj.getMonth() + 1))
    .replace(/DD/g, day)
    .replace(/D/g, String(dateObj.getDate()))
    .replace(/HH/g, hours)
    .replace(/H/g, String(dateObj.getHours()))
    .replace(/mm/g, minutes)
    .replace(/m/g, String(dateObj.getMinutes()))
    .replace(/ss/g, seconds)
    .replace(/s/g, String(dateObj.getSeconds()));
}

/**
 * 获取当前日期时间的格式化字符串
 * @param {string} format 格式化模式
 * @returns {string} 格式化后的当前日期时间
 */
export function getNow(format = 'YYYY-MM-DD HH:mm:ss') {
  return formatDate(new Date(), format);
}

/**
 * 获取当前日期的格式化字符串
 * @param {string} format 格式化模式
 * @returns {string} 格式化后的当前日期
 */
export function getToday(format = 'YYYY-MM-DD') {
  return formatDate(new Date(), format);
}

/**
 * 计算两个日期之间的差值
 * @param {Date|string|number} date1 日期1
 * @param {Date|string|number} date2 日期2
 * @param {string} unit 返回单位：days, hours, minutes, seconds
 * @returns {number} 差值
 */
export function dateDiff(date1, date2, unit = 'days') {
  const dateObj1 = new Date(date1);
  const dateObj2 = new Date(date2);
  
  const diffMs = Math.abs(dateObj2.getTime() - dateObj1.getTime());
  
  switch (unit) {
    case 'days':
      return Math.floor(diffMs / (1000 * 60 * 60 * 24));
    case 'hours':
      return Math.floor(diffMs / (1000 * 60 * 60));
    case 'minutes':
      return Math.floor(diffMs / (1000 * 60));
    case 'seconds':
      return Math.floor(diffMs / 1000);
    default:
      return diffMs;
  }
}

/**
 * 获取相对于某个日期的偏移日期
 * @param {Date|string|number} date 基准日期
 * @param {number} days 偏移天数，正数为未来，负数为过去
 * @param {string} format 返回的日期格式
 * @returns {string} 偏移后的日期
 */
export function getRelativeDate(date, days, format = 'YYYY-MM-DD') {
  const dateObj = new Date(date);
  dateObj.setDate(dateObj.getDate() + days);
  return formatDate(dateObj, format);
}

/**
 * 获取过去n天的日期
 * @param {number} days 天数
 * @param {string} format 返回的日期格式
 * @returns {string} 过去的日期
 */
export function getPastDay(days, format = 'YYYY-MM-DD') {
  return getRelativeDate(new Date(), -days, format);
}

/**
 * 获取未来n天的日期
 * @param {number} days 天数
 * @param {string} format 返回的日期格式
 * @returns {string} 未来的日期
 */
export function getFutureDay(days, format = 'YYYY-MM-DD') {
  return getRelativeDate(new Date(), days, format);
}

/**
 * 判断日期是否为今天
 * @param {Date|string|number} date 日期
 * @returns {boolean} 是否为今天
 */
export function isToday(date) {
  const dateObj = new Date(date);
  const today = new Date();
  return dateObj.getDate() === today.getDate() && 
         dateObj.getMonth() === today.getMonth() && 
         dateObj.getFullYear() === today.getFullYear();
}

/**
 * 判断日期是否在过去
 * @param {Date|string|number} date 日期
 * @returns {boolean} 是否在过去
 */
export function isPast(date) {
  return new Date(date).getTime() < new Date().getTime();
}

/**
 * 判断日期是否在未来
 * @param {Date|string|number} date 日期
 * @returns {boolean} 是否在未来
 */
export function isFuture(date) {
  return new Date(date).getTime() > new Date().getTime();
}

/**
 * 将日期格式化为友好的相对时间表示
 * @param {Date|string|number} date 日期
 * @returns {string} 友好的时间表示，如"刚刚"、"5分钟前"、"2小时前"等
 */
export function formatRelativeTime(date) {
  if (!date) return '';

  const now = new Date();
  const d = new Date(date);
  const diff = now - d;
  const seconds = Math.floor(diff / 1000);
  const minutes = Math.floor(seconds / 60);
  const hours = Math.floor(minutes / 60);
  const days = Math.floor(hours / 24);

  if (days > 0) {
    return `${days}天前`;
  } else if (hours > 0) {
    return `${hours}小时前`;
  } else if (minutes > 0) {
    return `${minutes}分钟前`;
  } else {
    return '刚刚';
  }
}

/**
 * 数字补零
 * @param {number} num 数字
 * @returns {string} 补零后的字符串
 */
function padZero(num) {
  return num < 10 ? `0${num}` : String(num);
}

// 默认导出所有日期工具函数
export default {
  formatDate,
  getNow,
  getToday,
  dateDiff,
  getRelativeDate,
  getPastDay,
  getFutureDay,
  isToday,
  isPast,
  isFuture,
  formatRelativeTime
}; 