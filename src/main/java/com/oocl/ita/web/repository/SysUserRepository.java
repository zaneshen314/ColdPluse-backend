package com.oocl.ita.web.repository;

import com.oocl.ita.web.domain.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface SysUserRepository
		extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User>, Serializable {

	public List<User> findByEmail(String email);
}
