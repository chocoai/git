/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.utils;

import com.blit.blit_jlzz.model.Metadata;
import com.blit.blit_jlzz.model.Pagination;
import com.jfinal.plugin.activerecord.Page;

/**
 * API结果返回包装工具类
 *
 * @author caibenxiang
 */
public class WrapperResponseUtils {
    
    public static Metadata wrapperMeta(Page page) {
        
        Metadata meta = new Metadata();
        
        meta.setPagination(new Pagination());
        meta.getPagination().setTotal((int) page.getTotalRow());
        meta.getPagination().setPage(page.getPageNumber());
        meta.getPagination().setLimit(page.getPageSize());
                
        meta.setMd5(MD5Util.encryMd5(page.getList()));
        meta.setResponseTime(DateUtil.getNow());
        
        return meta;
    }
}
