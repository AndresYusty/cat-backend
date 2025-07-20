package com.cat_backend.service;

import com.cat_backend.dto.BreedDTO;
import java.util.List;

public interface BreedService {
    List<BreedDTO> getAllBreeds();
    BreedDTO getBreedById(String id);
    List<BreedDTO> searchBreeds(String q);
}
