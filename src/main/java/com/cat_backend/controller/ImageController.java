package com.cat_backend.controller;

import com.cat_backend.dto.ImageDTO;
import com.cat_backend.service.ImageService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/imagesbybreedid")
public class ImageController {
    private final ImageService svc;
    public ImageController(ImageService svc) { this.svc = svc; }

    @GetMapping
    public List<ImageDTO> images(@RequestParam("breedId") String breedId) {
        return svc.getImagesByBreedId(breedId);
    }
}
