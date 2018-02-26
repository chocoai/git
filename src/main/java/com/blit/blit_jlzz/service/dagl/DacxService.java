/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.service.dagl;

import cn.hutool.json.JSONArray;
import com.jfinal.kit.Kv;

/**
 * 档案查询服务类
 *
 * @author caibenxiang
 */
public class DacxService extends BaseService {

    /**
     * 根据传入的参数查询变电站
     *
     * @param params
     * @return
     */
    public JSONArray findSubstationByParams(Kv params) {
        return super.useDb("jlzz").queryList("dagl.findSubstationByParams", params);
    }
    
    /**
     * 根据传入的参数查询线路
     *
     * @param params
     * @return
     */
    public JSONArray findLineByParams(Kv params) {
        return super.useDb("jlzz").queryList("dagl.findFeederLineByParam", params);
    }
    
    /**
     * 根据线路ID查询下挂用户信息
     *
     * @param params
     * @return
     */
    public JSONArray findConsListByLine(Kv params) {
        return super.useDb("jlzz").queryList("dagl.findConsListByLine", params);
    }
    
    /**
     * 根据指定用户ID查询下挂终端信息
     *
     * @param params
     * @return
     */
    public JSONArray findTmnlByConsId(Kv params) {
        return super.useDb("jlzz").queryList("dagl.findTmnlByConsId", params);
    }
    
    /**
     * 根据指定用户ID查询下挂终端信息
     *
     * @param params
     * @return
     */
    public JSONArray findMeterByTmnlId(Kv params) {
        return super.useDb("jlzz").queryList("dagl.findMeterByTmnlId", params);
    }
    
    /**
     * 根据指定用户ID查询下挂终端信息
     *
     * @param params
     * @return
     */
    public JSONArray findCollectorsByZdbs(Kv params) {
        return super.useDb("jlzz").queryList("dagl.findCollectorsByZdbs", params);
    }
    
    /**
     * 根据变电站ID查询下面所有计量点列表
     *
     * @param params
     * @return
     */
    public JSONArray findMeteringPointBySub(Kv params) {
        return super.useDb("jlzz").queryList("dagl.findMeteringPointBySub", params);
    }
    
    /**
     * 查询指定变电站ID下的所有主变
     *
     * @param params
     * @return
     */
    public JSONArray findMainTransformerBySub(Kv params) {
        return super.useDb("jlzz").queryList("dagl.findMainTransformerBySub", params);
    }
    
    /**
     * 根据传入的参数查询电厂
     *
     * @param params
     * @return
     */
    public JSONArray findPowerPlantByParams(Kv params) {
        return super.useDb("jlzz").queryList("dagl.findPowerPlantByParams", params);
    }
}

