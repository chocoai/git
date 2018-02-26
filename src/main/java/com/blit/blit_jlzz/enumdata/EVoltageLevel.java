/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz.enumdata;

/**
 * 电压等级枚举类
 *
 * @author caibenxiang
 */
public enum EVoltageLevel {

    JL_110V("01", "交流110V"),
    JL_220V("02", "交流220V"),
    JL_380V("03", "交流380V"),
    JL_660V("04", "交流660V"),
    JL_1000V("05", "交流1000V"),
    JL_3KV("06", "交流3KV"),
    JL_6KV("07", "交流6KV"),
    JL_10KV("08", "交流10KV"),
    JL_20KV("09", "交流20KV"),
    JL_35KV("10", "交流35KV"),
    JL_66KV("11", "交流66KV"),
    JL_110KV("12", "交流110KV"),
    JL_220KV("13", "交流220KV"),
    JL_330KV("14", "交流330KV"),
    JL_500KV("15", "交流500KV"),
    JL_750KV("16", "交流750KV"),
    JL_1000KV("17", "交流1000KV"),
    JL_5V("20", "交流5V"),
    JL_6V("21", "交流6V"),
    JL_12V("22", "交流12V"),
    JL_15V("23", "交流15V"),
    JL_24V("24", "交流24V"),
    JL_36V("25", "交流36V"),
    JL_42V("26", "交流42V"),
    JL_48V("27", "交流48V"),
    JL_60V("28", "交流60V"),
    JL_100V("29", "交流100V"),
    JL_127V("30", "交流127V"),
    JL_115V("31", "交流115V"),
    JL_230V("32", "交流230V"),
    JL_400V("33", "交流400V"),
    JL_690V("34", "交流690V"),
    JL_3150V("35", "交流3150V"),
    JL_6300V("36", "交流6300V"),
    JL_10_5KV("37", "交流10.5KV"),
    JL_13_8KV("38", "交流13.8KV"),
    JL_15_75KV("39", "交流15.75KV"),
    JL_18KV("40", "交流18KV"),
    JL_22KV("42", "交流22KV"),
    JL_24KV("43", "交流24KV"),
    JL_26KV("44", "交流26KV"),
    JL_132KV("45", "交流132KV"),
    JL_400KV("46", "交流400KV"),
    JL_OTHER("49", "其它交流电压"),
    ZL_24V("51", "直流24V"),
    ZL_36V("52", "直流36V"),
    ZL_48V("53", "直流48V"),
    ZL_110V("54", "直流110V"),
    ZL_220V("55", "直流220V"),
    ZL_1_2V("56", "直流1.2V"),
    ZL_1_5V("57", "直流1.5V"),
    ZL_2V("58", "直流2V"),
    ZL_2_4V("59", "直流2.4V"),
    ZL_3V("60", "直流3V"),
    ZL_4_5V("61", "直流4.5V"),
    ZL_5V("62", "直流5V"),
    ZL_6V("63", "直流6V"),
    ZL_9V("64", "直流9V"),
    ZL_12V("65", "直流12V"),
    ZL_15V("66", "直流15V"),
    ZL_30V("67", "直流30V"),
    ZL_60V("68", "直流60V"),
    ZL_72V("69", "直流72V"),
    ZL_160V("70", "直流160V"),
    ZL_400V("71", "直流400V"),
    ZL_440V("72", "直流440V"),
    ZL_630V("73", "直流630V"),
    ZL_800V("74", "直流800V"),
    ZL_1000V("75", "直流1000V"),
    ZL_1250V("76", "直流1250V"),
    ZL_1500V("77", "直流1500V"),
    ZL_2000V("78", "直流2000V"),
    ZL_3000V("79", "直流3000V"),
    ZL_160KV("80", "直流160KV"),
    ZL_500KV("81", "直流500KV"),
    ZL_700KV("82", "直流700KV"),
    ZL_800KV("83", "直流800KV"),
    ZL_115V("91", "直流115V"),
    ZL_230V("92", "直流230V"),
    ZL_460V("93", "直流460V"),
    ZL_600V("94", "直流600V"),
    ZL_750V("95", "直流750V"),
    ZL_OTHER("99", "其它直流电压"),
    ALL("00", "不区分电压等级");

    private String index;
    private String desc;

    private EVoltageLevel(String index, String desc) {
        setIndex(index);
        setDesc(desc);
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getIndex() {
        return this.index;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }
}
