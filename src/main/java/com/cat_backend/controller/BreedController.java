package com.cat_backend.controller;

import com.cat_backend.dto.BreedDTO;
import com.cat_backend.service.BreedService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/breeds")
public class BreedController {
    private final BreedService svc;
    public BreedController(BreedService svc) { this.svc = svc; }

    @GetMapping
    public List<BreedDTO> all() {
        return svc.getAllBreeds();
    }

    @GetMapping("/{breedId}")
    public BreedDTO byId(@PathVariable("breedId") String breedId) {
        return svc.getBreedById(breedId);
    }

    @GetMapping("/search")
    public List<BreedDTO> search(@RequestParam("q") String q) {
        return svc.searchBreeds(q);
    }
}
