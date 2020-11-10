# 简介
做一个从多张图片中随机选择一张的App.
# 主要特性
1. 支持倒计时
2. 支持网络下载图片
3. 支持改变图片背景框颜色
4. 支持动画显示图片
# 效果图
![](https://img-blog.csdnimg.cn/img_convert/68a91f226f62b4519166d797779dc6c5.gif)
# 下载地址
![](https://img-blog.csdnimg.cn/img_convert/5cc4a6d01f0f6793721b817ee8277e80.png)
https://gitee.com/cxyzy1/select-beauty/raw/master/apk/app7-debug.apk
# 技术点
1. 协程Coroutines:用于执行网络调用及界面更新
2. Retrofit:获取网络数据
3. Glide:加载图片
4. 流式布局FlowLayout:用于自动换行显示任意数量子控件
5. 本地相册选择(ImageSelector):用于从本地相册中选择图片
6. 自定义View倒计时:实现倒计时功能
# 代码目录截图
![](https://img-blog.csdnimg.cn/img_convert/86904177d6c1d8f320536735332925de.png)

# 完整源代码
https://gitee.com/cxyzy1/select-beauty