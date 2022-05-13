import java.util.Arrays;

public class Knapsack {
    private final int maxWeight;
    private final int populationSize;
    private final int nElements;
    private final int[] weights;
    private final int[] values;
    private final int[][] initialPopulation;

    public Knapsack(int maxWeight, int populationSize, int nElements, int[] weights, int[] values) {
        this.maxWeight = maxWeight;
        this.populationSize = populationSize;
        this.nElements = nElements;
        this.weights = weights;
        this.values = values;
        this.initialPopulation = generateRandomGeneration(populationSize, nElements);
    }

    public static void main(String[] args) {

        int nGenerations = 20;

        int[] w = {15, 60, 40, 30, 10, 120, 150, 80};
        int[] v = {10, 80, 45, 60, 70, 34, 100, 92};

        Knapsack ks = new Knapsack(200, 8, 8, w, v);

        ks.generation(ks.initialPopulation, 0, 10);
    }

    public int[][] generateRandomGeneration(int populationSize, int nElements) {
        int[][] population = new int[populationSize][nElements];

        // Randomly generate a population
        for (int i = 0; i < populationSize; i++) {
            for (int j = 0; j < nElements; j++) {
                population[i][j] = (int) Math.round(Math.random());
            }
        }

        return population;
    }

    public void generation(int[][] population, int currentGeneration, int nGenerations) {

        int[] generationWeights = new int[populationSize];
        int[] generationValues = new int[populationSize];

        int generationTotalValue = 0; // for probability purposes

        System.out.println("Generation " + currentGeneration + "\n");
        System.out.println("Initialisation");

        // Randomly generate a population
        for (int i = 0; i < populationSize; i++) {
            int totalWeight = 0;
            int totalValue = 0;

            // Randomly generate the bit string
            for (int j = 0; j < nElements; j++) {

                if (population[i][j] == 1) {
                    totalWeight += weights[j];
                    totalValue += values[j];
                }
            }

            if (totalWeight > maxWeight) {
                totalValue = 0;
            }

            generationWeights[i] = totalWeight;
            generationValues[i] = totalValue;

            generationTotalValue += totalValue;

            System.out.println("Individual " + i + "; Bit string: " + Arrays.toString(population[i]) + "; Total weight: " + totalWeight + "; Total value: " + totalValue);
        }

        System.out.println("Starting crossover process \n");

        int[][] newPopulation = new int[populationSize][nElements];

        for (int i = 0; i < populationSize; i++) {

            // Generate a random value between 0 and the generationTotalValue in order to select the first parent
            int randomA = (int) Math.floor(Math.random() * generationTotalValue);
//            System.out.println("GenerationTotalValue is " + generationTotalValue);
//            System.out.println("RandomA is " + randomA);
            int a = 0;
            int sumA = 0;
            while (sumA < randomA) {
//                System.out.println("a is: " + a + ", individual value is " + generationValues[a] + ", and sumA is " + sumA);
                sumA += generationValues[a++];
            }
            int[] firstParent = population[a];
            System.out.println("First parent is " + a);

            // Generate a random value between 0 and the generationTotalValue in order to select the second parent.
            int randomB = (int) Math.floor(Math.random() * generationTotalValue);
//            System.out.println("GenerationTotalValue is " + generationTotalValue);
//            System.out.println("RandomB is " + randomB);
            int b = 0;
            int sumB = 0;
            while (sumB < randomB) {
                sumB += generationValues[b++];
//                System.out.println("a is: " + a + " and sumB is " + sumB);
            }
            int[] secondParent = population[b];
            System.out.println("Second parent is " + b);

            int[] result = crossover(firstParent, secondParent);
            newPopulation[i] = result;
        }

        if (currentGeneration <= nGenerations) {
            generation(newPopulation, ++currentGeneration, nGenerations);
        } else {
            System.out.println("END");
        }

    }

    public int[] crossover(int[] a, int[] b) {

        int[] result = new int[nElements];

        System.out.println("Doing crossover between " + Arrays.toString(a) + " and " + Arrays.toString(b));

        for (int i = 0; i < nElements; i++) {
            if (i < (nElements) / 2) {
                result[i] = a[i];
            } else {
                result[i] = b[i];
            }
        }

        System.out.println("Bit string: " + Arrays.toString(result));

        return result;
    }
}
