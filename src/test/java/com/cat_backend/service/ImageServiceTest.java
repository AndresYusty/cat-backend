package com.cat_backend.service;

import com.cat_backend.dto.ImageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    RestTemplate rt;

    @InjectMocks
    ImageServiceImpl service;

    private final String baseUrl = "https://api.thecatapi.com/v1";
    private final String apiKey  = "KEY";

    @BeforeEach
    void setup() {
        service = new ImageServiceImpl(rt, baseUrl, apiKey);
    }

    @Test
    void getImagesByBreedId_deberíaDevolverLista() {
        ImageDTO img = new ImageDTO();
        img.setId("img123");
        img.setUrl("http://foo.jpg");
        when(rt.getForObject(
                baseUrl + "/images/search?breed_id=abys&limit=10&api_key=" + apiKey,
                ImageDTO[].class
        )).thenReturn(new ImageDTO[]{ img });

        List<ImageDTO> lista = service.getImagesByBreedId("abys");
        assertFalse(lista.isEmpty());
        assertEquals("img123", lista.get(0).getId());
    }
}
