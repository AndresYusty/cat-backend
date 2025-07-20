package com.cat_backend.service;

import com.cat_backend.dto.BreedDTO;
import com.cat_backend.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import java.util.Arrays;
import java.util.List;

@Service
public class BreedServiceImpl implements BreedService {
    private final RestTemplate rt;
    private final String baseUrl;
    private final String apiKey;

    public BreedServiceImpl(RestTemplate rt,
                            @Value("${thecatapi.base-url}") String baseUrl,
                            @Value("${thecatapi.key}") String apiKey) {
        this.rt = rt;
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    private <T> T get(String url, Class<T> clazz) {
        return rt.getForObject(url + "&api_key=" + apiKey, clazz);
    }

    @Override
    public List<BreedDTO> getAllBreeds() {
        BreedDTO[] arr = get(baseUrl + "/breeds?limit=100", BreedDTO[].class);
        return Arrays.asList(arr);
    }

    @Override
    public BreedDTO getBreedById(String id) {
        return getAllBreeds().stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Raza no encontrada: " + id));
    }

    @Override
    public List<BreedDTO> searchBreeds(String q) {
        BreedDTO[] arr = get(baseUrl + "/breeds/search?q=" + q, BreedDTO[].class);
        return Arrays.asList(arr);
    }
}
