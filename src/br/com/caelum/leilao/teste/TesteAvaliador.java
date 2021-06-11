package br.com.caelum.leilao.teste;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;

public class TesteAvaliador {

    private Avaliador leiloeiro;
    private Usuario joao;
    private Usuario jose;
    private Usuario maria;

    @Before
    public void setUp() {
        this.leiloeiro = new Avaliador();
        this.joao = new Usuario("João");
        this.jose = new Usuario("José");
        this.maria = new Usuario("Maria");
        System.out.println("inicializando teste!");
    }

    @After
    public void finaliza() {
        System.out.println("fim");
    }

    @BeforeClass
    public static void testandoBeforeClass() {
        // ele vai executar antes de todos os testes dessa classe
        System.out.println("before class");
    }

    @AfterClass
    public static void testandoAfterClass() {
        // ele vai executar ao finalizar todos os testes dessa classe
        System.out.println("after class");
    }

    @Test
    public void deveEntenderLancesEmOrdemCrescente() {

        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").lance(joao, 250.0).lance(jose, 300.0)
                .lance(maria, 400.0).constroi();

        leiloeiro.avalia(leilao);

        double maiorEsperado = 400;
        double menorEsperado = 250;

        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001); // o 0.00001 � um delta Como double tem limites
                                                                         // de precis�o, a vers�o mais nova do JUnit
                                                                         // pede para voc� passar o "tamanho da precis�o
                                                                         // aceit�vel".
        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
    }

    @Test
    public void deveEntenderLancesEmOrdemCrescenteComOutrosValores() {

        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").lance(joao, 1000.0).lance(jose, 2000.0)
                .lance(maria, 3000.0).constroi();

        leiloeiro.avalia(leilao);

        double maiorEsperado = 3000;
        double menorEsperado = 1000;

        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001); // o 0.00001 � um delta Como double tem limites
                                                                         // de precis�o, a vers�o mais nova do JUnit
                                                                         // pede para voc� passar o "tamanho da precis�o
                                                                         // aceit�vel".
        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
    }

    @Test
    public void deveEntenderLeilaoComApenasUmLance() {

        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").lance(jose, 1000.0).constroi();

        leiloeiro.avalia(leilao);

        assertThat(leiloeiro.getMaiorLance(), equalTo(1000.0));
    }

    @Test
    public void deveEncontrarOsTresMaioresLances() {

        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").lance(joao, 100.0).lance(maria, 200.0)
                .lance(joao, 300.0).lance(maria, 400.0).constroi();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertThat(maiores.size(), equalTo(3));
        assertThat(maiores, hasItems(new Lance(maria, 400.0), new Lance(joao, 300.0), new Lance(maria, 200.0)));

    }

    @Test
    public void deveEntenderLeilaoComLancesEmOrdemRandomica() {

        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").lance(joao, 200.0).lance(maria, 450.0)
                .lance(joao, 120.0).lance(maria, 700.0).lance(joao, 630.0).lance(maria, 230.0).constroi();

        leiloeiro.avalia(leilao);

        assertThat(leiloeiro.getMaiorLance(), equalTo(700.0));
        assertThat(leiloeiro.getMenorLance(), equalTo(120.0));
    }

    @Test
    public void deveDevolverTodosLancesCasoNaoHajaNoMinimo3() {

        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").lance(joao, 100.0).lance(maria, 200.0)
                .constroi();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertThat(maiores.size(), equalTo(2));
        assertThat(maiores.get(0).getValor(), equalTo(200.0));
        assertThat(maiores.get(1).getValor(), equalTo(100.0));
    }

    @Test(expected = RuntimeException.class)
    public void deveDevolverListaVaziaCasoNaoHajaLances() {

        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").constroi();

        leiloeiro.avalia(leilao);
        Assert.fail();

    }

    @Test
    public void deveEntenderLeilaoComLancesEmOrdemDecrescente() {

        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").lance(joao, 400.0).lance(maria, 300.0)
                .lance(joao, 200.0).lance(maria, 100.0).constroi();

        leiloeiro.avalia(leilao);

        assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
        assertThat(leiloeiro.getMenorLance(), equalTo(100.0));
    }

    @Test
    public void deveCalcularAMedia() {
        // cenario: 3 lances em ordem crescente

        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").lance(maria, 300.0).lance(joao, 400.0)
                .lance(jose, 500.0).constroi();

        // executando a acao
        leiloeiro.avalia(leilao);

        // comparando a saida com o esperado
        assertThat(leiloeiro.getMedia(), equalTo(400.0));
    }

    @Test(expected = RuntimeException.class)
    public void testaMediaDeZeroLance() {
        // cenario
        // Usuario ewertom = new Usuario("Ewertom");

        // acao
        Leilao leilao = new CriadorDeLeilao().para("iPhone 12").constroi();

        leiloeiro.avalia(leilao);
        // validacao
        Assert.fail();

    }
}
