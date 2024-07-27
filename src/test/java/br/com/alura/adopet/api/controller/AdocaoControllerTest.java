package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.service.AdocaoService;
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
class AdocaoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AdocaoService service;

    @Autowired
    private JacksonTester<SolicitacaoAdocaoDto> jsonDtoSolic;

    @Autowired
    private JacksonTester<AprovacaoAdocaoDto> jsonDtoAprov;

    @Autowired
    private JacksonTester<ReprovacaoAdocaoDto> jsonDtoReprov;

    @Test
    @DisplayName("Devolver código 400 para solicitação de adoção com erro")
    void SolicitacaoAdocaoErro() throws Exception {

        String json = "{}";

        var response = mvc.perform(
                post("/adocoes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Devolver código 200 para solicitação de adoção correta")
    void SolicitacaoAdocaoSemErro() throws Exception {

//        String json = """
//                {
//                        "idPet": 1,
//                        "idTutor": 1,
//                        "motivo": "Motivo qualquer"
//                }
//                """;
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(1l, 1l, "Motivo qualquer");

        var response = mvc.perform(
                post("/adocoes")
                        .content(jsonDtoSolic.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Devolver código 200 para aprovação da adoção")
    void AprovacaoAdocao() throws Exception {

        AprovacaoAdocaoDto dto = new AprovacaoAdocaoDto(1l);

        var response = mvc.perform(
                put("/adocoes/aprovar")
                        .content(jsonDtoAprov.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Devolver código 200 para reprovação da adoção")
    void ReprovacaoAdocao() throws Exception {

        ReprovacaoAdocaoDto dto = new ReprovacaoAdocaoDto(1l, "Motivo");

        var response = mvc.perform(
                put("/adocoes/reprovar")
                        .content(jsonDtoReprov.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }
}