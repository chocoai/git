/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  caibenxiang
 * Created: 2018-1-14
 */
#include("/dagl/yhda.sql")

--变电站档案查询
#sql("findSubstationByParams")
    select 
        b.keyid,
        b.bdzbs,
        b.bdzbh,
        b.bdzmc,
        b.bdzdz,
        b.dydjdm,
        b.bdzyxzt,
        vw_yxzt.bdzyxztdm_mc,
        b.cqgsdm,
        b.gisid,
        b.gddwbm,
        to_char(b.cjsj, 'yyyy-mm-dd hh24:mi:ss') cjsj,
        to_char(b.czsj, 'yyyy-mm-dd hh24:mi:ss') czsj,
        b.xlxdbs, 
        b.gldwbm, 
        b.bdzlxdm,
        x.bdzlxdm_mc, 
        b.dydjdm,
        d.dydjdm_mc, 
        b.cjsj, 
        b.czsj
    from 
        dw_bdz b, vw_bdzlxdm x, vw_dydjdm d, 
        vw_bdzyxztdm vw_yxzt, o_org o
    where 
        b.bdzlxdm = x.bdzlxdm_bm(+) 
        and b.dydjdm = d.dydjdm_bm(+) 
        and b.BDZYXZT = vw_yxzt.bdzyxztdm_bm(+)
        and o.org_no = b.gddwbm(+)
    #if(strKit.notBlank(gddwbm))
        AND EXISTS (SELECT 1 FROM XT_GDDWGX oo WHERE oo.ZGDDWBH = b.gddwbm AND oo.GDDWBH = #para(gddwbm))
    #end
    #if(strKit.notBlank(bdzbs))
        AND b.bdzbs = #para(bdzbs)
    #end
    #if(strKit.notBlank(bdzbh))
        AND b.bdzbh = #para(bdzbh)
    #end
    #if(strKit.notBlank(dydjdm))
        AND b.dydjdm = #para(dydjdm)
    #end
    order by 
        b.bdzbs
#end

--线路档案查询
#sql("findFeederLineByParam")
    select 
        g.gddwbm, 
        x.xlxdbs, 
        x.xlbh, 
        x.xlmc,
        bdz.bdzmc, 
        x.xllbdm, 
        x.xllxdm, 
        x.dydjdm, 
        x.yxztdm, 
        a.xllbdm_mc, 
        b.dydjdm_mc, 
        z.xlyxztdm_mc, 
        d.xllxdm_mc 
    from 
        dw_xlxd x, dw_xlgldw g, dw_bdz bdz,dw_bdzxlgx gx, 
        vw_xllbdm a, vw_dydjdm b, vw_xlyxztdm z, vw_xllxdm d 
    where 
        x.xlxdbs = g.xlxdbs 
        and x.xllbdm = a.xllbdm_bm(+) 
        and x.dydjdm = b.dydjdm_bm(+) 
        and x.yxztdm = z.xlyxztdm_bm(+) 
        and x.xllxdm = d.xllxdm_bm(+) 
        and x.xlxdbs = gx.xlxdbs(+) 
        and gx.bdzbs = bdz.bdzbs(+) 
    #if(strKit.notBlank(gddwbm))
        AND g.gddwbm = #para(gddwbm) 
    #end
    #if(strKit.notBlank(xllbdm))
        AND x.xllbdm = #para(xllbdm) 
    #end
    #if(strKit.notBlank(dydjdm))
        AND x.dydjdm = #para(dydjdm)
    #end
    #if(strKit.notBlank(bdzbs))
        AND gx.bdzbs = #para(bdzbs)
    #end
    order by 
        bdz.bdzmc, x.xlmc, x.xlbh
#end

--根据线路ID查询下挂用户信息
#sql("findConsListByLine")
    select 
	distinct k.yhbh, 
	k.yhbh, 
	k.yhmc, 
	k.yddz, 
	k.ydlbdm, 
	k.dydjdm, 
	k.hyfldm, 
	k.jlfsdm, 
	k.yhlbdm, 
	k.gddwbm, 
	k.htrl, 
	k.yxrl, 
	k.khfqbzdm, 
	j.xlxdbs, 
	a.yhlbdm_mc, 
	b.ydlbdm_mc, 
	h.hyfldm_mc, 
	d.dydjdm_mc
    from 
	kh_ydkh   k, 
	dw_xlxd   x, 
	kh_jld    j, 
	vw_yhlbdm a, 
	vw_ydlbdm b, 
	vw_hyfldm h, 
	vw_dydjdm d 
    where 
	k.yhbh = j.yhbh 
	and j.xlxdbs = x.xlxdbs 
	and k.yhlbdm = a.yhlbdm_bm(+) 
	and k.ydlbdm = b.ydlbdm_bm(+) 
	and k.hyfldm = h.hyfldm_bm(+) 
	and k.dydjdm = d.dydjdm_bm(+) 
	and k.yhlbdm in (#para(enumConsGXZBKH), #para(enumConsZXZBKH), #para(enumConsTQKHBH)) 
        and j.xlxdbs = #para(xlxdbs)
    order by k.yhbh 
#end

#sql("findTmnlByConsId")
    select 
        z.zdbs, 
        z.zddz, 
        z.zdlxdm, 
        nvl(x.zdlxdm_mc, '未知类型') as zdlxdm_mc, 
        z.yxztdm, 
        nvl(v.zdyxztdm_mc, '未知状态') as yxztdm_mc, 
        z.zdmc, 
        z.gybbh as txgydm, 
        nvl(g.txgydm_mc, '未知规约') as txgydm_mc, 
        z.zdlxsx, 
        decode(z.zdlxsx, 1, 'I型集中器', 2, 'II型集中器', '其他') as zdlxsx_mc, 
        z.gddwbm, 
        E.SFZX as SFZX,
        o.zzmc as gddwbm_mc, 
        c.zcbh, 
        c.sccjbs as zdsccjdm, 
        nvl(j.zdcj_mc, '未知厂商') as zdsccjdm_mc, 
        y.yhbh, 
        y.yhmc 
    from 
        sb_yxzd     z, 
        sb_zdzc     c, 
        sb_yhzdgx   k, 
        kh_ydkh     y, 
        cj_zdssgk   E,
        xt_zzjg     o, 
        vw_zdyxztdm v, 
        vw_zdlxdm   x, 
        vw_zdcj     j, 
        vw_txgydm   g 
    where 
        z.zdbs = k.zdbs(+) 
        and z.zdzcbs = c.zdzcbs(+) 
        and z.zdbs = k.zdbs(+) 
        and k.yhbh = y.yhbh(+) 
        and Z.ZDZCBH = E.ZDZCBH(+)
        and z.gddwbm = o.zzbmdm(+) 
        and z.yxztdm = v.zdyxztdm_bm(+) 
        and z.zdlxdm = x.zdlxdm_bm(+) 
        and z.gybbh = g.txgydm_bm(+) 
        and c.sccjbs = j.zdcj_bm(+) 
        and k.yhbh = #para(yhbh)
        and z.zdlxdm in (#para(enumTmnlLOAD_MANAGE_TMNL), #para(enumTmnlSUB_TMNL), #para(enumTmnlCONCENTRATOR))
#end

#sql("findMeterByTmnlId")
    select 
        z.dnbbs, 
        z.zdbs, 
        z.dnbzch, 
        d.zcmc, 
        z.txgy as txgydm, 
        nvl(g.dnbgy_mc, '未知规约') as txgydm_mc, 
        z.zhbl, 
        z.btl, 
        z.zfbbz, 
        z.ct, 
        z.pt, 
        d.sccjbs as sccjdm, 
        nvl(c.dbcj_mc, '未知厂商') as sccjdm_mc, 
        d.jxfsdm, 
        nvl(f.jxfsdm_mc, '未知接线方式') as jxfsdm_mc, 
        z.yxztdm statusCode, 
        nvl(v.sbyxztdm_mc, '未知状态') as yxztdm_mc
    from 
        kh_cldzb z, vw_dbcj c, vw_dnbgy g, vw_sbyxztdm v, sb_dnbzc d, vw_jxfsdm f 
    where 
        z.dnbbs = d.dnbzcbs 
        and d.sccjbs = c.dbcj_bm(+) 
        and d.jxfsdm = f.jxfsdm_bm(+) 
        and z.txgy = g.dnbgy_bm(+) 
        and z.yxztdm = v.sbyxztdm_bm(+) 
        and z.cldlx = #para(cldlx)
        and z.zdbs = #para(zdbs)
#end

#sql("findCollectorsByZdbs")
    select 
        z.cjqbs, 
        z.cjqbh, 
        z.zdbs, 
        z.zdljdz, 
        z.tqbh, 
        z.sccjbs,
        z.txgydm, 
        z.gddwbm, 
        z.zcbh, 
        z.cjqmc 
    from 
        sb_yxcjq z 
    where 
        z.zdbs = #para(0)
    order by 
        z.cjqbs
#end

#sql("findMeteringPointBySub")
    select 
        j.jldbh, 
        'a' BBZ,
        ZB.CLDH, 
        nvl(j.yjldmc,j.jldmc) jldmc, 
        j.jlfsdm, 
        j.jldydjdm as dydjdm, 
        j.yhbh, 
        j.dyzh, 
        j.dybh, 
        j.kgbh, 
        j.jlzzfldm, 
        j.ydlbdm, 
        j.jldztdm, 
        j.ptbbdm, 
        j.ctbbdm, 
        j.hyfldm, 
        j.fsjfbz, 
        j.gddwbm, 
        j.jldlbdm, 
        j.jldlxdm, 
        j.jldytdm, 
        j.cjsj, 
        d.dydjdm_mc, 
        y.ydlbdm_mc, 
        x.jldlx_mc as jldlxdm_mc
    from 
        kh_jld j, kh_jldbdzgx gx, KH_CLDZB ZB, vw_jldlx x, vw_dydjdm d, vw_ydlbdm y 
    where 
        j.jldbh = gx.jldbh 
        and j.Pointid = ZB.ID(+)  
        and j.jldydjdm = d.dydjdm_bm(+) 
        and j.ydlbdm = y.ydlbdm_bm(+) 
        and j.jldlxdm = x.jldlx_bm(+) 
        and gx.bdzbs = #para(bdzbs)
#end

#sql("findMainTransformerBySub")
    select 
        b.keyid, 
        b.yxbyqbs, 
        b.byqzcbh, 
        b.byqmc, 
        b.yhbh, 
        b.tqbs, 
        b.gddwbm, 
        b.sblxdm, 
        b.sbxhdm, 
        b.edrl, 
        b.yxztdm, 
        b.gbzbbz, 
        b.gisid, 
        b.gpswd, 
        b.gpsjd, 
        b.cjsj, 
        b.czsj, 
        b.cjmc, 
        b.ccbh, 
        b.dyzh, 
        b.lqfsdm, 
        b.gyeddy, 
        b.zyeddy, 
        b.dyeddy, 
        b.dqzjxfs, 
        z.sbyxztdm_mc as yxztdm_mc, 
        g.gbzbbz_mc, 
        x.byqlx_mc as sblxdm_mc 
    from 
        dw_yxbyq b, vw_sbyxztdm z, vw_gbzbbz g, vw_byqlx x 
    where 
        b.yxztdm = z.sbyxztdm_bm(+) 
        and b.gbzbbz = g.gbzbbz_bm(+) 
        and b.sblxdm = x.byqlx_bm(+) 
        and b.sblxdm = '3' 
        and b.bdzbs = #para(bdzbs)
#end

#sql("findPowerPlantByParams")
    select 
	t.keyid, 
	t.dcbh, 
	t.gdnylxdm as gddylxdm, 
	t.dcmc, 
	t.dcdz, 
	t.dyydkhbh, 
	t.hddcbz, 
	t.ddgxdm, 
	t.zjzrl, 
	t.jsgx, 
	t.dydjdm, 
	t.yxztdm, 
	t.gddwbm, 
	t.gldwbm, 
	t.dcxz, 
	t.jzts, 
	t.cjsj, 
	t.czsj, 
	t.tcsj,  
	g.gddylxdm_mc , 
	y.dcyxztdm_mc as yxztdm_mc, 
	d.dydjdm_mc 
    from 
	dw_dc t, 
        vw_gddylxdm g, 
        vw_dcyxztdm y, 
        vw_dydjdm d 
    where 
	t.gdnylxdm = g.gddylxdm_bm(+) 
        and t.dydjdm = d.dydjdm_bm(+) 
        and t.yxztdm = y.dcyxztdm_bm(+) 
    #if(strKit.notBlank(gddwbm))
        AND EXISTS (SELECT 1 FROM XT_GDDWGX oo WHERE oo.ZGDDWBH = t.gddwbm AND oo.GDDWBH = #para(gddwbm))
    #end
    #if(strKit.notBlank(dydjdm))
        and t.dydjdm = #para(dydjdm)
    #end
    order by 
        t.dcbh
#end