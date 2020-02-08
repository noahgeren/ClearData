module.exports = {
  productionSourceMap: false,
  /* ONLY FOR MULTIPAGE APPS
  pages: {
    client: {
      entry: 'src/client/main.js',
      template: 'public/index.html',
      filename: 'client.html'
    },
    admin: {
      entry: 'src/admin/main.js',
      template: 'public/index.html',
      filename: 'admin.html'
    }
  },
  */
  devServer: {
    /* ONLY FOR MULTIPAGE APPS
    historyApiFallback: {
      rewrites: [
        { from: /^\/game/, to: '/client.html' },
        { from: /^\/admin/, to: '/admin.html' }
      ]
    },
    */
    proxy: 'http://localhost:8080'
  },
  chainWebpack: config => {
    if(config.plugins.has('extract-css')) {
      const extractCSSPlugin = config.plugin('extract-css')
      extractCSSPlugin && extractCSSPlugin.tap(() => [{
        filename: '[name].css',
        chunkFilename: '[name].css'
      }])
    }
  },
  configureWebpack: {
    output: {
      filename: '[name].js',
      chunkFilename: '[name].js'
    }
  }
}