import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Advent of Code 2022 - Day 3, Solution 1
 */
public class Solution1 {

    /**
     * Cranks this baby up!
     *
     * @param args
     *    Commandline args
     */
    public static void main(String[] args) {

        try {
            int total = 0;
            BufferedReader garbIn = new BufferedReader(new FileReader("input.txt"));
            String line = garbIn.readLine();
            while (line != null) {
                System.out.println("Processing: " + line);
                char[] duplicates = findDupes(line);
                for (char dupe : duplicates) {
                    int points = score(dupe);
                    total += points;
                    System.out.println("Found duplicated item " + dupe + " adding " + points + " to the score");
                }
                line = garbIn.readLine();
            }
            garbIn.close();
            System.out.println("Total Points: "+ total);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Scoring is as follows:
     *  Lowercase item types a through z have priorities 1 through 26
     *  Uppercase item types A through Z have priorities 27 through 52
     *
     * @param dupeVal
     *    Value to determine score for
     *
     * @return value to add to score
     */
    private static int score(char dupeVal) {
        if (dupeVal >= 'a' && dupeVal <= 'z')
            return (int) dupeVal - 'a' + 1;
        if (dupeVal >= 'A' && dupeVal <= 'Z')
            return (int) dupeVal - 'A' + 27;

        // If it is not a letter we're not going to calculate it
        return 0;
    }

    /**
     * Finds any duplicates using Set theory... Applies the intersection of both halves.
     *
     * @param dataIn
     *      All the items in the rucksack as characters
     *
     * @return a character array of duplicate items between the two halves.
     */
    private static char[] findDupes(String dataIn) {
        Set<Character> firstHalfItems = getUniqueItems(dataIn.substring(0, dataIn.length() / 2));
        Set<Character> secondHalfItems = getUniqueItems(dataIn.substring(dataIn.length() / 2));

        // Perform intersection
        firstHalfItems.retainAll(secondHalfItems);

        // use a string to collect the dupes before returning as a char array
        StringBuilder sb = new StringBuilder();
        firstHalfItems.forEach(sb::append);  // Cool lambda trick I didn't know about!

        return sb.toString().toCharArray();
    }

    /**
     * Squash everything into a set to de-dupe the collection.
     *
     * @param items
     *     A string of items represented as characters.
     *
     * @return A set that has a single instance of each item.
     */
    private static Set<Character> getUniqueItems(String items) {
        Set<Character> uniques = new HashSet<>();

        for (char item : items.toCharArray()) {
            uniques.add(item);
        }

        return uniques;
    }
}
