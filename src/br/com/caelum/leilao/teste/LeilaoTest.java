package br.com.caelum.leilao.teste;

import static br.com.caelum.leilao.matcher.LeilaoMatcher.temUmLance;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class LeilaoTest {

    @Test
    public void deveReceberUmLance() {
        Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15").constroi();
        assertEquals(0, leilao.getLances().size());

        Lance lance = new Lance(new Usuario("Steve Jobs"), 2000);
        leilao.propoe(lance);

        assertThat(leilao.getLances().size(), equalTo(1));
        assertThat(leilao, temUmLance(lance));
    }

    @Test
    public void deveReceberVariosLance() {
        Leilao leilao = new Leilao("Macbook pro 15");

        leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000));
        leilao.propoe(new Lance(new Usuario("Steve Wozniak"), 3000));

        assertEquals(2, leilao.getLances().size());

        assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
        assertEquals(3000, leilao.getLances().get(1).getValor(), 0.00001);
    }

    @Test
    public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
        Leilao leilao = new Leilao("Macbook pro 15");

        Usuario steveJobs = new Usuario("Steve Jobs");

        leilao.propoe(new Lance(steveJobs, 2000));
        leilao.propoe(new Lance(steveJobs, 3000));

        assertEquals(1, leilao.getLances().size());
        assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
    }

    @Test
    public void naoDeveAceitarMaisDoQueCincoLancesDeUmMesmoUsuario() {
        Leilao leilao = new Leilao("Macbook pro 15");

        Usuario steveJobs = new Usuario("Steve Jobs");
        Usuario billGates = new Usuario("Bill Gates");

        leilao.propoe(new Lance(steveJobs, 2000));
        leilao.propoe(new Lance(billGates, 3000));

        leilao.propoe(new Lance(steveJobs, 4000));
        leilao.propoe(new Lance(billGates, 5000));

        leilao.propoe(new Lance(steveJobs, 6000));
        leilao.propoe(new Lance(billGates, 7000));

        leilao.propoe(new Lance(steveJobs, 8000));
        leilao.propoe(new Lance(billGates, 9000));

        leilao.propoe(new Lance(steveJobs, 10000));
        leilao.propoe(new Lance(billGates, 11000));

        // Deve ser ignorado
        leilao.propoe(new Lance(steveJobs, 12000));

        assertEquals(10, leilao.getLances().size());
        assertEquals(11000, leilao.getLances().get(leilao.getLances().size() - 1).getValor(), 0.00001);
    }

    @Test
    public void deveDobrarOUltimoLanceDado() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        Usuario steveJobs = new Usuario("Steve Jobs");
        Usuario billGates = new Usuario("Bill Gates");

        leilao.propoe(new Lance(steveJobs, 2000));
        leilao.propoe(new Lance(billGates, 3000));
        leilao.dobraLance(steveJobs);

        assertEquals(4000, leilao.getLances().get(2).getValor(), 0.00001);
    }

    @Test
    public void naoDeveDobrarCasoNaoHajaLanceAnterior() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        Usuario steveJobs = new Usuario("Steve Jobs");

        leilao.dobraLance(steveJobs);

        assertEquals(0, leilao.getLances().size());
    }

}
