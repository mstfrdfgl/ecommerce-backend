package com.redifoglu.ecommerce.repository;

import com.redifoglu.ecommerce.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.authority =:authority")
    Optional<Role> findByAuthority(@Param("authority")String authority);
}
