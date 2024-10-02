package com.ex3.microservice_filiere.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ex3.microservice_filiere.Service.FiliereService;
import com.ex3.microservice_filiere.dto.FiliereDTO;
import java.util.List;
import org.springframework.http.HttpStatus;
@OpenAPIDefinition(
        info = @Info(
                title = "API de Gestion des Filieres",
                description = "Cette API permet de gérer les informations des filieres",
                version = "1.0.0"
        )
)
@RestController
@RequestMapping("/microservice-filiere/filieres")
public class FiliereController {
    @Autowired
    private FiliereService filiereService;
    @Operation(summary = "Récupérer toutes les filières",
            description = "Retourne une liste de toutes les filières.")
    @GetMapping
    public ResponseEntity<List<FiliereDTO>> getAllFilieres() {
        List<FiliereDTO> filieres = filiereService.getAllFilieres();
        return ResponseEntity.ok(filieres);
    }
    @Operation(
               summary = "Créer une nouvelle filière",
               description = "Créer une nouvelle filière avec les détails fournis."
               )
    @ApiResponse(responseCode = "201", description = "Filière créée avec succès")
    @PostMapping
    public ResponseEntity<FiliereDTO> createFiliere(@RequestBody FiliereDTO filiereDTO) {
        FiliereDTO createdFiliere = filiereService.createFiliere(filiereDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFiliere);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FiliereDTO> getFiliereById(@PathVariable Integer id) {
        FiliereDTO filiere = filiereService.getFiliereById(id);
        if (filiere != null) {
            return ResponseEntity.ok(filiere);
        } else {
            return ResponseEntity.notFound().build(); // Retourne 404 si non trouvé
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FiliereDTO> updateFiliere(@PathVariable Integer id, @RequestBody FiliereDTO filiereDTO) {
        FiliereDTO updatedFiliere = filiereService.updateFiliere(id, filiereDTO);
        if (updatedFiliere != null) {
            return ResponseEntity.ok(updatedFiliere);
        } else {
            return ResponseEntity.notFound().build(); // Retourne 404 si non trouvé
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFiliere(@PathVariable Integer id) {
        filiereService.deleteFiliere(id);
        return ResponseEntity.noContent().build(); // Retourne 204 No Content
    }
}
