/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.service.system;

import com.blit.blit_jlzz.model.system.UserSpec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 系统用户服务类
 *
 * @author caibenxiang
 */
public class UserService {

    private final ObjectMapper mapper = Duang.duang(ObjectMapper.class);

    /**
     * 根据登录用户账号查询对应的用户信息
     *
     * @param staffNo
     * @return
     */
    public UserSpec findByStaffNo(String staffNo) {
        Record record = Db.use("jlzz").findFirst(Db.getSqlPara("system.findSystemUser", staffNo));
        try {
            return mapper.readValue(record.toJson(), UserSpec.class);
        } catch (IOException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
