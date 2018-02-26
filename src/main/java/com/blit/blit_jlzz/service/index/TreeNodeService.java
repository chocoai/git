/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.service.index;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.blit.blit_jlzz.enumdata.EConcentratorType;
import com.blit.blit_jlzz.enumdata.EConsType;
import com.blit.blit_jlzz.enumdata.ELineType;
import com.blit.blit_jlzz.enumdata.EMesuringPointType;
import com.blit.blit_jlzz.enumdata.ENode;
import com.blit.blit_jlzz.enumdata.ENodeSubType;
import com.blit.blit_jlzz.enumdata.ETmnlTypeCode;
import com.blit.blit_jlzz.enumdata.EVoltageLevel;
import com.blit.blit_jlzz.model.dagl.BdzdaSpec;
import com.blit.blit_jlzz.model.dagl.ByqdaSpec;
import com.blit.blit_jlzz.model.dagl.CjqdaSpec;
import com.blit.blit_jlzz.model.dagl.DcdaSpec;
import com.blit.blit_jlzz.model.dagl.DnbdaSpec;
import com.blit.blit_jlzz.model.dagl.JlddaSpec;
import com.blit.blit_jlzz.model.dagl.XldaSpec;
import com.blit.blit_jlzz.model.dagl.YhdaSpec;
import com.blit.blit_jlzz.model.dagl.ZddaSpec;
import com.blit.blit_jlzz.model.index.TreeNode;
import com.blit.blit_jlzz.model.system.OrgSpec;
import com.blit.blit_jlzz.model.system.UserSpec;
import com.blit.blit_jlzz.service.dagl.DacxService;
import com.blit.blit_jlzz.service.system.OrgService;
import com.blit.blit_jlzz.service.system.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfinal.aop.Duang;
import com.jfinal.kit.Kv;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author caibenxiang
 */
public class TreeNodeService {

    private final ObjectMapper mapper = Duang.duang(ObjectMapper.class);

    private final DacxService apiDacxService = Duang.duang(DacxService.class);
    private final UserService apiUserService = Duang.duang(UserService.class);
    private final OrgService apiOrgService = Duang.duang(OrgService.class);
    //private final YhcxService apiYhcxService = Duang.duang(YhcxService.class);
    //private final ByqcxService apiByqcxService = Duang.duang(ByqcxService.class);
    //private final DccxService apiDccxService = Duang.duang(DccxService.class);

    /**
     * 实现配电网树形查询
     *
     * @param node
     * @param loginStaffNo
     * @return
     */
    public String leftTree(String node, String loginStaffNo) {

        List<TreeNode> list = new ArrayList();

        String nodeType = "";
        String nodeId = "";
        String[] arr = StrUtil.split(node, "_");
        if (arr.length > 1) {
            nodeType = arr[0];
            nodeId = arr[1];
        }

        if (StrUtil.equalsIgnoreCase(node, "orgtree_root") || StrUtil.isBlank(node)) {
            list.add(convertOrg(apiOrgService.findOrgById(apiUserService.findByStaffNo(loginStaffNo).getGddwbh())));
        } else if (StrUtil.equalsIgnoreCase(nodeType, ENode.ORG.getIndex())) {
            //首先遍历出全部的下级单位
            apiOrgService.findOrgListById(nodeId, apiUserService.findByStaffNo(loginStaffNo).getCzybh()).forEach(org -> list.add(convertOrg(org)));
            //之后遍历全部在该下级单位下的所有线路
            apiDacxService.findLineByParams(
                    Kv.by("gddwbm", nodeId)
                            .set("xllbdm", ELineType.FEEDER_LINE.getIndex())
                            .set("dydjdm", EVoltageLevel.JL_10KV.getIndex())
            ).forEach(xl -> {
                JSONObject jsonObj = (JSONObject) xl;
                jsonObj.put("xlmc", (StrUtil.isBlank(jsonObj.getStr("bdzmc")) ? "-" : jsonObj.getStr("bdzmc")) + " / " + jsonObj.getStr("xlmc"));
                try {
                    list.add(convertLine(mapper.readValue(jsonObj.toString(), XldaSpec.class), false));
                } catch (IOException ex) {
                    Logger.getLogger(TreeNodeService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } else if (nodeType.equals(ENode.LINE.getIndex())) {
            apiDacxService.findConsListByLine(
                    Kv.by("enumConsGXZBKH", EConsType.GXZBKH.getIndex())
                            .set("enumConsZXZBKH", EConsType.ZXZBKH.getIndex())
                            .set("enumConsTQKHBH", EConsType.TQKHBH.getIndex())
                            .set("xlxdbs", nodeId)
            ).forEach(yh -> {
                JSONObject jsonObj = (JSONObject) yh;
                try {
                    list.add(convertCons(mapper.readValue(jsonObj.toString(), YhdaSpec.class)));
                } catch (IOException ex) {
                    Logger.getLogger(TreeNodeService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } else if (nodeType.equals(ENode.CONS.getIndex())) {
            apiDacxService.findTmnlByConsId(
                    Kv.by("yhbh", nodeId)
                            .set("enumTmnlLOAD_MANAGE_TMNL", ETmnlTypeCode.LOAD_MANAGE_TMNL.getIndex())
                            .set("enumTmnlSUB_TMNL", ETmnlTypeCode.SUB_TMNL.getIndex())
                            .set("enumTmnlCONCENTRATOR", ETmnlTypeCode.CONCENTRATOR.getIndex())
            ).forEach(zd -> {
                JSONObject jsonObj = (JSONObject) zd;
                try {
                    list.add(convertTmnl(mapper.readValue(jsonObj.toString(), ZddaSpec.class)));
                } catch (IOException ex) {
                    Logger.getLogger(TreeNodeService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } else if (nodeType.equals(ENode.TMNL.getIndex())) {
            String terminalTypeCode = "";
            if (arr.length > 2) {
                terminalTypeCode = arr[2];
            }
            if (StrUtil.isNotBlank(terminalTypeCode)
                    && (StrUtil.equals(terminalTypeCode, ETmnlTypeCode.LOAD_MANAGE_TMNL.getIndex())
                    || StrUtil.equals(terminalTypeCode, ETmnlTypeCode.SUB_TMNL.getIndex()))) {
                apiDacxService.findMeterByTmnlId(
                        Kv.by("cldlx", EMesuringPointType.METER.getIndex()).set("zdbs", nodeId)
                ).forEach(dnb -> {
                    JSONObject jsonObj = (JSONObject) dnb;
                    try {
                        list.add(convertMeter(mapper.readValue(jsonObj.toString(), DnbdaSpec.class)));
                    } catch (IOException ex) {
                        Logger.getLogger(TreeNodeService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            } else if (StrUtil.isNotBlank(terminalTypeCode)
                    && StrUtil.equals(terminalTypeCode, ETmnlTypeCode.CONCENTRATOR.getIndex())) {
                String concentratorTypeCode = "";
                if (arr.length > 3) {
                    concentratorTypeCode = StrUtil.equalsIgnoreCase(arr[3], "null") ? "" : arr[3];
                }
                if (StrUtil.isNotBlank(concentratorTypeCode)) {
                    int concentratorType = Integer.parseInt(concentratorTypeCode);
                    if (EConcentratorType.TYPE_1.getIndex() == concentratorType) {
                        apiDacxService.findCollectorsByZdbs(
                                Kv.by("zdbs", nodeId)
                        ).forEach(cjq -> {
                            JSONObject jsonObj = (JSONObject) cjq;
                            try {
                                list.add(convertCollector(mapper.readValue(jsonObj.toString(), CjqdaSpec.class)));
                            } catch (IOException ex) {
                                Logger.getLogger(TreeNodeService.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }
                }
            }
        }

        try {
            return mapper.writeValueAsString(list);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(TreeNodeService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * 实现输电网树形查询
     *
     * @param node
     * @param loginStaffNo
     * @return
     */
    public String leftTreeTrans_leftTree(String node, String loginStaffNo) {

        List<TreeNode> list = new ArrayList();

        String nodeType = "";
        String nodeId = "";
        String[] arr = StrUtil.split(node, "_");
        if (arr.length > 1) {
            nodeType = arr[0];
            nodeId = arr[1];
        }

        if (StrUtil.equalsIgnoreCase(node, "trans_net_root") || StrUtil.isBlank(node)) {
            TreeNode treeNode = convertOrg(apiOrgService.findOrgById(apiUserService.findByStaffNo(loginStaffNo).getGddwbh()));
            treeNode.setId("trans_net_first_node");
            list.add(treeNode);
        } else if (StrUtil.equalsIgnoreCase(node, "trans_net_first_node")) {
            createTransNetSecondNodes().forEach(netNode -> list.add(netNode));
        } else if (StrUtil.equalsIgnoreCase(node, "trans_org_root")) {
            UserSpec userSpec = apiUserService.findByStaffNo(loginStaffNo);
            apiOrgService.findOrgListById(userSpec.getGddwbh(), userSpec.getCzybh()).forEach(org -> list.add(convertOrg(org)));
        } else if (nodeType.equals(ENode.ORG.getIndex())) {
            apiDacxService.findSubstationByParams(Kv.by("gddwbm", nodeId)).forEach(sub -> {
                try {
                    list.add(convertSub(mapper.readValue(((JSONObject) sub).toString(), BdzdaSpec.class), ENodeSubType.FIRST));
                } catch (IOException ex) {
                    Logger.getLogger(TreeNodeService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } else if (nodeType.equals(ENode.SUB.getIndex())) {
            String subType = "";
            if (arr.length > 2) {
                subType = arr[2];
            }
            if (subType.equals(ENodeSubType.FIRST.getIndex())) {
                int countMeteringPointBySub = apiDacxService.findMeteringPointBySub(Kv.by("bdzbs", nodeId)).size();
                apiDacxService.findSubstationByParams(Kv.by("bdzbs", nodeId)).forEach(sub -> {
                    JSONObject jsonObj = (JSONObject) sub;
                    jsonObj.put("bdzmc", jsonObj.get("bdzmc") + "(" + countMeteringPointBySub + ")");
                    try {
                        list.add(convertSub(mapper.readValue(jsonObj.toString(), BdzdaSpec.class), ENodeSubType.SECOND));
                    } catch (IOException ex) {
                        Logger.getLogger(TreeNodeService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                list.add(new TreeNode("malinelinelose_" + nodeId, "母线", ENode.ROOT.getIndex(), "treenode-busline", true, false));
                list.add(new TreeNode("relationlinelose_" + nodeId, "联络线", ENode.ROOT.getIndex(), "treenode-linkline", false, false));
                list.add(new TreeNode("kuilinelose_" + nodeId, "馈线", ENode.ROOT.getIndex(), "treenode-feederline", false, false));
                list.add(new TreeNode("mainchangestation_" + nodeId, "主变", ENode.ROOT.getIndex(), "treenode-transformer", false, false));
            }
            if (subType.equals(ENodeSubType.SECOND.getIndex())) {
                apiDacxService.findMeteringPointBySub(Kv.by("bdzbs", nodeId)).forEach(jld -> {
                    JSONObject jsonObj = (JSONObject) jld;
                    try {
                        list.add(convertMP(mapper.readValue(jsonObj.toString(), JlddaSpec.class)));
                    } catch (IOException ex) {
                        Logger.getLogger(TreeNodeService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        } else if (StrUtil.startWith(node, "malinelinelose")) {
            apiDacxService.findLineByParams(Kv.by("xllbdm", ELineType.BUS_LINE.getIndex()).set("bdzbs", nodeId)).forEach(xl -> {
                try {
                    list.add(convertLine(mapper.readValue(((JSONObject) xl).toString(), XldaSpec.class), false));
                } catch (IOException ex) {
                    Logger.getLogger(TreeNodeService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } else if (StrUtil.startWith(node, "relationlinelose")) {
            apiDacxService.findLineByParams(Kv.by("xllbdm", ELineType.LINK_LINE.getIndex()).set("bdzbs", nodeId)).forEach(xl -> {
                try {
                    list.add(convertLine(mapper.readValue(((JSONObject) xl).toString(), XldaSpec.class), false));
                } catch (IOException ex) {
                    Logger.getLogger(TreeNodeService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } else if (StrUtil.startWith(node, "kuilinelose")) {
            apiDacxService.findLineByParams(Kv.by("xllbdm", ELineType.FEEDER_LINE.getIndex()).set("bdzbs", nodeId)).forEach(xl -> {
                try {
                    list.add(convertLine(mapper.readValue(((JSONObject) xl).toString(), XldaSpec.class), false));
                } catch (IOException ex) {
                    Logger.getLogger(TreeNodeService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } else if (StrUtil.startWith(node, "mainchangestation")) {
            apiDacxService.findMainTransformerBySub(Kv.by("bdzbs", nodeId)).forEach(byq -> {
                try {
                    list.add(convertTransformer(mapper.readValue(((JSONObject) byq).toString(), ByqdaSpec.class)));
                } catch (IOException ex) {
                    Logger.getLogger(TreeNodeService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } else if (StrUtil.equalsIgnoreCase(node, "trans_part_voltage_root")) {
            createPartVoltNodes().forEach(netNode -> list.add(netNode));
        } else if (StrUtil.equalsIgnoreCase(node, "trans_part_voltage_500kv")) {
            String gddwbh = apiUserService.findByStaffNo(loginStaffNo).getGddwbh();
            apiDacxService.findSubstationByParams(Kv.by("gddwbm", gddwbh).set("dydjdm", EVoltageLevel.JL_500KV.getIndex())).forEach(sub -> {
                try {
                    list.add(convertSub(mapper.readValue(((JSONObject) sub).toString(), BdzdaSpec.class), ENodeSubType.FIRST));
                } catch (IOException ex) {
                    Logger.getLogger(TreeNodeService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            apiDacxService.findPowerPlantByParams(Kv.by("dydjdm", EVoltageLevel.JL_500KV.getIndex()).set("gddwbm", gddwbh)).forEach(dc -> {
                try {
                    list.add(convertPowerPlant(mapper.readValue(((JSONObject) dc).toString(), DcdaSpec.class)));
                } catch (IOException ex) {
                    Logger.getLogger(TreeNodeService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } else if (StrUtil.equalsIgnoreCase(node, "trans_part_voltage_220kv")) {
            String gddwbh = apiUserService.findByStaffNo(loginStaffNo).getGddwbh();
            apiDacxService.findSubstationByParams(Kv.by("gddwbm", gddwbh).set("dydjdm", EVoltageLevel.JL_220KV.getIndex())).forEach(sub -> {
                try {
                    list.add(convertSub(mapper.readValue(((JSONObject) sub).toString(), BdzdaSpec.class), ENodeSubType.FIRST));
                } catch (IOException ex) {
                    Logger.getLogger(TreeNodeService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            apiDacxService.findPowerPlantByParams(Kv.by("dydjdm", EVoltageLevel.JL_220KV.getIndex()).set("gddwbm", gddwbh)).forEach(dc -> {
                try {
                    list.add(convertPowerPlant(mapper.readValue(((JSONObject) dc).toString(), DcdaSpec.class)));
                } catch (IOException ex) {
                    Logger.getLogger(TreeNodeService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } else if (StrUtil.equalsIgnoreCase(node, "trans_part_voltage_110kv")) {
            String gddwbh = apiUserService.findByStaffNo(loginStaffNo).getGddwbh();
            apiDacxService.findSubstationByParams(Kv.by("gddwbm", gddwbh).set("dydjdm", EVoltageLevel.JL_110KV.getIndex())).forEach(sub -> {
                try {
                    list.add(convertSub(mapper.readValue(((JSONObject) sub).toString(), BdzdaSpec.class), ENodeSubType.FIRST));
                } catch (IOException ex) {
                    Logger.getLogger(TreeNodeService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            apiDacxService.findPowerPlantByParams(Kv.by("dydjdm", EVoltageLevel.JL_110KV.getIndex()).set("gddwbm", gddwbh)).forEach(dc -> {
                try {
                    list.add(convertPowerPlant(mapper.readValue(((JSONObject) dc).toString(), DcdaSpec.class)));
                } catch (IOException ex) {
                    Logger.getLogger(TreeNodeService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

        try {
            return mapper.writeValueAsString(list);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(TreeNodeService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

//    /**
//     * 实现特殊定制格式的电网结构树
//     *
//     * @param node
//     * @param loginStaffNo
//     * @return
//     */
//    public String leftTreeCustom(String node, String loginStaffNo) {
//
//        String gddwbh = apiUserService.findByStaffNo(loginStaffNo).getGddwbh();
//
//        List<TreeNode> list = new ArrayList();
//
//        String nodeType = "";
//        String nodeId = "";
//        String[] arr = StrUtil.split(node, "_");
//        if (arr.length > 1) {
//            nodeType = arr[0];
//            nodeId = arr[1];
//        }
//
//        if (StrUtil.equalsIgnoreCase(node, "orgtree_root") || StrUtil.isBlank(node)) {
//            TreeNode treeNode = convertOrg(apiOrgService.findOrgById(apiUserService.findByStaffNo(loginStaffNo).getGddwbh()));
//            treeNode.setId("custom_lefttree_first_node");
//            list.add(treeNode);
//        } else if (StrUtil.equalsIgnoreCase(node, "custom_lefttree_first_node")) {
//            createCustomSecondNodes().forEach(netNode -> list.add(netNode));
//        } else if (StrUtil.equalsIgnoreCase(node, "bdz_root")) {
//            Map<String, String> mapVolt = new HashMap();
//            BdzcxRequest params = new BdzcxRequest();
//            params.setGddwbm(gddwbh);
//            params.setNodeType(ENode.SUB.getIndex());
////            apiBdzcxService.findSubstationByParams(params).stream()
////                    .filter(bdz -> StrUtil.isNotBlank(bdz.getDydjdm_mc()))
////                    .forEach(bdz -> mapVolt.put(bdz.getDydjdm(), bdz.getDydjdm_mc()));
//            LinkedHashMap<String, String> sortVoltMap = mapVolt.entrySet().stream()
//                    .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
//                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (newValue, oldValue) -> oldValue, LinkedHashMap::new));
//            convertVolt(sortVoltMap).forEach(tn -> list.add(tn));
//        } else if (StrUtil.startWith(node, "bdz_root_volt")) {
//            String dydj = "";
//            if (arr.length > 2) {
//                dydj = arr[3];
//            }
//            Map<String, String> mapVolt = new HashMap();
//            BdzcxRequest params = new BdzcxRequest();
//            params.setGddwbm(gddwbh);
//            params.setNodeType(ENode.SUB.getIndex());
//            params.setDydjdm(dydj);
////            apiBdzcxService.findSubstationByParams(params).forEach(sub -> list.add(convertSub(sub, ENodeSubType.FIRST)));
//        } else if (StrUtil.startWith(node, "sub_") && arr.length == 3) {
//            XlcxRequest params = new XlcxRequest();
//            params.setGddwbm(gddwbh);
//            params.setNodeType(ENode.LINE.getIndex());
//            Map<String, String> mapLineVolt = new HashMap();
//            apiXlcxService.findLineByParams(params).stream()
//                    .filter(xl -> StrUtil.isNotBlank(xl.getDydjdm_mc()))
//                    .forEach(xl -> mapLineVolt.put(xl.getDydjdm(), xl.getDydjdm_mc()));
//            LinkedHashMap<String, String> sortVoltMap = mapLineVolt.entrySet().stream()
//                    .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
//                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (newValue, oldValue) -> oldValue, LinkedHashMap::new));
//            convertSubVolt(arr[1], sortVoltMap).forEach(tn -> list.add(tn));
//        }
//
//        try {
//            return mapper.writeValueAsString(list);
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(TreeNodeService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return null;
//    }
    private TreeNode convertOrg(OrgSpec org) {
        TreeNode tn = new TreeNode();
        tn.setId(ENode.ORG.getIndex() + "_" + org.getOrgNo() + "_" + org.getOrgType());
        tn.setName(org.getOrgName());
        tn.setIconCls("treenode-org");
        tn.setType(ENode.ORG.getIndex());
        tn.setExpanded(Boolean.FALSE);
        tn.setIsLeaf(Boolean.FALSE);
        tn.setQtip(formatQtip(ENode.ORG.getDesc(), tn.getName()));
        tn.setAttributes(org);
        return tn;
    }

    private TreeNode convertLine(XldaSpec line, boolean isLeaf) {

        if (line == null) {
            return null;
        }

        TreeNode tn = new TreeNode();

        tn.setName(line.getXlmc());
        tn.setType(ENode.LINE.getIndex());
        String nodeId = ENode.LINE.getIndex() + "_" + line.getXlxdbs() + "_" + line.getXllbdm();
        tn.setId(nodeId);
        tn.setIsLeaf(isLeaf);
        tn.setQtip(formatQtip(ENode.LINE.getDesc(), tn.getName()));

        String tipsTitle = ENode.LINE.getDesc();
        if (ELineType.FEEDER_LINE.getIndex().equals(line.getXllbdm())) {
            tipsTitle = ELineType.FEEDER_LINE.getDesc();
            tn.setIconCls("treenode-feederline");
        } else if (ELineType.BUS_LINE.getIndex().equals(line.getXllbdm())) {
            tipsTitle = ELineType.BUS_LINE.getDesc();
            tn.setIconCls("treenode-busline");
            tn.setExpanded(true);
        } else if (ELineType.LINK_LINE.getIndex().equals(line.getXllbdm())) {
            tipsTitle = ELineType.LINK_LINE.getDesc();
            tn.setIconCls("treenode-linkline");
        }

        if (line.getDydjdm() != null) {
            EVoltageLevel[] arrayOfEVoltageLevel;
            int j = (arrayOfEVoltageLevel = EVoltageLevel.values()).length;
            for (int i = 0; i < j; i++) {
                EVoltageLevel e = arrayOfEVoltageLevel[i];
                if (line.getDydjdm().equals(e.getIndex())) {
                    tipsTitle = e.getDesc() + tipsTitle;
                    break;
                }
            }
        }
        tn.setQtip(formatQtip(tipsTitle, tn.getName()));
        tn.setAttributes(line);
        return tn;
    }

    private TreeNode convertCons(YhdaSpec cons) {
        if (cons == null) {
            return null;
        }
        TreeNode tn = new TreeNode();
        tn.setName(cons.getYhbh() + ":" + cons.getYhmc());
        tn.setType(ENode.CONS.getIndex());
        String nodeId = ENode.CONS.getIndex() + "_" + cons.getYhbh() + "_" + cons.getYhbh();
        tn.setId(nodeId);
        tn.setIsLeaf(false);
        tn.setIconCls("treenode-usr");

        String tipsTitle = ENode.CONS.getDesc();
        if (StrUtil.isNotBlank(cons.getYhlbdm())) {
            EConsType[] arrayOfEConsType;
            int j = (arrayOfEConsType = EConsType.values()).length;
            for (int i = 0; i < j; i++) {
                EConsType e = arrayOfEConsType[i];
                if (cons.getYhlbdm().equals(e.getIndex())) {
                    tipsTitle = e.getDesc();
                    break;
                }
            }
        }
        tn.setQtip(formatQtip(tipsTitle, tn.getName()));
        tn.setAttributes(cons);

        return tn;
    }

    private TreeNode convertTmnl(ZddaSpec tmnl) {
        if (tmnl == null) {
            return null;
        }
        TreeNode tn = new TreeNode();
        tn.setName(tmnl.getZcbh() + "：" + tmnl.getZddz());
        tn.setType(ENode.TMNL.getIndex());
        String nodeId = ENode.TMNL.getIndex() + "_" + tmnl.getZdbs() + "_" + tmnl.getZdlxdm() + "_" + tmnl.getZdlxsx();
        tn.setId(nodeId);

        String tipsTitle = ENode.TMNL.getDesc();
        String terminalTypeCode = tmnl.getZdlxdm();
        if (terminalTypeCode != null) {
            if (terminalTypeCode.equals(ETmnlTypeCode.LOAD_MANAGE_TMNL.getIndex())) {
                tipsTitle = ETmnlTypeCode.LOAD_MANAGE_TMNL.getDesc();
                tn.setIsLeaf(false);
                if (tmnl.getSfzx() != null && tmnl.getSfzx()) {
                    tn.setIconCls("treenode-tmnl1");
                } else {
                    tn.setIconCls("treenode-tmnl1-stop");
                }
            } else if (terminalTypeCode.equals(ETmnlTypeCode.SUB_TMNL.getIndex())) {
                tipsTitle = ETmnlTypeCode.SUB_TMNL.getDesc();
                tn.setIsLeaf(false);
                if (tmnl.getSfzx() != null && tmnl.getSfzx()) {
                    tn.setIconCls("treenode-tmnl2");
                } else {
                    tn.setIconCls("treenode-tmnl2-stop");
                }
            } else if (terminalTypeCode.equals(ETmnlTypeCode.CONCENTRATOR.getIndex())) {
                String concentratorTypeCode = tmnl.getZdlxsx();
                if (concentratorTypeCode != null) {
                    int concentratorType = Integer.parseInt(concentratorTypeCode);
                    if (EConcentratorType.TYPE_1.getIndex() == concentratorType) {
                        tipsTitle = EConcentratorType.TYPE_1.getDesc();
                        tn.setIsLeaf(false);
                    } else {
                        tipsTitle = EConcentratorType.TYPE_2.getDesc();
                        tn.setIsLeaf(true);
                    }
                    if (tmnl.getSfzx() != null && tmnl.getSfzx()) {
                        tn.setIconCls("treenode-tmnl3");
                    } else {
                        tn.setIconCls("treenode-tmnl3-stop");
                    }
                } else {
                    tipsTitle = ETmnlTypeCode.CONCENTRATOR.getDesc();
                    tn.setIsLeaf(false);
                    if (tmnl.getSfzx() != null && tmnl.getSfzx()) {
                        tn.setIconCls("treenode-collector");
                    } else {
                        tn.setIconCls("treenode-collector-stop");
                    }
                }
            }
        }

        tn.setQtip(formatQtip(tipsTitle, "终端资产号：" + tmnl.getZcbh() + "\n终端地址" + tmnl.getZddz()));
        tn.setAttributes(tmnl);

        return tn;
    }

    private TreeNode convertMeter(DnbdaSpec meter) {
        if (meter == null) {
            return null;
        }
        TreeNode tn = new TreeNode();
        tn.setName(meter.getDnbzch());
        tn.setType(ENode.METER.getIndex());
        String nodeId = ENode.METER.getIndex() + "_" + meter.getDnbzch() + "_" + meter.getDnbzch();
        tn.setId(nodeId);
        tn.setIsLeaf(true);
        tn.setIconCls("treenode-meter");
        tn.setQtip(formatQtip(ENode.METER.getDesc(), "电表资产号：" + tn.getName()));
        tn.setAttributes(meter);
        return tn;
    }

    private TreeNode convertCollector(CjqdaSpec collector) {
        if (collector == null) {
            return null;
        }
        TreeNode tn = new TreeNode();
        tn.setName(collector.getZcbh() + ":" + collector.getCjqmc());
        tn.setType(ENode.COLLECTOR.getIndex());
        String nodeId = ENode.COLLECTOR.getIndex() + "_" + collector.getCjqbs() + "_" + collector.getZcbh();
        tn.setId(nodeId);
        tn.setIsLeaf(true);
        tn.setIconCls("treenode-collector");
        tn.setQtip(formatQtip(ENode.COLLECTOR.getDesc(), "采集器资产号：" + collector.getZcbh() + "</br>采集器名称：" + collector.getCjqmc()));
        tn.setAttributes(collector);
        return tn;
    }

    private String formatQtip(String title, String content) {
        return "<font style='font-style:italic'>" + title + "</font></br>" + content;
    }

    private List<TreeNode> createTransNetSecondNodes() {

        List<TreeNode> rs = new ArrayList();

        TreeNode tn1 = new TreeNode();
        tn1.setId("trans_org_root");
        tn1.setName("分局");
        tn1.setIconCls("treenode-org");
        tn1.setType(ENode.ROOT.getIndex());
        tn1.setIsLeaf(Boolean.FALSE);
        rs.add(tn1);

        TreeNode tn3 = new TreeNode();
        tn3.setId("trans_part_voltage_root");
        tn3.setName("分压");
        tn3.setIconCls("treenode-volt");
        tn3.setType(ENode.ROOT.getIndex());
        tn3.setIsLeaf(Boolean.FALSE);
        rs.add(tn3);

        return rs;
    }

    private TreeNode convertSub(BdzdaSpec sub, ENodeSubType eSeaNodeSubType) {
        if (sub == null) {
            return null;
        }
        TreeNode tn = new TreeNode();
        tn.setAttributes(sub);
        tn.setName(sub.getBdzmc());
        tn.setType(ENode.SUB.getIndex());
        String nodeId = ENode.SUB.getIndex() + "_" + sub.getBdzbs() + "_" + eSeaNodeSubType.getIndex();
        tn.setId(nodeId);
        tn.setQtip(formatQtip(ENode.SUB.getDesc(), tn.getName()));
        tn.setIsLeaf(false);
        tn.setIconCls("treenode-sub");
        return tn;
    }

    private TreeNode convertMP(JlddaSpec mp) {
        if (mp == null) {
            return null;
        }
        TreeNode tn = new TreeNode();
        tn.setAttributes(mp);
        tn.setName(mp.getJldmc() + "(序号" + mp.getCldh() + ")");
        tn.setType(ENode.METER_POINT.getIndex());
        String nodeId = ENode.METER_POINT.getIndex() + "_" + mp.getJldbh() + "_" + mp.getJldbh();
        tn.setId(nodeId);
        tn.setQtip(formatQtip(ENode.METER_POINT.getDesc(), "序号" + mp.getCldh() + "</br>计量点编号：" + mp.getJldbh() + "</br>计量点名称：" + mp.getJldmc()));
        tn.setIsLeaf(true);
        tn.setIconCls("treenode-mp");
        return tn;
    }

    private TreeNode convertTransformer(ByqdaSpec transformer) {
        if (transformer == null) {
            return null;
        }
        TreeNode tn = new TreeNode();
        tn.setAttributes(transformer);
        tn.setName(transformer.getByqmc());
        tn.setType(ENode.TRANSFORMER.getIndex());
        String nodeId = ENode.TRANSFORMER.getIndex() + "_" + transformer.getYxbyqbs();
        tn.setId(nodeId);
        tn.setQtip(formatQtip(ENode.TRANSFORMER.getDesc(), tn.getName()));
        tn.setIsLeaf(true);
        tn.setIconCls("treenode-transformer");
        tn.setExpanded(true);
        return tn;
    }

    private List<TreeNode> createPartVoltNodes() {

        List<TreeNode> rs = new ArrayList();

        TreeNode tn1 = new TreeNode();
        tn1.setId("trans_part_voltage_500kv");
        tn1.setName("500KV");
        tn1.setIconCls("treenode-volt");
        tn1.setType(ENode.ROOT.getIndex());
        tn1.setIsLeaf(Boolean.FALSE);
        rs.add(tn1);

        TreeNode tn2 = new TreeNode();
        tn2.setId("trans_part_voltage_220kv");
        tn2.setName("220KV");
        tn2.setIconCls("treenode-volt");
        tn2.setType(ENode.ROOT.getIndex());
        tn2.setIsLeaf(Boolean.FALSE);
        rs.add(tn2);

        TreeNode tn3 = new TreeNode();
        tn3.setId("trans_part_voltage_110kv");
        tn3.setName("110KV");
        tn3.setIconCls("treenode-volt");
        tn3.setType(ENode.ROOT.getIndex());
        tn3.setIsLeaf(Boolean.FALSE);
        rs.add(tn3);

        return rs;
    }

    private TreeNode convertPowerPlant(DcdaSpec pp) {
        if (pp == null) {
            return null;
        }
        TreeNode tn = new TreeNode();
        tn.setAttributes(pp);
        tn.setName(pp.getDcmc());
        tn.setType(ENode.POWER_PLANT.getIndex());
        String nodeId = ENode.POWER_PLANT.getIndex() + "_" + pp.getDcbh();
        tn.setId(nodeId);
        tn.setQtip(formatQtip(ENode.POWER_PLANT.getDesc(), tn.getName()));
        tn.setIsLeaf(false);
        tn.setIconCls("treenode-powerplant");
        return tn;
    }

    private List<TreeNode> createCustomSecondNodes() {

        List<TreeNode> rs = new ArrayList();

        TreeNode tn1 = new TreeNode();
        tn1.setId("bdz_root");
        tn1.setName("变电站");
        tn1.setIconCls("bdz-root");
        tn1.setType(ENode.ROOT.getIndex());
        rs.add(tn1);

        TreeNode tn2 = new TreeNode();
        tn2.setId("dfdc_root");
        tn2.setName("地方电厂");
        tn2.setIconCls("dfdc-root");
        tn2.setType(ENode.ROOT.getIndex());
        rs.add(tn2);

        TreeNode tn3 = new TreeNode();
        tn3.setId("xsd_root");
        tn3.setName("小水电");
        tn3.setIconCls("xsd-root");
        tn3.setType(ENode.ROOT.getIndex());
        rs.add(tn3);

        return rs;
    }

    private List<TreeNode> convertVolt(LinkedHashMap<String, String> sortedMap) {

        if (sortedMap == null) {
            return null;
        }

        List<TreeNode> listTreeNode = new ArrayList<>();
        sortedMap.forEach((key, value) -> {
            TreeNode tn = new TreeNode();
            tn.setName(value);
            tn.setId("bdz_root_volt_" + key);
            tn.setIsLeaf(false);
            tn.setIconCls("treenode-volt");
            listTreeNode.add(tn);
        });

        return listTreeNode;
    }

    private List<TreeNode> convertSubVolt(String subId, LinkedHashMap<String, String> sortedMap) {

        if (sortedMap == null) {
            return null;
        }

        List<TreeNode> listTreeNode = new ArrayList<>();
        sortedMap.forEach((key, value) -> {
            TreeNode tn = new TreeNode();
            tn.setName(value);
            tn.setId("sub_" + subId + "_volt_" + key);
            tn.setIsLeaf(false);
            tn.setIconCls("treenode-volt");
            listTreeNode.add(tn);
        });

        return listTreeNode;
    }
}
