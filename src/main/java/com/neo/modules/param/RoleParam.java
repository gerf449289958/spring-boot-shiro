package com.neo.modules.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.math.BigInteger;

/**
 * @Auther: gerf
 * @Date: 2018/12/28 13:50
 * @Description:
 */
@Getter
@Setter
@ToString
public class RoleParam {

    private BigInteger id; // 编号
    @NotBlank(message = "角色名称不能为空")
    @Length(max = 15, min = 2, message = "角色名称长度需要在2-15个字之间")
    private String role; // 角色标识程序中判断使用,如"admin",这个是唯一的:
    @NotBlank(message = "角色描述不能为空")
    private String description; // 角色描述,UI界面显示使用
    private String available; // 是否可用,如果不可用将不会添加给用户
    @Length(max = 200, message = "备注长度需要在200个字以内")
    private String remarks; //备注
}