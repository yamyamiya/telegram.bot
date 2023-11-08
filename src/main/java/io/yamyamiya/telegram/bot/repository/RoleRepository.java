package io.yamyamiya.telegram.bot.repository;

import io.yamyamiya.telegram.bot.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
