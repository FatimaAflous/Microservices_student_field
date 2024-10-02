package com.ex3.microservice_etudiant.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.ex3.microservice_etudiant.repository.EtudiantRepository;
import com.ex3.microservice_etudiant.entity.Etudiant;
import com.ex3.microservice_etudiant.dto.EtudiantDTO;
import com.ex3.microservice_etudiant.dto.Filiere;

@Service
public class EtudiantService {
    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<EtudiantDTO> getAllEtudiants() {
        return etudiantRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EtudiantDTO createEtudiant(EtudiantDTO etudiantDTO) {
        Etudiant etudiant = convertToEntity(etudiantDTO);
        Etudiant savedEtudiant = etudiantRepository.save(etudiant);
        return convertToDTO(savedEtudiant);
    }

    public EtudiantDTO updateEtudiant(Integer id, EtudiantDTO etudiantDTO) {
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findById(id);
        if (optionalEtudiant.isPresent()) {
            Etudiant etudiant = optionalEtudiant.get();
            etudiant.setNom(etudiantDTO.getNom());
            etudiant.setPrenom(etudiantDTO.getPrenom());
            etudiant.setCne(etudiantDTO.getCne());
            etudiant.setIdFiliere(etudiantDTO.getIdFiliere());
            Etudiant updatedEtudiant = etudiantRepository.save(etudiant);
            return convertToDTO(updatedEtudiant);
        }
        return null;
    }


    public EtudiantDTO getEtudiantById(Integer id) {
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findById(id);
        return optionalEtudiant.map(this::convertToDTO).orElse(null);
    }

    public void deleteEtudiant(Integer id) {
        etudiantRepository.deleteById(id);
    }

    private EtudiantDTO convertToDTO(Etudiant etudiant) {
        EtudiantDTO dto = new EtudiantDTO();
        dto.setIdEtudiant(etudiant.getIdEtudiant());
        dto.setNom(etudiant.getNom());
        dto.setPrenom(etudiant.getPrenom());
        dto.setCne(etudiant.getCne());
        dto.setIdFiliere(etudiant.getIdFiliere());
        if (etudiant.getIdFiliere() != null) {
            Filiere filiere = restTemplate.getForObject("http://localhost:8080/microservice-filiere/filieres/" + etudiant.getIdFiliere(), Filiere.class);
            dto.setFiliere(filiere);
        }

        return dto;
    }


    private Etudiant convertToEntity(EtudiantDTO dto) {
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(dto.getIdEtudiant());
        etudiant.setNom(dto.getNom());
        etudiant.setPrenom(dto.getPrenom());
        etudiant.setCne(dto.getCne());
        etudiant.setIdFiliere(dto.getIdFiliere());

        return etudiant;
    }
}
