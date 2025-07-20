package com.cat_backend.service;

import com.cat_backend.dto.ImageDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import java.util.Arrays;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    private final RestTemplate rt;
    private final String baseUrl;
    private final String apiKey;

    public ImageServiceImpl(RestTemplate rt,
                            @Value("${thecatapi.base-url}") String baseUrl,
                            @Value("${thecatapi.key}") String apiKey) {
        this.rt = rt;
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    @Override
    public List<ImageDTO> getImagesByBreedId(String breedId) {
        ImageDTO[] arr = rt.getForObject(
                baseUrl + "/images/search?breed_id=" + breedId + "&limit=10&api_key=" + apiKey,
                ImageDTO[].class
        );
        return Arrays.asList(arr);
    }
}
