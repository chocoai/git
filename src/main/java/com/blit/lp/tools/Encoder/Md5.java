package com.blit.lp.tools.Encoder;

import java.security.MessageDigest;

import com.jfinal.kit.StrKit;

public class Md5 {
	public static String md5(String str) {
        if (StrKit.isBlank(str)) {
            return str;
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (java.security.NoSuchAlgorithmException ex) {
            //断言失败：JDK 带有 MD5 算法实现
            throw new AssertionError(ex);
        }
        byte[] res = md.digest(str.getBytes());
        return dumpBytes(res);
    }
	
	private static String dumpBytes(byte[] bytes) {
        int size = bytes.length;
        StringBuffer sb = new StringBuffer(size * 2);
        String s;
        for (int i = 0; i < size; ++i) {
            s = Integer.toHexString(bytes[i]);
            if (s.length() == 8) {          // -128 <= bytes[i] < 0
                sb.append(s.substring(6));
            } else if (s.length() == 2) {   // 16 <= bytes[i] < 128
                sb.append(s);
            } else {
                sb.append("0" + s);         // 0 <= bytes[i] < 16
            }
        }
        return sb.toString();
    }
}
