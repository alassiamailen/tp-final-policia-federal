package com.PoliciaFederal.Policia_federal.repository;

import com.PoliciaFederal.Policia_federal.model.Asalto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AsaltoRepository extends JpaRepository<Asalto, Long> {
    List<Asalto> findBySucursalId(Long sucursalId);
    List<Asalto> findByAsaltanteId(Long asaltanteId);
}

