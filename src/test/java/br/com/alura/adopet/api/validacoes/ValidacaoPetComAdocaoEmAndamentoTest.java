package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoPetComAdocaoEmAndamento service;

    @Mock
    private AdocaoRepository repository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    @DisplayName("Validação lançara um erro")
    void validarComErro(){

        given(repository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(true);

        assertThrows(ValidacaoException.class, () -> service.validar(dto));
    }

    @Test
    @DisplayName("Validação não lançara um erro")
    void validarSemErro(){

        given(repository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(false);

        assertDoesNotThrow(() -> service.validar(dto));
    }
}