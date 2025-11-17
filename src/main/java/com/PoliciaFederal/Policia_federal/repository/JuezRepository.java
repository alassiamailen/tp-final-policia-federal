package com.PoliciaFederal.Policia_federal.repository;

import com.PoliciaFederal.Policia_federal.model.Juez;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JuezRepository extends JpaRepository<Juez, Long> {
}

