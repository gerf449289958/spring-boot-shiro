package com.neo.modules.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * @Auther: gerf
 * @Date: 2018/12/27 16:16
 * @Description:
 */
@Getter
@Setter
@ToString
public class MenuParam {

    private BigInteger id;
    private BigInteger parentId = BigInteger.ZERO;
    @NotBlank(message = "菜单名称不可以为空")
    @Length(max = 15, min = 2, message = "菜单名称长度需要在2-15个字之间")
    private String name;
    @NotNull(message = "排序不可以为空")
    @Min(value = 1,message = "排序最小为1")
    private Integer sort;
    private String icon;
    private String url;
    @NotBlank(message = "菜单类型不可以为空")
    private String type;
    @NotBlank(message = "展示状态不可以为空")
    private String available;
    private String permission;
    @Length(max = 200, message = "备注长度需要在200个字以内")
    private String remarks;

}