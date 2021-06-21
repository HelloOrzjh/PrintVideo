# PrintVideo
字符画播放《超级敏感》

&nbsp;

原理是用FFmpegFrameGrabber抓取视频的每一帧 

用灰度化算法转成灰度图 

以一定的时间间隔循环输出

&nbsp;

/img 为视频中抓取的帧

/output 为每一帧对应的灰度图

/img和/output不需要下载 只要确保项目中有这两个文件夹即可

&nbsp;

PrintVideo.java里的prework()方法用来获得/img和/output 

只需运行一次即可添加注释，只使用play()方法输出

这样能避免每次运行都要进行预处理的情况
