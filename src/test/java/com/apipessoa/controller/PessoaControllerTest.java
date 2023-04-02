package com.apipessoa.controller;

import com.apipessoa.entity.Pessoa;
import com.apipessoa.repository.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;


@SpringBootTest
@AutoConfigureMockMvc
public class PessoaControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PessoaRepository pessoaRepository;

    @Test
    void deveSalvarUmNovoRegistroNaEntidadePessoa() throws Exception {
        //Arrange
        var pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setName("Rhael Henrique");
        pessoa.setEmail("teste@teste.com");

        //Action
        when(pessoaRepository.save(any())).thenReturn(pessoa);
        this.mockMvc.perform(post("/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"name\":\"Rhael Henrique\",\"email\":\"teste@teste.com\"}")
                        .accept(MediaType.APPLICATION_JSON)
                )//Expect
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Rhael Henrique"))
                .andExpect(jsonPath("$.email").value("teste@teste.com"))
                .andExpect(content().json("{'id':1,name:'Rhael Henrique',email:'teste@teste.com'}"));
    }

    @Test
    void deveListarRegistroSalvoDaEntidadePessoa() throws Exception {
        //Arrange
        var pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setName("Rhael Henrique");
        pessoa.setEmail("teste@teste.com");

        //Action
        when(pessoaRepository.findAll()).thenReturn(List.of(pessoa));
        this.mockMvc.perform(get("/pessoa"))
                .andDo(print())//Expect
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,name:'Rhael Henrique',email:'teste@teste.com'}]"));
    }

    @Test
    void deveBuscarUmRegistroSalvoNaEntidadePessoaPeloId() throws Exception {
        //Arrange
        var pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setName("Rhael Henrique");
        pessoa.setEmail("teste@teste.com");

        //Action
        when(pessoaRepository.findById(any())).thenReturn(Optional.of(pessoa));
        this.mockMvc.perform(get("/pessoa/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )//Expect
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':1,name:'Rhael Henrique',email:'teste@teste.com'}"));
    }

    @Test
    void deveBuscarUmRegistroNaoSalvoNaEntidadePessoaPeloId() throws Exception {

        //Action
        this.mockMvc.perform(get("/pessoa/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )//Expect
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deveDeletarUmRegistroSalvoPeloId() throws Exception {
        //Arrange
        var pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setName("Rhael Henrique");
        pessoa.setEmail("teste@teste.com");

        //Action
        when(pessoaRepository.findById(any())).thenReturn(Optional.of(pessoa));
        this.mockMvc.perform(get("/pessoa/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )//Expect
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(delete("/pessoa/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deveDeletarUmRegistroNaoSalvoPeloId() throws Exception {
        //Arrange
        var pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setName("Rhael Henrique");
        pessoa.setEmail("teste@teste.com");

        //Action
        when(pessoaRepository.findById(any())).thenReturn(Optional.of(pessoa));
        this.mockMvc.perform(get("/pessoa/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )//Expect
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(delete("/pessoa/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deveAtualizarUmRegistroSalvoNaEntidadePessoaPeloId() throws Exception {
        //Arrange
        var pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setName("Rhael Henrique");
        pessoa.setEmail("teste@teste.com");

        //Action
        when(pessoaRepository.findById(any())).thenReturn(Optional.of(pessoa));
        this.mockMvc.perform(get("/pessoa/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )//Expect
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"name\":\"Rhael Rodrigues\",\"email\":\"teste@teste2.com\"}")
                        .accept(MediaType.APPLICATION_JSON)
                )//Expect
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void deveAtualizarUmRegistroNaoSalvoNaEntidadePessoaPeloId() throws Exception {
        this.mockMvc.perform(get("/pessoa/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )//Expect
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
