const storage = {
    set(key, value) {
        try {
            // 特殊处理 Token，直接存储不做 JSON 序列化
            if (key === 'Token' || key === 'token') {
                localStorage.setItem(key, value);
                return;
            }
            
            // 其他数据正常处理
            if (typeof value === 'string') {
                localStorage.setItem(key, value);
            } else {
                localStorage.setItem(key, JSON.stringify(value));
            }
        } catch (error) {
            console.error('存储数据失败:', error);
        }
    },
    get(key) {
        try {
            const value = localStorage.getItem(key);
            if (!value) return "";
            
            // 特殊处理 Token，确保没有引号
            if (key === 'Token' || key === 'token') {
                // 移除任何引号
                return value.replace(/^["']|["']$/g, "");
            }
            
            // 尝试解析 JSON，如果失败则返回原始值
            try {
                const parsed = JSON.parse(value);
                return typeof parsed === 'string' ? parsed : parsed;
            } catch (e) {
                return value;
            }
        } catch (error) {
            console.error('获取数据失败:', error);
            return "";
        }
    },
    getObj(key) {
        try {
            const value = localStorage.getItem(key);
            if (!value) return null;
            
            try {
                return JSON.parse(value);
            } catch (e) {
                return null;
            }
        } catch (error) {
            console.error('获取对象数据失败:', error);
            return null;
        }
    },
    remove(key) {
        try {
            localStorage.removeItem(key);
            
            // 如果删除 Token，确保两种 key 都删除
            if (key === 'Token') {
                localStorage.removeItem('token');
            } else if (key === 'token') {
                localStorage.removeItem('Token');
            }
        } catch (error) {
            console.error('删除数据失败:', error);
        }
    },
    clear() {
        try {
            localStorage.clear();
        } catch (error) {
            console.error('清除存储失败:', error);
        }
    }
}
export default storage;
