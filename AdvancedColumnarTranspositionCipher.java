import java.util.Arrays;
import java.util.Scanner;

public class AdvancedColumnarTranspositionCipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the string to be encrypted:");
        String message = scanner.nextLine().replaceAll("\\s", "").toUpperCase();

        System.out.println("Enter the first key (a sequence of numbers):");
        String key1 = scanner.nextLine();

        System.out.println("Enter the second key (a sequence of numbers):");
        String key2 = scanner.nextLine();

        String encryptedMessage = encrypt(message, key1, key2);
        System.out.println("Encrypted message: " + encryptedMessage);

        scanner.close();
    }

    public static String encrypt(String message, String key1, String key2) {
        // First transposition
        String firstTransposition = columnarTransposition(message, key1);

        // Second transposition
        String secondTransposition = columnarTransposition(firstTransposition, key2);

        return secondTransposition;
    }

    private static String columnarTransposition(String message, String key) {
        int numCols = key.length();
        int numRows = (int) Math.ceil((double) message.length() / numCols);

        char[][] grid = new char[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            Arrays.fill(grid[i], 'X'); // Fill grid with 'X' to handle padding
        }

        int index = 0;
        for (int r = 0; r < numRows && index < message.length(); r++) {
            for (int c = 0; c < numCols && index < message.length(); c++) {
                grid[r][c] = message.charAt(index++);
            }
        }

        int[] keyOrder = getKeyOrder(key);

        StringBuilder transposedMessage = new StringBuilder();
        for (int k : keyOrder) {
            for (int r = 0; r < numRows; r++) {
                transposedMessage.append(grid[r][k]);
            }
        }

        return transposedMessage.toString();
    }

    private static int[] getKeyOrder(String key) {
        int length = key.length();
        KeyValuePair[] keyValuePairs = new KeyValuePair[length];

        for (int i = 0; i < length; i++) {
            keyValuePairs[i] = new KeyValuePair(key.charAt(i), i);
        }

        Arrays.sort(keyValuePairs);

        int[] keyOrder = new int[length];
        for (int i = 0; i < length; i++) {
            keyOrder[i] = keyValuePairs[i].index;
        }

        return keyOrder;
    }

    private static class KeyValuePair implements Comparable<KeyValuePair> {
        char key;
        int index;

        KeyValuePair(char key, int index) {
            this.key = key;
            this.index = index;
        }

        @Override
        public int compareTo(KeyValuePair other) {
            return Character.compare(this.key, other.key);
        }
    }
}
