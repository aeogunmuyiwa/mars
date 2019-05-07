package com.mars.repository;

import com.mars.domain.MarsVault;

import org.springframework.data.jpa.repository.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Spring Data JPA repository for the MarsVault entity.
 */
@SuppressWarnings("unused")
public interface MarsVaultRepository extends JpaRepository<MarsVault, Long> {

	@Query("select marsVault from MarsVault marsVault where marsVault.user.login = ?#{principal.username}")
	Page<MarsVault> findByUserIsCurrentUser(Pageable pageable);
}
