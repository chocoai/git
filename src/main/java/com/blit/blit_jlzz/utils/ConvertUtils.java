/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Record;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author caibenxiang
 */
public class ConvertUtils {

    public final static ObjectMapper MAPPER = new ObjectMapper(); 

    public static <T> List<T> convertPageRecordToList(List<Record> pageRecord, Class<T> targetClass) {

        List<T> list = new ArrayList<>();

        pageRecord.forEach(action -> {
            try {
                list.add(MAPPER.readValue(action.toJson(), targetClass));
            } catch (IOException ex) {
                LogKit.error(ex.getMessage(), ex);
            }
        });
        
        return list;
    }
}
