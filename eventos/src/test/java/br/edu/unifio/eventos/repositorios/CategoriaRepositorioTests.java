package br.edu.unifio.eventos.repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
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
        categoria.setNome("Cosméticos");
        categoria.setDescricao("Perfumes e loções");

        categoriaRepositorio.save(categoria);

        assertNotNull(categoria.getId());
    }

    @Test
    public void deveBuscarUmaCategoriaPorId() {
        var categoria = categoriaRepositorio.findById((short) 1).orElse(null);

        assertNotNull(categoria);
        assertEquals("Informática", categoria.getNome());
    }

    @Test
    public void deveBuscarTodosAsCategorias() {
        List<Categoria> categorias = categoriaRepositorio.findAll(Sort.by("nome"));

        assertEquals(6, categorias.size());
        assertEquals("Cosméticos", categorias.get(0).getNome());
        assertEquals("Eletrônicos", categorias.get(1).getNome());
    }

    @Test
    public void deveExcluirUmaCategoriaPorId () {
        var categoria = categoriaRepositorio.findById((short) 1).orElse(null);
        categoriaRepositorio.delete(categoria);
        assertFalse(categoriaRepositorio.existsById((short) 1));
        
        var categoria = categoriaRepositorio.findById(Short.parseShort("1")).orElseThrow();
        categoria.setCategoria(categoria);

        categoriaRepositorio.save(categoria);

        assertTrue(categoriaRepositorio.existsById(categoria.getId()));
        categoriaRepositorio.deleteById(categoria.getId());
        assertFalse(categoriaRepositorio.existsById(categoria.getId()));
    }
}