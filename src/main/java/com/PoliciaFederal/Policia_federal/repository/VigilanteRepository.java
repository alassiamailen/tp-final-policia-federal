package com.PoliciaFederal.Policia_federal.repository;

import com.PoliciaFederal.Policia_federal.model.Vigilante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VigilanteRepository extends JpaRepository<Vigilante, Long> {
    
    @Query("SELECT v FROM Vigilante v JOIN v.usuario u WHERE u.id = :usuarioId")
    Optional<Vigilante> findByUsuarioId(@Param("usuarioId") Long usuarioId);
}

