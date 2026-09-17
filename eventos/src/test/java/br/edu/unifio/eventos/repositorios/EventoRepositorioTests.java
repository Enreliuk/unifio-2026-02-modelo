package br.edu.unifio.eventos.repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import br.edu.unifio.eventos.entidades.Evento;

@SpringBootTest
public class EventoRepositorioTests {

    @Autowired
    private EventoRepositorio eventoRepositorio;

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Autowired
    private LocalRepositorio localRepositorio;

    @Autowired
    private PalestranteRepositorio palestranteRepositorio;

    @Test
    public void deveSalvarUmEventoNovo() {
        var evento = new Evento();
        evento.setNome("Bootcamp de Microserviços");
        evento.setDescricao("Aprenda arquitetura de microserviços na prática com Spring Cloud.");
        evento.setDataInicio(LocalDateTime.of(2026, 10, 5, 9, 0));
        evento.setDataFim(LocalDateTime.of(2026, 10, 5, 17, 0));
        evento.setCapacidade(Short.parseShort("40"));
        evento.setStatus("AGENDADO");

        var categoria = categoriaRepositorio.findById(Short.parseShort("1")).orElseThrow();
        var local = localRepositorio.findById(Short.parseShort("1")).orElseThrow();
        var palestrante = palestranteRepositorio.findById(Short.parseShort("1")).orElseThrow();

        evento.setCategoria(categoria);
        evento.setLocal(local);
        evento.setPalestrante(palestrante);

        eventoRepositorio.save(evento);

        assertNotNull(evento.getId());
        assertEquals((short) 6, evento.getId());
    }

    @Test
    public void deveBuscarUmEventoPorId() {
        Evento evento = eventoRepositorio.findById(Short.parseShort("3")).orElseThrow();

        assertNotNull(evento);
        assertEquals("Gestão de Projetos", evento.getNome());
    }

    @Test
    public void deveBuscarTodosOsEventos() {
        List<Evento> eventos = eventoRepositorio.findAll(Sort.by("nome"));

        assertEquals(5, eventos.size());
        assertEquals("Gestão de Projetos", eventos.get(0).getNome());
        assertEquals("Workshop de Java", eventos.get(4).getNome());
    }

    @Test
    public void deveExcluirUmEventoPorId() {
        var evento = new Evento();
        evento.setNome("Evento Teste Exclusão");
        evento.setDescricao("Descrição Teste");
        evento.setDataInicio(LocalDateTime.now());
        evento.setDataFim(LocalDateTime.now().plusHours(2));
        evento.setCapacidade(Short.parseShort("30"));
        evento.setStatus("RASCUNHO");

        var categoria = categoriaRepositorio.findById(Short.parseShort("1")).orElseThrow();
        var local = localRepositorio.findById(Short.parseShort("1")).orElseThrow();
        var palestrante = palestranteRepositorio.findById(Short.parseShort("1")).orElseThrow();

        evento.setCategoria(categoria);
        evento.setLocal(local);
        evento.setPalestrante(palestrante);

        eventoRepositorio.save(evento);

        assertTrue(eventoRepositorio.existsById(evento.getId()));
        eventoRepositorio.deleteById(evento.getId());
        assertFalse(eventoRepositorio.existsById(evento.getId()));
    }

    @Test
    public void deveAtualizarONomeDeUmEvento() {
        var evento = new Evento();
        evento.setNome("Nome Teste");
        evento.setDescricao("Descrição Teste");
        evento.setDataInicio(LocalDateTime.now());
        evento.setDataFim(LocalDateTime.now().plusHours(2));
        evento.setCapacidade(Short.parseShort("20"));
        evento.setStatus("RASCUNHO");

        var categoria = categoriaRepositorio.findById(Short.parseShort("1")).orElseThrow();
        var local = localRepositorio.findById(Short.parseShort("1")).orElseThrow();
        var palestrante = palestranteRepositorio.findById(Short.parseShort("1")).orElseThrow();

        evento.setCategoria(categoria);
        evento.setLocal(local);
        evento.setPalestrante(palestrante);

        eventoRepositorio.save(evento);

        evento.setNome("Outro Nome Teste");
        eventoRepositorio.save(evento);

        assertEquals("Outro Nome Teste", eventoRepositorio.findById(evento.getId()).orElseThrow().getNome());
    }
}