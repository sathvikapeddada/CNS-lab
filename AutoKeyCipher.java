import java.util.Scanner;

public class AutoKeyCipher {

    // Encrypt the plaintext using AutoKey Cipher
    public static String encrypt(String plaintext, String key) {
        StringBuilder extendedKey = new StringBuilder(key);
        extendedKey.append(plaintext); // Extend the key with the plaintext

        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = extendedKey.charAt(i);

            // Encrypt using the formula: C = (P + K) % 26
            char cipherChar = (char) (((plainChar - 'A' + keyChar - 'A') % 26) + 'A');
            ciphertext.append(cipherChar);
        }
        return ciphertext.toString();
    }

    // Decrypt the ciphertext using AutoKey Cipher
    public static String decrypt(String ciphertext, String key) {
        StringBuilder extendedKey = new StringBuilder(key);
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);
            char keyChar = extendedKey.charAt(i);

            // Decrypt using the formula: P = (C - K + 26) % 26
            char plainChar = (char) (((cipherChar - 'A' - (keyChar - 'A') + 26) % 26) + 'A');
            plaintext.append(plainChar);

            // Extend the key with the decrypted character
            extendedKey.append(plainChar);
        }
        return plaintext.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input plaintext and key
        System.out.print("Enter the plaintext (uppercase letters only): ");
        String plaintext = scanner.nextLine().toUpperCase();

        System.out.print("Enter the keyword (uppercase letters only): ");
        String key = scanner.nextLine().toUpperCase();

        // Encrypt the plaintext
        String ciphertext = encrypt(plaintext, key);
        System.out.println("Ciphertext: " + ciphertext);

        // Decrypt the ciphertext
        String decryptedText = decrypt(ciphertext, key);
        System.out.println("Decrypted Text: " + decryptedText);

        scanner.close();
    }
}