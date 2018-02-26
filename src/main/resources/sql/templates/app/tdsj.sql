/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Administrator
 * Created: 2018-2-1
 */
--3.2.5.1 停电事件查询.
#sql("StopElectTimequery")
select
  tdsj.DQBM as gljg,
  (select zzmc from XT_ZZJG where ZZJGBM = zzjg.SJZZBM) as sjgljg,
  ydkh.YHMC as yhmc,  
  tdsj.YHBH as yhbh, 
  #decode('byq.GBZBBZ') as yhxz, 
  #decode('ydkh.DQTZDM') as dqtz, 
  to_char(tdsj.TDKSSJ, 'yyyy-mm-dd hh24:mi:ss') as TDKSSJ, 
  to_char(tdsj.TDJSSJ, 'yyyy-mm-dd hh24:mi:ss') as TDJSSJ, 
  tdsj.TDSC, 
  xl.XLMC as ssxl, 
  decode(tdsj.TDLY, '1', '停电任务', '2', '双边停电', '3', '负荷数据', '4', '单边停电', '5', '单边复电') as sjly,
  decode(tdsj.TDFXDM, '1', '有效', '2', '误报', '3', '不确定') as tdfx
from
  TD_YHTDSJXX tdsj, 
  (select YHBH, YXBYQBS, XLXDBS, BDZBS, ZZJGBM from SV_TPGX where DXLB in ('80', '82')) v_sv,  --对象类别为 大客户、公变台区，
  KH_YDKH ydkh, 
  DW_YXBYQ byq, 
  DW_XLXD xl, 
  DW_BDZ bdz, 
  XT_ZZJG zzjg
where
  tdsj.YHBH = v_sv.YHBH(+)
  and tdsj.YHBH = ydkh.YHBH(+)
  and v_sv.YXBYQBS = byq.YXBYQBS(+)
  and v_sv.XLXDBS = xl.XLXDBS(+)
  and v_sv.BDZBS = bdz.BDZBS(+)
  and v_sv.ZZJGBM = zzjg.ZZJGBM(+)
#if(strKit.notBlank(tdkssj))
        AND tdsj.tdkssj = #para(tdkssj)
    #end
#if(strKit.notBlank(tdjssj))
        AND tdsj.tdjssj = #para(tdjssj)
    #end
#if(strKit.notBlank(xlmc))
        AND xl.tdjssj = #para(xlmc)
    #end
#if(strKit.notBlank(dqtzdm))
        AND ydkh.dqtzdm = #para(dqtzdm)
    #end
#if(strKit.notBlank(yhmc))
        AND ydkh.yhmc = #para(yhmc)
    #end
#if(strKit.notBlank(yhbh))
        AND tdsj.yhbh = #para(yhbh)
    #end
#if(strKit.notBlank(gbzbbz))
        AND byq.gbzbbz = #para(gbzbbz)
    #end
#if(strKit.notBlank(tdly))
        AND tdsj.tdly = #para(tdly)
    #end
#if(strKit.notBlank(tdfxdm))
        AND tdsj.tdfxdm = #para(tdfxdm)
    #end
#if(strKit.notBlank(tdsc))
        AND tdsj.tdsc = #para(tdsc)
    #end
#if(strKit.notBlank(sddsyxdm))
        AND tdsj.sddsyxdm = #para(sddsyxdm)
    #end
#end
  --以下为 查询条件，选填
--   and tdsj.TDKSSJ >= to_date(?, 'yyyy-mm-dd')  --停电开始时间
--   and tdsj.TDKSSJ <= to_date(?, 'yyyy-mm-dd')  --停电结束时间
--   and xl.XLMC like '%?%'  --线路名称
--   and ydkh.DQTZDM = ?  --地区特征， 下拉
--   and ydkh.YHMC like '%?%'  --用户名称
--   and tdsj.YHBH = ?  --用户编号
--   and byq.GBZBBZ = ?  --用户性质，下拉
--   and tdsj.TDLY = ?  --数据来源，下拉
--   and tdsj.TDFXDM = ?  --停电分析，下拉
--   and tdsj.TDSC [=、>、>=、<、<=] ? --需要拼接，停电时长
--   and tdsj.SFFSYXDM = ?  --是否发送营销。下拉，1是；0否
