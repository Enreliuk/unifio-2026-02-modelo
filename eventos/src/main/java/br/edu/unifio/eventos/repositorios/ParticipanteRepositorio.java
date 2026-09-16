package br.edu.unifio.eventos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.unifio.eventos.entidades.Participante;

public interface ParticipanteRepositorio extends JpaRepository<Participante, Short> {

}