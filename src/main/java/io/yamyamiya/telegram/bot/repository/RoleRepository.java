package io.yamyamiya.telegram.bot.repository;

import io.yamyamiya.telegram.bot.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * RoleRepository interface extends JpaRepository<Role, Integer>.
 * contains objects of {@link Role class}
 * linked with table "role" in DB
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
