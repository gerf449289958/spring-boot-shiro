package com.neo.modules.dao;

import com.neo.common.base.BaseDao;
import com.neo.modules.entity.SysUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

@Repository
public interface SysUserDao extends BaseDao<SysUser, BigInteger> {

    /**通过username查找用户信息;*/
    SysUser findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "update SysUser u set u.delFlag='1' where u.id =?1")
    void deleteUser(BigInteger id);

    @Modifying
    @Transactional
    @Query(value = "update SysUser u set u.state=?2 where u.id =?1")
    void userStatus(BigInteger id,String state);

    @Modifying
    @Transactional
    @Query(value = "update SysUser u set u.state=?2 where u.id in (?1)")
    void allUserStatus(List<BigInteger> ids,String state);
}