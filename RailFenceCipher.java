import java.util.Scanner;

public class RailFenceCipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the string to be encrypted:");
        String message = scanner.nextLine().replaceAll("\\s", "").toUpperCase();
        
        System.out.println("Enter the number of rails:");
        int numRails = scanner.nextInt();

        String encryptedMessage = encrypt(message, numRails);
        System.out.println("Encrypted message: " + encryptedMessage);

        scanner.close();
    }

    public static String encrypt(String message, int numRails) {
        if (numRails == 1) {
            return message; // No change if only one rail
        }

        StringBuilder[] railBuilders = new StringBuilder[numRails];
        for (int i = 0; i < numRails; i++) {
            railBuilders[i] = new StringBuilder();
        }

        int rail = 0;
        boolean directionDown = true;

        for (char ch : message.toCharArray()) {
            railBuilders[rail].append(ch);

            if (rail == 0) {
                directionDown = true;
            } else if (rail == numRails - 1) {
                directionDown = false;
            }

            rail += directionDown ? 1 : -1;
        }

        StringBuilder encryptedMessage = new StringBuilder();
        for (StringBuilder railBuilder : railBuilders) {
            encryptedMessage.append(railBuilder);
        }

        return encryptedMessage.toString();
    }
}
