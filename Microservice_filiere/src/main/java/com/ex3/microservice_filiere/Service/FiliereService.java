package com.ex3.microservice_filiere.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ex3.microservice_filiere.repository.FiliereRepository;
import com.ex3.microservice_filiere.dto.FiliereDTO;
import java.util.List;
import java.util.stream.Collectors;

import com.ex3.microservice_filiere.entity.Filiere;



@Service
public class FiliereService {
    @Autowired
    private FiliereRepository filiereRepository;

    public List<FiliereDTO> getAllFilieres() {
        return filiereRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public FiliereDTO createFiliere(FiliereDTO filiereDTO) {
        Filiere filiere = convertToEntity(filiereDTO);
        Filiere savedFiliere = filiereRepository.save(filiere);
        return convertToDTO(savedFiliere);
    }

    public FiliereDTO getFiliereById(Integer idFiliere) {
        return filiereRepository.findById(idFiliere)
                .map(this::convertToDTO)
                .orElse(null); // Gérer le cas où l'ID n'existe pas
    }

    public FiliereDTO updateFiliere(Integer idFiliere, FiliereDTO filiereDTO) {
        return filiereRepository.findById(idFiliere)
                .map(existingFiliere -> {
                    existingFiliere.setCode(filiereDTO.getCode());
                    existingFiliere.setLibelle(filiereDTO.getLibelle());
                    Filiere updatedFiliere = filiereRepository.save(existingFiliere);
                    return convertToDTO(updatedFiliere);
                })
                .orElse(null); // Gérer le cas où l'ID n'existe pas
    }

    public void deleteFiliere(Integer idFiliere) {
        filiereRepository.deleteById(idFiliere);
    }

    private FiliereDTO convertToDTO(Filiere filiere) {
        FiliereDTO dto = new FiliereDTO();
        dto.setIdFiliere(filiere.getIdFiliere());
        dto.setCode(filiere.getCode());
        dto.setLibelle(filiere.getLibelle());
        return dto;
    }

    private Filiere convertToEntity(FiliereDTO dto) {
        Filiere filiere = new Filiere();
        filiere.setIdFiliere(dto.getIdFiliere());
        filiere.setCode(dto.getCode());
        filiere.setLibelle(dto.getLibelle());
        return filiere;
    }
}
