
--建立数据库
create tablespace lp_data  
logging  
datafile '/u01/app/oracle/oradata/xe/lp_data.dbf' 
size 1024m  
autoextend on  
next 100m maxsize 20480m  
extent management local;

create user lp identified by zaq12WSX default tablespace lp_data; 

grant connect,resource,dba to lp; 
﻿
--操作指令
drop table lp_sys_attach cascade constraints;

drop table lp_sys_dept cascade constraints;

drop table lp_sys_dict cascade constraints;

drop table lp_sys_dictsort cascade constraints;

drop table lp_sys_log cascade constraints;

drop table lp_sys_menu cascade constraints;

drop table lp_sys_order cascade constraints;

drop table lp_sys_role cascade constraints;

drop table lp_sys_rolemenu cascade constraints;

drop table lp_sys_task cascade constraints;

drop table lp_sys_user cascade constraints;

drop table lp_sys_userex cascade constraints;

drop table lp_sys_userrole cascade constraints;


/*==============================================================*/
/* Table: lp_sys_attach                                         */
/*==============================================================*/
create table lp_sys_attach  (
   id                   VARCHAR2(50)                    not null,
   filename             VARCHAR2(200),
   filesize             NUMBER,
   savepath             VARCHAR2(50),
   tablename            VARCHAR2(200),
   field                VARCHAR2(200),
   objid                VARCHAR2(200),
   addtime              TIMESTAMP,
   constraint PK_LP_SYS_ATTACH primary key (id)
);

comment on table lp_sys_attach is
'附件表';

comment on column lp_sys_attach.id is
'主键';

comment on column lp_sys_attach.filename is
'文件名';

comment on column lp_sys_attach.filesize is
'文件大小';

comment on column lp_sys_attach.savepath is
'存储路径';

comment on column lp_sys_attach.tablename is
'关联表';

comment on column lp_sys_attach.field is
'关联字段';

comment on column lp_sys_attach.objid is
'关联ID';

comment on column lp_sys_attach.addtime is
'添加时间';

/*==============================================================*/
/* Table: lp_sys_dept                                           */
/*==============================================================*/
create table lp_sys_dept  (
   id                   VARCHAR2(50)                    not null,
   pid                  VARCHAR2(50),
   deptcode             VARCHAR2(200),
   deptname             VARCHAR2(200),
   depttype             VARCHAR2(200),
   px                   NUMBER,
   addtime              TIMESTAMP,
   constraint PK_LP_SYS_DEPT primary key (id)
);

comment on table lp_sys_dept is
'机构表';

comment on column lp_sys_dept.id is
'主键';

comment on column lp_sys_dept.pid is
'父级机构ID';

comment on column lp_sys_dept.deptcode is
'机构编码';

comment on column lp_sys_dept.deptname is
'机构名称';

comment on column lp_sys_dept.depttype is
'机构类型';

comment on column lp_sys_dept.px is
'排序字段';

comment on column lp_sys_dept.addtime is
'添加时间';

/*==============================================================*/
/* Table: lp_sys_dict                                           */
/*==============================================================*/
create table lp_sys_dict  (
   id                   VARCHAR2(50)                    not null,
   pid                  VARCHAR2(50),
   code                 VARCHAR2(200),
   val                  VARCHAR2(200),
   sortcode             VARCHAR2(200),
   px                   NUMBER,
   addtime              TIMESTAMP,
   constraint PK_LP_SYS_DICT primary key (id)
);

comment on table lp_sys_dict is
'数据字典';

comment on column lp_sys_dict.id is
'主键';

comment on column lp_sys_dict.pid is
'父级ID';

comment on column lp_sys_dict.code is
'代码';

comment on column lp_sys_dict.val is
'值';

comment on column lp_sys_dict.sortcode is
'数据字典分类编码';

comment on column lp_sys_dict.px is
'排序字段';

comment on column lp_sys_dict.addtime is
'添加时间';

/*==============================================================*/
/* Table: lp_sys_dictsort                                       */
/*==============================================================*/
create table lp_sys_dictsort  (
   id                   VARCHAR2(50)                    not null,
   sortcode             VARCHAR2(200),
   sortname             VARCHAR2(200),
   note                 VARCHAR2(500),
   px                   NUMBER,
   addtime              TIMESTAMP,
   constraint PK_LP_SYS_DICTSORT primary key (id)
);

comment on table lp_sys_dictsort is
'数据字典分类';

comment on column lp_sys_dictsort.id is
'主键';

comment on column lp_sys_dictsort.sortcode is
'字典分类编码';

comment on column lp_sys_dictsort.sortname is
'字典分类名称';

comment on column lp_sys_dictsort.note is
'描述';

comment on column lp_sys_dictsort.px is
'排序字段';

comment on column lp_sys_dictsort.addtime is
'添加时间';

/*==============================================================*/
/* Table: lp_sys_log                                            */
/*==============================================================*/
create table lp_sys_log  (
   id                   VARCHAR2(50)                    not null,
   logtype              VARCHAR2(200),
   info                 VARCHAR2(2000),
   ip                   VARCHAR2(200),
   userid               VARCHAR2(50),
   username             VARCHAR2(200),
   addtime              TIMESTAMP,
   status             VARCHAR2(200),
   constraint PK_LP_SYS_LOG primary key (id)
);

comment on table lp_sys_log is
'系统日志表';

comment on column lp_sys_log.id is
'主键';

comment on column lp_sys_log.logtype is
'日志类型(登录)';

comment on column lp_sys_log.info is
'日志内容';

comment on column lp_sys_log.ip is
'操作IP';

comment on column lp_sys_log.userid is
'用户id';

comment on column lp_sys_log.username is
'用户名称';

comment on column lp_sys_log.addtime is
'添加时间';

comment on column lp_sys_log.status is
'操作状态';

/*==============================================================*/
/* Table: lp_sys_menu                                           */
/*==============================================================*/
create table lp_sys_menu  (
   id                   VARCHAR2(50)                    not null,
   pid                  VARCHAR2(50),
   menuname             VARCHAR2(200),
   url                  VARCHAR2(200),
   openway              VARCHAR2(200),
   px                   NUMBER,
   addtime              TIMESTAMP,
   constraint PK_LP_SYS_MENU primary key (id)
);

comment on table lp_sys_menu is
'菜单表';

comment on column lp_sys_menu.id is
'主键';

comment on column lp_sys_menu.pid is
'父ID';

comment on column lp_sys_menu.menuname is
'菜单名称';

comment on column lp_sys_menu.url is
'打开地址';

comment on column lp_sys_menu.openway is
'打开方式';

comment on column lp_sys_menu.px is
'排序字段';

comment on column lp_sys_menu.addtime is
'添加时间';

/*==============================================================*/
/* Table: lp_sys_order                                          */
/*==============================================================*/
create table lp_sys_order  (
   tablename            VARCHAR2(50)                    not null,
   val                  NUMBER,
   constraint PK_LP_SYS_ORDER primary key (tablename)
);

comment on table lp_sys_order is
'排序字段存储表';

comment on column lp_sys_order.tablename is
'表名';

comment on column lp_sys_order.val is
'当前值';

/*==============================================================*/
/* Table: lp_sys_role                                           */
/*==============================================================*/
create table lp_sys_role  (
   id                   VARCHAR2(50)                    not null,
   deptid               VARCHAR2(50),
   rolecode             VARCHAR2(200),
   rolename             VARCHAR2(200),
   px                   NUMBER,
   addtime              TIMESTAMP,
   constraint PK_LP_SYS_ROLE primary key (id)
);

comment on table lp_sys_role is
'角色表';

comment on column lp_sys_role.id is
'主键';

comment on column lp_sys_role.deptid is
'机构ID';

comment on column lp_sys_role.rolecode is
'角色编码';

comment on column lp_sys_role.rolename is
'角色名称';

comment on column lp_sys_role.px is
'排序字段';

comment on column lp_sys_role.addtime is
'添加时间';

/*==============================================================*/
/* Table: lp_sys_rolemenu                                       */
/*==============================================================*/
create table lp_sys_rolemenu  (
   id                   VARCHAR2(50)                    not null,
   roleid               VARCHAR2(50),
   menuid               VARCHAR2(50),
   constraint PK_LP_SYS_ROLEMENU primary key (id)
);

comment on table lp_sys_rolemenu is
'角色菜单表';

comment on column lp_sys_rolemenu.id is
'主键';

comment on column lp_sys_rolemenu.roleid is
'角色ID';

comment on column lp_sys_rolemenu.menuid is
'菜单ID';

/*==============================================================*/
/* Table: lp_sys_task                                           */
/*==============================================================*/
create table lp_sys_task  (
   id                   VARCHAR2(50)                    not null,
   taskname             VARCHAR2(200),
   exp                  VARCHAR2(200),
   class                VARCHAR2(200),
   status               VARCHAR2(200),
   note                 VARCHAR2(200),
   addtime              TIMESTAMP,
   constraint PK_LP_SYS_TASK primary key (id)
);

comment on table lp_sys_task is
'定时任务表';

comment on column lp_sys_task.id is
'主键';

comment on column lp_sys_task.taskname is
'任务名称';

comment on column lp_sys_task.exp is
'表达式';

comment on column lp_sys_task.class is
'实现类';

comment on column lp_sys_task.status is
'状态，''0'':禁用、''1'':启用';

comment on column lp_sys_task.note is
'描述';

comment on column lp_sys_task.addtime is
'添加时间';

/*==============================================================*/
/* Table: lp_sys_user                                           */
/*==============================================================*/
create table lp_sys_user  (
   id                   VARCHAR2(50)                    not null,
   deptid               VARCHAR2(50),
   usernum              VARCHAR2(200),
   username             VARCHAR2(200),
   pwd                  VARCHAR2(200),
   managedeptid         VARCHAR2(50),
   phone                VARCHAR2(200),
   status               VARCHAR2(50),
   issuperadmin         VARCHAR2(20),
   px                   NUMBER,
   addtime              TIMESTAMP,
   constraint PK_LP_SYS_USER primary key (id)
);

comment on table lp_sys_user is
'用户表';

comment on column lp_sys_user.id is
'主键';

comment on column lp_sys_user.deptid is
'机构ID';

comment on column lp_sys_user.usernum is
'用户名';

comment on column lp_sys_user.username is
'用户名称';

comment on column lp_sys_user.pwd is
'密码';

comment on column lp_sys_user.managedeptid is
'可分级管理机构ID';

comment on column lp_sys_user.phone is
'手机号码';

comment on column lp_sys_user.status is
'0：停用，1：启用';

comment on column lp_sys_user.issuperadmin is
'是否超级管理员';

comment on column lp_sys_user.px is
'排序字段';

comment on column lp_sys_user.addtime is
'添加时间';

/*==============================================================*/
/* Table: lp_sys_userex                                         */
/*==============================================================*/
create table lp_sys_userex  (
   id                   VARCHAR2(50)                    not null,
   userid               VARCHAR2(50),
   key                  VARCHAR2(200),
   val                  VARCHAR2(500),
   px                   NUMBER,
   addtime              TIMESTAMP,
   constraint PK_LP_SYS_USEREX primary key (id)
);

comment on table lp_sys_userex is
'用户表扩展信息';

comment on column lp_sys_userex.id is
'主键';

comment on column lp_sys_userex.userid is
'用户ID';

comment on column lp_sys_userex.key is
'扩展属性名称';

comment on column lp_sys_userex.val is
'扩展属性值';

comment on column lp_sys_userex.px is
'排序字段';

comment on column lp_sys_userex.addtime is
'添加时间';

/*==============================================================*/
/* Table: lp_sys_userrole                                       */
/*==============================================================*/
create table lp_sys_userrole  (
   id                   VARCHAR2(50)                    not null,
   userid               VARCHAR2(50),
   roleid               VARCHAR2(50),
   constraint PK_LP_SYS_USERROLE primary key (id)
);

comment on table lp_sys_userrole is
'用户角色表';

comment on column lp_sys_userrole.id is
'主键';

comment on column lp_sys_userrole.userid is
'用户ID';

comment on column lp_sys_userrole.roleid is
'角色ID';

--V1.2版本改动
alter table lp_sys_menu add iconcls VARCHAR2(200);
alter table lp_sys_menu add opensubmenuindex VARCHAR2(200);

alter table lp_sys_attach add fileext VARCHAR2(200);
alter table lp_sys_attach add userid VARCHAR2(200);
alter table lp_sys_attach add username VARCHAR2(200);

/*==============================================================*/
/* 初始化数据开始                                       		*/
/*==============================================================*/

INSERT INTO lp_sys_dept(
	id, pid, deptcode, deptname, depttype, px, addtime)
	VALUES ('-1', null, null, '组织机构', '单位', '0', sysdate);
	
INSERT INTO lp_sys_user(
	id, deptid, usernum, username, pwd, managedeptid, phone, status, issuperadmin, px, addtime)
	VALUES ('0', '-1', 'admin', '管理员', '78becbe4454a95a15ea9abcda568a962', null, null, '1', '1', '0', sysdate);
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime,iconcls)
	VALUES ('d9e006abcc2f489ba78a9969d1055b13','-1','系统管理',null,null,1,sysdate,'fa fa-cog');
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime,iconcls)
	VALUES ('acf5a89736714522938de480841a05dd','-1','系统安全',null,null,2,sysdate,'fa fa-window-restore');
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('13c3ffe4237e44d39f9021edc9769ed6','d9e006abcc2f489ba78a9969d1055b13','机构维护','/dept/index','属性页',1,sysdate);

INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('065775d70a6f4ef994542c21c960d94b','d9e006abcc2f489ba78a9969d1055b13','用户维护','/user/index','属性页',2,sysdate);
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('844f91c129034f6d9febee4b99fbca24','d9e006abcc2f489ba78a9969d1055b13','角色维护','/role/index','属性页',3,sysdate);
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('8cb92e6271284155b9bcb6cd92e38098','d9e006abcc2f489ba78a9969d1055b13','菜单维护','/menu/index','属性页',4,sysdate);
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('8889df7e4b724fb9bada47b0e2fa2888','d9e006abcc2f489ba78a9969d1055b13','定时任务','/task/index','属性页',5,sysdate);
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('24ff1ae9748e40168d3f808b1f59e066','acf5a89736714522938de480841a05dd','系统日志','/sys/log/index','属性页',1,sysdate);

INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('cb8f44a434d448b9990df23e524c6a98','acf5a89736714522938de480841a05dd','用户解锁','/sys/lockuser/index','属性页',2,sysdate);
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('022a1692d2974e0a9584deed77706b73','acf5a89736714522938de480841a05dd','开发演示','/sys/demo/index','属性页',3,sysdate);
	
drop table lp_test cascade constraints;

/*==============================================================*/
/* Table: lp_test                                               */
/*==============================================================*/
create table lp_test  (
   id                   VARCHAR2(50)                    not null,
   txt1                 VARCHAR2(200),
   txt2                 VARCHAR2(200),
   txt3                 CHAR(200),
   int1                 INTEGER,
   int2                 INTEGER,
   long1                VARCHAR2(1),
   num1                 NUMBER,
   decimal1             NUMBER,
   float1               FLOAT,
   numeric1             NUMBER,
   date1                DATE,
   ts1                  TIMESTAMP,
   ts2                  TIMESTAMP,
   constraint PK_LP_TEST primary key (id)
);

comment on table lp_test is
'示例用表';

comment on column lp_test.id is
'id';

comment on column lp_test.txt1 is
'txt1';

comment on column lp_test.txt2 is
'txt2';

comment on column lp_test.txt3 is
'txt3';

comment on column lp_test.int1 is
'int1';

comment on column lp_test.int2 is
'int2';

comment on column lp_test.long1 is
'long1';

comment on column lp_test.num1 is
'num1';

comment on column lp_test.decimal1 is
'decimal1';

comment on column lp_test.float1 is
'float1';

comment on column lp_test.numeric1 is
'numeric1';

comment on column lp_test.date1 is
'date1';

comment on column lp_test.ts1 is
'ts1';

comment on column lp_test.ts2 is
'ts2';


--V1.3版本改动

alter table lp_sys_menu add subsystem VARCHAR2(200);
alter table lp_sys_user add dwid VARCHAR2(50);
alter table lp_sys_user add bmid VARCHAR2(50);

INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('81c5492308d2475c9451af68e93bb5aa','d9e006abcc2f489ba78a9969d1055b13','数据字典','/sys/dict/index','属性页',6,sysdate);
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('f9d7fa9cab44406d8d50018ee7872899','d9e006abcc2f489ba78a9969d1055b13','流程配置','/sys/flow/index','属性页',7,sysdate);

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('-1', '', '', '数据字典', '', null, sysdate);

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380374138117685248', '-1', '', 'LP系统', '', null, sysdate);

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380383611062321152', '380374138117685248', '', '机构类型', '', 1, sysdate);

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380383911647117312', '380383611062321152', '1', '单位', 'lp.depttype', 1, sysdate);

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380383911668088832', '380383611062321152', '2', '部门', 'lp.depttype', 2, sysdate);

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380383911693254656', '380383611062321152', '3', '班组', 'lp.depttype', 3, sysdate);

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380645045998452736', '380374138117685248', '', '日志类型', '', 2, sysdate);

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380645252433707008', '380645045998452736', '系统登录', '系统登录', 'lp.logtype', 1, sysdate);

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380645252450484224', '380645045998452736', '系统管理', '系统管理', 'lp.logtype', 2, sysdate);

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380645252463067136', '380645045998452736', '系统安全', '系统安全', 'lp.logtype', 3, sysdate);

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380645252500815872', '380645045998452736', '业务应用', '业务应用', 'lp.logtype', 4, sysdate);



drop table lp_wfi_flow cascade constraints;

drop index IDX_I_FLOWNODE_FLOWINSID;

drop table lp_wfi_flownode cascade constraints;

drop index IDX_I_OPERATOR_FLOWNODEINSID;

drop table lp_wfi_operator cascade constraints;

drop index IDX_I_FLOWPARAM_FLOWINSID;

drop table lp_wfi_param cascade constraints;

drop index IDX_I_RECEIVER_FLOWNODEINSID;

drop table lp_wfi_receiver cascade constraints;

drop table lp_wfi_waitdo cascade constraints;

drop table lp_wfs_condition cascade constraints;

drop table lp_wfs_event cascade constraints;

drop table lp_wfs_flow cascade constraints;

drop table lp_wfs_flownode cascade constraints;

drop table lp_wfs_flownodeext cascade constraints;

drop table lp_wfs_flowtrans cascade constraints;

drop table lp_wfs_flowtransext cascade constraints;

drop table lp_wfs_flowversion cascade constraints;

drop table lp_wfs_folder cascade constraints;

drop table lp_wfs_param cascade constraints;

/*==============================================================*/
/* Table: lp_wfi_flow                                           */
/*==============================================================*/
create table lp_wfi_flow  (
   id                   VARCHAR2(50)                    not null,
   flowversionid        VARCHAR2(200),
   flowname             VARCHAR2(200),
   starttime            DATE,
   endtime              DATE,
   createuser           VARCHAR2(200),
   createuserid         VARCHAR2(200),
   runstatus            NUMBER,
   saveversion          VARCHAR2(50),
   addtime              DATE,
   constraint PK_LP_WFI_FLOW primary key (id)
);

comment on table lp_wfi_flow is
'流程实例';

comment on column lp_wfi_flow.id is
'主键';

comment on column lp_wfi_flow.flowversionid is
'流程版本id';

comment on column lp_wfi_flow.flowname is
'流程名称';

comment on column lp_wfi_flow.starttime is
'启动时间';

comment on column lp_wfi_flow.endtime is
'结束时间';

comment on column lp_wfi_flow.createuser is
'创建人';

comment on column lp_wfi_flow.createuserid is
'创建人id';

comment on column lp_wfi_flow.runstatus is
'当前状态。0：运行；1：完成；2：终止；3：挂起；';

comment on column lp_wfi_flow.saveversion is
'保存冲突版本';

comment on column lp_wfi_flow.addtime is
'添加时间';

/*==============================================================*/
/* Table: lp_wfi_flownode                                       */
/*==============================================================*/
create table lp_wfi_flownode  (
   id                   VARCHAR2(50)                    not null,
   flowversionid        VARCHAR2(50),
   flowinstanceid       VARCHAR2(50),
   flownodeid           VARCHAR2(50),
   flownodename         VARCHAR2(200),
   nodetype             NUMBER,
   runstatus            NUMBER,
   dealstatus           NUMBER,
   pids                 VARCHAR2(500),
   senderid             VARCHAR2(200),
   sender               VARCHAR2(200),
   begintime            DATE,
   endtime              DATE,
   note                 VARCHAR2(500),
   px                   NUMBER,
   addtime              DATE,
   constraint PK_LP_WFI_FLOWNODE primary key (id)
);

comment on table lp_wfi_flownode is
'流程节点实例';

comment on column lp_wfi_flownode.id is
'主键';

comment on column lp_wfi_flownode.flowversionid is
'流程版本id';

comment on column lp_wfi_flownode.flowinstanceid is
'流程实例id';

comment on column lp_wfi_flownode.flownodeid is
'流程节点id';

comment on column lp_wfi_flownode.flownodename is
'节点名称';

comment on column lp_wfi_flownode.nodetype is
'节点类型。0:开始节点、1:普通节点、2：子流程节点、3：自动节点、9：结束节点';

comment on column lp_wfi_flownode.runstatus is
'运行状态。-1：未启动；0：运行中；1：已完成';

comment on column lp_wfi_flownode.dealstatus is
'处理状态。0：未处理；1：提交；2：退回；3：撤回；4：作废';

comment on column lp_wfi_flownode.pids is
'上一个节点实例Id。JSON格式：[节点实例Id,...]';

comment on column lp_wfi_flownode.senderid is
'发送人id';

comment on column lp_wfi_flownode.sender is
'发送人';

comment on column lp_wfi_flownode.begintime is
'开始时间';

comment on column lp_wfi_flownode.endtime is
'结束时间';

comment on column lp_wfi_flownode.note is
'备注';

comment on column lp_wfi_flownode.px is
'排序字段';

comment on column lp_wfi_flownode.addtime is
'添加时间';

/*==============================================================*/
/* Index: IDX_I_FLOWNODE_FLOWINSID                              */
/*==============================================================*/
create index IDX_I_FLOWNODE_FLOWINSID on lp_wfi_flownode (
   flowinstanceid ASC
);

/*==============================================================*/
/* Table: lp_wfi_operator                                       */
/*==============================================================*/
create table lp_wfi_operator  (
   id                   VARCHAR2(50)                    not null,
   flownodeinstanceid   VARCHAR2(50),
   operatortype         NUMBER,
   operatorid           VARCHAR2(50),
   operator             VARCHAR2(200),
   addtime              DATE,
   constraint PK_LP_WFI_OPERATOR primary key (id)
);

comment on table lp_wfi_operator is
'流程处理人';

comment on column lp_wfi_operator.id is
'主键';

comment on column lp_wfi_operator.flownodeinstanceid is
'流程节点实例id';

comment on column lp_wfi_operator.operatortype is
'接收人类型。0:人员；1:角色；2:部门';

comment on column lp_wfi_operator.operatorid is
'处理人id';

comment on column lp_wfi_operator.operator is
'处理人';

comment on column lp_wfi_operator.addtime is
'添加时间';

/*==============================================================*/
/* Index: IDX_I_OPERATOR_FLOWNODEINSID                          */
/*==============================================================*/
create index IDX_I_OPERATOR_FLOWNODEINSID on lp_wfi_operator (
   flownodeinstanceid ASC
);

/*==============================================================*/
/* Table: lp_wfi_param                                          */
/*==============================================================*/
create table lp_wfi_param  (
   id                   VARCHAR2(50)                    not null,
   flowinstanceid       VARCHAR2(50),
   parambm              VARCHAR2(200),
   val                  VARCHAR2(200),
   addtime              DATE,
   constraint PK_LP_WFI_PARAM primary key (id)
);

comment on table lp_wfi_param is
'流程参数实例';

comment on column lp_wfi_param.id is
'主键';

comment on column lp_wfi_param.flowinstanceid is
'流程实例id';

comment on column lp_wfi_param.parambm is
'参数编码';

comment on column lp_wfi_param.val is
'参数值';

comment on column lp_wfi_param.addtime is
'添加时间';

/*==============================================================*/
/* Index: IDX_I_FLOWPARAM_FLOWINSID                             */
/*==============================================================*/
create index IDX_I_FLOWPARAM_FLOWINSID on lp_wfi_param (
   flowinstanceid ASC
);

/*==============================================================*/
/* Table: lp_wfi_receiver                                       */
/*==============================================================*/
create table lp_wfi_receiver  (
   id                   VARCHAR2(50)                    not null,
   flownodeinstanceid   VARCHAR2(50),
   receivertype         NUMBER,
   receiverid           VARCHAR2(50),
   receiver             VARCHAR2(200),
   addtime              DATE,
   constraint PK_LP_WFI_RECEIVER primary key (id)
);

comment on table lp_wfi_receiver is
'流程接收人';

comment on column lp_wfi_receiver.id is
'主键';

comment on column lp_wfi_receiver.flownodeinstanceid is
'流程节点实例id';

comment on column lp_wfi_receiver.receivertype is
'接收人类型。0:人员；1:角色；2:部门';

comment on column lp_wfi_receiver.receiverid is
'接收人id';

comment on column lp_wfi_receiver.receiver is
'接收人';

comment on column lp_wfi_receiver.addtime is
'添加时间';

/*==============================================================*/
/* Index: IDX_I_RECEIVER_FLOWNODEINSID                          */
/*==============================================================*/
create index IDX_I_RECEIVER_FLOWNODEINSID on lp_wfi_receiver (
   flownodeinstanceid ASC
);

/*==============================================================*/
/* Table: lp_wfi_waitdo                                         */
/*==============================================================*/
create table lp_wfi_waitdo  (
   id                   VARCHAR2(50)                    not null,
   flowversionid        VARCHAR2(50),
   flowinstanceid       VARCHAR2(50),
   flownodeinstanceid   VARCHAR2(50),
   flownodeid           VARCHAR2(50),
   flownodename         VARCHAR2(50),
   senderid             VARCHAR2(50),
   sender               VARCHAR2(50),
   receivertype         NUMBER,
   receiverid           VARCHAR2(50),
   receiver             VARCHAR2(200),
   openurl              VARCHAR2(500),
   addtime              DATE,
   constraint PK_LP_WFI_WAITDO primary key (id)
);

comment on table lp_wfi_waitdo is
'流程待办';

comment on column lp_wfi_waitdo.id is
'主键';

comment on column lp_wfi_waitdo.flowversionid is
'流程版本id';

comment on column lp_wfi_waitdo.flowinstanceid is
'流程实例id';

comment on column lp_wfi_waitdo.flownodeinstanceid is
'流程节点实例id';

comment on column lp_wfi_waitdo.flownodeid is
'流程节点id';

comment on column lp_wfi_waitdo.flownodename is
'流程节点名称';

comment on column lp_wfi_waitdo.senderid is
'发送人id';

comment on column lp_wfi_waitdo.sender is
'发送人';

comment on column lp_wfi_waitdo.receivertype is
'接收人类型。0:人员；1:角色；2:部门';

comment on column lp_wfi_waitdo.receiverid is
'接收人id';

comment on column lp_wfi_waitdo.receiver is
'接收人';

comment on column lp_wfi_waitdo.openurl is
'流程待办';

comment on column lp_wfi_waitdo.addtime is
'添加时间';

/*==============================================================*/
/* Table: lp_wfs_condition                                      */
/*==============================================================*/
create table lp_wfs_condition  (
   id                   VARCHAR2(50),
   flowversionid        VARCHAR2(50),
   conditionname        VARCHAR2(200),
   conditiontype        NUMBER,
   comparetype          NUMBER,
   leftval                 VARCHAR2(200),
   rightval                VARCHAR2(200),
   px                   NUMBER,
   addtime              DATE,
   constraint AK_KEY_1_LP_WFS_C unique (id)
);

comment on table lp_wfs_condition is
'流程条件';

comment on column lp_wfs_condition.id is
'主键';

comment on column lp_wfs_condition.flowversionid is
'流程版本id';

comment on column lp_wfs_condition.conditionname is
'条件名称';

comment on column lp_wfs_condition.conditiontype is
'条件类型。0：流程参数@流程参数；1：流程参数@常量；2：流程参数@条件；3：条件@条件；4：条件@常量；5：常量@常量';

comment on column lp_wfs_condition.comparetype is
'比较符。0：包含；1：小于；2：小于等于；3：大于；4：大于等于；5：等于；6：不等于；7：与；8：或；10：不包含；11：左包含；12：右包含；13：非';

comment on column lp_wfs_condition.leftval is
'左操作数';

comment on column lp_wfs_condition.rightval is
'右操作数';

comment on column lp_wfs_condition.px is
'排序字段';

comment on column lp_wfs_condition.addtime is
'添加时间';

/*==============================================================*/
/* Table: lp_wfs_event                                          */
/*==============================================================*/
create table lp_wfs_event  (
   id                   VARCHAR2(50),
   flowversionid        VARCHAR2(50),
   javaclass            VARCHAR2(200),
   px                   NUMBER,
   addtime              DATE,
   constraint AK_KEY_1_LP_WFS_E unique (id)
);

comment on table lp_wfs_event is
'流程事件';

comment on column lp_wfs_event.id is
'主键';

comment on column lp_wfs_event.flowversionid is
'流程版本id';

comment on column lp_wfs_event.javaclass is
'事件处理类';

comment on column lp_wfs_event.px is
'排序字段';

comment on column lp_wfs_event.addtime is
'添加时间';

/*==============================================================*/
/* Table: lp_wfs_flow                                           */
/*==============================================================*/
create table lp_wfs_flow  (
   id                   VARCHAR2(50)                    not null,
   flowname             VARCHAR2(200),
   folderid             VARCHAR2(50),
   addtime              DATE,
   constraint PK_LP_WFS_FLOW primary key (id)
);

comment on table lp_wfs_flow is
'流程建模信息表';

comment on column lp_wfs_flow.id is
'主键';

comment on column lp_wfs_flow.flowname is
'流程名称';

comment on column lp_wfs_flow.folderid is
'所属分组';

comment on column lp_wfs_flow.addtime is
'添加时间';

/*==============================================================*/
/* Table: lp_wfs_flownode                                       */
/*==============================================================*/
create table lp_wfs_flownode  (
   id                   VARCHAR2(50)                    not null,
   flowid               VARCHAR2(50),
   flowversionid        VARCHAR2(50),
   flownodename         VARCHAR2(200),
   nodetype             NUMBER,
   embranch             NUMBER,
   converge             NUMBER,
   px                   NUMBER,
   addtime              DATE,
   constraint PK_LP_WFS_FLOWNODE primary key (id)
);

comment on table lp_wfs_flownode is
'流程节点';

comment on column lp_wfs_flownode.id is
'主键';

comment on column lp_wfs_flownode.flowid is
'流程id';

comment on column lp_wfs_flownode.flowversionid is
'流程版本id';

comment on column lp_wfs_flownode.flownodename is
'节点名称';

comment on column lp_wfs_flownode.nodetype is
'节点类型。0:开始节点、1:普通节点、2：子流程节点、3：自动节点、9：结束节点';

comment on column lp_wfs_flownode.embranch is
'分支模式。0：单一分支；1：单一分支（带选择）；2：多路分支；3：多路分支（带选择）';

comment on column lp_wfs_flownode.converge is
'聚合模式。0：单一聚合；1：多路聚合';

comment on column lp_wfs_flownode.px is
'排序字段';

comment on column lp_wfs_flownode.addtime is
'添加时间';

/*==============================================================*/
/* Table: lp_wfs_flownodeext                                    */
/*==============================================================*/
create table lp_wfs_flownodeext  (
   id                   VARCHAR2(50)                    not null,
   drawjson             VARCHAR2(2000),
   actorjson            VARCHAR2(2000),
   extjson           VARCHAR2(2000),
   addtime              DATE,
   constraint PK_LP_WFS_FLOWNODEEXT primary key (id)
);

comment on table lp_wfs_flownodeext is
'流程节点扩展数据';

comment on column lp_wfs_flownodeext.id is
'主键';

comment on column lp_wfs_flownodeext.drawjson is
'绘图数据';

comment on column lp_wfs_flownodeext.actorjson is
'参与者数据。0：参与者列表；0：参与者列表；1：流程启动者；2：节点处理人；3：流程参数；4：自定义';

comment on column lp_wfs_flownodeext.extjson is
'扩展节点数据。';

comment on column lp_wfs_flownodeext.addtime is
'添加时间';

/*==============================================================*/
/* Table: lp_wfs_flowtrans                                      */
/*==============================================================*/
create table lp_wfs_flowtrans  (
   id                   VARCHAR2(50)                    not null,
   flowversionid        VARCHAR2(50),
   fromnodeid           VARCHAR2(50),
   tonodeid             VARCHAR2(50),
   trantype             NUMBER,
   allowuntread         NUMBER,
   conditionid          VARCHAR2(50),
   px                   NUMBER,
   addtime              DATE,
   constraint PK_LP_WFS_FLOWTRANS primary key (id)
);

comment on table lp_wfs_flowtrans is
'流程迁移';

comment on column lp_wfs_flowtrans.id is
'主键';

comment on column lp_wfs_flowtrans.flowversionid is
'流程版本id';

comment on column lp_wfs_flowtrans.fromnodeid is
'源节点id';

comment on column lp_wfs_flowtrans.tonodeid is
'目标节点id';

comment on column lp_wfs_flowtrans.trantype is
'迁移类型。0：提交；1：退回';

comment on column lp_wfs_flowtrans.allowuntread is
'是否允许回退。0：不可回退；1：可以回退';

comment on column lp_wfs_flowtrans.conditionid is
'条件id';

comment on column lp_wfs_flowtrans.px is
'排序字段';

comment on column lp_wfs_flowtrans.addtime is
'添加时间';

/*==============================================================*/
/* Table: lp_wfs_flowtransext                                   */
/*==============================================================*/
create table lp_wfs_flowtransext  (
   id                   VARCHAR2(50)                    not null,
   drawjson             VARCHAR2(2000),
   addtime              DATE,
   constraint PK_LP_WFS_FLOWTRANSEXT primary key (id)
);

comment on table lp_wfs_flowtransext is
'迁移绘图';

comment on column lp_wfs_flowtransext.id is
'主键';

comment on column lp_wfs_flowtransext.drawjson is
'绘图数据';

comment on column lp_wfs_flowtransext.addtime is
'添加时间';

/*==============================================================*/
/* Table: lp_wfs_flowversion                                    */
/*==============================================================*/
create table lp_wfs_flowversion  (
   id                   VARCHAR2(50)                    not null,
   flowid               VARCHAR2(200),
   versionnum           NUMBER,
   openurl              VARCHAR2(500),
   userid               VARCHAR2(200),
   username             VARCHAR2(200),
   starttime            DATE,
   endtime              DATE,
   saveversion          VARCHAR2(50),
   addtime              DATE,
   constraint PK_LP_WFS_FLOWVERSION primary key (id)
);

comment on table lp_wfs_flowversion is
'流程版本表';

comment on column lp_wfs_flowversion.id is
'主键';

comment on column lp_wfs_flowversion.flowid is
'流程id';

comment on column lp_wfs_flowversion.versionnum is
'版本号';

comment on column lp_wfs_flowversion.openurl is
'待办地址';

comment on column lp_wfs_flowversion.userid is
'发布人id';

comment on column lp_wfs_flowversion.username is
'发布人';

comment on column lp_wfs_flowversion.starttime is
'启用时间';

comment on column lp_wfs_flowversion.endtime is
'结束时间';

comment on column lp_wfs_flowversion.saveversion is
'保存冲突版本';

comment on column lp_wfs_flowversion.addtime is
'添加时间';

/*==============================================================*/
/* Table: lp_wfs_folder                                         */
/*==============================================================*/
create table lp_wfs_folder  (
   id                   VARCHAR2(50)                    not null,
   foldername           VARCHAR2(200),
   pid                  VARCHAR2(50),
   addtime              DATE,
   constraint PK_LP_WFS_FOLDER primary key (id)
);

comment on table lp_wfs_folder is
'分组表';

comment on column lp_wfs_folder.id is
'主键';

comment on column lp_wfs_folder.foldername is
'分组名称';

comment on column lp_wfs_folder.pid is
'父分组';

comment on column lp_wfs_folder.addtime is
'添加时间';

/*==============================================================*/
/* Table: lp_wfs_param                                          */
/*==============================================================*/
create table lp_wfs_param  (
   id                   VARCHAR2(50),
   flowversionid        VARCHAR2(50),
   parambm              VARCHAR2(200),
   paramname            VARCHAR2(200),
   px                   NUMBER,
   addtime              DATE,
   constraint AK_KEY_1_LP_WFS_P unique (id)
);

comment on table lp_wfs_param is
'流程参数';

comment on column lp_wfs_param.id is
'主键';

comment on column lp_wfs_param.flowversionid is
'流程版本id';

comment on column lp_wfs_param.parambm is
'参数编码';

comment on column lp_wfs_param.paramname is
'参数名称';

comment on column lp_wfs_param.px is
'排序字段';

comment on column lp_wfs_param.addtime is
'添加时间';


--V1.3.1 版本改动
update lp_sys_dept set depttype='1' where id='-1';
