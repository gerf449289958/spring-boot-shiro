package com.neo.modules.vo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigInteger;
import java.util.Date;

/**
 * @Auther: gerf
 * @Date: 2019/1/15 15:18
 * @Description:
 */
@Getter
@Setter
public class UserVO {

    private BigInteger id;
    private String username;//帐号
    private String name;//名称（昵称或者真实姓名，不同系统不同定义）
    private String state;//用户状态,1:正常状态,0:用户被锁定.
    @Temporal(TemporalType.TIMESTAMP)
    private Date operateTime; //操作时间
}