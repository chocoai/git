/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  caibenxiang
 * Created: 2018-1-17
 */

#sql("listYhlxxx")
    select yhbh, lxr, yddh from KH_LXXX WHERE YHBH = #para(0)
#end

#sql("listYhda")
    SELECT 
        KH.YHBH, 
        KH.YHMC, 
        KH.YDDZ, 
        KH.CBQDBH, 
        KH.HTRL, 
        to_char(KH.XHRQ, 'yyyy-mm-dd') XHRQ, 
        to_char(KH.CJSJ, 'yyyy-mm-dd') CJSJ, 
        to_char(KH.CZSJ, 'yyyy-mm-dd') CZSJ, 
        KH.FHXZDM AS FHXZDM, 
        KH.GHNHYLBDM AS GHNHYLBDM, 
        KH.YFFLXDM AS YFFLXDM, 
        KH.DYDJDM AS DYDJDM, 
        KH.YDLBDM AS YDLBDM, 
        KH.FFMSDM AS FFMSDM, 
        KH.JLFSDM AS JLFSDM, 
        KH.YHZTDM AS YHZTDM, 
        KH.YHLBDM AS YHLBDM, 
        KH.FKMSDM AS FKMSDM_MC, 
        KH.KHFQBZDM AS KHFQBZDM, 
        KH.HYFLDM AS HYFLDM, 
        ZZ.ZZMC AS GDDWBM
    FROM 
        KH_YDKH KH, XT_ZZJG ZZ 
    WHERE 
        KH.GDDWBM = ZZ.ZZBMDM(+) 
    #if(strKit.notBlank(nodeValue))
        AND EXISTS (SELECT 1 FROM XT_GDDWGX OORELA WHERE OORELA.ZGDDWBH = ZZ.ZZBMDM AND OORELA.GDDWBH = #para(nodeValue))
    #end
    #if(strKit.notBlank(yhmcValue))
        and KH.YHMC like '%'||#para(yhmcValue)||'%' 
    #end
    #if(strKit.notBlank(yhbhValue))
        and KH.YHBH like '%'||#para(yhbhValue)||'%' 
    #end
    #if(strKit.notBlank(yhlxType))
        AND KH.YHLBDM = #para(yhlxType)
    #end
    #if(strKit.notBlank(yhztType))
        AND KH.YHZTDM = #para(yhztType)
    #end
    #if(strKit.notBlank(dydjType))
        AND KH.DYDJDM = #para(dydjType)
    #end
#end