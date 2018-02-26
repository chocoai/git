/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.dao;

import com.jfinal.plugin.activerecord.Db;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化视图
 *
 * @author caibenxiang
 */
public class ViewTableDao {

    public Map<String, List<Map<String, Object>>> findAllViewTableList() {

        Map<String, List<Map<String, Object>>> allViewTableList = new HashMap<>();

        //负荷性质代码
        String VW_FHXZDM = "SELECT FHXZDM_BM, FHXZDM_MC FROM VW_FHXZDM";
        allViewTableList.put("VW_FHXZDM", queryView(VW_FHXZDM));

        //高耗能用电行业的分类
        String VW_GHNHYLBDM = "SELECT GHNHYLBDM_BM, GHNHYLBDM_MC FROM VW_GHNHYLBDM";
        allViewTableList.put("VW_GHNHYLBDM", queryView(VW_GHNHYLBDM));

        //预付费类型代码
        String VW_YFFLXDM = "SELECT YFFLXDM_BM, YFFLXDM_MC FROM VW_YFFLXDM";
        allViewTableList.put("VW_YFFLXDM", queryView(VW_YFFLXDM));

        //电压等级代码
        String VW_DYDJDM = "SELECT DYDJDM_BM, DYDJDM_MC FROM VW_DYDJDM";
        allViewTableList.put("VW_DYDJDM", queryView(VW_DYDJDM));

        //用电类别
        String VW_YDLBDM = "SELECT YDLBDM_BM, YDLBDM_MC FROM VW_YDLBDM";
        allViewTableList.put("VW_YDLBDM", queryView(VW_YDLBDM));

        //付费模式代码
        String VW_FFMSDM = "SELECT FFMSDM_BM, FFMSDM_MC FROM VW_FFMSDM";
        allViewTableList.put("VW_FFMSDM", queryView(VW_FFMSDM));

        //计量方式代码
        String VW_JLFSDM = "SELECT JLFSDM_BM, JLFSDM_MC FROM VW_JLFSDM";
        allViewTableList.put("VW_JLFSDM", queryView(VW_JLFSDM));

        //用户类别代码
        String VW_YHLBDM = "SELECT YHLBDM_BM, YHLBDM_MC FROM VW_YHLBDM";
        allViewTableList.put("VW_YHLBDM", queryView(VW_YHLBDM));

        //用户状态
        String VW_YHZTDM = "SELECT YHZTDM_BM, YHZTDM_MC FROM VW_YHZTDM";
        allViewTableList.put("VW_YHZTDM", queryView(VW_YHZTDM));

        //费控模式代码
        String VW_FKMSDM = "SELECT FKMSDM_BM, FKMSDM_MC FROM VW_FKMSDM";
        allViewTableList.put("VW_FKMSDM", queryView(VW_FKMSDM));

        //客户分群标志代码
        String VW_KHFQBZDM = "SELECT KHFQBZDM_BM, KHFQBZDM_MC FROM VW_KHFQBZDM";
        allViewTableList.put("VW_KHFQBZDM", queryView(VW_KHFQBZDM));

        //行业分类
        String VW_HYFLDM = ""
                + "SELECT \n"
                + "  HYFLDM_BM, \n"
                + "  SUBSTR((SELECT HYFLDM_MC FROM VW_HYFLDM WHERE HYFLDM_SJBM = 'Z0' START WITH HYFLDM_BM = DM.HYFLDM_BM CONNECT BY PRIOR HYFLDM_SJBM = HYFLDM_BM ), 3) AS HYFLDM_MC \n"
                + "FROM VW_HYFLDM DM";
        allViewTableList.put("VW_HYFLDM", queryView(VW_HYFLDM));

        //线路运行状态代码
        String VW_XLYXZTDM = "SELECT XLYXZTDM_BM, XLYXZTDM_MC FROM VW_XLYXZTDM";
        allViewTableList.put("VW_XLYXZTDM", queryView(VW_XLYXZTDM));

        //线路类别代码，{1馈线;2联络线;3母线;4站用线;5励磁变;6启备变;7电容;8电抗}
        String VW_XLLBDM = "SELECT XLLBDM_BM, XLLBDM_MC FROM VW_XLLBDM";
        allViewTableList.put("VW_XLLBDM", queryView(VW_XLLBDM));

        //计量点类型
        String VW_JLDLX = "SELECT JLDLX_BM, JLDLX_MC FROM VW_JLDLX";
        allViewTableList.put("VW_JLDLX", queryView(VW_JLDLX));

        //计量装置分类代码
        String VW_JLZZFLDM = "SELECT JLZZFLDM_BM, JLZZFLDM_MC FROM VW_JLZZFLDM";
        allViewTableList.put("VW_JLZZFLDM", queryView(VW_JLZZFLDM));

        //计量点状态，包括：1在用、0停用
        String VW_JLDZTDM = "SELECT JLDZTDM_BM, JLDZTDM_MC FROM VW_JLDZTDM";
        allViewTableList.put("VW_JLDZTDM", queryView(VW_JLDZTDM));

        //PT变比代码，详见VW_PT
        String VW_PT = "SELECT PTBBBM, PTBBSZ, DMBMZ FROM VW_PT";
        allViewTableList.put("VW_PT", queryView(VW_PT));

        //CT变比代码，详见VW_CT
        String VW_CT = "SELECT CTBBBM, CTBBSZ, DMBMZ FROM VW_CT";
        allViewTableList.put("VW_CT", queryView(VW_CT));

        //上网电量计量方向
        String VW_SWDLJLFX = "SELECT SWDLJLFX_BM, SWDLJLFX_MC FROM VW_SWDLJLFX";
        allViewTableList.put("VW_SWDLJLFX", queryView(VW_SWDLJLFX));

        //接线方式代码,具体含义详见视图：VW_JXFSDM
        String VW_JXFSDM = "SELECT JXFSDM_BM, JXFSDM_MC FROM VW_JXFSDM";
        allViewTableList.put("VW_JXFSDM", queryView(VW_JXFSDM));

        //通讯方式
        String VW_TXFSDM = "SELECT TXFSDM_BM, TXFSDM_MC FROM VW_TXFSDM";
        allViewTableList.put("VW_TXFSDM", queryView(VW_TXFSDM));

        //电能表型号
        String VW_DNBXH = "SELECT DNBXH_BM, DNBXH_MC FROM VW_DNBXH";
        allViewTableList.put("VW_DNBXH", queryView(VW_DNBXH));

        //通讯规约
        String VW_DNBGY = "SELECT DNBGY_BM, DNBGY_MC FROM VW_DNBGY";
        allViewTableList.put("VW_DNBGY", queryView(VW_DNBGY));

        //表码位数
        String VW_BMWSDM = "SELECT BMWSDM_BM, BMWSDM_MC FROM VW_BMWSDM";
        allViewTableList.put("VW_BMWSDM", queryView(VW_BMWSDM));

        //电能表波特率
        String VW_BTL = "SELECT BTL_BM, BTL_MC FROM VW_BTL";
        allViewTableList.put("VW_BTL", queryView(VW_BTL));

        //电能表运行状态代码
        String VW_DNBYXZTDM = "SELECT DNBYXZTDM_BM, DNBYXZTDM_MC FROM VW_DNBYXZTDM";
        allViewTableList.put("VW_DNBYXZTDM", queryView(VW_DNBYXZTDM));

        //受电容量等级
        String VW_RLDJ = "SELECT RLDJ_BM, RLDJ_MC, RLDJ_ZXZ, RLDJ_ZDZ FROM VW_RLDJ";
        allViewTableList.put("VW_RLDJ", queryView(VW_RLDJ));

        return allViewTableList;
    }

    private List<Map<String, Object>> queryView(String sql) {
        List<Map<String, Object>> list = new ArrayList<>();
        Db.use("jlzz").find(sql).iterator().forEachRemaining(action -> list.add(action.getColumns()));
        return list;
    }
}
