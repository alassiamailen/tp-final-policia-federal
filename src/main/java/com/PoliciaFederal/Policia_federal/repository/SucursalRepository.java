package com.PoliciaFederal.Policia_federal.repository;

import com.PoliciaFederal.Policia_federal.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
    List<Sucursal> findByEntidadBancariaId(Long entidadBancariaId);
}

