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
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;//主键.
    private String name;//名称.
    private String type;//资源类型，[menu|button]
    private String url;//资源路径.
    private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
    private BigInteger parentId; //父编号
    private String parentIds; //父编号列表
    private String available; //是否可用
    private int sort; //排序
    private String icon; //图标
    private String remarks; //备注
    private String operator; //操作者
    @Temporal(TemporalType.TIMESTAMP)
    private Date operateTime; //操作时间
    private String operateIp; //操作Ip
    private String delFlag; //删除标记
    @ManyToMany
    @JoinTable(name="SysRoleMenu",joinColumns={@JoinColumn(name="menuId")},inverseJoinColumns={@JoinColumn(name="roleId")})
    private List<SysRole> roles;
}