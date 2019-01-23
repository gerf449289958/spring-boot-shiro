package com.neo.modules.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;

/**
 * @Auther: gerf
 * @Date: 2018/12/24 14:19
 * @Description:
 */
@Getter
@Setter
public class MenuVO {

    private BigInteger id;

    private BigInteger parentId;

    private String name;

    private Integer sort;

    private String url;

    private String icon;

    private String available;

    private String type;

    private String permission;

    private List<MenuVO> children;

}