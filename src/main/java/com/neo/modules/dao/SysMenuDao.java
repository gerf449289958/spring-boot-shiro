package com.neo.modules.dao;

import com.neo.common.base.BaseDao;
import com.neo.modules.entity.SysMenu;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * @Auther: gerf
 * @Date: 2018/12/27 11:28
 * @Description:
 */
@Repository
public interface SysMenuDao extends BaseDao<SysMenu, BigInteger> {

    List<SysMenu> findByDelFlagOrderById(String delFlag);

    @Query(value = "select * from sys_menu where del_flag = '0' and parent_ids like ?1",nativeQuery = true)
    List<SysMenu> getChildMenuListByParentIds(String parentIds);

    Long countByParentIdAndDelFlag(BigInteger parentId,String delFlag);

    @Modifying
    @Transactional
    @Query(value = "update SysMenu m set m.delFlag='1' where m.id =?1")
    void deleteMenu(BigInteger id);

    @Query(value = "from SysMenu sm where sm.delFlag = '0' and sm.type = '1'")
    List<SysMenu> findAllMenu();

    @Query(value = "from SysMenu sm where sm.delFlag = '0' and sm.id in (?1)")
    List<SysMenu> findByIds(List<BigInteger> ids);

}