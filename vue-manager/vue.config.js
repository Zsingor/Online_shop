const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer:{
    port:7001,
    proxy: {
      '/api': {
        target: process.env.VUE_APP_BSAEURL,
        changeOrigin: true
      }
    }
  }
})
