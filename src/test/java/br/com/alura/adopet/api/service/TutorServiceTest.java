package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    private TutorService tutorService;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private CadastroTutorDto dto;

    @Mock
    private AtualizacaoTutorDto dtoAtualizacao;

    @Mock
    private Tutor tutor;

    @Test
    @DisplayName("Cadastrar um tutor que já tenha sido cadastrado, laçara um erro")
    void cadastraTutorComErro() {

        given(tutorRepository.existsByTelefoneOrEmail(dto.telefone(), dto.email())).willReturn(true);

        assertThrows(ValidacaoException.class, () -> tutorService.cadastrar(dto));

    }

    @Test
    @DisplayName("Cadastrar um tutor que não tenha sido cadastrado")
    void cadastraTutorSemErro() {

        given(tutorRepository.existsByTelefoneOrEmail(dto.telefone(), dto.email())).willReturn(false);

        assertDoesNotThrow(() -> tutorService.cadastrar(dto));
        then(tutorRepository).should().save(new Tutor(dto));
    }

    @Test
    @DisplayName("Atualizar dados do tutor")
    void atualizaTutor() {

        given(tutorRepository.getReferenceById(dtoAtualizacao.id())).willReturn(tutor);

        tutorService.atualizar(dtoAtualizacao);

        then(tutor).should().atualizarDados(dtoAtualizacao);
    }
}