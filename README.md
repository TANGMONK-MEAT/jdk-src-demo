

## 在 IDEA(2019.3) 搭建 jdk1.8.0_231 源码阅读环境
<!-- TOC -->
目录
- [1. JDK源码在哪里？](#1-jdk源码在哪里)
- [2. 搭建步骤](#2-搭建步骤)
- [3. 搭建过程中的问题总结](#3-搭建过程中的问题总结)

<!-- /TOC -->

参考博客

* [IDEA构建JDK源码阅读的环境以及遇到的问题](https://blog.csdn.net/dataiyangu/article/details/105011703)

* [自己编译JDK源码踩坑纪实](https://my.oschina.net/hansonwang99/blog/4387387)

### 1. JDK源码在哪里？

> 找到JDK安装包所在目录，会看到src.zip的压缩包，这里面就是JDK的源码

![tUlo6K.png](https://s1.ax1x.com/2020/06/03/tUlo6K.png)
***

### 2. 搭建步骤

* 新建一个简单的Java工程，把源码src.zip解压到该工程下的src目录下，编译源码

> 打开idea，菜单栏File —> Project，出现如下图:

[![tU3Y5j.png](https://s1.ax1x.com/2020/06/03/tU3Y5j.png)](https://imgchr.com/i/tU3Y5j)
> 在点Next， 选个初始化一个应用，出现如下图：

[![tU3JaQ.png](https://s1.ax1x.com/2020/06/03/tU3JaQ.png)](https://imgchr.com/i/tU3JaQ)
> 再点Next, 输入项目名， 及保存路径：

[![tU3GVg.png](https://s1.ax1x.com/2020/06/03/tU3GVg.png)](https://imgchr.com/i/tU3GVg)
> 最后出现的界面如下：  
> 注意：  
> （1）此时在工程下只有一个空的src包  
> （2）需要先将src.zip解压出的文件夹，复制到此src目录下  
> （3）再在src目录下创建一个Main类    
> （4）再进行编译debug，但是编译会报错误:  
> > 问题1：缺少com.sun.tools包  
> > 问题2：缺少sun.awt.UNIXToolkit 和 sun.font.FontConfigManager这两个类  
> > 问题3：debug的时候，调用的src.zip中的文件  
> > 问题4：资源不足

![tU8dFH.png](https://s1.ax1x.com/2020/06/03/tU8dFH.png)

***

### 3. 搭建过程中的问题总结

问题1：缺少com.sun.tools包  
> File —> Project structure —> Libraries 把jdk路径下的lib包下的tools.jar添加到工程中

[![tUYTXt.png](https://s1.ax1x.com/2020/06/03/tUYTXt.png)](https://imgchr.com/i/tUYTXt)
问题2：缺少sun.awt.UNIXToolkit 和 sun.font.FontConfigManager这两个类  
> 在src的目录下手动添加这两个类  
> 解决缺少的这两个类，可以去[OpenJDK](http://openjdk.java.net/)拷贝  
> 拷贝参考的博客：https://blog.csdn.net/IT_Migrant_worker/article/details/104743218

[![tUtBE8.png](https://s1.ax1x.com/2020/06/03/tUtBE8.png)](https://imgchr.com/i/tUtBE8)


问题3：debug的时候，调用的src.zip中的文件
> 移除掉src.zip文件，按下图操作即可  

[![tUYo6I.png](https://s1.ax1x.com/2020/06/03/tUYo6I.png)](https://imgchr.com/i/tUYo6I)

问题4：资源不足
> 增加堆区的内存大小（原来默认是 700，增加到 1000试试）

[![tUNoeP.png](https://s1.ax1x.com/2020/06/03/tUNoeP.png)](https://imgchr.com/i/tUNoeP)


#### 微信公众号
[![tYkNnK.jpg](https://s1.ax1x.com/2020/06/02/tYkNnK.jpg)](https://imgchr.com/i/tYkNnK)

#### 博客
https://blog.csdn.net/weixin_44730681/article/details/106519418
