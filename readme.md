# Java框架文档 

[架构关系](./docs/framework.drawio)使用[DrawIO工具][drawio]打开

# 目录结构
```text
├── apps
│   └── point XXX项目
├── buildSrc  ## gradle构建插件公约
├── commons   ## 公用组件和公约
│   ├── cloud     ## 服务组件
│   ├── mysql     ## 数据库组件公约
│   ├── redis     ## redis 组件公约
│   ├── tracing   ## 关键操作框架实现
│   ├── utilities ## 通用工具类，asserts 
│   ├── valut     ## 安全参数实现
│   ├── visitor   ## 用户获取框架，提供了JWT实现,多设备下线
│   └── web       ## web框架全局规范实现
├── components          ## 组件内容
│   └── stars     ## stars组件（Org、用户、权限）
│       ├── stars-api       ## 对外提供的API
│       ├── stars-starter   ## stars 系统
│       └── stars-visitor   ## 用户访问项目实现
└── managers   ## 管理模块
│  ├── admin        ## ****【技术】***** 管理后台, 三方后台入口GW
│  ├── nacos        ## nacos 纳入管理实现（暂未）
│  ├── durid        ## durid 纳入管理实现（暂未）
│  ├── cloud        ## cloud admin 纳入管理实现（暂未）
│  └── sentinel     ## sentinel 纳入管理实现（暂未）
├── devops
│   ├── docker      ## 本地运行docker-compose文件
└── docs    文档内容
```

## buildSrc --> gradle plugins

gradle 编译公约，版本公约，检查公约，以上均是简单是实现，需要通过git submodule方式引入，之后采用plugins提供

```text
├── groovy
│   └── com
│       └── neuralgalaxy
│           ├── cloud
│           │   ├── com.neuralgalaxy.cloud-api.gradle       ##  
│           │   ├── com.neuralgalaxy.cloud-starter.gradle   ## 设置打包和依赖
│           │   └── com.neuralgalaxy.cloud.gradle           ## 约定 framework/boot/cloud/alibaba 版本号规则和依赖
│           └── java    
│               ├── com.neuralgalaxy.checkstyle.gradle            ## checkstyle公约
│               ├── com.neuralgalaxy.java.gradle                  ## java使用公约
│               ├── com.neuralgalaxy.maven-publish.gradle         ## 发布公约
│               └── com.neuralgalaxy.spotbugs.gradle              ## spotbugs
└── resources
    └── config
        └── checkstyle.xml          ##
    └── spotbugs                    ## 配置内容
```

## checkstyle
https://github.com/zhnlk/ali-checkstyle-xml

## GraphQL

## Spring Tests Cases
https://github.com/spring-guides/gs-testing-web


## TODO

1. stars-visitor impl
2. vault
3. GraphQL
4. checkstyle
5. spotbugs

[drawio]: https://app.diagrams.net/
