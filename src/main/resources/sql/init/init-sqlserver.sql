if exists (select 1
            from  sysobjects
           where  id = object_id('lp_sys_attach')
            and   type = 'U')
   drop table lp_sys_attach
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_sys_dept')
            and   type = 'U')
   drop table lp_sys_dept
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_sys_dict')
            and   type = 'U')
   drop table lp_sys_dict
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_sys_dictsort')
            and   type = 'U')
   drop table lp_sys_dictsort
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_sys_log')
            and   type = 'U')
   drop table lp_sys_log
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_sys_menu')
            and   type = 'U')
   drop table lp_sys_menu
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_sys_order')
            and   type = 'U')
   drop table lp_sys_order
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_sys_role')
            and   type = 'U')
   drop table lp_sys_role
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_sys_rolemenu')
            and   type = 'U')
   drop table lp_sys_rolemenu
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_sys_task')
            and   type = 'U')
   drop table lp_sys_task
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_sys_user')
            and   type = 'U')
   drop table lp_sys_user
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_sys_userex')
            and   type = 'U')
   drop table lp_sys_userex
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_sys_userrole')
            and   type = 'U')
   drop table lp_sys_userrole
go

/*==============================================================*/
/* Table: lp_sys_attach                                         */
/*==============================================================*/
create table lp_sys_attach (
   id                   varchar(50)          not null,
   filename             varchar(200)         null,
   filesize             numeric              null,
   savepath             varchar(50)          null,
   tablename            varchar(200)         null,
   field                varchar(200)         null,
   objid                varchar(200)         null,
   addtime              datetime             null,
   constraint PK_LP_SYS_ATTACH primary key nonclustered (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '文件名',
   'user', @CurrentUser, 'table', 'lp_sys_attach', 'column', 'filename'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '文件大小',
   'user', @CurrentUser, 'table', 'lp_sys_attach', 'column', 'filesize'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '存储路径',
   'user', @CurrentUser, 'table', 'lp_sys_attach', 'column', 'savepath'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '关联表',
   'user', @CurrentUser, 'table', 'lp_sys_attach', 'column', 'tablename'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '关联字段',
   'user', @CurrentUser, 'table', 'lp_sys_attach', 'column', 'field'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '关联ID',
   'user', @CurrentUser, 'table', 'lp_sys_attach', 'column', 'objid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '添加时间',
   'user', @CurrentUser, 'table', 'lp_sys_attach', 'column', 'addtime'
go

/*==============================================================*/
/* Table: lp_sys_dept                                           */
/*==============================================================*/
create table lp_sys_dept (
   id                   varchar(50)          not null,
   pid                  varchar(50)          null,
   deptcode             varchar(200)         null,
   deptname             varchar(200)         null,
   depttype             varchar(200)         null,
   px                   numeric              null,
   addtime              datetime             null,
   constraint PK_LP_SYS_DEPT primary key nonclustered (id)
)
go

/*==============================================================*/
/* Table: lp_sys_dict                                           */
/*==============================================================*/
create table lp_sys_dict (
   id                   varchar(50)          not null,
   pid                  varchar(50)          null,
   code                 varchar(200)         null,
   val                  varchar(200)         null,
   sortcode             varchar(200)         null,
   px                   numeric              null,
   addtime              datetime             null,
   constraint PK_LP_SYS_DICT primary key nonclustered (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '父级ID',
   'user', @CurrentUser, 'table', 'lp_sys_dict', 'column', 'pid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '代码',
   'user', @CurrentUser, 'table', 'lp_sys_dict', 'column', 'code'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '值',
   'user', @CurrentUser, 'table', 'lp_sys_dict', 'column', 'val'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '数据字典分类编码',
   'user', @CurrentUser, 'table', 'lp_sys_dict', 'column', 'sortcode'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '排序字段',
   'user', @CurrentUser, 'table', 'lp_sys_dict', 'column', 'px'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '添加时间',
   'user', @CurrentUser, 'table', 'lp_sys_dict', 'column', 'addtime'
go

/*==============================================================*/
/* Table: lp_sys_dictsort                                       */
/*==============================================================*/
create table lp_sys_dictsort (
   id                   varchar(50)          not null,
   sortcode             varchar(200)         null,
   sortname             varchar(200)         null,
   note                 varchar(500)         null,
   px                   numeric              null,
   addtime              datetime             null,
   constraint PK_LP_SYS_DICTSORT primary key nonclustered (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字典分类编码',
   'user', @CurrentUser, 'table', 'lp_sys_dictsort', 'column', 'sortcode'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字典分类名称',
   'user', @CurrentUser, 'table', 'lp_sys_dictsort', 'column', 'sortname'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '描述',
   'user', @CurrentUser, 'table', 'lp_sys_dictsort', 'column', 'note'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '排序字段',
   'user', @CurrentUser, 'table', 'lp_sys_dictsort', 'column', 'px'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '添加时间',
   'user', @CurrentUser, 'table', 'lp_sys_dictsort', 'column', 'addtime'
go

/*==============================================================*/
/* Table: lp_sys_log                                            */
/*==============================================================*/
create table lp_sys_log (
   id                   varchar(50)          not null,
   logtype              varchar(200)         null,
   info                 varchar(2000)        null,
   ip                   varchar(200)         null,
   userid               varchar(50)          null,
   username             varchar(200)         null,
   addtime              datetime             null,
   status             	varchar(200)         null,
   constraint PK_LP_SYS_LOG primary key nonclustered (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '日志类型(登录)',
   'user', @CurrentUser, 'table', 'lp_sys_log', 'column', 'logtype'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '日志内容',
   'user', @CurrentUser, 'table', 'lp_sys_log', 'column', 'info'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作IP',
   'user', @CurrentUser, 'table', 'lp_sys_log', 'column', 'ip'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户id',
   'user', @CurrentUser, 'table', 'lp_sys_log', 'column', 'userid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户名称',
   'user', @CurrentUser, 'table', 'lp_sys_log', 'column', 'username'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '添加时间',
   'user', @CurrentUser, 'table', 'lp_sys_log', 'column', 'addtime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作状态',
   'user', @CurrentUser, 'table', 'lp_sys_log', 'column', 'status'
go

/*==============================================================*/
/* Table: lp_sys_menu                                           */
/*==============================================================*/
create table lp_sys_menu (
   id                   varchar(50)          not null,
   pid                  varchar(50)          null,
   menuname             varchar(200)         null,
   url                  varchar(200)         null,
   openway              varchar(200)         null,
   px                   numeric              null,
   addtime              datetime             null,
   constraint PK_LP_SYS_MENU primary key nonclustered (id)
)
go

/*==============================================================*/
/* Table: lp_sys_order                                          */
/*==============================================================*/
create table lp_sys_order (
   tablename            varchar(50)          not null,
   val                  numeric              null,
   constraint PK_LP_SYS_ORDER primary key nonclustered (tablename)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '表名',
   'user', @CurrentUser, 'table', 'lp_sys_order', 'column', 'tablename'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '当前值',
   'user', @CurrentUser, 'table', 'lp_sys_order', 'column', 'val'
go

/*==============================================================*/
/* Table: lp_sys_role                                           */
/*==============================================================*/
create table lp_sys_role (
   id                   varchar(50)          not null,
   deptid               varchar(50)          null,
   rolecode             varchar(200)         null,
   rolename             varchar(200)         null,
   px                   numeric              null,
   addtime              datetime             null,
   constraint PK_LP_SYS_ROLE primary key nonclustered (id)
)
go

/*==============================================================*/
/* Table: lp_sys_rolemenu                                       */
/*==============================================================*/
create table lp_sys_rolemenu (
   id                   varchar(50)          not null,
   roleid               varchar(50)          null,
   menuid               varchar(50)          null,
   constraint PK_LP_SYS_ROLEMENU primary key nonclustered (id)
)
go

/*==============================================================*/
/* Table: lp_sys_task                                           */
/*==============================================================*/
create table lp_sys_task (
   id                   varchar(50)          not null,
   taskname             varchar(200)         null,
   exp                  varchar(200)         null,
   class                varchar(200)         null,
   status               varchar(200)         null,
   note                 varchar(200)         null,
   addtime              datetime             null,
   constraint PK_LP_SYS_TASK primary key nonclustered (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '任务名称',
   'user', @CurrentUser, 'table', 'lp_sys_task', 'column', 'taskname'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '表达式',
   'user', @CurrentUser, 'table', 'lp_sys_task', 'column', 'exp'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '实现类',
   'user', @CurrentUser, 'table', 'lp_sys_task', 'column', 'class'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '状态，''0'':禁用、''1'':启用',
   'user', @CurrentUser, 'table', 'lp_sys_task', 'column', 'status'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '描述',
   'user', @CurrentUser, 'table', 'lp_sys_task', 'column', 'note'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '添加时间',
   'user', @CurrentUser, 'table', 'lp_sys_task', 'column', 'addtime'
go

/*==============================================================*/
/* Table: lp_sys_user                                           */
/*==============================================================*/
create table lp_sys_user (
   id                   varchar(50)          not null,
   deptid               varchar(50)          null,
   usernum              varchar(200)         null,
   username             varchar(200)         null,
   pwd                  varchar(200)         null,
   managedeptid         varchar(50)          null,
   phone                varchar(200)         null,
   status               varchar(50)          null,
   issuperadmin         varchar(20)          null,
   px                   numeric              null,
   addtime              datetime             null,
   constraint PK_LP_SYS_USER primary key nonclustered (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0：停用，1：启用',
   'user', @CurrentUser, 'table', 'lp_sys_user', 'column', 'status'
go

/*==============================================================*/
/* Table: lp_sys_userex                                         */
/*==============================================================*/
create table lp_sys_userex (
   id                   varchar(50)          not null,
   userid               varchar(50)          null,
   "key"                varchar(200)         null,
   val                  varchar(500)         null,
   px                   numeric              null,
   addtime              datetime             null,
   constraint PK_LP_SYS_USEREX primary key nonclustered (id)
)
go

/*==============================================================*/
/* Table: lp_sys_userrole                                       */
/*==============================================================*/
create table lp_sys_userrole (
   id                   varchar(50)          not null,
   userid               varchar(50)          null,
   roleid               varchar(50)          null,
   constraint PK_LP_SYS_USERROLE primary key nonclustered (id)
)
go

--V1.2版本改动
alter table lp_sys_menu add iconcls varchar(200);
alter table lp_sys_menu add opensubmenuindex varchar(200);

alter table lp_sys_attach add fileext varchar(200);
alter table lp_sys_attach add userid varchar(200);
alter table lp_sys_attach add username varchar(200);
go

/*==============================================================*/
/* 初始化数据开始                                       		*/
/*==============================================================*/

INSERT INTO lp_sys_dept(
	id, pid, deptcode, deptname, depttype, px, addtime)
	VALUES ('-1', null, null, '组织机构', '单位', '0', null);
	
INSERT INTO lp_sys_user(
	id, deptid, usernum, username, pwd, managedeptid, phone, status, issuperadmin, px, addtime)
	VALUES ('0', '-1', 'admin', '管理员', '78becbe4454a95a15ea9abcda568a962', null, null, '1', '1', '0', null);
--getdate

INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime,iconcls)
	VALUES ('d9e006abcc2f489ba78a9969d1055b13','-1','系统管理',null,null,1,getdate(),'fa fa-cog');
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime,iconcls)
	VALUES ('acf5a89736714522938de480841a05dd','-1','系统安全',null,null,2,getdate(),'fa fa-window-restore');
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('13c3ffe4237e44d39f9021edc9769ed6','d9e006abcc2f489ba78a9969d1055b13','机构维护','/dept/index','属性页',1,getdate());

INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('065775d70a6f4ef994542c21c960d94b','d9e006abcc2f489ba78a9969d1055b13','用户维护','/user/index','属性页',2,getdate());
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('844f91c129034f6d9febee4b99fbca24','d9e006abcc2f489ba78a9969d1055b13','角色维护','/role/index','属性页',3,getdate());
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('8cb92e6271284155b9bcb6cd92e38098','d9e006abcc2f489ba78a9969d1055b13','菜单维护','/menu/index','属性页',4,getdate());
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('8889df7e4b724fb9bada47b0e2fa2888','d9e006abcc2f489ba78a9969d1055b13','定时任务','/task/index','属性页',5,getdate());
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('24ff1ae9748e40168d3f808b1f59e066','acf5a89736714522938de480841a05dd','系统日志','/sys/log/index','属性页',1,getdate());

INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('cb8f44a434d448b9990df23e524c6a98','acf5a89736714522938de480841a05dd','用户解锁','/sys/lockuser/index','属性页',2,getdate());
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('022a1692d2974e0a9584deed77706b73','acf5a89736714522938de480841a05dd','开发演示','/sys/demo/index','属性页',3,getdate());

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_test')
            and   type = 'U')
   drop table lp_test
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_test')
            and   type = 'U')
   drop table lp_test
go

/*==============================================================*/
/* Table: lp_test                                               */
/*==============================================================*/
create table lp_test (
   id                   varchar(50)          not null,
   txt1                 varchar(200)         null,
   txt2                 varchar(200)         null,
   txt3                 char(200)            null,
   int1                 int                  null,
   int2                 int                  null,
   long1                varchar(1)           null,
   num1                 numeric              null,
   decimal1             numeric              null,
   float1               float(16)            null,
   numeric1             numeric              null,
   date1                datetime             null,
   ts1                  datetime             null,
   ts2                  datetime             null,
   constraint PK_LP_TEST primary key nonclustered (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '示例用表',
   'user', @CurrentUser, 'table', 'lp_test'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'id',
   'user', @CurrentUser, 'table', 'lp_test', 'column', 'id'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'txt1',
   'user', @CurrentUser, 'table', 'lp_test', 'column', 'txt1'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'txt2',
   'user', @CurrentUser, 'table', 'lp_test', 'column', 'txt2'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'txt3',
   'user', @CurrentUser, 'table', 'lp_test', 'column', 'txt3'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'int1',
   'user', @CurrentUser, 'table', 'lp_test', 'column', 'int1'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'int2',
   'user', @CurrentUser, 'table', 'lp_test', 'column', 'int2'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'long1',
   'user', @CurrentUser, 'table', 'lp_test', 'column', 'long1'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'num1',
   'user', @CurrentUser, 'table', 'lp_test', 'column', 'num1'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'decimal1',
   'user', @CurrentUser, 'table', 'lp_test', 'column', 'decimal1'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'float1',
   'user', @CurrentUser, 'table', 'lp_test', 'column', 'float1'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'numeric1',
   'user', @CurrentUser, 'table', 'lp_test', 'column', 'numeric1'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'date1',
   'user', @CurrentUser, 'table', 'lp_test', 'column', 'date1'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'ts1',
   'user', @CurrentUser, 'table', 'lp_test', 'column', 'ts1'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'ts2',
   'user', @CurrentUser, 'table', 'lp_test', 'column', 'ts2'
go


--V1.3版本改动
alter table lp_sys_menu add subsystem varchar(200);
alter table lp_sys_user add dwid varchar(50);
alter table lp_sys_user add bmid varchar(50);

INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('81c5492308d2475c9451af68e93bb5aa','d9e006abcc2f489ba78a9969d1055b13','数据字典','/sys/dict/index','属性页',6,getdate());
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('f9d7fa9cab44406d8d50018ee7872899','d9e006abcc2f489ba78a9969d1055b13','流程配置','/sys/flow/index','属性页',7,getdate());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('-1', '', '', '数据字典', '', null, getdate());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380374138117685248', '-1', '', 'LP系统', '', null, getdate());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380383611062321152', '380374138117685248', '', '机构类型', '', 1, getdate());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380383911647117312', '380383611062321152', '1', '单位', 'lp.depttype', 1, getdate());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380383911668088832', '380383611062321152', '2', '部门', 'lp.depttype', 2, getdate());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380383911693254656', '380383611062321152', '3', '班组', 'lp.depttype', 3, getdate());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380645045998452736', '380374138117685248', '', '日志类型', '', 2, getdate());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380645252433707008', '380645045998452736', '系统登录', '系统登录', 'lp.logtype', 1, getdate());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380645252450484224', '380645045998452736', '系统管理', '系统管理', 'lp.logtype', 2, getdate());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380645252463067136', '380645045998452736', '系统安全', '系统安全', 'lp.logtype', 3, getdate());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380645252500815872', '380645045998452736', '业务应用', '业务应用', 'lp.logtype', 4, getdate());
	
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_wfi_flow')
            and   type = 'U')
   drop table lp_wfi_flow
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('lp_wfi_flownode')
            and   name  = 'IDX_I_FLOWNODE_FLOWINSID'
            and   indid > 0
            and   indid < 255)
   drop index lp_wfi_flownode.IDX_I_FLOWNODE_FLOWINSID
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_wfi_flownode')
            and   type = 'U')
   drop table lp_wfi_flownode
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('lp_wfi_operator')
            and   name  = 'IDX_I_OPERATOR_FLOWNODEINSID'
            and   indid > 0
            and   indid < 255)
   drop index lp_wfi_operator.IDX_I_OPERATOR_FLOWNODEINSID
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_wfi_operator')
            and   type = 'U')
   drop table lp_wfi_operator
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('lp_wfi_param')
            and   name  = 'IDX_I_FLOWPARAM_FLOWINSID'
            and   indid > 0
            and   indid < 255)
   drop index lp_wfi_param.IDX_I_FLOWPARAM_FLOWINSID
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_wfi_param')
            and   type = 'U')
   drop table lp_wfi_param
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('lp_wfi_receiver')
            and   name  = 'IDX_I_RECEIVER_FLOWNODEINSID'
            and   indid > 0
            and   indid < 255)
   drop index lp_wfi_receiver.IDX_I_RECEIVER_FLOWNODEINSID
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_wfi_receiver')
            and   type = 'U')
   drop table lp_wfi_receiver
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_wfi_waitdo')
            and   type = 'U')
   drop table lp_wfi_waitdo
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_wfs_condition')
            and   type = 'U')
   drop table lp_wfs_condition
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_wfs_event')
            and   type = 'U')
   drop table lp_wfs_event
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_wfs_flow')
            and   type = 'U')
   drop table lp_wfs_flow
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_wfs_flownode')
            and   type = 'U')
   drop table lp_wfs_flownode
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_wfs_flownodeext')
            and   type = 'U')
   drop table lp_wfs_flownodeext
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_wfs_flowtrans')
            and   type = 'U')
   drop table lp_wfs_flowtrans
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_wfs_flowtransext')
            and   type = 'U')
   drop table lp_wfs_flowtransext
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_wfs_flowversion')
            and   type = 'U')
   drop table lp_wfs_flowversion
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_wfs_folder')
            and   type = 'U')
   drop table lp_wfs_folder
go

if exists (select 1
            from  sysobjects
           where  id = object_id('lp_wfs_param')
            and   type = 'U')
   drop table lp_wfs_param
go

/*==============================================================*/
/* Table: lp_wfi_flow                                           */
/*==============================================================*/
create table lp_wfi_flow (
   id                   varchar(50)          not null,
   flowversionid        varchar(200)         null,
   flowname             varchar(200)         null,
   starttime            datetime            null,
   endtime              datetime            null,
   createuser           varchar(200)         null,
   createuserid         varchar(200)         null,
   runstatus            numeric              null,
   saveversion          varchar(50)          null,
   addtime              datetime            null,
   constraint PK_LP_WFI_FLOW primary key nonclustered (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程实例',
   'user', @CurrentUser, 'table', 'lp_wfi_flow'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主键',
   'user', @CurrentUser, 'table', 'lp_wfi_flow', 'column', 'id'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程版本id',
   'user', @CurrentUser, 'table', 'lp_wfi_flow', 'column', 'flowversionid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程名称',
   'user', @CurrentUser, 'table', 'lp_wfi_flow', 'column', 'flowname'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '启动时间',
   'user', @CurrentUser, 'table', 'lp_wfi_flow', 'column', 'starttime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '结束时间',
   'user', @CurrentUser, 'table', 'lp_wfi_flow', 'column', 'endtime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建人',
   'user', @CurrentUser, 'table', 'lp_wfi_flow', 'column', 'createuser'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建人id',
   'user', @CurrentUser, 'table', 'lp_wfi_flow', 'column', 'createuserid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '当前状态。0：运行；1：完成；2：终止；3：挂起；',
   'user', @CurrentUser, 'table', 'lp_wfi_flow', 'column', 'runstatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '保存冲突版本',
   'user', @CurrentUser, 'table', 'lp_wfi_flow', 'column', 'saveversion'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '添加时间',
   'user', @CurrentUser, 'table', 'lp_wfi_flow', 'column', 'addtime'
go

/*==============================================================*/
/* Table: lp_wfi_flownode                                       */
/*==============================================================*/
create table lp_wfi_flownode (
   id                   varchar(50)          not null,
   flowversionid        varchar(50)          null,
   flowinstanceid       varchar(50)          null,
   flownodeid           varchar(50)          null,
   flownodename         varchar(200)         null,
   nodetype             numeric              null,
   runstatus            numeric              null,
   dealstatus           numeric              null,
   pids                 varchar(500)         null,
   senderid             varchar(200)         null,
   sender               varchar(200)         null,
   begintime            datetime            null,
   endtime              datetime            null,
   note                 varchar(500)         null,
   px                   numeric              null,
   addtime              datetime            null,
   constraint PK_LP_WFI_FLOWNODE primary key nonclustered (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程节点实例',
   'user', @CurrentUser, 'table', 'lp_wfi_flownode'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主键',
   'user', @CurrentUser, 'table', 'lp_wfi_flownode', 'column', 'id'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程版本id',
   'user', @CurrentUser, 'table', 'lp_wfi_flownode', 'column', 'flowversionid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程实例id',
   'user', @CurrentUser, 'table', 'lp_wfi_flownode', 'column', 'flowinstanceid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程节点id',
   'user', @CurrentUser, 'table', 'lp_wfi_flownode', 'column', 'flownodeid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '节点名称',
   'user', @CurrentUser, 'table', 'lp_wfi_flownode', 'column', 'flownodename'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '节点类型。0:开始节点、1:普通节点、2：子流程节点、3：自动节点、9：结束节点',
   'user', @CurrentUser, 'table', 'lp_wfi_flownode', 'column', 'nodetype'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '运行状态。-1：未启动；0：运行中；1：已完成',
   'user', @CurrentUser, 'table', 'lp_wfi_flownode', 'column', 'runstatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '处理状态。0：未处理；1：提交；2：退回；3：撤回；4：作废',
   'user', @CurrentUser, 'table', 'lp_wfi_flownode', 'column', 'dealstatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '上一个节点实例Id。JSON格式：[节点实例Id,...]',
   'user', @CurrentUser, 'table', 'lp_wfi_flownode', 'column', 'pids'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '发送人id',
   'user', @CurrentUser, 'table', 'lp_wfi_flownode', 'column', 'senderid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '发送人',
   'user', @CurrentUser, 'table', 'lp_wfi_flownode', 'column', 'sender'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '开始时间',
   'user', @CurrentUser, 'table', 'lp_wfi_flownode', 'column', 'begintime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '结束时间',
   'user', @CurrentUser, 'table', 'lp_wfi_flownode', 'column', 'endtime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'lp_wfi_flownode', 'column', 'note'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '排序字段',
   'user', @CurrentUser, 'table', 'lp_wfi_flownode', 'column', 'px'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '添加时间',
   'user', @CurrentUser, 'table', 'lp_wfi_flownode', 'column', 'addtime'
go

/*==============================================================*/
/* Index: IDX_I_FLOWNODE_FLOWINSID                              */
/*==============================================================*/
create index IDX_I_FLOWNODE_FLOWINSID on lp_wfi_flownode (
flowinstanceid ASC
)
go

/*==============================================================*/
/* Table: lp_wfi_operator                                       */
/*==============================================================*/
create table lp_wfi_operator (
   id                   varchar(50)          not null,
   flownodeinstanceid   varchar(50)          null,
   operatortype         numeric              null,
   operatorid           varchar(50)          null,
   operator             varchar(200)         null,
   addtime              datetime            null,
   constraint PK_LP_WFI_OPERATOR primary key nonclustered (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程处理人',
   'user', @CurrentUser, 'table', 'lp_wfi_operator'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主键',
   'user', @CurrentUser, 'table', 'lp_wfi_operator', 'column', 'id'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程节点实例id',
   'user', @CurrentUser, 'table', 'lp_wfi_operator', 'column', 'flownodeinstanceid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '接收人类型。0:人员；1:角色；2:部门',
   'user', @CurrentUser, 'table', 'lp_wfi_operator', 'column', 'operatortype'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '处理人id',
   'user', @CurrentUser, 'table', 'lp_wfi_operator', 'column', 'operatorid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '处理人',
   'user', @CurrentUser, 'table', 'lp_wfi_operator', 'column', 'operator'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '添加时间',
   'user', @CurrentUser, 'table', 'lp_wfi_operator', 'column', 'addtime'
go

/*==============================================================*/
/* Index: IDX_I_OPERATOR_FLOWNODEINSID                          */
/*==============================================================*/
create index IDX_I_OPERATOR_FLOWNODEINSID on lp_wfi_operator (
flownodeinstanceid ASC
)
go

/*==============================================================*/
/* Table: lp_wfi_param                                          */
/*==============================================================*/
create table lp_wfi_param (
   id                   varchar(50)          not null,
   flowinstanceid       varchar(50)          null,
   parambm              varchar(200)         null,
   val                  varchar(200)         null,
   addtime              datetime            null,
   constraint PK_LP_WFI_PARAM primary key nonclustered (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程参数实例',
   'user', @CurrentUser, 'table', 'lp_wfi_param'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主键',
   'user', @CurrentUser, 'table', 'lp_wfi_param', 'column', 'id'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程实例id',
   'user', @CurrentUser, 'table', 'lp_wfi_param', 'column', 'flowinstanceid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '参数编码',
   'user', @CurrentUser, 'table', 'lp_wfi_param', 'column', 'parambm'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '参数值',
   'user', @CurrentUser, 'table', 'lp_wfi_param', 'column', 'val'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '添加时间',
   'user', @CurrentUser, 'table', 'lp_wfi_param', 'column', 'addtime'
go

/*==============================================================*/
/* Index: IDX_I_FLOWPARAM_FLOWINSID                             */
/*==============================================================*/
create index IDX_I_FLOWPARAM_FLOWINSID on lp_wfi_param (
flowinstanceid ASC
)
go

/*==============================================================*/
/* Table: lp_wfi_receiver                                       */
/*==============================================================*/
create table lp_wfi_receiver (
   id                   varchar(50)          not null,
   flownodeinstanceid   varchar(50)          null,
   receivertype         numeric              null,
   receiverid           varchar(50)          null,
   receiver             varchar(200)         null,
   addtime              datetime            null,
   constraint PK_LP_WFI_RECEIVER primary key nonclustered (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程接收人',
   'user', @CurrentUser, 'table', 'lp_wfi_receiver'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主键',
   'user', @CurrentUser, 'table', 'lp_wfi_receiver', 'column', 'id'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程节点实例id',
   'user', @CurrentUser, 'table', 'lp_wfi_receiver', 'column', 'flownodeinstanceid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '接收人类型。0:人员；1:角色；2:部门',
   'user', @CurrentUser, 'table', 'lp_wfi_receiver', 'column', 'receivertype'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '接收人id',
   'user', @CurrentUser, 'table', 'lp_wfi_receiver', 'column', 'receiverid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '接收人',
   'user', @CurrentUser, 'table', 'lp_wfi_receiver', 'column', 'receiver'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '添加时间',
   'user', @CurrentUser, 'table', 'lp_wfi_receiver', 'column', 'addtime'
go

/*==============================================================*/
/* Index: IDX_I_RECEIVER_FLOWNODEINSID                          */
/*==============================================================*/
create index IDX_I_RECEIVER_FLOWNODEINSID on lp_wfi_receiver (
flownodeinstanceid ASC
)
go

/*==============================================================*/
/* Table: lp_wfi_waitdo                                         */
/*==============================================================*/
create table lp_wfi_waitdo (
   id                   varchar(50)          not null,
   flowversionid        varchar(50)          null,
   flowinstanceid       varchar(50)          null,
   flownodeinstanceid   varchar(50)          null,
   flownodeid           varchar(50)          null,
   flownodename         varchar(50)          null,
   senderid             varchar(50)          null,
   sender               varchar(50)          null,
   receivertype         numeric              null,
   receiverid           varchar(50)          null,
   receiver             varchar(200)         null,
   openurl              varchar(500)         null,
   addtime              datetime            null,
   constraint PK_LP_WFI_WAITDO primary key nonclustered (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程待办',
   'user', @CurrentUser, 'table', 'lp_wfi_waitdo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主键',
   'user', @CurrentUser, 'table', 'lp_wfi_waitdo', 'column', 'id'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程版本id',
   'user', @CurrentUser, 'table', 'lp_wfi_waitdo', 'column', 'flowversionid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程实例id',
   'user', @CurrentUser, 'table', 'lp_wfi_waitdo', 'column', 'flowinstanceid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程节点实例id',
   'user', @CurrentUser, 'table', 'lp_wfi_waitdo', 'column', 'flownodeinstanceid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程节点id',
   'user', @CurrentUser, 'table', 'lp_wfi_waitdo', 'column', 'flownodeid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程节点名称',
   'user', @CurrentUser, 'table', 'lp_wfi_waitdo', 'column', 'flownodename'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '发送人id',
   'user', @CurrentUser, 'table', 'lp_wfi_waitdo', 'column', 'senderid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '发送人',
   'user', @CurrentUser, 'table', 'lp_wfi_waitdo', 'column', 'sender'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '接收人类型。0:人员；1:角色；2:部门',
   'user', @CurrentUser, 'table', 'lp_wfi_waitdo', 'column', 'receivertype'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '接收人id',
   'user', @CurrentUser, 'table', 'lp_wfi_waitdo', 'column', 'receiverid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '接收人',
   'user', @CurrentUser, 'table', 'lp_wfi_waitdo', 'column', 'receiver'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程待办',
   'user', @CurrentUser, 'table', 'lp_wfi_waitdo', 'column', 'openurl'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '添加时间',
   'user', @CurrentUser, 'table', 'lp_wfi_waitdo', 'column', 'addtime'
go

/*==============================================================*/
/* Table: lp_wfs_condition                                      */
/*==============================================================*/
create table lp_wfs_condition (
   id                   varchar(50)          null,
   flowversionid        varchar(50)          null,
   conditionname        varchar(200)         null,
   conditiontype        numeric              null,
   comparetype          numeric              null,
   leftval               varchar(200)         null,
   rightval              varchar(200)         null,
   px                   numeric              null,
   addtime              datetime            null,
   constraint AK_KEY_1_LP_WFS_C unique (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程条件',
   'user', @CurrentUser, 'table', 'lp_wfs_condition'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主键',
   'user', @CurrentUser, 'table', 'lp_wfs_condition', 'column', 'id'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程版本id',
   'user', @CurrentUser, 'table', 'lp_wfs_condition', 'column', 'flowversionid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '条件名称',
   'user', @CurrentUser, 'table', 'lp_wfs_condition', 'column', 'conditionname'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '条件类型。0：流程参数@流程参数；1：流程参数@常量；2：流程参数@条件；3：条件@条件；4：条件@常量；5：常量@常量',
   'user', @CurrentUser, 'table', 'lp_wfs_condition', 'column', 'conditiontype'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '比较符。0：包含；1：小于；2：小于等于；3：大于；4：大于等于；5：等于；6：不等于；7：与；8：或；10：不包含；11：左包含；12：右包含；13：非',
   'user', @CurrentUser, 'table', 'lp_wfs_condition', 'column', 'comparetype'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '左操作数',
   'user', @CurrentUser, 'table', 'lp_wfs_condition', 'column', 'leftval'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '右操作数',
   'user', @CurrentUser, 'table', 'lp_wfs_condition', 'column', 'rightval'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '排序字段',
   'user', @CurrentUser, 'table', 'lp_wfs_condition', 'column', 'px'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '添加时间',
   'user', @CurrentUser, 'table', 'lp_wfs_condition', 'column', 'addtime'
go

/*==============================================================*/
/* Table: lp_wfs_event                                          */
/*==============================================================*/
create table lp_wfs_event (
   id                   varchar(50)          null,
   flowversionid        varchar(50)          null,
   javaclass            varchar(200)         null,
   px                   numeric              null,
   addtime              datetime            null,
   constraint AK_KEY_1_LP_WFS_E unique (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程事件',
   'user', @CurrentUser, 'table', 'lp_wfs_event'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主键',
   'user', @CurrentUser, 'table', 'lp_wfs_event', 'column', 'id'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程版本id',
   'user', @CurrentUser, 'table', 'lp_wfs_event', 'column', 'flowversionid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '事件处理类',
   'user', @CurrentUser, 'table', 'lp_wfs_event', 'column', 'javaclass'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '排序字段',
   'user', @CurrentUser, 'table', 'lp_wfs_event', 'column', 'px'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '添加时间',
   'user', @CurrentUser, 'table', 'lp_wfs_event', 'column', 'addtime'
go

/*==============================================================*/
/* Table: lp_wfs_flow                                           */
/*==============================================================*/
create table lp_wfs_flow (
   id                   varchar(50)          not null,
   flowname             varchar(200)         null,
   folderid             varchar(50)          null,
   addtime              datetime            null,
   constraint PK_LP_WFS_FLOW primary key nonclustered (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程建模信息表',
   'user', @CurrentUser, 'table', 'lp_wfs_flow'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主键',
   'user', @CurrentUser, 'table', 'lp_wfs_flow', 'column', 'id'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程名称',
   'user', @CurrentUser, 'table', 'lp_wfs_flow', 'column', 'flowname'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '所属分组',
   'user', @CurrentUser, 'table', 'lp_wfs_flow', 'column', 'folderid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '添加时间',
   'user', @CurrentUser, 'table', 'lp_wfs_flow', 'column', 'addtime'
go

/*==============================================================*/
/* Table: lp_wfs_flownode                                       */
/*==============================================================*/
create table lp_wfs_flownode (
   id                   varchar(50)          not null,
   flowid               varchar(50)          null,
   flowversionid        varchar(50)          null,
   flownodename         varchar(200)         null,
   nodetype             numeric              null,
   embranch             numeric              null,
   converge             numeric              null,
   px                   numeric              null,
   addtime              datetime            null,
   constraint PK_LP_WFS_FLOWNODE primary key nonclustered (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程节点',
   'user', @CurrentUser, 'table', 'lp_wfs_flownode'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主键',
   'user', @CurrentUser, 'table', 'lp_wfs_flownode', 'column', 'id'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程id',
   'user', @CurrentUser, 'table', 'lp_wfs_flownode', 'column', 'flowid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程版本id',
   'user', @CurrentUser, 'table', 'lp_wfs_flownode', 'column', 'flowversionid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '节点名称',
   'user', @CurrentUser, 'table', 'lp_wfs_flownode', 'column', 'flownodename'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '节点类型。0:开始节点、1:普通节点、2：子流程节点、3：自动节点、9：结束节点',
   'user', @CurrentUser, 'table', 'lp_wfs_flownode', 'column', 'nodetype'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分支模式。0：单一分支；1：单一分支（带选择）；2：多路分支；3：多路分支（带选择）',
   'user', @CurrentUser, 'table', 'lp_wfs_flownode', 'column', 'embranch'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '聚合模式。0：单一聚合；1：多路聚合',
   'user', @CurrentUser, 'table', 'lp_wfs_flownode', 'column', 'converge'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '排序字段',
   'user', @CurrentUser, 'table', 'lp_wfs_flownode', 'column', 'px'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '添加时间',
   'user', @CurrentUser, 'table', 'lp_wfs_flownode', 'column', 'addtime'
go

/*==============================================================*/
/* Table: lp_wfs_flownodeext                                    */
/*==============================================================*/
create table lp_wfs_flownodeext (
   id                   varchar(50)          not null,
   drawjson             varchar(2000)        null,
   actorjson            varchar(2000)        null,
   extjson           varchar(2000)        null,
   addtime              datetime            null,
   constraint PK_LP_WFS_FLOWNODEEXT primary key nonclustered (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程节点扩展数据',
   'user', @CurrentUser, 'table', 'lp_wfs_flownodeext'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主键',
   'user', @CurrentUser, 'table', 'lp_wfs_flownodeext', 'column', 'id'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '绘图数据',
   'user', @CurrentUser, 'table', 'lp_wfs_flownodeext', 'column', 'drawjson'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '参与者数据。0：参与者列表；0：参与者列表；1：流程启动者；2：节点处理人；3：流程参数；4：自定义',
   'user', @CurrentUser, 'table', 'lp_wfs_flownodeext', 'column', 'actorjson'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '扩展节点数据。',
   'user', @CurrentUser, 'table', 'lp_wfs_flownodeext', 'column', 'extjson'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '添加时间',
   'user', @CurrentUser, 'table', 'lp_wfs_flownodeext', 'column', 'addtime'
go

/*==============================================================*/
/* Table: lp_wfs_flowtrans                                      */
/*==============================================================*/
create table lp_wfs_flowtrans (
   id                   varchar(50)          not null,
   flowversionid        varchar(50)          null,
   fromnodeid           varchar(50)          null,
   tonodeid             varchar(50)          null,
   trantype             numeric              null,
   allowuntread         numeric              null,
   conditionid          varchar(50)          null,
   px                   numeric              null,
   addtime              datetime            null,
   constraint PK_LP_WFS_FLOWTRANS primary key nonclustered (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程迁移',
   'user', @CurrentUser, 'table', 'lp_wfs_flowtrans'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主键',
   'user', @CurrentUser, 'table', 'lp_wfs_flowtrans', 'column', 'id'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程版本id',
   'user', @CurrentUser, 'table', 'lp_wfs_flowtrans', 'column', 'flowversionid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '源节点id',
   'user', @CurrentUser, 'table', 'lp_wfs_flowtrans', 'column', 'fromnodeid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '目标节点id',
   'user', @CurrentUser, 'table', 'lp_wfs_flowtrans', 'column', 'tonodeid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '迁移类型。0：提交；1：退回',
   'user', @CurrentUser, 'table', 'lp_wfs_flowtrans', 'column', 'trantype'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否允许回退。0：不可回退；1：可以回退',
   'user', @CurrentUser, 'table', 'lp_wfs_flowtrans', 'column', 'allowuntread'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '条件id',
   'user', @CurrentUser, 'table', 'lp_wfs_flowtrans', 'column', 'conditionid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '排序字段',
   'user', @CurrentUser, 'table', 'lp_wfs_flowtrans', 'column', 'px'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '添加时间',
   'user', @CurrentUser, 'table', 'lp_wfs_flowtrans', 'column', 'addtime'
go

/*==============================================================*/
/* Table: lp_wfs_flowtransext                                   */
/*==============================================================*/
create table lp_wfs_flowtransext (
   id                   varchar(50)          not null,
   drawjson             varchar(2000)        null,
   addtime              datetime            null,
   constraint PK_LP_WFS_FLOWTRANSEXT primary key nonclustered (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '迁移绘图',
   'user', @CurrentUser, 'table', 'lp_wfs_flowtransext'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主键',
   'user', @CurrentUser, 'table', 'lp_wfs_flowtransext', 'column', 'id'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '绘图数据',
   'user', @CurrentUser, 'table', 'lp_wfs_flowtransext', 'column', 'drawjson'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '添加时间',
   'user', @CurrentUser, 'table', 'lp_wfs_flowtransext', 'column', 'addtime'
go

/*==============================================================*/
/* Table: lp_wfs_flowversion                                    */
/*==============================================================*/
create table lp_wfs_flowversion (
   id                   varchar(50)          not null,
   flowid               varchar(200)         null,
   versionnum           numeric              null,
   openurl              varchar(500)         null,
   userid               varchar(200)         null,
   username             varchar(200)         null,
   starttime            datetime            null,
   endtime              datetime            null,
   saveversion          varchar(50)          null,
   addtime              datetime            null,
   constraint PK_LP_WFS_FLOWVERSION primary key nonclustered (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程版本表',
   'user', @CurrentUser, 'table', 'lp_wfs_flowversion'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主键',
   'user', @CurrentUser, 'table', 'lp_wfs_flowversion', 'column', 'id'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程id',
   'user', @CurrentUser, 'table', 'lp_wfs_flowversion', 'column', 'flowid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '版本号',
   'user', @CurrentUser, 'table', 'lp_wfs_flowversion', 'column', 'versionnum'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '待办地址',
   'user', @CurrentUser, 'table', 'lp_wfs_flowversion', 'column', 'openurl'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '发布人id',
   'user', @CurrentUser, 'table', 'lp_wfs_flowversion', 'column', 'userid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '发布人',
   'user', @CurrentUser, 'table', 'lp_wfs_flowversion', 'column', 'username'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '启用时间',
   'user', @CurrentUser, 'table', 'lp_wfs_flowversion', 'column', 'starttime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '结束时间',
   'user', @CurrentUser, 'table', 'lp_wfs_flowversion', 'column', 'endtime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '保存冲突版本',
   'user', @CurrentUser, 'table', 'lp_wfs_flowversion', 'column', 'saveversion'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '添加时间',
   'user', @CurrentUser, 'table', 'lp_wfs_flowversion', 'column', 'addtime'
go

/*==============================================================*/
/* Table: lp_wfs_folder                                         */
/*==============================================================*/
create table lp_wfs_folder (
   id                   varchar(50)          not null,
   foldername           varchar(200)         null,
   pid                  varchar(50)          null,
   addtime              datetime            null,
   constraint PK_LP_WFS_FOLDER primary key nonclustered (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分组表',
   'user', @CurrentUser, 'table', 'lp_wfs_folder'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主键',
   'user', @CurrentUser, 'table', 'lp_wfs_folder', 'column', 'id'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分组名称',
   'user', @CurrentUser, 'table', 'lp_wfs_folder', 'column', 'foldername'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '父分组',
   'user', @CurrentUser, 'table', 'lp_wfs_folder', 'column', 'pid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '添加时间',
   'user', @CurrentUser, 'table', 'lp_wfs_folder', 'column', 'addtime'
go

/*==============================================================*/
/* Table: lp_wfs_param                                          */
/*==============================================================*/
create table lp_wfs_param (
   id                   varchar(50)          null,
   flowversionid        varchar(50)          null,
   parambm              varchar(200)         null,
   paramname            varchar(200)         null,
   px                   numeric              null,
   addtime              datetime            null,
   constraint AK_KEY_1_LP_WFS_P unique (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程参数',
   'user', @CurrentUser, 'table', 'lp_wfs_param'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主键',
   'user', @CurrentUser, 'table', 'lp_wfs_param', 'column', 'id'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程版本id',
   'user', @CurrentUser, 'table', 'lp_wfs_param', 'column', 'flowversionid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '参数编码',
   'user', @CurrentUser, 'table', 'lp_wfs_param', 'column', 'parambm'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '参数名称',
   'user', @CurrentUser, 'table', 'lp_wfs_param', 'column', 'paramname'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '排序字段',
   'user', @CurrentUser, 'table', 'lp_wfs_param', 'column', 'px'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '添加时间',
   'user', @CurrentUser, 'table', 'lp_wfs_param', 'column', 'addtime'
go

--V1.3.1 版本改动
update lp_sys_dept set depttype='1' where id='-1';