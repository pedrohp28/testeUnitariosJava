package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.service.TutorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TutorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TutorService service;

    @Autowired
    private JacksonTester<CadastroTutorDto> jsonDtoCad;

    @Autowired
    private JacksonTester<AtualizacaoTutorDto> jsonDtoAtual;

    @Test
    @DisplayName("Deverá retornar código 200 para a solicitação de cadastramento")
    void cadastrarTutorSemErro() throws Exception {

        CadastroTutorDto dto = new CadastroTutorDto("tutor", "(11)0000-1111", "email@teste.com");

        var response = mvc.perform(
                post("/tutores")
                        .content(jsonDtoCad.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deverá retornar código 400 para a solicitação de cadastramento inválida")
    void cadastrarTutorComErro() throws Exception {

        String json = "{}";

        var response = mvc.perform(
                post("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deverá retornar código 200 para a solicitação de atualização")
    void atualizarTutorSemErro() throws Exception {

        AtualizacaoTutorDto dto = new AtualizacaoTutorDto( 1l, "tutor", "(11)0000-1111", "email@teste.com");

        var response = mvc.perform(
                put("/tutores")
                        .content(jsonDtoAtual.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deverá retornar código 400 para a solicitação de atualização inválida")
    void atualizarTutorComErro() throws Exception {

        String json = "{}";

        var response = mvc.perform(
                put("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }
}