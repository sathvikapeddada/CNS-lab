import java.util.Arrays;
import java.util.Scanner;

public class ColumnarTranspositionCipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the string to be encrypted:");
        String message = scanner.nextLine().replaceAll("\\s", "").toUpperCase();

        System.out.println("Enter the key (a sequence of numbers):");
        String key = scanner.nextLine();

        String encryptedMessage = encrypt(message, key);
        System.out.println("Encrypted message: " + encryptedMessage);

        scanner.close();
    }

    public static String encrypt(String message, String key) {
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

        StringBuilder encryptedMessage = new StringBuilder();
        for (int k : keyOrder) {
            for (int r = 0; r < numRows; r++) {
                encryptedMessage.append(grid[r][k]);
            }
        }

        return encryptedMessage.toString();
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
