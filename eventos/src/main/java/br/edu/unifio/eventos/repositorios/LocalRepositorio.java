package br.edu.unifio.eventos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.unifio.eventos.entidades.Local;

public interface LocalRepositorio extends JpaRepository<Local, Short> {

}