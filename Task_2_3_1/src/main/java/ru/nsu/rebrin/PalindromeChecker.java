package ru.nsu.rebrin;

public class PalindromeChecker {

    public static boolean isPalindrome(String input) {
        if (input == null) return false;

        String cleaned = input.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        int len = cleaned.length();

        for (int i = 0; i < len / 2; i++) {
            if (cleaned.charAt(i) != cleaned.charAt(len - 1 - i)) {
                return false;
            }
        }
        return true;
    }
}
