package use_case.Words_of_Affirmation;

public class StringCleaner {

    public static String removeSpecialCharacters(String input) {
        // Replace all occurrences of '{', '}', and ':' with an empty string
        return input.replace("{", "").replace("}", "").replace(":", "").replace("'", "");
    }

    public static void main(String[] args) {
        String originalString = "Example {string} with: special characters";
        String cleanedString = removeSpecialCharacters(originalString);

        System.out.println("Original String: " + originalString);
        System.out.println("Cleaned String: " + cleanedString);
    }
}
