// // vue.config.js
// module.exports = {
//     configureWebpack: {
//       resolve: {
//         alias: {
//           '@': require('path').resolve(__dirname, 'src'),
//         },
//       },
//     },
//   };
//
//   module.exports = {
//     chainWebpack: (config) => {
//       config.plugin('define').tap((definitions) => {
//         Object.assign(definitions[0], {
//           __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: 'false', // 或者 'true' 如果你需要详细的错误信息
//         });
//         return definitions;
//       });
//     },
//   };
const path = require('path');

module.exports = {
  transpileDependencies: [
    'chart.js' // 让 Vue CLI 自动转译 chart.js
  ],
  configureWebpack: {
    resolve: {
      alias: {
        '@': path.resolve(__dirname, 'src'), // 确保别名正确
      },
    },
  },
  chainWebpack: config => {
    // 仅保留对 JS 文件的基础处理，不额外添加复杂的 exclude/include（避免冲突）
    config.module
        .rule('js')
        .test(/\.js$/)
        .use('babel-loader')
        .loader('babel-loader')
        .end();
  },
  devServer: {
    proxy: {
      '/immuse-risk': {
        target: 'http://localhost:8085',
        changeOrigin: true
      }
    }
  }
};