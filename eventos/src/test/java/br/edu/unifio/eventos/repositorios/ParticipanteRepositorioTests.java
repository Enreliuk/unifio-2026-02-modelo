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

import br.edu.unifio.eventos.entidades.Participante;

@SpringBootTest
public class ParticipanteRepositorioTests {

    @Autowired
    private ParticipanteRepositorio participanteRepositorio;

    @Test
    public void deveSalvarUmParticipanteNovo() {
        var participante = new Participante();
        participante.setNome("Camila Lima");
        participante.setEmail("camila.lima@email.com");
        participante.setTelefone("(14) 99999-6666");

        participanteRepositorio.save(participante);

        assertNotNull(participante.getId());
        assertEquals((short) 6, participante.getId());
    }

    @Test
    public void deveBuscarUmParticipantePorId() {
        Participante participante = participanteRepositorio.findById(Short.parseShort("3")).orElseThrow();

        assertNotNull(participante);
        assertEquals("Pedro Santos", participante.getNome());
    }

    @Test
    public void deveBuscarTodosOsParticipantes() {
        List<Participante> participantes = participanteRepositorio.findAll(Sort.by("nome"));

        assertEquals(6, participantes.size());
        assertEquals("Ana Oliveira", participantes.get(0).getNome());
        assertEquals("Marcos Ferreira", participantes.get(4).getNome());
    }

    @Test
    public void deveExcluirUmParticipantePorId() {
        var participante = new Participante();
        participante.setNome("Participante Teste");
        participante.setEmail("teste@email.com");
        participante.setTelefone("(14) 90000-0000");

        participanteRepositorio.save(participante);

        assertTrue(participanteRepositorio.existsById(participante.getId()));
        participanteRepositorio.deleteById(participante.getId());
        assertFalse(participanteRepositorio.existsById(participante.getId()));
    }

    @Test
    public void deveAtualizarONomeDeUmParticipante() {
        var participante = new Participante();
        participante.setNome("Nome Teste");
        participante.setEmail("teste@email.com");
        participante.setTelefone("(14) 90000-0000");

        participanteRepositorio.save(participante);

        participante.setNome("Outro Nome Teste");
        participanteRepositorio.save(participante);

        assertEquals("Outro Nome Teste", participanteRepositorio.findById(participante.getId()).orElseThrow().getNome());
    }
}