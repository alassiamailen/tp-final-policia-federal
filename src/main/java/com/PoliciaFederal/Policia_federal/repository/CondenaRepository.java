package com.PoliciaFederal.Policia_federal.repository;

import com.PoliciaFederal.Policia_federal.model.Condena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CondenaRepository extends JpaRepository<Condena, Long> {
    List<Condena> findByAsaltanteId(Long asaltanteId);
}

