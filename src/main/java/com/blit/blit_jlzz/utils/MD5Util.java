/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.utils;

import cn.hutool.json.JSONUtil;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author caibenxiang
 */
public class MD5Util {

    public static String encryMd5(List listResult) {
        if (listResult != null && !listResult.isEmpty()) {
            return DigestUtils.md5Hex(JSONUtil.parse(listResult).toString());
        }
        return null;
    }
}
