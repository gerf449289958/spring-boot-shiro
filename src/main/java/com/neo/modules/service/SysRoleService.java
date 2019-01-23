package com.neo.modules.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.neo.common.base.BaseService;
import com.neo.common.config.Global;
import com.neo.common.exception.ParamException;
import com.neo.common.exception.ShiroException;
import com.neo.common.utils.BeanValidator;
import com.neo.common.utils.UpdateUtil;
import com.neo.modules.dao.SysMenuDao;
import com.neo.modules.dao.SysRoleDao;
import com.neo.modules.dao.SysUserDao;
import com.neo.modules.entity.SysMenu;
import com.neo.modules.entity.SysRole;
import com.neo.modules.param.RoleParam;
import com.neo.modules.vo.MenuVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: gerf
 * @Date: 2018/12/27 11:33
 * @Description:
 */
@Service
@Transactional
public class SysRoleService extends BaseService {

    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysMenuDao sysMenuDao;

    public SysRole findOne(BigInteger id){
        if(!sysRoleDao.existsById(id)){
            throw new ShiroException("查询的角色不存在");
        }
        return sysRoleDao.findById(id).get();
    }

    public List<SysRole> getAll(){
        return sysRoleDao.getAll();
    }

    /**
     * 保存
     * @param param
     */
    public void save(RoleParam param) {
        //1.验证参数
        BeanValidator.check(param);
        //2.校验角色名称唯一性
        if(checkNameExist(param.getRole(),param.getId())){
            throw new ParamException("角色名称已经存在");
        }
        //3.参数封装和保存
        SysRole role = SysRole.builder()
                .role(param.getRole())
                .description(param.getDescription())
                .available(Global.YES)
                .remarks(param.getRemarks())
                .operator(getUsername())
                .operateTime(new Date())
                .operateIp(getUserIp()).build();
        sysRoleDao.saveAndFlush(role);
    }

    /**
     * 更新
     * @param param
     */
    public void update(RoleParam param) {
        //1.验证参数
        BeanValidator.check(param);
        //2.校验角色名称唯一性
        if(checkNameExist(param.getRole(),param.getId())){
            throw new ParamException("角色名称已经存在");
        }
        //3.验证角色是否存在
        SysRole before = sysRoleDao.findById(param.getId()).get();
        Preconditions.checkNotNull(before, "待更新的角色不存在");
        //4.参数封装和更新
        SysRole after = SysRole.builder()
                .id(param.getId())
                .role(param.getRole())
                .description(param.getDescription())
                .remarks(param.getRemarks())
                .operator(getUsername())
                .operateTime(new Date())
                .operateIp(getUserIp()).build();
        UpdateUtil.copyNonNullProperties(after,before);
        sysRoleDao.saveAndFlush(after);
    }

    /**
     * 删除
     * @param roleId
     */
    public void delete(BigInteger roleId) {
        //1.验证角色
        if(!sysRoleDao.existsById(roleId)){
            throw new ShiroException("待删除的角色不存在");
        }
        //2.验证是否存在关联用户
        SysRole role = sysRoleDao.findById(roleId).get();
        if(CollectionUtils.isNotEmpty(role.getUsers())){
            throw new ShiroException("待删除的角色下存在使用用户");
        }
        //3.删除角色
        sysRoleDao.deleteRole(roleId);
    }

    /**
     * 分页列表
     * @param pageable
     * @param param
     * @return
     */
    public Page<SysRole> findPage(Pageable pageable, RoleParam param) {
        return sysRoleDao.findAll(new Specification<SysRole>() {
            @Override
            public Predicate toPredicate(Root<SysRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = Lists.newArrayList();
                if (StringUtils.isNotBlank(param.getRole())) {
                    list.add(cb.like(root.get("role").as(String.class), "%" + param.getRole() + "%"));
                }
                if (StringUtils.isNotBlank(param.getAvailable())) {
                    list.add(cb.equal(root.get("available").as(String.class), param.getAvailable()));
                }
                list.add(cb.equal(root.get("delFlag").as(String.class), Global.DEL_FLAG_NORMAL));
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        },pageable);
    }

    /**
     * 验证角色名称唯一性
     * @param role
     * @param roleId
     * @return
     */
    public boolean checkNameExist(String role, BigInteger roleId) {
        long count = sysRoleDao.count(new Specification<SysRole>() {
            @Override
            public Predicate toPredicate(Root<SysRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = Lists.newArrayList();
                if (StringUtils.isNotBlank(role)) {
                    list.add(cb.equal(root.get("role").as(String.class), role));
                }
                if (null != roleId) {
                    list.add(cb.notEqual(root.get("id").as(BigInteger.class), roleId));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
        return count > 0;
    }

    /**
     * 停用角色
     * @param roleId
     */
    public void stop(BigInteger roleId) {
        //1.验证角色
        if(!sysRoleDao.existsById(roleId)){
            throw new ShiroException("待停用的角色不存在");
        }
        //2.停用相关用户
        SysRole role = sysRoleDao.findById(roleId).get();
        if(CollectionUtils.isNotEmpty(role.getUsers())){
            List<BigInteger> ids = role.getUsers().stream().map(user -> user.getId()).collect(Collectors.toList());
            sysUserDao.allUserStatus(ids,Global.NO);
        }
        //3.停用角色
        sysRoleDao.roleStatus(roleId,Global.NO);
    }

    /**
     * 启用
     * @param roleId
     */
    public void star(BigInteger roleId) {
        //1.验证角色
        if(!sysRoleDao.existsById(roleId)){
            throw new ShiroException("待启用的角色不存在");
        }
        //2.启用角色
        sysRoleDao.roleStatus(roleId,Global.YES);
    }

    /**
     * 获取菜单json字符串
     * @param roleId
     * @return
     */
    public String getMenuJson(BigInteger roleId){
        //1.获取角色
        SysRole sysRole = findOne(roleId);
        //2.封装菜单数据
        List<MenuVO> menuVOS = Lists.newArrayList();
        List<SysMenu> sysMenus = sysRole.getMenus();
        if(CollectionUtils.isNotEmpty(sysMenus)){
            for(SysMenu menu:sysMenus){
                MenuVO menuVO = new MenuVO();
                BeanUtils.copyProperties(menu,menuVO);
                menuVOS.add(menuVO);
            }
        }
        return JSONObject.toJSONString(menuVOS);
    }

    /**
     * 保存授权菜单
     * @param roleId
     * @param menuIds
     */
    public void saveMenu(BigInteger roleId,List<BigInteger> menuIds) {
        //1.验证角色
        if(!sysRoleDao.existsById(roleId)){
            throw new ShiroException("待更新的角色不存在");
        }
        //2.获取菜单
        List<SysMenu> menus = sysMenuDao.findByIds(menuIds);
        if(CollectionUtils.isNotEmpty(menus)){
            //3.取消授权菜单
            SysRole sysRole = findOne(roleId);
            sysRole.setMenus(null);
            sysRoleDao.saveAndFlush(sysRole);
            //4.保存角色授权菜单
            sysRole.setMenus(menus);
            sysRoleDao.saveAndFlush(sysRole);
        }
    }

}