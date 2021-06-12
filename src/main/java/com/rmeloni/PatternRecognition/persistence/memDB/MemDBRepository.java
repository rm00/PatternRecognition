package com.rmeloni.PatternRecognition.persistence.memDB;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemDBRepository extends CrudRepository<MemDBPoint, Long> {
}
