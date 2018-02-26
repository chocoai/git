/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.service.dagl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author caibenxiang
 */
public class BaseService {

    private DbPro dbPro = Db.use();

    private JSONObject convertRecordToJson(Record record) {
        JSONObject json = JSONUtil.createObj();
        record.getColumns().forEach((key, value) -> {
            json.put(key.toLowerCase(), value);
        });
        return json;
    }

    private boolean isEmpty(Object obj) {
        return obj == null || obj.toString().length() == 0;
    }

    /**
     * 移除map中的空值
     *
     * Iterator 是工作在一个独立的线程中，并且拥有一个 mutex 锁。 Iterator
     * 被创建之后会建立一个指向原来对象的单链索引表，当原来的对象数量发生变化时，这个索引表的内容不会同步改变，
     * 所以当索引指针往后移动的时候就找不到要迭代的对象，所以按照 fail-fast 原则 Iterator 会马上抛出
     * java.util.ConcurrentModificationException 异常。 所以 Iterator
     * 在工作的时候是不允许被迭代的对象被改变的。 但你可以使用 Iterator 本身的方法 remove() 来删除对象，
     * Iterator.remove() 方法会在删除当前迭代对象的同时维护索引的一致性。
     *
     * @param obj
     * @param iterator
     */
    private void remove(Object obj, Iterator iterator) {
        if (obj instanceof String) {
            String str = (String) obj;
            if (isEmpty(str)) {  //过滤掉为null和""的值 主函数输出结果map：{2=BB, 1=AA, 5=CC, 8=  }  
//            if("".equals(str.trim())){  //过滤掉为null、""和" "的值 主函数输出结果map：{2=BB, 1=AA, 5=CC}  
                iterator.remove();
            }
        } else if (obj instanceof Collection) {
            Collection col = (Collection) obj;
            if (col.isEmpty()) {
                iterator.remove();
            }
        } else if (obj instanceof Map) {
            Map temp = (Map) obj;
            if (temp.isEmpty()) {
                iterator.remove();
            }
        } else if (obj instanceof Object[]) {
            Object[] array = (Object[]) obj;
            if (array.length <= 0) {
                iterator.remove();
            }
        } else {
            if (obj == null) {
                iterator.remove();
            }
        }
    }

    private Kv removeNullValue(Kv inKv) {

        Set set = inKv.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            Object obj = (Object) iterator.next();
            Object value = (Object) inKv.get(obj);
            remove(value, iterator);
        }

        inKv.keySet().forEach(key -> {
            if (inKv.get(key) instanceof String[]) {
                inKv.set(key, ((String[]) inKv.get(key))[0]);
            }
        });

        return inKv;
    }

    private Kv convertGetParaToKv(Map<String, String[]> params) {
        Kv kvReturn = Kv.create();
        params.keySet().forEach(key -> kvReturn.set(StrUtil.contains(key, '.') ? StrUtil.split(key, ".")[1] : key, params.get(key)[0]));
        return removeNullValue(kvReturn);
    }

    /**
     * 设置查询所用连接
     *
     * @param dbKey
     * @return
     */
    public BaseService useDb(String dbKey) {
        this.dbPro = Db.use(dbKey);
        return this;
    }

    /**
     * 查询单条记录
     *
     * @param dbTemplateKey JFinal SQL模板键
     * @param searchMaps 输入的查询参数
     * @return
     */
    public JSONObject query(String dbTemplateKey, Kv searchMaps) {
        SqlPara sqlPara = Db.getSqlPara(dbTemplateKey, removeNullValue(searchMaps));
        try {
            List<Record> records = dbPro.find(sqlPara);
            if (!records.isEmpty()) {
                return convertRecordToJson(records.get(0));
            }
        } catch (ActiveRecordException ex) {
            throw new ActiveRecordException(sqlPara.getSql(), ex);
        }
        return JSONUtil.createObj();
    }

    /**
     * 查询单条记录
     *
     * @param dbTemplateKey JFinal SQL模板键
     * @param params
     * @return
     */
    public JSONObject queryPara(String dbTemplateKey, Map<String, String[]> params) {
        return query(dbTemplateKey, convertGetParaToKv(params));
    }

    /**
     * 查询多条记录
     *
     * @param dbTemplateKey JFinal SQL模板键
     * @param searchMaps 输入的查询参数
     * @return
     */
    public JSONArray queryList(String dbTemplateKey, Kv searchMaps) {
        JSONArray array = JSONUtil.createArray();
        SqlPara sqlPara = Db.getSqlPara(dbTemplateKey, removeNullValue(searchMaps));
        try {
            List<Record> records = dbPro.find(sqlPara);
            records.forEach(record -> array.add(convertRecordToJson(record)));
        } catch (ActiveRecordException ex) {
            throw new ActiveRecordException(sqlPara.getSql(), ex);
        }
        return array;
    }

    /**
     * 查询多条记录
     *
     * @param dbTemplateKey JFinal SQL模板键
     * @param params
     * @return
     */
    public JSONArray queryParaList(String dbTemplateKey, Map<String, String[]> params) {
        return queryList(dbTemplateKey, convertGetParaToKv(params));
    }

    /**
     * 按分页查询多条记录
     *
     * @param dbTemplateKey JFinal SQL模板键
     * @param searchMaps 输入的查询参数
     * @param pageNumber 查询页数
     * @param pageSize 查询页行数
     * @return
     */
    public JSONObject queryListByPagination(String dbTemplateKey, Kv searchMaps, int pageNumber, int pageSize) {
        JSONObject returnJson = JSONUtil.createObj();
        SqlPara sqlPara = Db.getSqlPara(dbTemplateKey, removeNullValue(searchMaps));
        try {
            Page<Record> records = dbPro.paginate(pageNumber, pageSize, sqlPara);
            JSONObject pageJson = JSONUtil.createObj()
                    .append("pageNumber", records.getPageNumber())
                    .append("pageSize", records.getPageSize())
                    .append("totalPage", records.getTotalPage())
                    .append("totalRow", records.getTotalRow());
            returnJson.append("pagination", pageJson);
            JSONArray lists = JSONUtil.createArray();
            records.getList().forEach(record -> lists.add(convertRecordToJson(record)));
            returnJson.append("list", lists);

        } catch (ActiveRecordException ex) {
            throw new ActiveRecordException(sqlPara.getSql(), ex);
        }
        return returnJson;
    }

    /**
     * 按分页查询多条记录
     *
     * @param dbTemplateKey JFinal SQL模板键
     * @param params 约定，前台传输页数为：pageNumber， 每页行数为 pageSize
     * @return
     */
    public JSONObject queryListByPagination(String dbTemplateKey, Map<String, String[]> params) {
        Kv kv = convertGetParaToKv(params);
        int pageNumber = kv.getInt("pageNumber") == null ? 1 : kv.getInt("pageNumber");
        int pageSize = kv.getInt("pageSize") == null ? 50 : kv.getInt("pageSize");
        return queryListByPagination(dbTemplateKey, kv, pageNumber, pageSize);
    }
}
