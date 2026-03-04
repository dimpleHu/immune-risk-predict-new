com.immuserisk
├── annotation/                   # 注解
│   ├── IgnoreAuth.java           # 忽略认证注解
│   └── LoginUser.java            # 登录用户注解
├── common/                       # 通用类
│   ├── R.java                    # 统一响应结果
│   ├── PageQuery.java            # 分页查询参数
│   ├── PageResult.java           # 分页查询结果
│   └── exception/                # 异常处理
│       ├── BusinessException.java    # 业务异常
│       └── GlobalExceptionHandler.java # 全局异常处理
├── config/                       # 配置类
│   ├── WebConfig.java            # Web配置（跨域、资源等）
│   ├── ShiroConfig.java          # Shiro配置
│   └── UserRealm.java            # 认证授权实现
├── controller/                   # 控制器
├── service/                      # 服务层
├── entity/                       # 实体类
├── dao/                          # 数据访问层
└── utils/                        # 工具类
└── SpringContextUtils.java   # Spring上下文工具