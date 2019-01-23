package com.neo.modules.dao;

import com.neo.common.base.BaseDao;
import com.neo.modules.entity.SysRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * @Auther: gerf
 * @Date: 2018/12/27 11:29
 * @Description:
 */
@Repository
public interface SysRoleDao extends BaseDao<SysRole, BigInteger> {

    @Modifying
    @Transactional
    @Query(value = "update SysRole r set r.delFlag='1' where r.id =?1")
    void deleteRole(BigInteger id);

    @Modifying
    @Transactional
    @Query(value = "update SysRole r set r.available=?2 where r.id =?1")
    void roleStatus(BigInteger id,String available);

    @Query(value = "from SysRole r where r.delFlag = '0'")
    List<SysRole> getAll();

    @Query(value = "from SysRole r where r.delFlag = '0' and r.id in (?1)")
    List<SysRole> findByIds(List<BigInteger> ids);
}