package br.com.unifacisa.gerador.web.rest;

import br.com.unifacisa.gerador.GeradorReferenciasApp;

import br.com.unifacisa.gerador.domain.Monografia;
import br.com.unifacisa.gerador.repository.MonografiaRepository;
import br.com.unifacisa.gerador.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MonografiaResource REST controller.
 *
 * @see MonografiaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeradorReferenciasApp.class)
public class MonografiaResourceIntTest {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_SUBTITULO = "AAAAAAAAAA";
    private static final String UPDATED_SUBTITULO = "BBBBBBBBBB";

    private static final String DEFAULT_EDICAO = "AAAAAAAAAA";
    private static final String UPDATED_EDICAO = "BBBBBBBBBB";

    private static final String DEFAULT_LOCAL = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL = "BBBBBBBBBB";

    private static final String DEFAULT_EDITORA = "AAAAAAAAAA";
    private static final String UPDATED_EDITORA = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_PUBLICACAO = "AAAAAAAAAA";
    private static final String UPDATED_DATA_PUBLICACAO = "BBBBBBBBBB";

    private static final String DEFAULT_PAGINAS = "AAAAAAAAAA";
    private static final String UPDATED_PAGINAS = "BBBBBBBBBB";

    private static final String DEFAULT_VOLUMES = "AAAAAAAAAA";
    private static final String UPDATED_VOLUMES = "BBBBBBBBBB";

    @Autowired
    private MonografiaRepository monografiaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restMonografiaMockMvc;

    private Monografia monografia;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MonografiaResource monografiaResource = new MonografiaResource(monografiaRepository);
        this.restMonografiaMockMvc = MockMvcBuilders.standaloneSetup(monografiaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Monografia createEntity() {
        Monografia monografia = new Monografia()
            .titulo(DEFAULT_TITULO)
            .subtitulo(DEFAULT_SUBTITULO)
            .edicao(DEFAULT_EDICAO)
            .local(DEFAULT_LOCAL)
            .editora(DEFAULT_EDITORA)
            .dataPublicacao(DEFAULT_DATA_PUBLICACAO)
            .paginas(DEFAULT_PAGINAS)
            .volumes(DEFAULT_VOLUMES);
        return monografia;
    }

    @Before
    public void initTest() {
        monografiaRepository.deleteAll();
        monografia = createEntity();
    }

    @Test
    public void createMonografia() throws Exception {
        int databaseSizeBeforeCreate = monografiaRepository.findAll().size();

        // Create the Monografia
        restMonografiaMockMvc.perform(post("/api/monografias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monografia)))
            .andExpect(status().isCreated());

        // Validate the Monografia in the database
        List<Monografia> monografiaList = monografiaRepository.findAll();
        assertThat(monografiaList).hasSize(databaseSizeBeforeCreate + 1);
        Monografia testMonografia = monografiaList.get(monografiaList.size() - 1);
        assertThat(testMonografia.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testMonografia.getSubtitulo()).isEqualTo(DEFAULT_SUBTITULO);
        assertThat(testMonografia.getEdicao()).isEqualTo(DEFAULT_EDICAO);
        assertThat(testMonografia.getLocal()).isEqualTo(DEFAULT_LOCAL);
        assertThat(testMonografia.getEditora()).isEqualTo(DEFAULT_EDITORA);
        assertThat(testMonografia.getDataPublicacao()).isEqualTo(DEFAULT_DATA_PUBLICACAO);
        assertThat(testMonografia.getPaginas()).isEqualTo(DEFAULT_PAGINAS);
        assertThat(testMonografia.getVolumes()).isEqualTo(DEFAULT_VOLUMES);
    }

    @Test
    public void createMonografiaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = monografiaRepository.findAll().size();

        // Create the Monografia with an existing ID
        monografia.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonografiaMockMvc.perform(post("/api/monografias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monografia)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Monografia> monografiaList = monografiaRepository.findAll();
        assertThat(monografiaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = monografiaRepository.findAll().size();
        // set the field null
        monografia.setTitulo(null);

        // Create the Monografia, which fails.

        restMonografiaMockMvc.perform(post("/api/monografias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monografia)))
            .andExpect(status().isBadRequest());

        List<Monografia> monografiaList = monografiaRepository.findAll();
        assertThat(monografiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEdicaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = monografiaRepository.findAll().size();
        // set the field null
        monografia.setEdicao(null);

        // Create the Monografia, which fails.

        restMonografiaMockMvc.perform(post("/api/monografias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monografia)))
            .andExpect(status().isBadRequest());

        List<Monografia> monografiaList = monografiaRepository.findAll();
        assertThat(monografiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkLocalIsRequired() throws Exception {
        int databaseSizeBeforeTest = monografiaRepository.findAll().size();
        // set the field null
        monografia.setLocal(null);

        // Create the Monografia, which fails.

        restMonografiaMockMvc.perform(post("/api/monografias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monografia)))
            .andExpect(status().isBadRequest());

        List<Monografia> monografiaList = monografiaRepository.findAll();
        assertThat(monografiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEditoraIsRequired() throws Exception {
        int databaseSizeBeforeTest = monografiaRepository.findAll().size();
        // set the field null
        monografia.setEditora(null);

        // Create the Monografia, which fails.

        restMonografiaMockMvc.perform(post("/api/monografias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monografia)))
            .andExpect(status().isBadRequest());

        List<Monografia> monografiaList = monografiaRepository.findAll();
        assertThat(monografiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDataPublicacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = monografiaRepository.findAll().size();
        // set the field null
        monografia.setDataPublicacao(null);

        // Create the Monografia, which fails.

        restMonografiaMockMvc.perform(post("/api/monografias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monografia)))
            .andExpect(status().isBadRequest());

        List<Monografia> monografiaList = monografiaRepository.findAll();
        assertThat(monografiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllMonografias() throws Exception {
        // Initialize the database
        monografiaRepository.save(monografia);

        // Get all the monografiaList
        restMonografiaMockMvc.perform(get("/api/monografias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monografia.getId())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].subtitulo").value(hasItem(DEFAULT_SUBTITULO.toString())))
            .andExpect(jsonPath("$.[*].edicao").value(hasItem(DEFAULT_EDICAO.toString())))
            .andExpect(jsonPath("$.[*].local").value(hasItem(DEFAULT_LOCAL.toString())))
            .andExpect(jsonPath("$.[*].editora").value(hasItem(DEFAULT_EDITORA.toString())))
            .andExpect(jsonPath("$.[*].dataPublicacao").value(hasItem(DEFAULT_DATA_PUBLICACAO.toString())))
            .andExpect(jsonPath("$.[*].paginas").value(hasItem(DEFAULT_PAGINAS.toString())))
            .andExpect(jsonPath("$.[*].volumes").value(hasItem(DEFAULT_VOLUMES.toString())));
    }

    @Test
    public void getMonografia() throws Exception {
        // Initialize the database
        monografiaRepository.save(monografia);

        // Get the monografia
        restMonografiaMockMvc.perform(get("/api/monografias/{id}", monografia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(monografia.getId()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.subtitulo").value(DEFAULT_SUBTITULO.toString()))
            .andExpect(jsonPath("$.edicao").value(DEFAULT_EDICAO.toString()))
            .andExpect(jsonPath("$.local").value(DEFAULT_LOCAL.toString()))
            .andExpect(jsonPath("$.editora").value(DEFAULT_EDITORA.toString()))
            .andExpect(jsonPath("$.dataPublicacao").value(DEFAULT_DATA_PUBLICACAO.toString()))
            .andExpect(jsonPath("$.paginas").value(DEFAULT_PAGINAS.toString()))
            .andExpect(jsonPath("$.volumes").value(DEFAULT_VOLUMES.toString()));
    }

    @Test
    public void getNonExistingMonografia() throws Exception {
        // Get the monografia
        restMonografiaMockMvc.perform(get("/api/monografias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateMonografia() throws Exception {
        // Initialize the database
        monografiaRepository.save(monografia);
        int databaseSizeBeforeUpdate = monografiaRepository.findAll().size();

        // Update the monografia
        Monografia updatedMonografia = monografiaRepository.findOne(monografia.getId());
        updatedMonografia
            .titulo(UPDATED_TITULO)
            .subtitulo(UPDATED_SUBTITULO)
            .edicao(UPDATED_EDICAO)
            .local(UPDATED_LOCAL)
            .editora(UPDATED_EDITORA)
            .dataPublicacao(UPDATED_DATA_PUBLICACAO)
            .paginas(UPDATED_PAGINAS)
            .volumes(UPDATED_VOLUMES);

        restMonografiaMockMvc.perform(put("/api/monografias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMonografia)))
            .andExpect(status().isOk());

        // Validate the Monografia in the database
        List<Monografia> monografiaList = monografiaRepository.findAll();
        assertThat(monografiaList).hasSize(databaseSizeBeforeUpdate);
        Monografia testMonografia = monografiaList.get(monografiaList.size() - 1);
        assertThat(testMonografia.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testMonografia.getSubtitulo()).isEqualTo(UPDATED_SUBTITULO);
        assertThat(testMonografia.getEdicao()).isEqualTo(UPDATED_EDICAO);
        assertThat(testMonografia.getLocal()).isEqualTo(UPDATED_LOCAL);
        assertThat(testMonografia.getEditora()).isEqualTo(UPDATED_EDITORA);
        assertThat(testMonografia.getDataPublicacao()).isEqualTo(UPDATED_DATA_PUBLICACAO);
        assertThat(testMonografia.getPaginas()).isEqualTo(UPDATED_PAGINAS);
        assertThat(testMonografia.getVolumes()).isEqualTo(UPDATED_VOLUMES);
    }

    @Test
    public void updateNonExistingMonografia() throws Exception {
        int databaseSizeBeforeUpdate = monografiaRepository.findAll().size();

        // Create the Monografia

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMonografiaMockMvc.perform(put("/api/monografias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monografia)))
            .andExpect(status().isCreated());

        // Validate the Monografia in the database
        List<Monografia> monografiaList = monografiaRepository.findAll();
        assertThat(monografiaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteMonografia() throws Exception {
        // Initialize the database
        monografiaRepository.save(monografia);
        int databaseSizeBeforeDelete = monografiaRepository.findAll().size();

        // Get the monografia
        restMonografiaMockMvc.perform(delete("/api/monografias/{id}", monografia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Monografia> monografiaList = monografiaRepository.findAll();
        assertThat(monografiaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Monografia.class);
        Monografia monografia1 = new Monografia();
        monografia1.setId("id1");
        Monografia monografia2 = new Monografia();
        monografia2.setId(monografia1.getId());
        assertThat(monografia1).isEqualTo(monografia2);
        monografia2.setId("id2");
        assertThat(monografia1).isNotEqualTo(monografia2);
        monografia1.setId(null);
        assertThat(monografia1).isNotEqualTo(monografia2);
    }
}
