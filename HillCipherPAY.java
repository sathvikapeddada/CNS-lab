public class HillCipherPAY {
    private static final int MATRIX_SIZE = 3;
    private static final int[][] KEY_MATRIX = {
        {17, 17, 5},
        {21, 18, 21},
        {2, 2, 19}
    };

    public static void main(String[] args) {
        String plaintext = "PAY";
        System.out.println("plaintext:" + plaintext);
        int[] encryptedVector = encrypt(plaintext, KEY_MATRIX);
        String encryptedText = vectorToString(encryptedVector);
        System.out.println("Encrypted message: " + encryptedText);

        int[][] inverseKeyMatrix = findInverse(KEY_MATRIX);
        int[] decryptedVector = decrypt(encryptedText, inverseKeyMatrix);
        String decryptedText = vectorToString(decryptedVector);
        System.out.println("Decrypted message: " + decryptedText);
    }

    private static int[] encrypt(String plaintext, int[][] keyMatrix) {
        int[] plaintextVector = stringToVector(plaintext);
        return matrixVectorMultiply(keyMatrix, plaintextVector);
    }

    private static int[] decrypt(String ciphertext, int[][] inverseKeyMatrix) {
        int[] ciphertextVector = stringToVector(ciphertext);
        return matrixVectorMultiply(inverseKeyMatrix, ciphertextVector);
    }

    private static int[] stringToVector(String text) {
        int[] vector = new int[MATRIX_SIZE];
        for (int i = 0; i < MATRIX_SIZE; i++) {
            vector[i] = text.charAt(i) - 'A';
        }
        return vector;
    }

    private static String vectorToString(int[] vector) {
        StringBuilder result = new StringBuilder();
        for (int value : vector) {
            result.append((char) (value + 'A'));
        }
        return result.toString();
    }

    private static int[] matrixVectorMultiply(int[][] matrix, int[] vector) {
        int[] result = new int[MATRIX_SIZE];
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
            result[i] = (result[i] % 26 + 26) % 26; // Handle negative values
        }
        return result;
    }

    private static int[][] findInverse(int[][] matrix) {
        int determinant = calculateDeterminant(matrix);
        int determinantInverse = findModularInverse(determinant, 26);

        int[][] adjugateMatrix = new int[MATRIX_SIZE][MATRIX_SIZE];
        adjugateMatrix[0][0] = matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1];
        adjugateMatrix[0][1] = -(matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0]);
        adjugateMatrix[0][2] = matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0];
        adjugateMatrix[1][0] = -(matrix[0][1] * matrix[2][2] - matrix[0][2] * matrix[2][1]);
        adjugateMatrix[1][1] = matrix[0][0] * matrix[2][2] - matrix[0][2] * matrix[2][0];
        adjugateMatrix[1][2] = -(matrix[0][0] * matrix[2][1] - matrix[0][1] * matrix[2][0]);
        adjugateMatrix[2][0] = matrix[0][1] * matrix[1][2] - matrix[0][2] * matrix[1][1];
        adjugateMatrix[2][1] = -(matrix[0][0] * matrix[1][2] - matrix[0][2] * matrix[1][0]);
        adjugateMatrix[2][2] = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                adjugateMatrix[i][j] = (adjugateMatrix[i][j] * determinantInverse) % 26;
                if (adjugateMatrix[i][j] < 0) {
                    adjugateMatrix[i][j] += 26;
                }
            }
        }
        return adjugateMatrix;
    }

    private static int calculateDeterminant(int[][] matrix) {
        return (matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
              - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
              + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0])) % 26;
    }

    private static int findModularInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return 1; // Modular inverse doesn't exist if determinant is 0
    }
}
