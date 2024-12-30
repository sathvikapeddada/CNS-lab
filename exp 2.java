public class Main {
    public static void main(String[] args) {
        String str = "Hello World";
        char[] str1 = new char[11];
        char[] str2 = str.toCharArray(); // Convert the string to a char array
        char[] str3 = new char[11];
        int len = str.length();

        // First loop: Perform bitwise AND with 127
        for (int i = 0; i < len; i++) {
            str1[i] = (char) (str.charAt(i) & 127);
            System.out.print(str1[i]);
        }
        System.out.println();

        // Second loop: Perform bitwise XOR with 127
        for (int i = 0; i < len; i++) {
            str3[i] = (char) (str2[i] ^ 127);
            System.out.print(str3[i]);
        }
        System.out.println();
    }
}
