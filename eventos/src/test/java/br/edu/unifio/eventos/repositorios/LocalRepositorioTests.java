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

import br.edu.unifio.eventos.entidades.Local;

@SpringBootTest
public class LocalRepositorioTests {

    @Autowired
    private LocalRepositorio localRepositorio;

    @Test
    public void deveSalvarUmLocalNovo() {
        var local = new Local();
        local.setNome("Sala Multimídia");
        local.setEndereço("Avenida Central, 500");
        local.setCapacidade(50);

        localRepositorio.save(local);

        assertNotNull(local.getId());
        assertEquals((short) 6, local.getId());
    }

    @Test
    public void deveBuscarUmLocalPorId() {
        Local local = localRepositorio.findById(Short.parseShort("3")).orElseThrow();

        assertNotNull(local);
        assertEquals("Centro de Eventos", local.getNome());
    }

    @Test
    public void deveBuscarTodosOsLocais() {
        List<Local> locais = localRepositorio.findAll(Sort.by("nome"));

        assertEquals(6, locais.size());
        assertEquals("Auditório Central", locais.get(0).getNome());
        assertEquals("Sala de Conferências", locais.get(4).getNome());
    }

    @Test
    public void deveExcluirUmLocalPorId() {
        var local = new Local();
        local.setNome("Local Teste");
        local.setEndereço("Rua Teste, 123");
        local.setCapacidade(30);

        localRepositorio.save(local);

        assertTrue(localRepositorio.existsById(local.getId()));
        localRepositorio.deleteById(local.getId());
        assertFalse(localRepositorio.existsById(local.getId()));
    }

    @Test
    public void deveAtualizarONomeDeUmLocal() {
        var local = new Local();
        local.setNome("Nome Teste");
        local.setEndereço("Endereço Teste");
        local.setCapacidade(20);

        localRepositorio.save(local);

        local.setNome("Outro Nome Teste");
        localRepositorio.save(local);

        assertEquals("Outro Nome Teste", localRepositorio.findById(local.getId()).orElseThrow().getNome());
    }
}