package datastructuresproject2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author Nouredeen Hammad 
 *         Vildan Kavaklı
 */
public class Utility {

    public static BST fileToBST(String path) {
        //Creates a new BinarySearchTree
        BST dict = new BST();

        //Reads the file with FileReader and given path.
        //Uses BufferedReader to read the file line by line.
        try {
            FileInputStream fileInputStream = new FileInputStream(path);

            //Reads with UTF8 encoding to ensure it works with turkish characters
            InputStreamReader isr = new InputStreamReader(fileInputStream, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;

            //Goes through all lines one by one, and inserts them into the dictionary BST
            while ((line = br.readLine()) != null) {
                dict.insert(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        };

        return dict;
    }

    static int getLevenshteinDistance(String str1, String str2) {

        // Create a 2-D matrix to store previously calculated answers 
        // of subproblems in order to obtain the final distance value.
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];

        // Loop through each character of str1 and str2.
        for (int i = 0; i <= str1.length(); i++) {
            for (int j = 0; j <= str2.length(); j++) {

                // If str1 is empty, all characters of str2 are inserted into str1, 
                // which is the only possible method of conversion with minimum operations.
                if (i == 0) {
                    dp[i][j] = j;
                } // If str2 is empty, all characters of str1 are removed, 
                // which is the only possible method of conversion with minimum operations.
                else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    // Find the minimum among three operations:
                    // 1. Replace the i-th character of str1 with the j-th character of str2.
                    // 2. Delete the i-th character of str1.
                    // 3. Insert the j-th character of str2 into str1.
                    dp[i][j] = minm_edits(dp[i - 1][j - 1] + NumOfReplacement(str1.charAt(i - 1), str2.charAt(j - 1)), // replace
                            dp[i - 1][j] + 1, // delete
                            dp[i][j - 1] + 1); // insert
                }
            }
        }

        // Return the final Levenshtein distance value.
        return dp[str1.length()][str2.length()];
    }

// Calculates the number of replacements required to transform one character to another.
    static int NumOfReplacement(char c1, char c2) {
        return c1 == c2 ? 0 : 1;
    }

// Receives the count of different operations performed and returns the minimum value among them.
    static int minm_edits(int... nums) {
        return Arrays.stream(nums).min().orElse(Integer.MAX_VALUE);
    }
}
