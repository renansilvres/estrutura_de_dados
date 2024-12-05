import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class InsertSort {

    public static class SortResult {
        public long time; // Tempo de execução
        public int operations; // Número de operações
    }

    public static SortResult insertSort(int[] vetor) {
        int n = vetor.length;
        int temp;
        int operationCount = 0; // Contador de operações
        long startTime = System.nanoTime();
        for (int i = 1; i < vetor.length; i++) {
            int key = vetor[i];
            int j = i - 1;
            operationCount+=3; // Contar a comparaçã
            while (j >= 0 && vetor[j] > key) {
                vetor[j + 1] = vetor[j];
                j--;
                operationCount+=3; // Contar a comparação
            }
            vetor[j + 1] = key; 
            operationCount+=1; // Contar a comparação
        }
        long endTime = System.nanoTime();
        SortResult result = new SortResult();
        result.time = endTime - startTime;
        result.operations = operationCount;
        return result;
    }

    public static void main(String[] args) {
        int[] sizes = { 100, 200, 400, 600 };
        Random random = new Random();
        StringBuilder csvContent = new StringBuilder();

        // Adicionar cabeçalho ao CSV
        csvContent.append("Tamanho do Vetor,Tipo,Tempo(ns),Operações\n");

        for (int size : sizes) {
            // Criar vetores
            int[] vetorAleatorio = new int[size];
            int[] vetorCrescente = new int[size];
            int[] vetorDecrescente = new int[size];

            for (int i = 0; i < size; i++) {
                vetorAleatorio[i] = random.nextInt(1000); // Números aleatórios entre 0 e 999
                vetorCrescente[i] = i + 1; // De 1 a size
                vetorDecrescente[i] = size - i; // De size a 1
            }

            // Ordenar e medir para o vetor aleatório
            SortResult aleatorioResult = insertSort(Arrays.copyOf(vetorAleatorio, vetorAleatorio.length));
            csvContent.append(size).append(",Aleatório,").append(aleatorioResult.time).append(",")
                    .append(aleatorioResult.operations).append("\n");

            // Ordenar e medir para o vetor crescente
            SortResult crescenteResult = insertSort(Arrays.copyOf(vetorCrescente, vetorCrescente.length));
            csvContent.append(size).append(",Crescente,").append(crescenteResult.time).append(",")
                    .append(crescenteResult.operations).append("\n");

            // Ordenar e medir para o vetor decrescente
            SortResult decrescenteResult = insertSort(Arrays.copyOf(vetorDecrescente, vetorDecrescente.length));
            csvContent.append(size).append(",Decrescente,").append(decrescenteResult.time).append(",")
                    .append(decrescenteResult.operations).append("\n");
        }

        // Salvar conteúdo no arquivo CSV
        try (FileWriter writer = new FileWriter("resultados_selt_sort.csv")) {
            writer.write(csvContent.toString());
            System.out.println("Resultados salvos em 'resultados_selt_sort.csv'");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo CSV: " + e.getMessage());
        }
    }
}
