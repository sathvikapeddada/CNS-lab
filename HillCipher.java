import java.util.Scanner;

public class HillCipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the string to be encrypted (only uppercase letters):");
        String message = scanner.nextLine().replaceAll("\\s", "").toUpperCase();

        // Define a 2x2 key matrix
        int[][] keyMatrix = { {3, 3}, {2, 5} };

        // Pad the message with 'X' if its length is odd
        if (message.length() % 2 != 0) {
            message += 'X';
        }

        String encryptedMessage = encrypt(message, keyMatrix);
        System.out.println("Encrypted message: " + encryptedMessage);

        // Calculate the inverse of the key matrix
        int[][] inverseKeyMatrix = findInverse(keyMatrix);

        // Decrypt the encrypted message
        String decryptedMessage = decrypt(encryptedMessage, inverseKeyMatrix);
        System.out.println("Decrypted message: " + decryptedMessage);

        scanner.close();
    }

    public static String encrypt(String message, int[][] keyMatrix) {
        int len = message.length();
        StringBuilder result = new StringBuilder(len);

        for (int i = 0; i < len; i += 2) {
            int[] vector = { message.charAt(i) - 'A', message.charAt(i + 1) - 'A' };
            int[] encryptedVector = new int[2];

            for (int j = 0; j < 2; ++j) {
                encryptedVector[j] = 0;
                for (int k = 0; k < 2; ++k) {
                    encryptedVector[j] += keyMatrix[j][k] * vector[k];
                }
                encryptedVector[j] %= 26;
                result.append((char) (encryptedVector[j] + 'A'));
            }
        }
        return result.toString();
    }

    public static String decrypt(String message, int[][] inverseKeyMatrix) {
        int len = message.length();
        StringBuilder result = new StringBuilder(len);

        for (int i = 0; i < len; i += 2) {
            int[] vector = { message.charAt(i) - 'A', message.charAt(i + 1) - 'A' };
            int[] decryptedVector = new int[2];

            for (int j = 0; j < 2; ++j) {
                decryptedVector[j] = 0;
                for (int k = 0; k < 2; ++k) {
                    decryptedVector[j] += inverseKeyMatrix[j][k] * vector[k];
                }
                decryptedVector[j] %= 26;
                if (decryptedVector[j] < 0) {
                    decryptedVector[j] += 26;
                }
                result.append((char) (decryptedVector[j] + 'A'));
            }
        }
        return result.toString();
    }

    public static int[][] findInverse(int[][] matrix) {
        int determinant = (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) % 26;
        if (determinant < 0) {
            determinant += 26;
        }

        int inverseDeterminant = -1;
        for (int i = 0; i < 26; i++) {
            if ((determinant * i) % 26 == 1) {
                inverseDeterminant = i;
                break;
            }
        }

        if (inverseDeterminant == -1) {
            throw new ArithmeticException("Matrix is not invertible");
        }

        int[][] inverseMatrix = new int[2][2];
        inverseMatrix[0][0] = matrix[1][1] * inverseDeterminant % 26;
        inverseMatrix[0][1] = -matrix[0][1] * inverseDeterminant % 26;
        inverseMatrix[1][0] = -matrix[1][0] * inverseDeterminant % 26;
        inverseMatrix[1][1] = matrix[0][0] * inverseDeterminant % 26;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (inverseMatrix[i][j] < 0) {
                    inverseMatrix[i][j] += 26;
                }
            }
        }

        return inverseMatrix;
    }
}
