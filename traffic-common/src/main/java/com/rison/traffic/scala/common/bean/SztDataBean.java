package com.rison.traffic.scala.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author : Rison 2021/6/16 上午9:27
 *
 */
@AllArgsConstructor
@Data
public class SztDataBean {
    private String deal_date;
    private String close_date;
    private String card_no;
    private String deal_value;
    private String deal_type;
    private String company_name;
    private String car_no;
    private String station;
    private String conn_mark;
    private String deal_money;
    private String equ_no;
}
