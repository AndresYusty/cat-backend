package com.cat_backend.service;

import com.cat_backend.dto.ImageDTO;
import java.util.List;

public interface ImageService {
    List<ImageDTO> getImagesByBreedId(String breedId);
}
