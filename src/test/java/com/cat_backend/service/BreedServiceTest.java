package com.cat_backend.service;

import com.cat_backend.dto.BreedDTO;
import com.cat_backend.exception.ResourceNotFoundException;
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
class BreedServiceTest {

    @Mock
    RestTemplate rt;

    @InjectMocks
    BreedServiceImpl service;

    private final String baseUrl = "https://api.thecatapi.com/v1";
    private final String apiKey  = "KEY";

    @BeforeEach
    void setup() {
        service = new BreedServiceImpl(rt, baseUrl, apiKey);
    }

    @Test
    void getAllBreeds_deberíaDevolverLista() {
        BreedDTO b = new BreedDTO();
        b.setId("abys");
        b.setName("Abyssinian");
        when(rt.getForObject(baseUrl + "/breeds?limit=100&api_key=" + apiKey, BreedDTO[].class))
                .thenReturn(new BreedDTO[]{ b });

        List<BreedDTO> lista = service.getAllBreeds();
        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("abys", lista.get(0).getId());
    }

    @Test
    void getBreedById_encontrado() {
        BreedDTO b1 = new BreedDTO(); b1.setId("foo");
        BreedDTO b2 = new BreedDTO(); b2.setId("bar");
        when(rt.getForObject(baseUrl + "/breeds?limit=100&api_key=" + apiKey, BreedDTO[].class))
                .thenReturn(new BreedDTO[]{ b1, b2 });

        BreedDTO result = service.getBreedById("bar");
        assertEquals("bar", result.getId());
    }

    @Test
    void getBreedById_noEncontrado_debeLanzar() {
        when(rt.getForObject(baseUrl + "/breeds?limit=100&api_key=" + apiKey, BreedDTO[].class))
                .thenReturn(new BreedDTO[]{});
        assertThrows(ResourceNotFoundException.class,
                () -> service.getBreedById("no-existe"));
    }

    @Test
    void searchBreeds_devolverResultado() {
        BreedDTO b = new BreedDTO();
        b.setId("xyz");
        when(rt.getForObject(baseUrl + "/breeds/search?q=siam&api_key=" + apiKey, BreedDTO[].class))
                .thenReturn(new BreedDTO[]{ b });

        List<BreedDTO> result = service.searchBreeds("siam");
        assertEquals(1, result.size());
        assertEquals("xyz", result.get(0).getId());
    }
}
