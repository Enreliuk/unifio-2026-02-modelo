package br.edu.unifio.eventos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.unifio.eventos.entidades.Inscricao;

public interface InscricaoRepositorio extends JpaRepository<Inscricao, Short> {

}