package com.mars.service;

import com.mars.domain.MarsVault;
import com.mars.repository.MarsVaultRepository;
import com.mars.security.SecurityUtils;
import com.mars.service.dto.MarsVaultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Service Implementation for managing MarsVault.
 */
@Service
@Transactional
public class MarsVaultService {

	private final Logger log = LoggerFactory.getLogger(MarsVaultService.class);

	@Inject
	private MarsVaultRepository marsVaultRepository;

	@Inject
	private UserService userService;

	/**
	 * Save a marsVault.
	 *
	 * @param dto the entity to save
	 * @return the persisted entity
	 */
	public MarsVaultDTO save(MarsVaultDTO dto) {
		log.debug("Request to save MarsVault : {}", dto);

		MarsVault marsVault;

		if (dto.getId() != null) {
			marsVault = marsVaultRepository.findOne(dto.getId());

			if (marsVault == null) {
				return null;
			}
		} else {
			marsVault = new MarsVault();
			marsVault.setUser(userService.getCurrentUser());
		}

		marsVault.site(dto.getSite())
			.login(dto.getLogin())
			.password(dto.getPassword());

		return new MarsVaultDTO(marsVaultRepository.save(marsVault));
	}

	/**
	 * Get all the marsVaults of current user.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<MarsVaultDTO> findAllOfCurrentUser(Pageable pageable) {
		log.debug("Request to get all MarsVaults of current user");
		return marsVaultRepository.findByUserIsCurrentUser(pageable).map(MarsVaultDTO::new);
	}

	/**
	 * Get one marsVault by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public MarsVaultDTO findOne(Long id) {
		log.debug("Request to get MarsVault : {}", id);
		return new MarsVaultDTO(marsVaultRepository.findOne(id));
	}

	/**
	 * Delete the marsVault by id.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id) {
		log.debug("Request to delete MarsVault : {}", id);

		if (marsVaultRepository.findOne(id).getUser().equals(SecurityUtils.getCurrentUser())) {
            marsVaultRepository.delete(id);
        }
	}
}
