package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.AdocaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AdocaoService service;

    @Test
    @DisplayName("Deve retornar código 200 para a listagem de pets")
    void listPets() throws Exception {
        var response = mvc.perform(
                get("/pets")
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }
}