/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.jf;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.jfinal.template.Directive;
import com.jfinal.template.Env;
import com.jfinal.template.TemplateException;
import com.jfinal.template.expr.ast.Expr;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * sql自定义模板，decode解析类
 *
 * @author caibenxiang
 */
public class SqlDecodeDirective extends Directive {

    private final JSONObject json = new JSONObject("{\n"
            + "  \"GBZBBZ\": [\n"
            + "    {\n"
            + "      \"key\": \"1\",\n"
            + "      \"value\": \"公变\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"key\": \"2\",\n"
            + "      \"value\": \"专变\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"key\": \"3\",\n"
            + "      \"value\": \"专变合用\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"key\": \"4\",\n"
            + "      \"value\": \"专变公用\"\n"
            + "    }\n"
            + "  ],\n"
            + "  \"DQTZDM\": [\n"
            + "    {\n"
            + "      \"key\": \"1\",\n"
            + "      \"value\": \"市中心区\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"key\": \"2\",\n"
            + "      \"value\": \"市区\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"key\": \"3\",\n"
            + "      \"value\": \"城镇\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"key\": \"4\",\n"
            + "      \"value\": \"农村\"\n"
            + "    }\n"
            + "  ]\n"
            + "}");

    @Override
    public void exec(Env env, Scope scope, Writer writer) {

        String originalDecodeKey = null;
        String decodeKey = null;

        try {
            Expr expr = exprList.getActualExpr();
            if (expr != null) {
                originalDecodeKey = expr.eval(scope).toString();
            }
        } catch (TemplateException ex) {
            throw new TemplateException(ex.getMessage(), location);
        }

        if (StrUtil.isNotBlank(originalDecodeKey)) {
            if (StrUtil.contains(originalDecodeKey, '.')) {
                decodeKey = StrUtil.split(originalDecodeKey, ".")[1];
            } else {
                decodeKey = originalDecodeKey;
            }
        }

        StringBuilder decodeBuilder = new StringBuilder();
        if (json.containsKey(decodeKey)) {
            decodeBuilder.append("decode(").append(originalDecodeKey).append(", ");
            json.getJSONArray(decodeKey).forEach(obj -> {
                JSONObject jsonObj = (JSONObject) obj;
                decodeBuilder.append("'").append(jsonObj.getStr("key")).append("', '").append(jsonObj.getStr("value")).append("', ");
            });
            decodeBuilder.append(originalDecodeKey).append(")");
        } else {
            decodeBuilder.append(originalDecodeKey);
        }

        try {
            writer.write(decodeBuilder.toString());
        } catch (IOException ex) {
            Logger.getLogger(SqlDecodeDirective.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
