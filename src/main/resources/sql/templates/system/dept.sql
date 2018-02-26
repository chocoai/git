/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  caibenxiang
 * Created: 2018-1-18
 */
#sql("findOrgById")
    select o.ZZBMDM org_no, o.SJZZBS p_org_no, o.ZZLXDM org_type, o.ZZMC org_name from XT_ZZJG o where o.ZZBMDM = #para(0)
#end

#sql("findOrgListById")
    select 
        o.ZZBMDM org_no, o.SJZZBS p_org_no, o.ZZLXDM org_type, o.ZZMC org_name 
    from 
        XT_ZZJG o 
    where 
        o.SJZZBS = #para(orgNo)
        AND EXISTS ( SELECT 1 FROM QX_FWYDDW PRI_ORG WHERE PRI_ORG.GDDWBH = o.ZZBMDM AND PRI_ORG.CZYBH = #para(czybh)) 
    order by 
        o.ZZBMDM asc
#end

