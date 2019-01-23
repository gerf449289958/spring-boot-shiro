package com.neo.modules.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String username;//帐号
    private String name;//名称（昵称或者真实姓名，不同系统不同定义）
    private String password; //密码
    private String state;//用户状态,1:正常状态,0:用户被锁定.
    private String remarks; //备注
    private String operator; //操作者
    @Temporal(TemporalType.TIMESTAMP)
    private Date operateTime; //操作时间
    private String operateIp; //操作Ip
    private String delFlag; //删除标记
    @ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据
    @JoinTable(name = "SysUserRole", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns ={@JoinColumn(name = "roleId") })
    private List<SysRole> roles;// 一个用户具有多个角色
}