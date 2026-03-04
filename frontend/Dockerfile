# 阶段1：构建前端
FROM node:18-alpine as build-stage
ARG VUE_APP_BASE_API
ENV VUE_APP_BASE_API=${VUE_APP_BASE_API}
WORKDIR /app
COPY package*.json ./
RUN npm install --registry=https://registry.npm.taobao.org  # 国内源加速
COPY . .
RUN npm run build  # 打包生成dist目录

# 阶段2：部署前端（Nginx）
FROM nginx:alpine as production-stage
# 复制打包后的dist到Nginx静态目录
COPY --from=build-stage /app/dist /usr/share/nginx/html
# 复制自定义Nginx配置（解决Vue路由刷新404+反向代理后端接口）
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]