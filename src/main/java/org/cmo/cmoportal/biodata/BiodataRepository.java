package org.cmo.cmoportal.biodata;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BiodataRepository extends JpaRepository<Biodata, Integer> {
    Optional<Biodata> findByUserId(Integer integer);
}
