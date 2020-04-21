# LinkGame
## Android:连连看

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
