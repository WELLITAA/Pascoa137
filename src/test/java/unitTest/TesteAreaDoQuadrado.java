package unitTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static br.com.iterasys.Calculadora.areaQuadrado;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TesteAreaDoQuadrado {
    @Test
    @DisplayName("Teste único de cálculo da Área do Quadrado")
    public void testeCalculoAreaDoQuadrado() {
        //Configura
        double aresta = 10;

        //Executa
        double resultadoEsperado = 100;

        //Valida
        assertEquals(resultadoEsperado, areaQuadrado(aresta));
    }

    @DisplayName("Teste com lista de cálculo da Área do Quadrado")
    @ParameterizedTest(name = "{displayName} {index} - Aresta: {0} - Resultado: {1}")
    @CsvSource(value = {
            "5, 25",
            "3, 9",
            "-14, 196",
            "7.5, 56.25"
    }, delimiter = ',')
    public void testeCalculoAreaDoQuadradoComLista(String txtAresta, String txtResultadoEsperado) {
        //Configura
        double aresta = Double.parseDouble(txtAresta);
        double resultadoEsperado = Double.parseDouble(txtResultadoEsperado);

        //Executa
        double resultado = areaQuadrado(aresta);

        //Valida
        assertEquals(resultadoEsperado, resultado);
    }

    @ParameterizedTest(name = "{displayName} {index} - Aresta: {0} - Resultado: {1}")
    @CsvFileSource(resources = "/csv/massaAreaQuadrado.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Arquivo CSV de cálculo da Área do Quadrado")
    public void testeCalculoAreaDoQuadradoComAqruivoCSV(String txtAresta, String txtResultadoEsperado) {
        //Configura
        double aresta = Double.parseDouble(txtAresta);
        double resultadoEsperado = Double.parseDouble(txtResultadoEsperado);

        //Executa
        double resultado = areaQuadrado(aresta);

        //Valida
        assertEquals(resultadoEsperado, resultado);
    }
}
