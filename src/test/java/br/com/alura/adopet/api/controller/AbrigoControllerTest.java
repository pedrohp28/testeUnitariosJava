package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AbrigoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AbrigoService abrigoService;

    @MockBean
    private PetService petService;

    @Autowired
    private JacksonTester<CadastroAbrigoDto> jsonDtoAbrigo;

    @Autowired
    private JacksonTester<CadastroPetDto> jsonDtoPet;

    @Test
    @DisplayName("Deverá retornar código 200 para a listagem de adoções")
    void listarAbrigos() throws Exception {
        var response = mvc.perform(
                get("/abrigos")
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deverá retornar código 200 para a listagem de pets do abrigo informado pelo nome")
    void listarPetsPeloNomeAbrigo() throws Exception {

        String nome = "Abrigo";

        var response = mvc.perform(
                get("/abrigos/{nome}/pets", nome)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deverá retornar código 200 para a listagem de pets do abrigo informado pelo id")
    void listarPetsPeloIdAbrigo() throws Exception {

        String id = "1";

        var response = mvc.perform(
                get("/abrigos/{id}/pets", id)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deverá retornar código 404 para abrigo informado pelo nome não encontrado")
    void listarPetsPeloNomeAbrigoNaoEncontrado() throws Exception {

        String nome = "Abrigo";
        given(abrigoService.listarPetsDoAbrigo(nome)).willThrow(ValidacaoException.class);

        var response = mvc.perform(
                get("/abrigos/{nome}/pets", nome)
        ).andReturn().getResponse();

        assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("Deverá retornar código 404 para abrigo informado pelo id não encontrado")
    void listarPetsPeloIdAbrigoNaoEncontrado() throws Exception {

        String id = "1";
        given(abrigoService.listarPetsDoAbrigo(id)).willThrow(ValidacaoException.class);

        var response = mvc.perform(
                get("/abrigos/{nome}/pets", id)
        ).andReturn().getResponse();

        assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("Deverá retornar código 200 para solicitação de cadastramento de abrigo")
    void cadastrarAbrigoSemErro() throws Exception {

        CadastroAbrigoDto dto = new CadastroAbrigoDto("abrigo", "(11)0000-1111", "exemplo@teste.com");
        var response = mvc.perform(
                post("/abrigos")
                        .content(jsonDtoAbrigo.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deverá retornar código 400 para solicitação de cadastramento de abrigo inválida")
    void cadastrarAbrigoComErro() throws Exception {

        String json = "{}";
        var response = mvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deverá retornar código 200 para solicitação de cadastramento de pet pelo nome do abrigo informado")
    void cadastrarPetPeloNomeAbrigoo() throws Exception {

        CadastroPetDto dto = new CadastroPetDto(TipoPet.GATO, "gato", "gato-amarelo", 1, "amarelo", 8.5f);
        String nome = "abrigo";
        var response = mvc.perform(
                post("/abrigos/{nome}/pets", nome)
                        .content(jsonDtoPet.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deverá retornar código 200 para solicitação de cadastramento de pet pelo id do abrigo informado")
    void cadastrarPetPeloIdAbrigoo() throws Exception {

        CadastroPetDto dto = new CadastroPetDto(TipoPet.GATO, "gato", "gato-amarelo", 1, "amarelo", 8.5f);
        String id = "1";
        var response = mvc.perform(
                post("/abrigos/{nome}/pets", id)
                        .content(jsonDtoPet.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deverá retornar código 404 para solicitação de cadastramento de pet pelo nome do abrigo não encontrado")
    void cadastrarPetPeloNomeAbrigooNaoEncontrado() throws Exception {

        CadastroPetDto dto = new CadastroPetDto(TipoPet.GATO, "gato", "gato-amarelo", 1, "amarelo", 8.5f);
        String nome = "abrigo";
        given(abrigoService.carregarAbrigo(nome)).willThrow(ValidacaoException.class);
        var response = mvc.perform(
                post("/abrigos/{nome}/pets", nome)
                        .content(jsonDtoPet.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("Deverá retornar código 404 para solicitação de cadastramento de pet pelo id do abrigo não encontrado")
    void cadastrarPetPeloIdAbrigooNaoEncontrado() throws Exception {

        CadastroPetDto dto = new CadastroPetDto(TipoPet.GATO, "gato", "gato-amarelo", 1, "amarelo", 8.5f);
        String id = "1";
        given(abrigoService.carregarAbrigo(id)).willThrow(ValidacaoException.class);
        var response = mvc.perform(
                post("/abrigos/{nome}/pets", id)
                        .content(jsonDtoPet.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(404, response.getStatus());
    }
}