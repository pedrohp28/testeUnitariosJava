package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AbrigoDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @InjectMocks
    private AbrigoService service;

    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private Abrigo abrigo;

    @Test
    @DisplayName("Retornar a lista de abrigos sem ser nula")
    void listar(){

        var lista = given(abrigoRepository.findAll()).willReturn(List.of(abrigo));

        service.listar();

        then(abrigoRepository).should().findAll();
        assertNotNull(lista);
    }

    @Test
    @DisplayName("Retornar uma lista de pets dando o nome do abrigo")
    void listaPetsAbrigo(){

        String nome = "abrigo";

        given(abrigoRepository.findByNome(nome)).willReturn(Optional.of(abrigo));

        service.listarPetsDoAbrigo(nome);

        then(petRepository).should().findByAbrigo(abrigo);
    }


}