package br.com.caelum.leilao.teste;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;

public class TesteAvaliador {

	@Test
	public void deveEntenderLancesEmOrdemCrescente() {
		Usuario joao = new Usuario("Jo�o");
        Usuario jose = new Usuario("Jos�");
        Usuario maria = new Usuario("Maria");

        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 250.0));
        leilao.propoe(new Lance(jose, 300.0));
        leilao.propoe(new Lance(maria, 400.0));
        
        Avaliador leiloeiro = new Avaliador();
        
        leiloeiro.avalia(leilao);
        
        double maiorEsperado = 400;
        double menorEsperado = 250;


        Assert.assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001); // o  0.00001 � um delta Como double tem limites de precis�o, a vers�o mais nova do JUnit pede para voc� passar o "tamanho da precis�o aceit�vel".
        Assert.assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
	}

	@Test
    public void deveCalcularAMedia() {
        // cenario: 3 lances em ordem crescente
        Usuario joao = new Usuario("Joao");
        Usuario jose = new Usuario("Jos�");
        Usuario maria = new Usuario("Maria");

        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(maria,300.0));
        leilao.propoe(new Lance(joao,400.0));
        leilao.propoe(new Lance(jose,500.0));

        // executando a acao
        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);

        // comparando a saida com o esperado
        Assert.assertEquals(400, leiloeiro.getMedia(), 0.0001);
    }
	
	 @Test
	    public void testaMediaDeZeroLance(){

	        // cenario
	        Usuario ewertom = new Usuario("Ewertom");

	        // acao
	        Leilao leilao = new Leilao("Iphone 7");

	        Avaliador avaliador = new Avaliador();
	        avaliador.avalia(leilao);

	        //validacao
	        Assert.assertEquals(0, avaliador.getMedia(), 0.0001);

	    }
}
