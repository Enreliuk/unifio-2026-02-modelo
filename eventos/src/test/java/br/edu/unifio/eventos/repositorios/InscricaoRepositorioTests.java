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

import br.edu.unifio.eventos.entidades.Inscricao;

@SpringBootTest
public class InscricaoRepositorioTests {

    @Autowired
    private InscricaoRepositorio inscricaoRepositorio;

    @Autowired
    private ParticipanteRepositorio participanteRepositorio;

    @Test
    public void deveSalvarUmaInscricaoNova() {
        var inscricao = new Inscricao();
        inscricao.setDataInscricao(LocalDateTime.now());
        inscricao.setStatus("CONFIRMADA");

        var participante = participanteRepositorio.findById(Short.parseShort("1")).orElseThrow();
        inscricao.setParticipante(participante);

        inscricaoRepositorio.save(inscricao);

        assertNotNull(inscricao.getId());
        assertEquals((short) 6, inscricao.getId());
    }

    @Test
    public void deveBuscarUmaInscricaoPorId() {
        Inscricao inscricao = inscricaoRepositorio.findById(Short.parseShort("3")).orElseThrow();

        assertNotNull(inscricao);
        assertEquals("PENDENTE", inscricao.getStatus());
    }

    @Test
    public void deveBuscarTodasAsInscricoes() {
        List<Inscricao> inscricoes = inscricaoRepositorio.findAll(Sort.by("status"));

        assertEquals(7, inscricoes.size());
        assertEquals("CANCELADA", inscricoes.get(0).getStatus());
        assertEquals("CONFIRMADA", inscricoes.get(4).getStatus());
    }

    @Test
    public void deveExcluirUmaInscricaoPorId() {
        var inscricao = new Inscricao();
        inscricao.setDataInscricao(LocalDateTime.now());
        inscricao.setStatus("CANCELADA");

        var participante = participanteRepositorio.findById(Short.parseShort("1")).orElseThrow();
        inscricao.setParticipante(participante);

        inscricaoRepositorio.save(inscricao);

        assertTrue(inscricaoRepositorio.existsById(inscricao.getId()));
        inscricaoRepositorio.deleteById(inscricao.getId());
        assertFalse(inscricaoRepositorio.existsById(inscricao.getId()));
    }

    @Test
    public void deveAtualizarOStatusDeUmaInscricao() {
        var inscricao = new Inscricao();
        inscricao.setDataInscricao(LocalDateTime.now());
        inscricao.setStatus("PENDENTE");

        var participante = participanteRepositorio.findById(Short.parseShort("1")).orElseThrow();
        inscricao.setParticipante(participante);

        inscricaoRepositorio.save(inscricao);

        inscricao.setStatus("CONFIRMADA");
        inscricaoRepositorio.save(inscricao);

        assertEquals("CONFIRMADA", inscricaoRepositorio.findById(inscricao.getId()).orElseThrow().getStatus());
    }
}