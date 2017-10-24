package com.cos.bellisimo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cos.bellisimo.domain.Catalogue;

import com.cos.bellisimo.repository.CatalogueRepository;
import com.cos.bellisimo.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Catalogue.
 */
@RestController
@RequestMapping("/api")
public class CatalogueResource {

    private final Logger log = LoggerFactory.getLogger(CatalogueResource.class);

    private static final String ENTITY_NAME = "catalogue";

    private final CatalogueRepository catalogueRepository;

    public CatalogueResource(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    /**
     * POST  /catalogues : Create a new catalogue.
     *
     * @param catalogue the catalogue to create
     * @return the ResponseEntity with status 201 (Created) and with body the new catalogue, or with status 400 (Bad Request) if the catalogue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/catalogues")
    @Timed
    public ResponseEntity<Catalogue> createCatalogue(@RequestBody Catalogue catalogue) throws URISyntaxException {
        log.debug("REST request to save Catalogue : {}", catalogue);
        if (catalogue.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new catalogue cannot already have an ID")).body(null);
        }
        Catalogue result = catalogueRepository.save(catalogue);
        return ResponseEntity.created(new URI("/api/catalogues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /catalogues : Updates an existing catalogue.
     *
     * @param catalogue the catalogue to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated catalogue,
     * or with status 400 (Bad Request) if the catalogue is not valid,
     * or with status 500 (Internal Server Error) if the catalogue couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/catalogues")
    @Timed
    public ResponseEntity<Catalogue> updateCatalogue(@RequestBody Catalogue catalogue) throws URISyntaxException {
        log.debug("REST request to update Catalogue : {}", catalogue);
        if (catalogue.getId() == null) {
            return createCatalogue(catalogue);
        }
        Catalogue result = catalogueRepository.save(catalogue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, catalogue.getId().toString()))
            .body(result);
    }

    /**
     * GET  /catalogues : get all the catalogues.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of catalogues in body
     */
    @GetMapping("/catalogues")
    @Timed
    public List<Catalogue> getAllCatalogues() {
        log.debug("REST request to get all Catalogues");
        return catalogueRepository.findAll();
        }

    /**
     * GET  /catalogues/:id : get the "id" catalogue.
     *
     * @param id the id of the catalogue to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the catalogue, or with status 404 (Not Found)
     */
    @GetMapping("/catalogues/{id}")
    @Timed
    public ResponseEntity<Catalogue> getCatalogue(@PathVariable Long id) {
        log.debug("REST request to get Catalogue : {}", id);
        Catalogue catalogue = catalogueRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(catalogue));
    }

    /**
     * DELETE  /catalogues/:id : delete the "id" catalogue.
     *
     * @param id the id of the catalogue to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/catalogues/{id}")
    @Timed
    public ResponseEntity<Void> deleteCatalogue(@PathVariable Long id) {
        log.debug("REST request to delete Catalogue : {}", id);
        catalogueRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
