## BaseFrame

组件化实践开发框架（持续更新中...）

### 目的是什么？
首先说说插件化，是为了去适应并行开发，是为了解耦各个模块，是为了避免模块之间的交叉依赖，是为了加快编译速度，从而提高并行开发效率。

然而组件化这个方案没有任何『黑科技』，不牵涉任何 hook ，跟 Atlas 的区别就是无需关心不同的 Context ，无需再关心类、资源怎么去加载，无需关心 Context 的安全问题，无需关心不同机型的兼容适配...技术成本可能连 Atlas 的十分之一都不到！更适合中小型公司使用！

### 原理
该框架实现原理，使用多个 AndroidManifest.xml 文件配置 debug 和 release 两种运行环境。通过 gradle 配置的方式将 debug 和 release 分开打包。两种环境的区别：

* debug 时可以独立构建每个模块，每个模块都可以独立运行，独立测试，减少构建时间。

* release 时，再把每个模块当做Library，作为宿主APP的一部分。

而两种环境的切换，通过修改主目录下的 **gradle.properties** 文件中自定义的Boolean变量 **IsBuildModule**，来控制是否独立运行每个module：

* 设置为true时，可以独立运行每个module，每个module可以独立调试

* 设置为false时，可以编译运行整个project，注意自定义变量IsBuildModule变量设置改变时，要对gradle进行sysn。

当在gradle.properties中设置IsBuildMudle=true时，可以独立运行每个module，包括app module，单独build module app时，由于没有编译moduleA和moduleB，点击两个模块时不会跳转。每个module可独立运行调试。

当设置IsBuildMudle=false，可以编译运行整个project，注意IsBuildMudle变量设置改变时，要对gradle进行sysn。
