import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Advent of Code 2022 - Day 3, Solution 2
 * Basic problem:
 * - Each group of 3 lines represents 3 distinct elves
 * - Figure out what common item is shared by all 3 elves which becomes their "badge"
 * - See score() for how to tally up the value of that badge
 *
 * @author Macon Pegram
 */
public class Solution2 {
    private static final int ELF_GROUP_SIZE = 3;

    /**
     * Cranks this baby up!
     *
     * @param args
     *    Commandline args
     */
    public static void main(String[] args) {

        try {
            int total = 0;
            int subsetSize = 0;
            String [] itemSets = new String[ELF_GROUP_SIZE];
            BufferedReader garbIn = new BufferedReader(new FileReader("input.txt"));
            String line = garbIn.readLine();
            while (line != null) {
                itemSets[subsetSize] = line;
                subsetSize++;
                System.out.println("Processing: " + line);
                if (subsetSize == ELF_GROUP_SIZE) {
                    char badge = findBadge(itemSets);
                    int points = score(badge);
                    System.out.println("Common item carried: " + badge + " for a score of " + points);
                    System.out.println();
                    total += points;
                    
                    // reset values after processing the elf group
                    subsetSize = 0;
                    itemSets = new String[ELF_GROUP_SIZE];
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
     * Uses Sets to whittle down duplicate items by taking the intersection on each pass.
     *
     * @param dataIn
     *      An array of the items carried by each elf in the subgroup.
     *
     * @return The single (or first) common item that all the elves in the group carry.
     */
    private static char findBadge(String [] dataIn) {

        Set<Character> dupes = new HashSet<>();
        for (String line : dataIn) {
            // If first pass, create the initial set
            if (dupes.isEmpty()) {
                dupes = getUniqueItems(line);
            }
            // Every other pass just keep any duplicated items
            else {
                dupes.retainAll(getUniqueItems(line));
            }
        }

        Optional<Character> badge = dupes.stream().findFirst();
        return badge.isPresent() ? badge.get() : 0;
        
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
