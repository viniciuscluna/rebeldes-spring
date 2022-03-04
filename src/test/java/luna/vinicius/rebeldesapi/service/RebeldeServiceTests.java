package luna.vinicius.rebeldesapi.service;

import luna.vinicius.rebeldesapi.dto.NegociacaoDto;
import luna.vinicius.rebeldesapi.model.ItemInventario;
import luna.vinicius.rebeldesapi.model.Localizacao;
import luna.vinicius.rebeldesapi.model.Rebelde;
import luna.vinicius.rebeldesapi.repository.ItemInventarioRepository;
import luna.vinicius.rebeldesapi.repository.RebeldeRepository;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
public class RebeldeServiceTests {

    @MockBean
    private RebeldeRepository repository;

    @MockBean
    private ItemInventarioRepository itemInventarioRepository;

    @Autowired
    private  RebeldeService service;

    @Test
    void ShouldInsertAndReturnRebelde() {
        Rebelde rebelde = new Rebelde();

        rebelde.setItens(new ArrayList<>());

        var created = new Rebelde();
        created.setItens(new ArrayList<>());
        Mockito.when(this.repository.save(rebelde)).thenReturn(created);

        Mockito.when(this.itemInventarioRepository.saveAll(Mockito.anyCollection())).thenReturn(Mockito.any());

        var response = this.service.inserir(rebelde);

        //Assert
        Mockito.verify(this.repository).save(rebelde);
        Mockito.verify(this.itemInventarioRepository).saveAll(rebelde.getItens());
        Assertions.assertEquals(created, response);
    }

    @Test
    void ShouldReportTraidor() throws Exception {
        Integer rebeldeId = 1;

        var expectedRebelde = new Rebelde();
        expectedRebelde.setReportadoTraidor(0);

        Mockito.when(this.repository.findById(rebeldeId)).thenReturn(Optional.of(expectedRebelde));


        var response = this.service.reportarTraidor(rebeldeId);

        //Assert
        Mockito.verify(this.repository).findById(rebeldeId);

        Assertions.assertEquals("Reportado", response);
        Assertions.assertEquals(1, expectedRebelde.getReportadoTraidor());
    }

    @Test
    void ShouldReportAndSetTraidor() throws Exception {
        Integer rebeldeId = 1;

        var expectedRebelde = new Rebelde();
        expectedRebelde.setReportadoTraidor(2);

        Mockito.when(this.repository.findById(rebeldeId)).thenReturn(Optional.of(expectedRebelde));


        var response = this.service.reportarTraidor(rebeldeId);

        //Assert
        Mockito.verify(this.repository).findById(rebeldeId);

        Assertions.assertEquals("Reportado e virou traidor", response);
        Assertions.assertTrue(expectedRebelde.getTraidor());
    }

    @Test
    void ShouldThrowExceptionWhenTraidorIsNotFound() throws Exception {
        //Arrange
        Integer rebeldeId = 1;
        Mockito.when(this.repository.findById(rebeldeId)).thenReturn(Optional.empty());


        //Act
        var exception = Assertions.assertThrows(Exception.class, () -> {
            this.service.reportarTraidor(rebeldeId);
        });

        //Assert
        Mockito.verify(this.repository).findById(rebeldeId);
        Assertions.assertEquals("Rebelde não encontrado",exception.getMessage());
    }

    @Test
    void ShouldUpdateLocalizacao(){
        //Arrange
        var localizacao = new Localizacao();
        Integer rebeldeId = 1;


        var expectedRebelde = new Rebelde();

        Mockito.when(this.repository.findById(rebeldeId)).thenReturn(Optional.of(expectedRebelde));

        Mockito.when(this.repository.save(expectedRebelde)).thenReturn(expectedRebelde);


        //Act
        var response = this.service.atualizarLocalizacao(localizacao, rebeldeId);

        //Assert
        Mockito.verify(this.repository).findById(rebeldeId);
        Mockito.verify(this.repository).save(expectedRebelde);

        Assertions.assertEquals("Localização atualizada", response);
    }

    @Test
    void ShouldReturnCorrectNegociar() throws Exception {
        //Arrange
        var dto = new NegociacaoDto(new Integer[]{1,2}, 1,new Integer[]{3,4},2);


        var rebeldeA = new Rebelde();
        var itensRebeldeA = new ArrayList<ItemInventario>(){};
        itensRebeldeA.add(new ItemInventario(1,"arma", 5));
        itensRebeldeA.add(new ItemInventario(2,"comida", 6));
        Mockito.when(this.repository.findById(dto.getRebeldeAId())).thenReturn(Optional.of(rebeldeA));
        Mockito.when(this.itemInventarioRepository.filterByRebelde(dto.getRebeldeAId())).thenReturn(itensRebeldeA);

        var rebeldeB = new Rebelde();
        var itensRebeldeB = new ArrayList<ItemInventario>(){};
        itensRebeldeB.add(new ItemInventario(3,"arma", 5));
        itensRebeldeB.add(new ItemInventario(4,"comida", 6));
        Mockito.when(this.repository.findById(dto.getRebeldeBId())).thenReturn(Optional.of(rebeldeB));
        Mockito.when(this.itemInventarioRepository.filterByRebelde(dto.getRebeldeBId())).thenReturn(itensRebeldeB);



        //Act
        var response = this.service.negociar(dto);

        //Assert
        Mockito.verify(this.repository).findById(dto.getRebeldeAId());
        Mockito.verify(this.repository).findById(dto.getRebeldeBId());
        Mockito.verify(this.itemInventarioRepository).filterByRebelde(dto.getRebeldeAId());
        Mockito.verify(this.itemInventarioRepository).filterByRebelde(dto.getRebeldeBId());
        Assertions.assertEquals("Negociação finalizada", response);
    }

    @Test
    void ShouldThrowExceptionNegociarWhenPointsDontMatch() throws Exception {
        //Arrange
        var dto = new NegociacaoDto(new Integer[]{1,2}, 1,new Integer[]{3,4},2);


        var rebeldeA = new Rebelde();
        var itensRebeldeA = new ArrayList<ItemInventario>(){};
        itensRebeldeA.add(new ItemInventario(1,"arma", 5));
        itensRebeldeA.add(new ItemInventario(2,"comida", 6));
        Mockito.when(this.repository.findById(dto.getRebeldeAId())).thenReturn(Optional.of(rebeldeA));
        Mockito.when(this.itemInventarioRepository.filterByRebelde(dto.getRebeldeAId())).thenReturn(itensRebeldeA);

        var rebeldeB = new Rebelde();
        var itensRebeldeB = new ArrayList<ItemInventario>(){};
        itensRebeldeB.add(new ItemInventario(3,"arma", 5));
        itensRebeldeB.add(new ItemInventario(4,"comida", 7));
        Mockito.when(this.repository.findById(dto.getRebeldeBId())).thenReturn(Optional.of(rebeldeB));
        Mockito.when(this.itemInventarioRepository.filterByRebelde(dto.getRebeldeBId())).thenReturn(itensRebeldeB);



        //Act
        var exception = Assertions.assertThrows(Exception.class, () -> {
            this.service.negociar(dto);
        });

        //Assert
        Mockito.verify(this.repository).findById(dto.getRebeldeAId());
        Mockito.verify(this.repository).findById(dto.getRebeldeBId());
        Mockito.verify(this.itemInventarioRepository).filterByRebelde(dto.getRebeldeAId());
        Mockito.verify(this.itemInventarioRepository).filterByRebelde(dto.getRebeldeBId());
        Assertions.assertEquals("Quantidade entre pontos entre os rebeldes é diferente... abortando.", exception.getMessage());
    }

}
