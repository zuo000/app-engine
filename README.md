app-engine 是一个分布式的App服务端快速开发框架,包含了基本的权限认证、日志处理、接口防刷、系统监控等基本功能。
此框架围绕分布式服务系统构建，能够快速扩容，迎合微服务化，提供App服务端常用必备功能。

### 技术栈：
> 1. Spring Boot / Spring MVC / Spring Data Jpa
> 2. Gradle
> 3. Java8
> 4. Logback
> 5. Lombok

### 功能列表：
> 1. 认证方式： Basic、 Cookie、Header、内外网
> 2. 统一错误处理、统一Json格式模板
> 3. 接口请求日志统一处理
> 4. 接口频次拦截
> 5. 支持多数据源、主从分离
> 6. 多Profile支持，Gradle、Spring、应用程序Profile整合
> 7. 完善的系统监控
> 8. 热部署
> 9. 自动生成接口文档
> 10. docker支持（gradle创建image、docker-compose）

#### 环境配置
区分有三种环境dev、test、prod，不同环境会加载不同的配置文件
> 1. Gradle环境配置: gradle.properties里设置profile
> 2. Spring环境变量: application.yaml或application.properties里配置spring.profiles.active
> 3. 应用内获取环境变量: spring注入: @Autowired Environment env 或手动解析spring配置文件（不依赖Spring）

#### 数据源配置
> 1. 如果安装了docker，直接执行 deploy/bin/init_redis.sh脚本
> 2. 手动安装:
>     redis: ip:127.0.0.1 port:6379 password:无

#### 可执行jar包
运行 gradle clean jar assemble 会自动打可执行jar包，运行：
> 1. java -jar deploy/build/libs/deploy-${version}.jar
> 2. ./deploy/build/libs/deploy-${version}.jar 如需配置JVM等参数请修复deploy/config/deploy-${version}.conf并拷贝到可执行jar包相同目录，并修改${version}

#### 发布jar/war包到私有仓库
> 1. 修改build.gradle里uploadArchives的私有仓库地址、用户名、密码
> 2. 执行 ./gradlew uploadArchives 命令

#### 运行项目方式
> 1. 执行: gradle run
> 2. 执行运行: Application.java
> 3. 执行: ./gradlew run，此方式不用安装gradle

#### checkstyle findbugs
> 1. 指定 gradle check
> 2. 在build/reports目录会生成相关报告文件

#### 监控
> * 健康检查： http://localhost:7002/health
> * 次数监控： http://localhost:7002/metrics
> * APP信息： http://localhost:7002/info
> * dump信息： http://localhost:7002/dump
> * 环境信息： http://localhost:7002/env
> * 性能监控： http://localhost:8080/javasimon
> * 数据库监控： http://localhost:8080/druid
> * Tomcat监控： http://localhost:7002/jolokia/read/Tomcat:type=Connector,port=8080

#### 接口文档
> * swagger: http://localhost:8080/swagger-ui.html

#### TODO
> * 完善注释
> * 完善文档
> * 添加单元测试、集成测试、压力测试

test