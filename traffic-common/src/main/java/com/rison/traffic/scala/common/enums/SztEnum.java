package com.rison.traffic.scala.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.StringJoiner;

/**
 * @author : Rison 2021/6/16 上午9:20
 * 常用地段枚举类
 */
@Getter
@AllArgsConstructor
public enum SztEnum {
    /**
     * 常用地段枚举类
     */
    SZT_NAME_SPACE("szt", "hbase 命名空间"),
    SZT_TABLE_NAME("szt:data", "命名空间下的表名"),
    SZT_TABLE_CF("card", "表下面对应的列族名"),

    SZT_CL_DEAL_DATE("deal_date", "列名，交易时间"),
    SZT_CL_CLOSE_DATE("close_date", "列名，结算时间"),
    SZT_CL_CARD_NO("card_no", "卡号，9位字母"),
    SZT_CL_DEAL_VALUE("deal_value", "交易金额，整数分值，原价"),
    SZT_CL_DEAL_TYPE("deal_type", "交易类型，地铁入站|地铁出站|巴士"),
    SZT_CL_LINE("line", "公司名称，线名"),
    SZT_CL_CAR_NO("car_no", "车号"),
    SZT_CL_STATION("station", "站名"),
    SZT_CL_CONN_MARK("conn_mark", "联程标记"),
    SZT_CL_DEAL_MONEY("deal_money", "实收金额，整数分值，优惠后"),
    SZT_CL_EQU_NO("equ_no", "闸机号"),

    SZT_CL_CARD_NO_REVERSE("card_no_re", "反转卡号，9位字母");

    private String val;
    private String desc;

    @Override
    public String toString(){
        return new StringJoiner(", ", SztEnum.class.getSimpleName() + "[", "]")
                .add("val='" + val + "'")
                .add("desc='" + desc + "'")
                .toString();
    }

}
