package com.PoliciaFederal.Policia_federal.repository;

import com.PoliciaFederal.Policia_federal.model.Asaltante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AsaltanteRepository extends JpaRepository<Asaltante, Long> {
    List<Asaltante> findByBandaCriminalId(Long bandaCriminalId);
}

