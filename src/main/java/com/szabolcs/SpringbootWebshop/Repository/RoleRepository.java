package com.szabolcs.SpringbootWebshop.Repository;

import com.szabolcs.SpringbootWebshop.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(String role);

}
