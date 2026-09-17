package br.edu.unifio.eventos.repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import br.edu.unifio.eventos.entidades.Palestrante;

@SpringBootTest
public class PalestranteRepositorioTests {

    @Autowired
    private PalestranteRepositorio palestranteRepositorio;

    @Test
    public void deveSalvarUmPalestranteNovo() {
        var palestrante = new Palestrante();
        palestrante.setNome("Beatriz Rocha");
        palestrante.setMiniBio("Engenheira de dados e especialista em Big Analytics.");
        palestrante.setEmail("beatriz.rocha@email.com");

        palestranteRepositorio.save(palestrante);

        assertNotNull(palestrante.getId());
        assertEquals((short) 7, palestrante.getId());
    }

    @Test
    public void deveBuscarUmPalestrantePorId() {
        Palestrante palestrante = palestranteRepositorio.findById(Short.parseShort("3")).orElseThrow();

        assertNotNull(palestrante);
        assertEquals("Ricardo Oliveira", palestrante.getNome());
    }

    @Test
    public void deveBuscarTodosOsPalestrantes() {
        List<Palestrante> palestrantes = palestranteRepositorio.findAll(Sort.by("nome"));

        assertEquals(7, palestrantes.size());
        assertEquals("Beatriz Rocha", palestrantes.get(0).getNome());
        assertEquals("Mariana Santos", palestrantes.get(4).getNome());
    }

    @Test
    public void deveExcluirUmPalestrantePorId() {
        var palestrante = new Palestrante();
        palestrante.setNome("Palestrante Teste");
        palestrante.setMiniBio("Bio Teste");
        palestrante.setEmail("teste@email.com");

        palestranteRepositorio.save(palestrante);

        assertTrue(palestranteRepositorio.existsById(palestrante.getId()));
        palestranteRepositorio.deleteById(palestrante.getId());
        assertFalse(palestranteRepositorio.existsById(palestrante.getId()));
    }

    @Test
    public void deveAtualizarONomeDeUmPalestrante() {
        var palestrante = new Palestrante();
        palestrante.setNome("Nome Teste");
        palestrante.setMiniBio("Bio Teste");
        palestrante.setEmail("teste@email.com");

        palestranteRepositorio.save(palestrante);

        palestrante.setNome("Outro Nome Teste");
        palestranteRepositorio.save(palestrante);

        assertEquals("Outro Nome Teste", palestranteRepositorio.findById(palestrante.getId()).orElseThrow().getNome());
    }
}