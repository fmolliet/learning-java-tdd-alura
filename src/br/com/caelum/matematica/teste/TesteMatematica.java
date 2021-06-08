package br.com.caelum.matematica.teste;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.caelum.matematica.MatematicaMaluca;

public class TesteMatematica {

    @Test
    public void multiplicaPorQuatro() {

        MatematicaMaluca matematica = new MatematicaMaluca();

        int numero = 31;

        assertEquals(numero * 4, matematica.contaMaluca(numero));
    }

    @Test
    public void multiplicaPorTres() {

        MatematicaMaluca matematica = new MatematicaMaluca();

        int numero = 11;

        assertEquals(numero * 3, matematica.contaMaluca(numero));
    }

    @Test
    public void multiplicaPorDois() {

        MatematicaMaluca matematica = new MatematicaMaluca();

        int numero = 10;

        assertEquals(numero * 2, matematica.contaMaluca(numero));
    }
}
