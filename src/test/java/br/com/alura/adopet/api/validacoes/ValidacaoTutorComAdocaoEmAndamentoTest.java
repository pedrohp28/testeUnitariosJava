package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.*;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoTutorComAdocaoEmAndamento service;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private Tutor tutor;

    @Mock
    private Pet pet;

    @Spy
    private List<Adocao> listaDeAdocoes = new ArrayList<>();

    @Spy
    private Adocao adocao;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    @DisplayName("Validação lançará um erro")
    void validarComErro(){

        adocao = new Adocao(tutor, pet, "Motivo qualquer");
        listaDeAdocoes.add(adocao);

        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(adocaoRepository.findAll()).willReturn(listaDeAdocoes);

        assertThrows(ValidacaoException.class, () -> service.validar(dto));
    }

    @Test
    @DisplayName("Validação não lançará um erro")
    void validarSemmErro(){

        adocao = new Adocao(tutor, pet, "Motivo qualquer");
        adocao.marcarComoAprovada();
        listaDeAdocoes.add(adocao);

        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(adocaoRepository.findAll()).willReturn(listaDeAdocoes);

        assertDoesNotThrow(() -> service.validar(dto));
    }
}