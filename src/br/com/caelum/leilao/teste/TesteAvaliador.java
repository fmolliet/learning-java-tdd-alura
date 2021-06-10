package br.com.caelum.leilao.teste;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

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
    public void criaAvaliador() {
        this.leiloeiro = new Avaliador();
        this.joao = new Usuario("João");
        this.jose = new Usuario("José");
        this.maria = new Usuario("Maria");
    }

    @Test
    public void deveEntenderLancesEmOrdemCrescente() {

        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 250.0));
        leilao.propoe(new Lance(jose, 300.0));
        leilao.propoe(new Lance(maria, 400.0));

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

        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 1000.0));
        leilao.propoe(new Lance(jose, 3000.0));
        leilao.propoe(new Lance(maria, 2000.0));

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

        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(jose, 1000.0));

        leiloeiro.avalia(leilao);

        double maiorEsperado = 1000;

        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
    }

    @Test
    public void deveEncontrarOsTresMaioresLances() {

        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 100.0));
        leilao.propoe(new Lance(maria, 200.0));
        leilao.propoe(new Lance(joao, 300.0));
        leilao.propoe(new Lance(maria, 400.0));

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();
        assertEquals(3, maiores.size());
        assertEquals(400.0, maiores.get(0).getValor(), 0.00001);
        assertEquals(300.0, maiores.get(1).getValor(), 0.00001);
        assertEquals(200.0, maiores.get(2).getValor(), 0.00001);
    }

    @Test
    public void deveEntenderLeilaoComLancesEmOrdemRandomica() {

        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 200.0));
        leilao.propoe(new Lance(maria, 450.0));
        leilao.propoe(new Lance(joao, 120.0));
        leilao.propoe(new Lance(maria, 700.0));
        leilao.propoe(new Lance(joao, 630.0));
        leilao.propoe(new Lance(maria, 230.0));

        leiloeiro.avalia(leilao);

        assertEquals(700.0, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(120.0, leiloeiro.getMenorLance(), 0.0001);
    }

    @Test
    public void deveDevolverTodosLancesCasoNaoHajaNoMinimo3() {

        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 100.0));
        leilao.propoe(new Lance(maria, 200.0));

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertEquals(2, maiores.size());
        assertEquals(200, maiores.get(0).getValor(), 0.00001);
        assertEquals(100, maiores.get(1).getValor(), 0.00001);
    }

    @Test
    public void deveDevolverListaVaziaCasoNaoHajaLances() {
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertEquals(0, maiores.size());
    }

    @Test
    public void deveEntenderLeilaoComLancesEmOrdemDecrescente() {

        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 400.0));
        leilao.propoe(new Lance(maria, 300.0));
        leilao.propoe(new Lance(joao, 200.0));
        leilao.propoe(new Lance(maria, 100.0));

        leiloeiro.avalia(leilao);

        assertEquals(400.0, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(100.0, leiloeiro.getMenorLance(), 0.0001);
    }

    @Test
    public void deveCalcularAMedia() {
        // cenario: 3 lances em ordem crescente

        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(maria, 300.0));
        leilao.propoe(new Lance(joao, 400.0));
        leilao.propoe(new Lance(jose, 500.0));

        // executando a acao
        leiloeiro.avalia(leilao);

        // comparando a saida com o esperado
        assertEquals(400, leiloeiro.getMedia(), 0.0001);
    }

    @Test
    public void testaMediaDeZeroLance() {
        // cenario
        // Usuario ewertom = new Usuario("Ewertom");

        // acao
        Leilao leilao = new Leilao("Iphone 7");

        leiloeiro.avalia(leilao);
        // validacao
        assertEquals(0, leiloeiro.getMedia(), 0.0001);

    }
}
