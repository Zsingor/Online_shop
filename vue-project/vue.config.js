const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer:{
    port:7000,
    proxy: {
      '/api': {
        target: process.env.VUE_APP_BSAEURL,
        changeOrigin: true
      }
    }
  }
})
