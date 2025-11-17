package com.PoliciaFederal.Policia_federal.repository;

import com.PoliciaFederal.Policia_federal.model.ContratoSucVig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContratoSucVigRepository extends JpaRepository<ContratoSucVig, Long> {
    List<ContratoSucVig> findByVigilanteId(Long vigilanteId);
    List<ContratoSucVig> findBySucursalId(Long sucursalId);
}

