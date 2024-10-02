package com.ex3.microservice_etudiant.repository;

import com.ex3.microservice_etudiant.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudiantRepository extends JpaRepository<Etudiant,Integer> {
}
