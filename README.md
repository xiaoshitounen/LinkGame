# LinkGame
## Android:连连看

运行效果：

图片挂了可以在这里查看：[Android项目8：连连看](http://www.fanandjiu.com/article/45cb5d02.html)


![](https://hexo-photo-1300729795.cos.ap-nanjing.myqcloud.com/hexo-article/Android%E9%A1%B9%E7%9B%AE%EF%BC%9A%E8%BF%9E%E8%BF%9E%E7%9C%8B/linkgame_levels.gif)

![](https://hexo-photo-1300729795.cos.ap-nanjing.myqcloud.com/hexo-article/Android%E9%A1%B9%E7%9B%AE%EF%BC%9A%E8%BF%9E%E8%BF%9E%E7%9C%8B/lingame_level_game.gif)

![](https://hexo-photo-1300729795.cos.ap-nanjing.myqcloud.com/hexo-article/Android%E9%A1%B9%E7%9B%AE%EF%BC%9A%E8%BF%9E%E8%BF%9E%E7%9C%8B/linkgame_level_viectory.gif)

![](https://hexo-photo-1300729795.cos.ap-nanjing.myqcloud.com/hexo-article/Android%E9%A1%B9%E7%9B%AE%EF%BC%9A%E8%BF%9E%E8%BF%9E%E7%9C%8B/linkgame_level_prop.gif)



***


#### 第一次commit-2020-04-15:
- 项目基础操作。
- 自定义XLTextView和XLButton类，方便设置其字体样式为指定的字体样式。
- 成功，失败界面搭建。
- 文件结构调整。
- 数据库创建（第三方库：[Litepal](https://github.com/LitePalFramework/LitePal)）。

#### 第二次commit-2020-04-15：
- 数据库更新操作，使用可视化工具Navicate for Sqlite问题重重。
- 工具类，dp与px之间的转换。

#### 第三次commit-2020-04-16-00:06：
- 首页布局。
- 状态栏的沉浸模式（第三方库：[ImmersionBar](https://github.com/gyf-dev/ImmersionBar)）。
- 常量文件以及一些枚举的定义，设置枚举值以及测试。
- 数据库可视化依赖（第三方库：[Android-Debug-Database](https://github.com/amitshekhariitbhu/Android-Debug-Database)）。
- 数据库查询操作（注意DataSupport已经废弃，模型类需要继承于LitePalSupport）。
- 数据库查询测试。

#### 第四次commit-2020-04-17-19:10：
- 枚举的完善。
- Activity跳转，携带自定义类的数据。针对数据模型类，实现Serializable接口或Parcelable接口接口。
- 关卡页面布局。
- 自定义XLImageView，显示不同关卡状态的图片。
- 完善工具类：获取屏幕宽度，高度。

#### 第五次commit-2020-04-18-09:43：
- 关卡页面bug解决。不是Horizontal无法嵌套RelativeLayout而是计算页面的时候理论上应该这样计算`int pager = i / Constant.level_pager_count;`，但是最终写成了对`level_width`取整。
- 创建了游戏界面，关卡界面到游戏界面的数据传递已经完成。
- 注意：游戏界面到数据界面的回调数据还没有完成。

#### 第六次commit-2020-04-18-10:37:
- 数据库的初始化问题解决，代码自动判断生成关卡表的数据。
- Android Debug Database的使用条件是手机和电脑在同一个局域网下。

#### 第七次commit-2020-04-18-16:52:
- 关卡界面关卡页面切换显示当前页面和总页面。
- 关卡页面在第一页或者最后一页，对应按钮无法点击。
- 优化关卡进入游戏界面的甄别，只有正在游戏的关卡和闯关成功的关卡才可以进入游戏界面。
- 关闭HorizontalScrollView的滑动，防止影响文本显示。

#### 第八次commit-2020-04-19-21:25:
- 关于游戏界面。
- 抽象出AnimalView类用来表示连连看游戏时的每一个图标处的内容。
- 抽象出AnimalPoint类用来表示每一个图标处的内容的相对坐标。
- 抽象出LinkBoard接口用来存储相关布局以及数据。
- 抽象出AnimalSearch类用来判断在给定的布局中所给的两个AnimalView能否按照给定的规则连接。
- 抽象出LinkInfo类用来存储两个AnimalView相连接所涉及的点信息。

#### 第九次commit-2020-04-20-10:11:
- 抽象出的LinkBoard接口更改为LinkConstant作用不变。
- 使用单例模式抽象出LinkManager类用来管理一局游戏，目前已经完成了开始游戏的布局部分。
- 创建工具类LinkUtil辅助LinkManager完成任务。

#### 第十次commit-2020-04-20-21-30:
- 修改PxUtil传入参数density修改为context。
- 游戏的逻辑实现完成包括布局显示、事件交互、简单的动画。

#### 第十一次commit-2020-04-21-21:19:
- 游戏界面菜单的布局。
- 简单模式的完善。

#### 第十二次commit-2020-04-22-00:38：
- 游戏界面菜单的时间布局显示。
- 各个控件的回调事件准备完毕。

#### 第十三次commit-2020-04-22-15:44:
- 定时器相关逻辑实现完成。
- 刷新道具的功能完成。
- 暂停功能的完成。
- 游戏界面关卡数据的显示完成。
- 游戏失败界面的数据传递以及完善。
- 游戏成功界面的数据传递待完善。
- 存在适配问题，滑动过快问题。

#### 第十三次commit-2020-04-22-18:34:
- 游戏成功界面的数据完善，下一关内容按钮事件未完成。
- 拳头道具功能，炸弹道具功能完善。
- AnimalSearch修改bug。
- 所有常量名称修改为大写。

#### 第十四次commit-2020-04-23-00:05:
- 游戏成功界面下一关内容按钮事件的完善。
- 游戏界面金币操作，道具数量操作完善。
- 游戏界面金币，道具的数据库操作。

#### 第十五次commit-2020-04-23-11:40:
- 游戏成功界面文本部分，道具，暂停的布局适配
- 游戏界面AnimalView的适配。

#### 第十六次commit-2020-04-23-22:22:
- 帮助视图搭建完成，暂停视图搭建完成事件回调处理也完成。
- 利用第三方库：[X2C](https://github.com/iReaderAndroid/X2C)提高Xml布局文件的加载速度。
- 解决玩重复关卡时，结算时将下一关闯关状态不经判断设置为闯关中的bug。
- 解决一部分界面适配bug，关卡页面页面控制器的位置，游戏界面暂停按钮的位置。

#### 第十七次commit-2020-04-24-23:52:
- 普通模式，困难模式加入。
- 简单模式模板bug解决，普通模式模板bug解决了一个。

#### 第十八次commit-2020-04-24-11:23:
- 商店页面布局完成。
- 商店页面事件回调处理完毕。
- 商店页面数据库处理完成。

#### 第十九次commit-2020-04-24-23:26:
- 设置页面布局完成.
- 背景音乐播放完成.
- 设置页面回调控制背景音乐大小完成。
- 使用[NiceImageView](https://github.com/SheHuan/NiceImageView)第三方库达到圆角的效果。


***

![](https://hexo-photo-1300729795.cos.ap-nanjing.myqcloud.com/hexo-article/Android%E9%A1%B9%E7%9B%AE%EF%BC%9A%E8%BF%9E%E8%BF%9E%E7%9C%8B/bug.jpg)

#### 第二十次commit-2020-04-27-00:54:
- bug提交。
- 解决bug3，商店页面关闭按钮重叠，文字挤在一起。
- 解决bug4，相关的RGB，箭头图标替换。
- 结局bug6，文字重复。

#### 第二十一次commit-2020-04-28-00:24:
- 解决bug7，裁剪掉背景音乐的空格段。


#### 第二十二次commit-2020-04-29-00:08:
- 解决bug8，游戏成功页面字体对齐。

#### 第二十三次commit-2020-05-02-09:27:
- 代码完善：自定义View做成静态库:[NumberOfItem](https://github.com/xiaoshitounen/NumberOfItem)，为道具相关bug解决奠定基础。
- 解决bug1，主页面暂停按钮叠到道具图标上。
- 解决bug5，添加购买道具扣除金币的信息提示。
- 解决bug11，商店页面添加道具数量。

#### 第二十四次commit-2020-05-02-11:47:
- 解决bug9，布局中有岩石，补充提示。

#### 第二十五次commit-2020-05-02-15:57:
- 修改新增bug：数据库更新操作无法更新为默认值。
- 修改新增bug：息屏的时候没有关闭音乐。
- 修改新增bug：商店页面购买道具的时候没有屏蔽ViewGroup继续向下分发事件，通过向上抽取一个XLLinearLayout重写onInterceptTouchEvent返回true拦截事件。
- 代码重构：向上抽取一个BaseActivity，在这里做共同的操作。

#### 第二十六次commit-2020-05-02-22:00:
- 代码重构：将多线程直接addView的方式转换成动态添加fragment
- 功能添加：音效单例类完成，游戏成功、失败音效，按钮点击音效加载完毕。

#### 第二十七次commit-2020-05-04-00:07:
- 代码重构：自定义View做成静态库:[CircleProgress](https://github.com/xiaoshitounen/CircleProgress)，优化之前显示时间文本的代码结构，添加了动态显示的环形进度。

#### 第二十八次commit-2020-05-04-08:54:
- 颜色重构：修改了一些部分的颜色显示。

#### 第二十九次commit-2020-05-07-00:15:
- 优化了CircleProgress的显示。
- 帮助页面，暂停页面，文字错误信息修改，图片资源修改。
- 暂停页面的暂停中增大字体。
- 对连续点击进行了一些优化。
- 游戏成功，失败的音效和背景音乐完全混在一起的bug。
- 完善了游戏的点击音效，消除音效，无法点击音效。

#### 第三十次commit-2020-05-07-15:30:
- 宝可梦消除前，在之间绘制的线条优化为闪电效果，参考了[Android 闪电效果 (Electric Screen,电动屏幕)](https://blog.csdn.net/u013045971/article/details/41984879)。
- 宝可梦消除时，消除添加了破碎效果，使用了[ExplosionField](https://github.com/tyrantgit/ExplosionField)第三方库。
- 解决了，点击过快，连续消除的问题。

#### 第三十一次commit-2020-05-11-10:52:
- 解决bug，禁用了系统的返回键，参考:[Android重写系统返回键](https://blog.csdn.net/lanxuan1993/article/details/80360574)。
