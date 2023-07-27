package com.petopia.pointshop.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PointShopTO {
    private String ps_seq;
    private String ps_cate;
    private String ps_name;
    private String ps_price;
    private String ps_content;
    private String ps_img;
    private String ps_dt;
}