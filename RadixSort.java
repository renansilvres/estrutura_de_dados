import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class RadixSort {

    public static class SortResult {
        public long time; // Tempo de execução
        public int operations; // Número de operações
    }

    // Função para fazer Counting Sort para um dígito específico
    private static int countingSortByDigit(int[] array, int exp, int[] output) {
        int n = array.length;
        int[] count = new int[10]; // Contagem de 0 a 9 (dígitos decimais)
        int operationCount = 0;

        // Contar ocorrências dos dígitos
        for (int i = 0; i < n; i++) {
            int digit = (array[i] / exp) % 10;
            count[digit]++;
            operationCount += 2; // Uma operação para calcular o dígito, outra para incrementar
        }

        // Transformar count para armazenar posições
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
            operationCount++;
        }

        // Construir o array de saída
        for (int i = n - 1; i >= 0; i--) {
            int digit = (array[i] / exp) % 10;
            output[count[digit] - 1] = array[i];
            count[digit]--;
            operationCount += 3; // Para acessar, atualizar e copiar
        }

        // Copiar de volta para o array original
        for (int i = 0; i < n; i++) {
            array[i] = output[i];
            operationCount++;
        }

        return operationCount;
    }

    // Função principal do Radix Sort
    public static SortResult radixSort(int[] array) {
        int n = array.length;
        int max = Arrays.stream(array).max().getAsInt(); // Encontrar o maior número no array
        int[] output = new int[n]; // Array de saída temporário
        int operationCount = 0;

        long startTime = System.nanoTime();

        // Aplicar Counting Sort para cada dígito (base 10)
        for (int exp = 1; max / exp > 0; exp *= 10) {
            operationCount += countingSortByDigit(array, exp, output);
        }

        long endTime = System.nanoTime();

        // Preparar o resultado
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
            SortResult aleatorioResult = radixSort(Arrays.copyOf(vetorAleatorio, vetorAleatorio.length));
            csvContent.append(size).append(",Aleatório,").append(aleatorioResult.time).append(",")
                    .append(aleatorioResult.operations).append("\n");

            // Ordenar e medir para o vetor crescente
            SortResult crescenteResult = radixSort(Arrays.copyOf(vetorCrescente, vetorCrescente.length));
            csvContent.append(size).append(",Crescente,").append(crescenteResult.time).append(",")
                    .append(crescenteResult.operations).append("\n");

            // Ordenar e medir para o vetor decrescente
            SortResult decrescenteResult = radixSort(Arrays.copyOf(vetorDecrescente, vetorDecrescente.length));
            csvContent.append(size).append(",Decrescente,").append(decrescenteResult.time).append(",")
                    .append(decrescenteResult.operations).append("\n");
        }

        // Salvar conteúdo no arquivo CSV
        try (FileWriter writer = new FileWriter("resultados_radix_sort.csv")) {
            writer.write(csvContent.toString());
            System.out.println("Resultados salvos em 'resultados_radix_sort.csv'");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo CSV: " + e.getMessage());
        }
    }
}
