# pornMagnetSpringBoot
大家好啊，这个项目是我用来学习springboot以及elasticsearch还有java基础相关的一些知识。所以可能有些地方写得很拉跨、希望大佬勿喷。
首先介绍一下这个主要的用途

基本功能

1、可以用来爬javdb这个网站上的片子信息，包括标题、番号、演员名称、是否有字幕、以及磁力链接并且保存到本地，并且可以点击缩略图预览
![1675259637429](https://user-images.githubusercontent.com/105400068/216062166-bcc637b1-7ab6-44f2-9fc2-b8ad09cc83d5.png)




2、爬取到片子信息并且保存到mysql数据库中后，可以根据不同的字段来搜索想要的片子
![1675259741333](https://user-images.githubusercontent.com/105400068/216062446-0639221c-4b67-4784-b9bb-f3d096def72e.png)



3、同时可以把当前数据库中的有的所有片子含有的种类分类，展示在首页。不知道搜索什么类别的时候可以选择，然后连同上面的搜索条目一起查找，点击下面的绿色按钮即可
![1675259831195](https://user-images.githubusercontent.com/105400068/216062801-2beb5f0e-1f7f-41c6-8dca-9bb24939bc3c.png)



4、如果想要某个片子的磁链，可以点击后面的磁链复制，或者多选前面的复选框多选，多选后选择批量复制就可以批量复制了。复制后，下一次查询的时候，复制过的片子，后面磁链按钮会变红，提醒我们这个片子已经被我们复制过了。
![1675259993321](https://user-images.githubusercontent.com/105400068/216063478-348a94be-6e4c-4fd3-b634-6e108d7cd3b6.png)



以上

好了，如果对您有用的话可以继续往下看，如何进行配置。首先介绍一下这个项目用到的技术栈都是很简单的包括：springboot、mybatis、mysql、elasticsearch、linux系统的一些操作等。

准备工作
1、首先希望你有一点java、spring相关的基础，如果哪里遇到问题的话可以咨询我。

2、同时需要自备梯子，用来上javdb.com网站（这里怎么获取梯子自己去找）



2、配置你本地的mysql连接，修改账号密码。两个模块都要改，在application.yml文件里面，同时还有一个爬虫我文件的地址也要配置，导入建表语句（这里提醒一下，希望最好使用mysql8.0版本）
![1675260463081](https://user-images.githubusercontent.com/105400068/216065238-e383bc66-0b15-4b4b-ae1f-4533bc9ef100.png)
![1675260941463](https://user-images.githubusercontent.com/105400068/216067204-967af55c-fbac-4190-ae71-cf146d7b9f26.png)
![1675260986539](https://user-images.githubusercontent.com/105400068/216067391-64794a0c-1eec-485f-933f-091c4e9e8923.png)



3、配置elasticsearch地址
![1675260605701](https://user-images.githubusercontent.com/105400068/216065858-9692beec-de2e-4c3c-b64d-f873e90cf00c.png)

4、配置爬虫中图片的保存文件夹，前端显示图片的提取文件夹也要修改
![1675260810105](https://user-images.githubusercontent.com/105400068/216066738-0c409cf4-efc1-4139-933c-a530a0cb6627.png)
![1675261060127](https://user-images.githubusercontent.com/105400068/216067679-f48cc922-c6f4-44b5-9f39-bc853747e4ef.png)


使用方法
1、上javdb网站，找到想要爬的页面，注意这里要点到第2页，只有这样url里面才会出现 page= 这个字符串。举例
![1675261314452](https://user-images.githubusercontent.com/105400068/216068749-d1ed82f0-5e25-4359-9d85-a9ca721bdf5f.png)
随后，复制当前的url，点击首页上 跳转到搜索页这个按钮 ，把url放进去，输入你要爬的页数，就可以开始下载信息了。
![1675261365885](https://user-images.githubusercontent.com/105400068/216068935-d3b8f4d7-9c60-4d4d-9bb8-e8212e89e62d.png)
![1675261383720](https://user-images.githubusercontent.com/105400068/216068981-95b4376b-013c-4244-8800-3d595c85bb49.png)

2、如果还有出现什么问题、或者宝贵的建议欢迎联系我一起交流学习 微信：z13626087940 谢谢大家
