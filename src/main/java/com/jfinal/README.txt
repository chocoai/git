JFinal改动说明

1、jfinal sql数据库默认的分页方式太慢，（只支持sql 2005及以上版本）
2、改造ActionReporter,Controller方法输出日志快速定位
3、改写Dialect基类，支持旧版本的数据分页写法
4、改造专为LP系统管理专用的Json Record 属性小写输出
5、改造ModelBuilder、RecordBuilder，支持oracle 时间类型
6、重写jFinal模版的FileStringSource,支持LP流程