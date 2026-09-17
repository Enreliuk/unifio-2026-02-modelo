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

import br.edu.unifio.eventos.entidades.Categoria;

@SpringBootTest
public class CategoriaRepositorioTests {

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Test
    public void deveSalvarUmaCategoriaNova() {
        var categoria = new Categoria();
        categoria.setNome("Design & UX");
        categoria.setDescricao("Cursos e eventos focados em interface e experiência do usuário");

        categoriaRepositorio.save(categoria);

        assertNotNull(categoria.getId());
        assertEquals((short) 6, categoria.getId());
    }

    @Test
    public void deveBuscarUmaCategoriaPorId() {
        Categoria categoria = categoriaRepositorio.findById(Short.parseShort("3")).orElseThrow();

        assertNotNull(categoria);
        assertEquals("Eletrônicos", categoria.getNome());
    }

    @Test
    public void deveBuscarTodasAsCategorias() {
        List<Categoria> categorias = categoriaRepositorio.findAll(Sort.by("nome"));

        assertEquals(7, categorias.size());
        assertEquals("Design & UX", categorias.get(0).getNome());
        assertEquals("Moda", categorias.get(4).getNome());
    }

    @Test
    public void deveExcluirUmaCategoriaPorId() {
        var categoria = new Categoria();
        categoria.setNome("Categoria Teste");
        categoria.setDescricao("Descrição Teste");

        categoriaRepositorio.save(categoria);

        assertTrue(categoriaRepositorio.existsById(categoria.getId()));
        categoriaRepositorio.deleteById(categoria.getId());
        assertFalse(categoriaRepositorio.existsById(categoria.getId()));
    }

    @Test
    public void deveAtualizarONomeDeUmaCategoria() {
        var categoria = new Categoria();
        categoria.setNome("Nome Teste");
        categoria.setDescricao("Descrição Teste");

        categoriaRepositorio.save(categoria);

        categoria.setNome("Outro Nome Teste");
        categoriaRepositorio.save(categoria);

        assertEquals("Outro Nome Teste", categoriaRepositorio.findById(categoria.getId()).orElseThrow().getNome());
    }
}