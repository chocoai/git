/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author caibe
 */
public class DateUtil {
    
    private final static DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
    
    public static String getNow() {
        return  Instant.now().atZone(ZoneId.of("Asia/Shanghai")).format(DATETIME_FORMAT);
    }
}
