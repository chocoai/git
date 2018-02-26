/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  caibenxiang
 * Created: 2018-1-22
 */

INSERT INTO LP_SYS_DEPT (
    ID, PID, DEPTCODE, DEPTNAME, DEPTTYPE, PX, ADDTIME
) VALUES (
    '403570797135790080', '-1', '0410', '贵港供电局', '1', null, TIMESTAMP '2018-01-18 15:26:16'
);

INSERT INTO LP_SYS_USER (
    ID, DEPTID, USERNUM, USERNAME, PWD, 
    MANAGEDEPTID, PHONE, STATUS, ISSUPERADMIN, PX, 
    ADDTIME, DWID, BMID
) VALUES (
    'e580465cca444c3fba17b4cf5fe86e3c', '403570797135790080', 'cai_bx', '蔡本祥', '78becbe4454a95a15ea9abcda568a962', 
    null, '18677075959', '1', null, null, 
    TIMESTAMP '2018-01-18 15:49:39', '403570797135790080', null
);
