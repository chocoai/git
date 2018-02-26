
drop table lp_sys_attach;

drop table lp_sys_dept;

drop table lp_sys_dict;

drop table lp_sys_dictsort;

drop table lp_sys_log;

drop table lp_sys_menu;

drop table lp_sys_order;

drop table lp_sys_role;

drop table lp_sys_rolemenu;

drop table lp_sys_task;

drop table lp_sys_user;

drop table lp_sys_userex;

drop table lp_sys_userrole;

/*==============================================================*/
/* Table: lp_sys_attach                                         */
/*==============================================================*/
create table lp_sys_attach (
   id                   VARCHAR(50)          not null,
   filename             VARCHAR(200)         null,
   filesize             NUMERIC              null,
   savepath             VARCHAR(50)          null,
   tablename            VARCHAR(200)         null,
   field                VARCHAR(200)         null,
   objid                VARCHAR(200)         null,
   addtime              TIMESTAMP            null,
   constraint PK_LP_SYS_ATTACH primary key (id)
);

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
create table lp_sys_dept (
   id                   VARCHAR(50)          not null,
   pid                  VARCHAR(50)          null,
   deptcode             VARCHAR(200)         null,
   deptname             VARCHAR(200)         null,
   depttype             VARCHAR(200)         null,
   px                   NUMERIC              null,
   addtime              TIMESTAMP            null,
   constraint PK_LP_SYS_DEPT primary key (id)
);

/*==============================================================*/
/* Table: lp_sys_dict                                           */
/*==============================================================*/
create table lp_sys_dict (
   id                   VARCHAR(50)          not null,
   pid                  VARCHAR(50)          null,
   code                 VARCHAR(200)         null,
   val                  VARCHAR(200)         null,
   sortcode             VARCHAR(200)         null,
   px                   NUMERIC              null,
   addtime              TIMESTAMP            null,
   constraint PK_LP_SYS_DICT primary key (id)
);

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
create table lp_sys_dictsort (
   id                   VARCHAR(50)          not null,
   sortcode             VARCHAR(200)         null,
   sortname             VARCHAR(200)         null,
   note                 VARCHAR(500)         null,
   px                   NUMERIC              null,
   addtime              TIMESTAMP            null,
   constraint PK_LP_SYS_DICTSORT primary key (id)
);

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
create table lp_sys_log (
   id                   VARCHAR(50)          not null,
   logtype              VARCHAR(200)         null,
   info                 VARCHAR(2000)        null,
   ip                   VARCHAR(200)         null,
   userid               VARCHAR(50)          null,
   username             VARCHAR(200)         null,
   addtime              TIMESTAMP            null,
   status              	VARCHAR(200)         null,
   constraint PK_LP_SYS_LOG primary key (id)
);

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
create table lp_sys_menu (
   id                   VARCHAR(50)          not null,
   pid                  VARCHAR(50)          null,
   menuname             VARCHAR(200)         null,
   url                  VARCHAR(200)         null,
   openway              VARCHAR(200)         null,
   px                   NUMERIC              null,
   addtime              TIMESTAMP            null,
   constraint PK_LP_SYS_MENU primary key (id)
);

/*==============================================================*/
/* Table: lp_sys_order                                          */
/*==============================================================*/
create table lp_sys_order (
   tablename            VARCHAR(50)          not null,
   val                  NUMERIC              null,
   constraint PK_LP_SYS_ORDER primary key (tablename)
);

comment on column lp_sys_order.tablename is
'表名';

comment on column lp_sys_order.val is
'当前值';

/*==============================================================*/
/* Table: lp_sys_role                                           */
/*==============================================================*/
create table lp_sys_role (
   id                   VARCHAR(50)          not null,
   deptid               VARCHAR(50)          null,
   rolecode             VARCHAR(200)         null,
   rolename             VARCHAR(200)         null,
   px                   NUMERIC              null,
   addtime              TIMESTAMP            null,
   constraint PK_LP_SYS_ROLE primary key (id)
);

/*==============================================================*/
/* Table: lp_sys_rolemenu                                       */
/*==============================================================*/
create table lp_sys_rolemenu (
   id                   VARCHAR(50)          not null,
   roleid               VARCHAR(50)          null,
   menuid               VARCHAR(50)          null,
   constraint PK_LP_SYS_ROLEMENU primary key (id)
);

/*==============================================================*/
/* Table: lp_sys_task                                           */
/*==============================================================*/
create table lp_sys_task (
   id                   VARCHAR(50)          not null,
   taskname             VARCHAR(200)         null,
   exp                  VARCHAR(200)         null,
   class                VARCHAR(200)         null,
   status               VARCHAR(200)         null,
   note                 VARCHAR(200)         null,
   addtime              TIMESTAMP            null,
   constraint PK_LP_SYS_TASK primary key (id)
);

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
create table lp_sys_user (
   id                   VARCHAR(50)          not null,
   deptid               VARCHAR(50)          null,
   usernum              VARCHAR(200)         null,
   username             VARCHAR(200)         null,
   pwd                  VARCHAR(200)         null,
   managedeptid         VARCHAR(50)          null,
   phone                VARCHAR(200)         null,
   status               VARCHAR(50)          null,
   issuperadmin         VARCHAR(20)          null,
   px                   NUMERIC              null,
   addtime              TIMESTAMP            null,
   constraint PK_LP_SYS_USER primary key (id)
);

comment on column lp_sys_user.status is
'0：停用，1：启用';

/*==============================================================*/
/* Table: lp_sys_userex                                         */
/*==============================================================*/
create table lp_sys_userex (
   id                   VARCHAR(50)          not null,
   userid               VARCHAR(50)          null,
   key                  VARCHAR(200)         null,
   val                  VARCHAR(500)         null,
   px                   NUMERIC              null,
   addtime              TIMESTAMP            null,
   constraint PK_LP_SYS_USEREX primary key (id)
);

/*==============================================================*/
/* Table: lp_sys_userrole                                       */
/*==============================================================*/
create table lp_sys_userrole (
   id                   VARCHAR(50)          not null,
   userid               VARCHAR(50)          null,
   roleid               VARCHAR(50)          null,
   constraint PK_LP_SYS_USERROLE primary key (id)
);

--V1.2版本改动
alter table lp_sys_menu add iconcls varchar(200);
alter table lp_sys_menu add opensubmenuindex varchar(200);

alter table lp_sys_attach add fileext varchar(200);
alter table lp_sys_attach add userid varchar(200);
alter table lp_sys_attach add username varchar(200);

/*==============================================================*/
/* 初始化数据开始                                       		*/
/*==============================================================*/

INSERT INTO lp_sys_dept(
	id, pid, deptcode, deptname, depttype, px, addtime)
	VALUES ('-1', null, null, '组织机构', '单位', '0', now());
	
INSERT INTO lp_sys_user(
	id, deptid, usernum, username, pwd, managedeptid, phone, status, issuperadmin, px, addtime)
	VALUES ('0', '-1', 'admin', '管理员', '78becbe4454a95a15ea9abcda568a962', null, null, '1', '1', '0', now());
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime,iconcls)
	VALUES ('d9e006abcc2f489ba78a9969d1055b13','-1','系统管理',null,null,1,now(),'fa fa-cog');
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime,iconcls)
	VALUES ('acf5a89736714522938de480841a05dd','-1','系统安全',null,null,2,now(),'fa fa-window-restore');
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('13c3ffe4237e44d39f9021edc9769ed6','d9e006abcc2f489ba78a9969d1055b13','机构维护','/dept/index','属性页',1,now());

INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('065775d70a6f4ef994542c21c960d94b','d9e006abcc2f489ba78a9969d1055b13','用户维护','/user/index','属性页',2,now());
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('844f91c129034f6d9febee4b99fbca24','d9e006abcc2f489ba78a9969d1055b13','角色维护','/role/index','属性页',3,now());
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('8cb92e6271284155b9bcb6cd92e38098','d9e006abcc2f489ba78a9969d1055b13','菜单维护','/menu/index','属性页',4,now());
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('8889df7e4b724fb9bada47b0e2fa2888','d9e006abcc2f489ba78a9969d1055b13','定时任务','/task/index','属性页',5,now());
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('24ff1ae9748e40168d3f808b1f59e066','acf5a89736714522938de480841a05dd','系统日志','/sys/log/index','属性页',1,now());

INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('cb8f44a434d448b9990df23e524c6a98','acf5a89736714522938de480841a05dd','用户解锁','/sys/lockuser/index','属性页',2,now());
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('022a1692d2974e0a9584deed77706b73','acf5a89736714522938de480841a05dd','开发演示','/sys/demo/index','属性页',3,now());

drop table lp_test;
/*==============================================================*/
/* Table: lp_test                                               */
/*==============================================================*/
create table lp_test (
   id                   VARCHAR(50)          not null,
   txt1                 VARCHAR(200)         null,
   txt2                 VARCHAR(200)         null,
   txt3                 CHAR(200)            null,
   int1                 INT4                 null,
   int2                 INT4                 null,
   long1                VARCHAR(1)           null,
   num1                 NUMERIC              null,
   decimal1             NUMERIC              null,
   float1               FLOAT8               null,
   numeric1             NUMERIC              null,
   date1                TIMESTAMP                 null,
   ts1                  TIMESTAMP                 null,
   ts2                  TIMESTAMP                 null,
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

alter table lp_sys_menu add subsystem varchar(200);
alter table lp_sys_user add dwid varchar(50);
alter table lp_sys_user add bmid varchar(50);

INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('81c5492308d2475c9451af68e93bb5aa','d9e006abcc2f489ba78a9969d1055b13','数据字典','/sys/dict/index','属性页',6,now());
	
INSERT INTO lp_sys_menu(
	id, pid, menuname, url, openway, px, addtime)
	VALUES ('f9d7fa9cab44406d8d50018ee7872899','d9e006abcc2f489ba78a9969d1055b13','流程配置','/sys/flow/index','属性页',7,now());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('-1', '', '', '数据字典', '', null, now());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380374138117685248', '-1', '', 'LP系统', '', null, now());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380383611062321152', '380374138117685248', '', '机构类型', '', 1, now());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380383911647117312', '380383611062321152', '1', '单位', 'lp.depttype', 1, now());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380383911668088832', '380383611062321152', '2', '部门', 'lp.depttype', 2, now());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380383911693254656', '380383611062321152', '3', '班组', 'lp.depttype', 3, now());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380645045998452736', '380374138117685248', '', '日志类型', '', 2, now());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380645252433707008', '380645045998452736', '系统登录', '系统登录', 'lp.logtype', 1, now());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380645252450484224', '380645045998452736', '系统管理', '系统管理', 'lp.logtype', 2, now());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380645252463067136', '380645045998452736', '系统安全', '系统安全', 'lp.logtype', 3, now());

insert into lp_sys_dict (ID, PID, CODE, VAL, SORTCODE, PX, ADDTIME)
values ('380645252500815872', '380645045998452736', '业务应用', '业务应用', 'lp.logtype', 4, now());




drop table lp_wfi_flow;

drop index IDX_I_FLOWNODE_FLOWINSID;

drop table lp_wfi_flownode;

drop index IDX_I_OPERATOR_FLOWNODEINSID;

drop table lp_wfi_operator;

drop index IDX_I_FLOWPARAM_FLOWINSID;

drop table lp_wfi_param;

drop index IDX_I_RECEIVER_FLOWNODEINSID;

drop table lp_wfi_receiver;

drop table lp_wfi_waitdo;

drop table lp_wfs_condition;

drop table lp_wfs_event;

drop table lp_wfs_flow;

drop table lp_wfs_flownode;

drop table lp_wfs_flownodeext;

drop table lp_wfs_flowtrans;

drop table lp_wfs_flowtransext;

drop table lp_wfs_flowversion;

drop table lp_wfs_folder;

drop table lp_wfs_param;

/*==============================================================*/
/* Table: lp_wfi_flow                                           */
/*==============================================================*/
create table lp_wfi_flow (
   id                   VARCHAR(50)          not null,
   flowversionid        VARCHAR(200)         null,
   flowname             VARCHAR(200)         null,
   starttime            TIMESTAMP                 null,
   endtime              TIMESTAMP                 null,
   createuser           VARCHAR(200)         null,
   createuserid         VARCHAR(200)         null,
   runstatus            NUMERIC              null,
   saveversion          VARCHAR(50)          null,
   addtime              TIMESTAMP                 null,
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
create table lp_wfi_flownode (
   id                   VARCHAR(50)          not null,
   flowversionid        VARCHAR(50)          null,
   flowinstanceid       VARCHAR(50)          null,
   flownodeid           VARCHAR(50)          null,
   flownodename         VARCHAR(200)         null,
   nodetype             NUMERIC              null,
   runstatus            NUMERIC              null,
   dealstatus           NUMERIC              null,
   pids                 VARCHAR(500)         null,
   senderid             VARCHAR(200)         null,
   sender               VARCHAR(200)         null,
   begintime            TIMESTAMP                 null,
   endtime              TIMESTAMP                 null,
   note                 VARCHAR(500)         null,
   px                   NUMERIC              null,
   addtime              TIMESTAMP                 null,
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
create  index IDX_I_FLOWNODE_FLOWINSID on lp_wfi_flownode (
flowinstanceid
);

/*==============================================================*/
/* Table: lp_wfi_operator                                       */
/*==============================================================*/
create table lp_wfi_operator (
   id                   VARCHAR(50)          not null,
   flownodeinstanceid   VARCHAR(50)          null,
   operatortype         NUMERIC              null,
   operatorid           VARCHAR(50)          null,
   operator             VARCHAR(200)         null,
   addtime              TIMESTAMP                 null,
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
create  index IDX_I_OPERATOR_FLOWNODEINSID on lp_wfi_operator (
flownodeinstanceid
);

/*==============================================================*/
/* Table: lp_wfi_param                                          */
/*==============================================================*/
create table lp_wfi_param (
   id                   VARCHAR(50)          not null,
   flowinstanceid       VARCHAR(50)          null,
   parambm              VARCHAR(200)         null,
   val                  VARCHAR(200)         null,
   addtime              TIMESTAMP                 null,
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
create  index IDX_I_FLOWPARAM_FLOWINSID on lp_wfi_param (
flowinstanceid
);

/*==============================================================*/
/* Table: lp_wfi_receiver                                       */
/*==============================================================*/
create table lp_wfi_receiver (
   id                   VARCHAR(50)          not null,
   flownodeinstanceid   VARCHAR(50)          null,
   receivertype         NUMERIC              null,
   receiverid           VARCHAR(50)          null,
   receiver             VARCHAR(200)         null,
   addtime              TIMESTAMP                 null,
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
create  index IDX_I_RECEIVER_FLOWNODEINSID on lp_wfi_receiver (
flownodeinstanceid
);

/*==============================================================*/
/* Table: lp_wfi_waitdo                                         */
/*==============================================================*/
create table lp_wfi_waitdo (
   id                   VARCHAR(50)          not null,
   flowversionid        VARCHAR(50)          null,
   flowinstanceid       VARCHAR(50)          null,
   flownodeinstanceid   VARCHAR(50)          null,
   flownodeid           VARCHAR(50)          null,
   flownodename         VARCHAR(50)          null,
   senderid             VARCHAR(50)          null,
   sender               VARCHAR(50)          null,
   receivertype         NUMERIC              null,
   receiverid           VARCHAR(50)          null,
   receiver             VARCHAR(200)         null,
   openurl              VARCHAR(500)         null,
   addtime              TIMESTAMP                 null,
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
create table lp_wfs_condition (
   id                   VARCHAR(50)          null,
   flowversionid        VARCHAR(50)          null,
   conditionname        VARCHAR(200)         null,
   conditiontype        NUMERIC              null,
   comparetype          NUMERIC              null,
   leftval               VARCHAR(200)         null,
   rightval              VARCHAR(200)         null,
   px                   NUMERIC              null,
   addtime              TIMESTAMP                 null,
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
create table lp_wfs_event (
   id                   VARCHAR(50)          null,
   flowversionid        VARCHAR(50)          null,
   javaclass            VARCHAR(200)         null,
   px                   NUMERIC              null,
   addtime              TIMESTAMP                 null,
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
create table lp_wfs_flow (
   id                   VARCHAR(50)          not null,
   flowname             VARCHAR(200)         null,
   folderid             VARCHAR(50)          null,
   addtime              TIMESTAMP                 null,
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
create table lp_wfs_flownode (
   id                   VARCHAR(50)          not null,
   flowid               VARCHAR(50)          null,
   flowversionid        VARCHAR(50)          null,
   flownodename         VARCHAR(200)         null,
   nodetype             NUMERIC              null,
   embranch             NUMERIC              null,
   converge             NUMERIC              null,
   px                   NUMERIC              null,
   addtime              TIMESTAMP                 null,
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
create table lp_wfs_flownodeext (
   id                   VARCHAR(50)          not null,
   drawjson             VARCHAR(2000)        null,
   actorjson            VARCHAR(2000)        null,
   extjson           VARCHAR(2000)        null,
   addtime              TIMESTAMP                 null,
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
create table lp_wfs_flowtrans (
   id                   VARCHAR(50)          not null,
   flowversionid        VARCHAR(50)          null,
   fromnodeid           VARCHAR(50)          null,
   tonodeid             VARCHAR(50)          null,
   trantype             NUMERIC              null,
   allowuntread         NUMERIC              null,
   conditionid          VARCHAR(50)          null,
   px                   NUMERIC              null,
   addtime              TIMESTAMP                 null,
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
create table lp_wfs_flowtransext (
   id                   VARCHAR(50)          not null,
   drawjson             VARCHAR(2000)        null,
   addtime              TIMESTAMP                 null,
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
create table lp_wfs_flowversion (
   id                   VARCHAR(50)          not null,
   flowid               VARCHAR(200)         null,
   versionnum           NUMERIC              null,
   openurl              VARCHAR(500)         null,
   userid               VARCHAR(200)         null,
   username             VARCHAR(200)         null,
   starttime            TIMESTAMP                 null,
   endtime              TIMESTAMP                 null,
   saveversion          VARCHAR(50)          null,
   addtime              TIMESTAMP                 null,
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
create table lp_wfs_folder (
   id                   VARCHAR(50)          not null,
   foldername           VARCHAR(200)         null,
   pid                  VARCHAR(50)          null,
   addtime              TIMESTAMP                 null,
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
create table lp_wfs_param (
   id                   VARCHAR(50)          null,
   flowversionid        VARCHAR(50)          null,
   parambm              VARCHAR(200)         null,
   paramname            VARCHAR(200)         null,
   px                   NUMERIC              null,
   addtime              TIMESTAMP                 null,
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