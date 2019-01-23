package com.neo.modules.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.neo.common.base.BaseService;
import com.neo.common.config.Global;
import com.neo.common.exception.ParamException;
import com.neo.common.utils.BeanValidator;
import com.neo.common.utils.LevelUtil;
import com.neo.common.utils.UpdateUtil;
import com.neo.modules.dao.SysMenuDao;
import com.neo.modules.entity.SysMenu;
import com.neo.modules.param.MenuParam;
import com.neo.modules.vo.MenuVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * @Auther: gerf
 * @Date: 2018/12/27 11:32
 * @Description:
 */
@Service
@Transactional
public class SysMenuService extends BaseService {

    @Autowired
    private SysMenuDao sysMenuDao;

    /**
     * 获取详情
     * @param menuId
     */
    public SysMenu findOne(BigInteger menuId) {
        if(!sysMenuDao.existsById(menuId)){
            throw new ParamException("查询的菜单不存在");
        }
        return sysMenuDao.findById(menuId).get();
    }

    /**
     * 获取父菜单
     * @param parentId
     * @return
     */
    public SysMenu findByParentId(BigInteger parentId) {
        if(!sysMenuDao.existsById(parentId)){
            throw new ParamException("查询的父菜单不存在");
        }
        return sysMenuDao.findById(parentId).get();
    }

    /**
     * 菜单list
     * @return
     */
    public List<MenuVO> menuTree(){
        List<SysMenu> menus = sysMenuDao.findAllMenu();
        List<MenuVO> menuVOList = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(menus)){
            for(SysMenu m:menus){
                if(m.getParentId().compareTo(BigInteger.ZERO) == 0){
                    MenuVO menuVO = new MenuVO();
                    BeanUtils.copyProperties(m,menuVO);
                    menuVOList.add(menuVO);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(menuVOList)){
            for(MenuVO mVO:menuVOList){
                List<MenuVO> menuVOs = Lists.newArrayList();
                for(SysMenu m:menus){
                    if(m.getParentId().compareTo(mVO.getId()) == 0){
                        MenuVO menuVO = new MenuVO();
                        BeanUtils.copyProperties(m,menuVO);
                        menuVOs.add(menuVO);
                    }
                }
                mVO.setChildren(menuVOs);
            }
        }
        return menuVOList;
    }


    /**
     * 新增
     * @param param
     */
    public void save(MenuParam param) {
        BeanValidator.check(param);
        if(checkNameExist(param.getName(),param.getParentId(),param.getId())) {
            throw new ParamException("菜单名称已经存在");
        }
        SysMenu menu = SysMenu.builder()
                .parentId(param.getParentId())
                .name(param.getName())
                .sort(param.getSort())
                .url(param.getUrl())
                .type(param.getType())
                .icon(param.getIcon())
                .available(param.getAvailable())
                .permission(param.getPermission())
                .remarks(param.getRemarks()).build();
        menu.setParentIds(LevelUtil.calculateLevel(getParentIds(param.getParentId()),param.getParentId()));
        menu.setOperator(getUsername());
        menu.setOperateTime(new Date());
        menu.setOperateIp(getUserIp());
        sysMenuDao.save(menu);
    }

    /**
     * 更新
     * @param param
     */
    public void update(MenuParam param) {
        BeanValidator.check(param);
        if(checkNameExist(param.getName(),param.getParentId(),param.getId())) {
            throw new ParamException("菜单名称已经存在");
        }
        if(!sysMenuDao.existsById(param.getId())){
            throw new ParamException("待更新的菜单不存在");
        }
        SysMenu before = sysMenuDao.getOne(param.getId());
        SysMenu after = SysMenu.builder()
                .id(param.getId())
                .parentId(param.getParentId())
                .name(param.getName())
                .sort(param.getSort())
                .url(param.getUrl())
                .type(param.getType())
                .icon(param.getIcon())
                .available(param.getAvailable())
                .permission(param.getPermission())
                .remarks(param.getRemarks()).build();
        after.setParentIds(LevelUtil.calculateLevel(getParentIds(param.getParentId()),param.getParentId()));
        after.setOperator(getUsername());
        after.setOperateTime(new Date());
        after.setOperateIp(getUserIp());
        UpdateUtil.copyNonNullProperties(after,before);
        updateWithChild(before,after);
    }

    /**
     * 删除菜单
     * @param menuId
     */
    public void delete(BigInteger menuId) {
        if(!sysMenuDao.existsById(menuId)){
            throw new ParamException("待删除的菜单不存在，无法删除");
        }
        if (sysMenuDao.countByParentIdAndDelFlag(menuId, Global.DEL_FLAG_NORMAL) > 0) {
            throw new ParamException("当前部门下面有子部门，无法删除");
        }
        SysMenu menu = sysMenuDao.getOne(menuId);
        sysMenuDao.deleteMenu(menu.getId());
    }

    /**
     * AllList转Json
     * @return
     */
    public String getAllListToJson(){
        List<SysMenu> menuList = sysMenuDao.findByDelFlagOrderById(Global.DEL_FLAG_NORMAL);
        List<MenuVO> menuVOS = Lists.newArrayList();
        for(SysMenu menu:menuList){
            MenuVO menuVO = new MenuVO();
            BeanUtils.copyProperties(menu,menuVO);
            menuVOS.add(menuVO);
        }
        return JSONObject.toJSONString(menuVOS);
    }


    /**
     * 更新下级菜单层级
     * @param before
     * @param after
     */
    private void updateWithChild(SysMenu before, SysMenu after) {
        String newLevelPrefix = after.getParentIds();
        String oldLevelPrefix = before.getParentIds();
        if (!after.getParentIds().equals(before.getParentIds())) {
            List<SysMenu> menuList = sysMenuDao.getChildMenuListByParentIds(before.getParentIds()+"/%");
            if (CollectionUtils.isNotEmpty(menuList)) {
                for (SysMenu menu : menuList) {
                    String parentIds = menu.getParentIds();
                    if (parentIds.indexOf(oldLevelPrefix) == 0) {
                        parentIds = newLevelPrefix + parentIds.substring(oldLevelPrefix.length());
                        menu.setParentIds(parentIds);
                    }
                }
                sysMenuDao.saveAll(menuList);
            }
        }
        sysMenuDao.saveAndFlush(after);
    }

    /**
     * 校验同级菜单名称唯一性
     * @param name
     * @param parentId
     * @param menuId
     * @return
     */
    public boolean checkNameExist(String name, BigInteger parentId, BigInteger menuId) {
        long count = sysMenuDao.count(new Specification<SysMenu>() {
            @Override
            public Predicate toPredicate(Root<SysMenu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = Lists.newArrayList();
                if (StringUtils.isNotBlank(name)) {
                    list.add(cb.equal(root.get("name").as(String.class), name));
                }
                if (null != parentId) {
                    list.add(cb.equal(root.get("parentId").as(BigInteger.class), parentId));
                }
                if (null != menuId) {
                    list.add(cb.notEqual(root.get("id").as(BigInteger.class), menuId));
                }
                list.add(cb.equal(root.get("delFlag").as(BigInteger.class), Global.DEL_FLAG_NORMAL));
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
        return count > 0;
    }

    /**
     * 获取所有父级编号
     * @param menuId
     * @return
     */
    private String getParentIds(BigInteger menuId) {
        String parentIds = null;
        if (sysMenuDao.existsById(menuId)) {
            return sysMenuDao.findById(menuId).get().getParentIds();
        }
        return parentIds;
    }
}