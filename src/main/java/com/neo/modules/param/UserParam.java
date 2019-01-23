package com.neo.modules.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.math.BigInteger;

/**
 * @Auther: gerf
 * @Date: 2018/12/28 14:26
 * @Description:
 */
@Getter
@Setter
@ToString
public class UserParam {

    private BigInteger id;
    @NotBlank(message = "用户账号不可为空")
    private String username;//帐号
    @NotBlank(message = "用户名称不可为空")
    private String name;//名称（昵称或者真实姓名，不同系统不同定义）
    private String state;//用户状态,0:禁用状态 , 1:正常状态.
    @Length(max = 200 ,message = "备注长度需要在200个字以内")
    private String remarks; //备注
}