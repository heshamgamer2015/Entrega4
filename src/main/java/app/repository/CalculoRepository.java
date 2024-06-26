package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Saida;

public interface CalculoRepository extends JpaRepository<Saida, Long> {

}
