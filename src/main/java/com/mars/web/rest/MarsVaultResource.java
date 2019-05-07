package com.mars.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mars.service.MarsVaultService;
import com.mars.service.dto.MarsVaultDTO;
import com.mars.web.rest.util.HeaderUtil;
import com.mars.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MarsVault.
 */
@RestController
@RequestMapping("/api")
public class MarsVaultResource {

	private final Logger log = LoggerFactory.getLogger(MarsVaultResource.class);

	@Inject
	private MarsVaultService marsVaultService;

	/**
	 * POST /mars-vaults : Create a new marsVault.
	 *
	 * @param marsVault the marsVault to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new marsVault, or with status 400 (Bad
	 * Request) if the marsVault has already an ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PostMapping("/mars-vaults")
	@Timed
	public ResponseEntity<MarsVaultDTO> createMarsVault(@Valid @RequestBody MarsVaultDTO marsVault) throws URISyntaxException {
		log.debug("REST request to save MarsVault : {}", marsVault);
		if (marsVault.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("marsVault", "idexists", "A new marsVault cannot already have an ID")).body(null);
		}
		MarsVaultDTO result = marsVaultService.save(marsVault);
		return ResponseEntity.created(new URI("/api/mars-vaults/" + result.getId()))
			.headers(HeaderUtil.createEntityCreationAlert("marsVault", result.getId().toString()))
			.body(result);
	}

	/**
	 * PUT /mars-vaults : Updates an existing marsVault.
	 *
	 * @param marsVault the marsVault to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated marsVault, or with status 400 (Bad
	 * Request) if the marsVault is not valid, or with status 500 (Internal Server Error) if the marsVault couldnt be
	 * updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("/mars-vaults")
	@Timed
	public ResponseEntity<MarsVaultDTO> updateMarsVault(@Valid @RequestBody MarsVaultDTO marsVault) throws URISyntaxException {
		log.debug("REST request to update MarsVault : {}", marsVault);
		if (marsVault.getId() == null) {
			return createMarsVault(marsVault);
		}
		MarsVaultDTO result = marsVaultService.save(marsVault);
		return ResponseEntity.ok()
			.headers(HeaderUtil.createEntityUpdateAlert("marsVault", marsVault.getId().toString()))
			.body(result);
	}

	/**
	 * GET /mars-vaults : get all the marsVaults.
	 *
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of marsVaults in body
	 * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
	 */
	@GetMapping("/mars-vaults")
	@Timed
	public ResponseEntity<List<MarsVaultDTO>> getAllMarsVaults(@ApiParam Pageable pageable)
		throws URISyntaxException {
		log.debug("REST request to get a page of MarsVaults");
		Page<MarsVaultDTO> page = marsVaultService.findAllOfCurrentUser(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mars-vaults");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /mars-vaults/:id : get the "id" marsVault.
	 *
	 * @param id the id of the marsVault to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the marsVault, or with status 404 (Not Found)
	 */
	@GetMapping("/mars-vaults/{id}")
	@Timed
	public ResponseEntity<MarsVaultDTO> getMarsVault(@PathVariable Long id) {
		log.debug("REST request to get MarsVault : {}", id);
		MarsVaultDTO marsVault = marsVaultService.findOne(id);
		return Optional.ofNullable(marsVault)
			.map(result -> new ResponseEntity<>(
					result,
					HttpStatus.OK))
			.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /mars-vaults/:id : delete the "id" marsVault.
	 *
	 * @param id the id of the marsVault to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/mars-vaults/{id}")
	@Timed
	public ResponseEntity<Void> deleteMarsVault(@PathVariable Long id) {
		log.debug("REST request to delete MarsVault : {}", id);
		marsVaultService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("marsVault", id.toString())).build();
	}

}
