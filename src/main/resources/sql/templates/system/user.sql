/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  caibenxiang
 * Created: 2018-1-18
 */
#sql("findSystemUser")
    select xtyhbs, czybh, gddwbh, bmbh, ywryxm from QX_XTYH where czybh = #para(0)
#end
