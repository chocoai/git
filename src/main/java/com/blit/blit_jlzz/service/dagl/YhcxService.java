/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.service.dagl;

import cn.hutool.core.util.StrUtil;
import com.blit.blit_jlzz.model.dagl.Yhda;
import com.blit.blit_jlzz.model.dagl.YhdaLxxxSpec;
import com.blit.blit_jlzz.model.dagl.YhdaSpec;
import com.blit.blit_jlzz.plugin.InitViewTablePlugin;
import com.blit.blit_jlzz.utils.ConvertUtils;
import com.blit.blit_jlzz.utils.WrapperResponseUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfinal.aop.Duang;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author caibenxiang
 */
public class YhcxService {

    private final ObjectMapper mapper = Duang.duang(ObjectMapper.class);

    /**
     * 查询用户档案列表
     *
     * @param kv 传入参数MAP
     * @param start 查询起始页，从1开始
     * @param limit 查询分页行数
     * @return
     */
    public String listYHDA(Kv kv, int start, int limit) {

        Yhda yhda = new Yhda();

        SqlPara sqlPara = Db.getSqlPara("dagl.listYhda", kv);

        try {
            Page<Record> page = Db.use("jlzz").paginate(start, limit, sqlPara);

            yhda.setMetadata(WrapperResponseUtils.wrapperMeta(page));
            yhda.setResponse(ConvertUtils.convertPageRecordToList(page.getList(), YhdaSpec.class));

            yhda.getResponse().forEach(spec -> {
                List<Record> record = Db.use("jlzz").find(Db.getSqlPara("dagl.listYhlxxx", spec.getYhbh()));
                if (!record.isEmpty()) {
                    spec.setLxxx(ConvertUtils.convertPageRecordToList(record, YhdaLxxxSpec.class));
                }
            });

            yhda.getResponse().forEach(spec -> {

                if (spec.getLxxx() != null && !spec.getLxxx().isEmpty()) {
                    String lxr = "";
                    String lxdh = "";
                    for (YhdaLxxxSpec lxxxSpec : spec.getLxxx()) {
                        lxr += StrUtil.isBlank(lxxxSpec.getLxr()) ? "" : lxxxSpec.getLxr() + ", ";
                        lxdh += StrUtil.isBlank(lxxxSpec.getYddh()) ? "" : lxxxSpec.getYddh() + ", ";
                    }
                    if (StrUtil.endWith(lxr, ", ")) {
                        spec.setLxr(StrUtil.sub(lxr, 0, lxr.length() - 2));
                    }
                    if (StrUtil.endWith(lxdh, ", ")) {
                        spec.setLxdh(StrUtil.sub(lxdh, 0, lxdh.length() - 2));
                    }
                }
                spec.setYdlbdm(InitViewTablePlugin.findValueFromMap("VW_YDLBDM", "YDLBDM_BM", spec.getYdlbdm(), "YDLBDM_MC"));
                spec.setDydjdm(InitViewTablePlugin.findValueFromMap("VW_DYDJDM", "DYDJDM_BM", spec.getDydjdm(), "DYDJDM_MC"));
                spec.setHyfldm(InitViewTablePlugin.findValueFromMap("VW_HYFLDM", "HYFLDM_BM", spec.getHyfldm(), "HYFLDM_MC"));
                spec.setJlfsdm(InitViewTablePlugin.findValueFromMap("VW_JLFSDM", "JLFSDM_BM", spec.getJlfsdm(), "JLFSDM_MC"));
                spec.setYhlbdm(InitViewTablePlugin.findValueFromMap("VW_YHLBDM", "YHLBDM_BM", spec.getYhlbdm(), "YHLBDM_MC"));
                spec.setFhxzdm(InitViewTablePlugin.findValueFromMap("VW_FHXZDM", "FHXZDM_BM", spec.getFhxzdm(), "FHXZDM_MC"));
                spec.setGhnhylbdm(InitViewTablePlugin.findValueFromMap("VW_GHNHYLBDM", "GHNHYLBDM_BM", spec.getGhnhylbdm(), "GHNHYLBDM_MC"));
                spec.setYhztdm(InitViewTablePlugin.findValueFromMap("VW_YHZTDM", "YHZTDM_BM", spec.getYhztdm(), "YHZTDM_MC"));
                spec.setYfflxdm(InitViewTablePlugin.findValueFromMap("VW_YFFLXDM", "YFFLXDM_BM", spec.getYfflxdm(), "YFFLXDM_MC"));
                spec.setFkmsdm_mc(InitViewTablePlugin.findValueFromMap("VW_FKMSDM", "FKMSDM_BM", spec.getFkmsdm_mc(), "FKMSDM_MC"));
                //spec.setJlfsdm(InitViewTablePlugin.findValueFromMap("VW_FKMSDM", "FKMSDM_BM", spec.getYdlbdm(), "FKMSDM_MC"));
                spec.setKhfqbzdm(InitViewTablePlugin.findValueFromMap("VW_KHFQBZDM", "KHFQBZDM_BM", spec.getKhfqbzdm(), "KHFQBZDM_MC"));

            });

            try {
                return mapper.writeValueAsString(yhda);
            } catch (JsonProcessingException ex) {
                Logger.getLogger(YhcxService.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
            
        } catch (ActiveRecordException ex) {
            throw new ActiveRecordException(sqlPara.getSql(), ex);
        }
    }
}
