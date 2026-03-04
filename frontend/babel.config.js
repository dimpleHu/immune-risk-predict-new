// module.exports = {
//   presets: [
//     '@vue/cli-plugin-babel/preset'
//   ]
// }

module.exports = {
  presets: [
    [
      '@vue/cli-plugin-babel/preset',
      {
        targets: {
          browsers: ['last 2 versions', 'ie >= 11'] // 覆盖主流浏览器
        },
        useBuiltIns: 'usage', // 自动引入 polyfill
        corejs: 3 // 配合 core-js@3
      }
    ]
  ],
  plugins: [
    '@babel/plugin-proposal-class-properties', // 处理类静态属性
    '@babel/plugin-proposal-optional-chaining', // 处理 ?.
    '@babel/plugin-proposal-nullish-coalescing-operator' // 处理 ??
  ]
};