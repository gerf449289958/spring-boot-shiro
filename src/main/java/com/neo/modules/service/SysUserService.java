package com.neo.modules.service;

import com.google.common.collect.Lists;
import com.neo.common.base.BaseService;
import com.neo.common.config.Global;
import com.neo.common.exception.ParamException;
import com.neo.common.exception.ShiroException;
import com.neo.common.utils.BeanValidator;
import com.neo.common.utils.UpdateUtil;
import com.neo.modules.dao.SysRoleDao;
import com.neo.modules.dao.SysUserDao;
import com.neo.modules.entity.SysRole;
import com.neo.modules.entity.SysUser;
import com.neo.modules.param.UserParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

@Service
@Transactional
public class SysUserService extends BaseService {

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysRoleDao sysRoleDao;


    /**
     * 通过username查找用户信息
     * @param username
     * @return
     */
    public SysUser findByUsername(String username){
        return sysUserDao.findByUsername(username);
    }

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "users", key = "'user:'+#id")
    public SysUser findOne(BigInteger id){
        if(!sysUserDao.existsById(id)){
            throw new ShiroException("该用户不存在");
        }
        return sysUserDao.findById(id).get();
    }



    /**
     * 保存
     * @param param 用户
     */
    public void save(UserParam param) {
        BeanValidator.check(param);
        if(checkUsernameExist(param.getUsername(), param.getId())) {
            throw new ParamException("用户名已被占用");
        }
        SysUser user =  SysUser.builder()
                .username(param.getUsername())
                .password(getPwd())
                .name(param.getName())
                .state(Global.YES)
                .remarks(param.getRemarks())
                .operator(getUsername())
                .operateTime(new Date())
                .operateIp(getUserIp())
                .build();
        sysUserDao.saveAndFlush(user);
    }

    /**
     * 更新
     * @param param
     */
    public void update(UserParam param) {
        BeanValidator.check(param);
        if(checkUsernameExist(param.getUsername(), param.getId())) {
            throw new ParamException("用户名已被占用");
        }
        if(!sysUserDao.existsById(param.getId())){
            throw new ShiroException("待更新的用户不存在");
        }
        SysUser before = sysUserDao.findById(param.getId()).get();
        SysUser after =  SysUser.builder()
                .id(param.getId())
                .username(param.getUsername())
                .name(param.getName())
                .remarks(param.getRemarks())
                .operator(getUsername())
                .operateTime(new Date())
                .operateIp(getUserIp())
                .build();
        UpdateUtil.copyNonNullProperties(after,before);
        sysUserDao.saveAndFlush(after);
    }

    /**
     * 列表
     * @param pageable
     * @param param
     * @return
     */
    public Page<SysUser> findPage(Pageable pageable, UserParam param) {
        return sysUserDao.findAll(new Specification<SysUser>() {
            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = Lists.newArrayList();
                if (StringUtils.isNotBlank(param.getUsername())) {
                    list.add(cb.like(root.get("username").as(String.class), "%" + param.getUsername() + "%"));
                }
                if (StringUtils.isNotBlank(param.getName())) {
                    list.add(cb.like(root.get("name").as(String.class), "%" + param.getName() + "%"));
                }
                if (StringUtils.isNotBlank(param.getState())) {
                    list.add(cb.equal(root.get("state").as(String.class), param.getState()));
                }
                list.add(cb.equal(root.get("delFlag").as(String.class), Global.DEL_FLAG_NORMAL));
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        },pageable);
    }

    /**
     * 验证用户名唯一性
     * @param username
     * @param userId
     * @return
     */
    public boolean checkUsernameExist(String username, BigInteger userId) {
        return sysUserDao.count(new Specification<SysUser>() {
            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = Lists.newArrayList();
                if (StringUtils.isNotBlank(username)) {
                    list.add(cb.equal(root.get("username").as(String.class), username));
                }
                if (null != userId) {
                    list.add(cb.notEqual(root.get("id").as(BigInteger.class), userId));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        }) > 0;
    }

    /**
     * 删除用户
     * @param userId
     */
    public void delete(BigInteger userId) {
        if(!sysUserDao.existsById(userId)){
            throw new ParamException("待删除的用户不存在，无法删除");
        }
        sysUserDao.deleteUser(userId);
    }

    public void saveRole(BigInteger userId, List<BigInteger> roleIdList) {
        //1.验证用户
        if(!sysUserDao.existsById(userId)){
            throw new ParamException("待授权角色的用户不存在，无法删除");
        }
        //2.获取角色
        List<SysRole> sysRoleList = sysRoleDao.findByIds(roleIdList);
        if(CollectionUtils.isNotEmpty(sysRoleList)){
            //3.取消授权角色
            SysUser sysUser = findOne(userId);
            sysUser.setRoles(null);
            sysUserDao.saveAndFlush(sysUser);
            //4.保存授权角色
            sysUser.setRoles(sysRoleList);
            sysUserDao.saveAndFlush(sysUser);
        }
    }
}