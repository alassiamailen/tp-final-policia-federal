package com.PoliciaFederal.Policia_federal.repository;

import com.PoliciaFederal.Policia_federal.model.BandaCriminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BandaCriminalRepository extends JpaRepository<BandaCriminal, Long> {
    Optional<BandaCriminal> findByNumeroIdentificacion(Integer numeroIdentificacion);
}

