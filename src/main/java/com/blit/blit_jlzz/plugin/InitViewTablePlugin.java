/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.plugin;

import cn.hutool.core.util.StrUtil;
import com.blit.blit_jlzz.dao.ViewTableDao;
import com.jfinal.aop.Duang;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.IPlugin;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * 初始化视图插件
 *
 * @author caibenxiang
 */
public class InitViewTablePlugin implements IPlugin {

    private static Map<String, List<Map<String, Object>>> allViewTableList;

    private final ViewTableDao viewTable = Duang.duang(ViewTableDao.class);

    @Override
    public boolean start() {
        LogKit.info("----------------视图加载开始！-----------------");
        allViewTableList = viewTable.findAllViewTableList();
        LogKit.info("----------------视图加载结束！-----------------");
        return true;
    }

    @Override
    public boolean stop() {
        return true;
    }
    
    /**
     * 
     * @param viewName 视图名称
     * @param findKeyName 查找视图键名称
     * @param findKeyValue 查找视图键值
     * @param displayColumn 视图使用字段名称
     * @return 
     */
    public static String findValueFromMap(String viewName, String findKeyName, String findKeyValue, String displayColumn) {

        if (StrUtil.isBlank(findKeyValue)) {
            return null;
        }
        try {
            return allViewTableList.get(viewName).stream().filter(
                    map -> StrUtil.equalsIgnoreCase(map.get(findKeyName).toString(), findKeyValue)
            ).findFirst().get().get(displayColumn).toString();
        } catch (NullPointerException | NoSuchElementException ex) {
            return null;
        }

    }

}
