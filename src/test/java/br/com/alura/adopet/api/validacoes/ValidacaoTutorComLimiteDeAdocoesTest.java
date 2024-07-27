package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
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

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComLimiteDeAdocoesTest {

    @InjectMocks
    private ValidacaoTutorComLimiteDeAdocoes service;
    @Mock
    private AdocaoRepository adocaoRepository;
    @Mock
    private TutorRepository tutorRepository;
    @Mock
    private SolicitacaoAdocaoDto dto;
    @Mock
    private Tutor tutor;
    @Mock
    private Pet pet;
    @Spy
    private List<Adocao> listaDeAdocoes = new ArrayList<>();
    @Spy
    private Adocao adocao;

    @Test
    @DisplayName("Validador retornará um erro")
    void validarComErro(){

        adocao = new Adocao(tutor, pet, "Motivo");
        adocao.marcarComoAprovada();
        for (int i = 0; i < 5; i++){
            listaDeAdocoes.add(adocao);
        }
        given(adocaoRepository.findAll()).willReturn(listaDeAdocoes);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);

        assertThrows(ValidacaoException.class, () -> service.validar(dto));
    }

    @Test
    @DisplayName("Validador não retornará um erro")
    void validarSemErro(){

        adocao = new Adocao(tutor, pet, "Motivo");
        adocao.marcarComoAprovada();
        listaDeAdocoes.add(adocao);

        given(adocaoRepository.findAll()).willReturn(listaDeAdocoes);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);

        assertDoesNotThrow(() -> service.validar(dto));
    }
}
