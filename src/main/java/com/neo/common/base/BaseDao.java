package com.neo.common.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @Auther: gerf
 * @Date: 2018/12/5 16:39
 * @Description:
 */
@NoRepositoryBean
public interface BaseDao<T,PK extends Serializable> extends JpaRepository<T, PK>, JpaSpecificationExecutor<T> {
}