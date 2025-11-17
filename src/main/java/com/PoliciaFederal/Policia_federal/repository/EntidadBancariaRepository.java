package com.PoliciaFederal.Policia_federal.repository;

import com.PoliciaFederal.Policia_federal.model.EntidadBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EntidadBancariaRepository extends JpaRepository<EntidadBancaria, Long> {
    Optional<EntidadBancaria> findByNombre(String nombre);
}

