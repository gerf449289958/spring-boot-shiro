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
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id; // 编号
    private String role; // 角色标识程序中判断使用,如"admin",这个是唯一的:
    private String description; // 角色描述,UI界面显示使用
    private String available; // 是否可用,如果不可用将不会添加给用户
    private String remarks; //备注
    private String operator; //操作者
    @Temporal(TemporalType.TIMESTAMP)
    private Date operateTime; //操作时间
    private String operateIp; //操作Ip
    private String delFlag; //删除标记

    //角色 -- 权限关系：多对多关系
    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(name="SysRoleMenu",joinColumns={@JoinColumn(name="roleId")},inverseJoinColumns={@JoinColumn(name="menuId")})
    private List<SysMenu> menus;

    // 用户 - 角色关系定义
    @ManyToMany
    @JoinTable(name="SysUserRole",joinColumns={@JoinColumn(name="roleId")},inverseJoinColumns={@JoinColumn(name="userId")})
    private List<SysUser> users;// 一个角色对应多个用户
}