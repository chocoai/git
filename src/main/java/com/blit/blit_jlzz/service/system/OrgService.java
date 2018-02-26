/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.service.system;

import com.blit.blit_jlzz.model.system.OrgSpec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfinal.aop.Duang;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 组织服务类
 *
 * @author caibenxiang
 */
public class OrgService {

    private final ObjectMapper mapper = Duang.duang(ObjectMapper.class);

    /**
     * 根据组织机构ID查询组织信息
     *
     * @param orgNo 单位编码
     * @return
     */
    public OrgSpec findOrgById(String orgNo) {
        Record record = Db.use("jlzz").findFirst(Db.getSqlPara("system.findOrgById", orgNo));
        try {
            return mapper.readValue(record.toJson(), OrgSpec.class);
        } catch (IOException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * 根据组织机构ID查询组织信息
     *
     * @param orgNo 单位编码
     * @param czybh 登录用户账号
     * @return
     */
    public List<OrgSpec> findOrgListById(String orgNo, String czybh) {

        Kv kv = Kv.create()
                .set("orgNo", orgNo)
                .set("czybh", czybh);
        List<Record> records = Db.use("jlzz").find(Db.getSqlPara("system.findOrgListById", kv));

        List<OrgSpec> listOrg = new ArrayList<>();
        records.forEach(record -> {
            try {
                listOrg.add(mapper.readValue(record.toJson(), OrgSpec.class));
            } catch (IOException ex) {
                Logger.getLogger(OrgService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        return listOrg;
    }
}
