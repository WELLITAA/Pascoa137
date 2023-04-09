package unitTestSprint3;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class RectangleAreaTest {

    @Test
    public void RectangleArea() {
        // Configura
        double base = 5;
        double altura = 10;
        double resultadoEsperado = 50;

        // Executa
        double resultadoAtual = base * altura;

        // Valida
        assertEquals(resultadoEsperado, resultadoAtual);
    }
    public static class Main {
        public static void main(String[] args) {
            List<double[]> retangulos = new ArrayList<>();
            retangulos.add(new double[]{2.5, 4.0});
            retangulos.add(new double[]{3.0, 5.0});
            retangulos.add(new double[]{4.2, 6.1});
            retangulos.add(new double[]{5.5, 7.3});
            retangulos.add(new double[]{7.5, 8.0});
            retangulos.add(new double[]{8.0, 9.0});
            retangulos.add(new double[]{9.5,10.0});



            for (double[] retangulo : retangulos) {
                double base = retangulo[0];
                double altura = retangulo[1];
                double area = areaRetangulo(base, altura);
                System.out.println("Base: " + base + ", Altura: " + altura + ", Área: " + area);
            }
        }

        public static double areaRetangulo(double base, double altura) {
            return base * altura;
        }
    }
    public static void main(String[] args) {
        String filePath = "user3";
        File file = new File("C:\\Users\\Raquel\\Pascoa137\\src\\test\\resources\\user3\\retangulos.csv");

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] campos = linha.split(",");
                double base = Double.parseDouble(campos[0]);
                double altura = Double.parseDouble(campos[1]);
                double area = areaRetangulo(base, altura);
                System.out.println("Base: " + base + ", Altura: " + altura + ", Área: " + area);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
        }
    }

    public static double areaRetangulo(double base, double altura) {
        return base * altura;
    }

}

