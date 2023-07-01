const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})
module.exports = {
  lintOnSave: false  //加入此行 , false为关闭true为开启
}
