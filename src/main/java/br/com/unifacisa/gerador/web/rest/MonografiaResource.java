package br.com.unifacisa.gerador.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.unifacisa.gerador.domain.Monografia;

import br.com.unifacisa.gerador.repository.MonografiaRepository;
import br.com.unifacisa.gerador.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Monografia.
 */
@RestController
@RequestMapping("/api")
public class MonografiaResource {

    private final Logger log = LoggerFactory.getLogger(MonografiaResource.class);

    private static final String ENTITY_NAME = "monografia";

    private final MonografiaRepository monografiaRepository;

    public MonografiaResource(MonografiaRepository monografiaRepository) {
        this.monografiaRepository = monografiaRepository;
    }

    /**
     * POST  /monografias : Create a new monografia.
     *
     * @param monografia the monografia to create
     * @return the ResponseEntity with status 201 (Created) and with body the new monografia, or with status 400 (Bad Request) if the monografia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/monografias")
    @Timed
    public ResponseEntity<Monografia> createMonografia(@Valid @RequestBody Monografia monografia) throws URISyntaxException {
        log.debug("REST request to save Monografia : {}", monografia);
        if (monografia.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new monografia cannot already have an ID")).body(null);
        }
        Monografia result = monografiaRepository.save(monografia);
        return ResponseEntity.created(new URI("/api/monografias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /monografias : Updates an existing monografia.
     *
     * @param monografia the monografia to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated monografia,
     * or with status 400 (Bad Request) if the monografia is not valid,
     * or with status 500 (Internal Server Error) if the monografia couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/monografias")
    @Timed
    public ResponseEntity<Monografia> updateMonografia(@Valid @RequestBody Monografia monografia) throws URISyntaxException {
        log.debug("REST request to update Monografia : {}", monografia);
        if (monografia.getId() == null) {
            return createMonografia(monografia);
        }
        Monografia result = monografiaRepository.save(monografia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, monografia.getId().toString()))
            .body(result);
    }

    /**
     * GET  /monografias : get all the monografias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of monografias in body
     */
    @GetMapping("/monografias")
    @Timed
    public List<Monografia> getAllMonografias() {
        log.debug("REST request to get all Monografias");
        return monografiaRepository.findAll();
    }

    /**
     * GET  /monografias/:id : get the "id" monografia.
     *
     * @param id the id of the monografia to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the monografia, or with status 404 (Not Found)
     */
    @GetMapping("/monografias/{id}")
    @Timed
    public ResponseEntity<Monografia> getMonografia(@PathVariable String id) {
        log.debug("REST request to get Monografia : {}", id);
        Monografia monografia = monografiaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(monografia));
    }

    /**
     * DELETE  /monografias/:id : delete the "id" monografia.
     *
     * @param id the id of the monografia to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/monografias/{id}")
    @Timed
    public ResponseEntity<Void> deleteMonografia(@PathVariable String id) {
        log.debug("REST request to delete Monografia : {}", id);
        monografiaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
