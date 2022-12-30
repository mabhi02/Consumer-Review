import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

public class Review {
    private static HashMap < String, Double > sentiment = new HashMap < String, Double > ();
    private static ArrayList < String > posAdjectives = new ArrayList < String > ();
    private static ArrayList < String > negAdjectives = new ArrayList < String > ();
    private static final String SPACE = " ";
    static {
        try {
            Scanner input = new Scanner(new File("cleanSentiment.csv"));
            while (input.hasNextLine()) {
                String[] temp = input.nextLine().split(",");
                sentiment.put(temp[0], Double.parseDouble(temp[1]));
            }
            input.close();
        } catch (Exception e) {
            System.out.println("Error reading or parsing cleanSentiment.csv");
        }
        try {
            Scanner input = new Scanner(new File("positiveAdjectives.txt"));
            while (input.hasNextLine()) {
                String temp = input.nextLine().trim();
                System.out.println(temp);
                posAdjectives.add(temp);
            }
            input.close();
        } catch (Exception e) {
            System.out.println("Error reading or parsing postitiveAdjectives.txt\n" + e);
        }
        try {
            Scanner input = new Scanner(new File("negativeAdjectives.txt"));
            while (input.hasNextLine()) {
                negAdjectives.add(input.nextLine().trim());
            }
            input.close();
        } catch (Exception e) {
            System.out.println("Error reading or parsing negativeAdjectives.txt");
        }
    }
    public static String textToString(String fileName) {
        String temp = "";
        try {
            Scanner input = new Scanner(new File(fileName));
            while (input.hasNext()) {
                temp = temp + input.next() + " ";
            }
            input.close();

        } catch (Exception e) {
            System.out.println("Unable to locate " + fileName);
        }
        return temp.trim();
    }
    public static double sentimentVal(String word) {
        try {
            return sentiment.get(word.toLowerCase());
        } catch (Exception e) {
            return 0;
        }
    }
    public static String getPunctuation(String word) {
        String punc = "";
        for (int i = word.length() - 1; i >= 0; i--) {
            if (!Character.isLetterOrDigit(word.charAt(i))) {
                punc = punc + word.charAt(i);
            } else {
                return punc;
            }
        }
        return punc;
    }
    public static String randomPositiveAdj() {
        int index = (int)(Math.random() * posAdjectives.size());
        return posAdjectives.get(index);
    }
    public static String randomNegativeAdj() {
        int index = (int)(Math.random() * negAdjectives.size());
        return negAdjectives.get(index);

    }
    public static String randomAdjective() {
        boolean positive = Math.random() < .5;
        if (positive) {
            return randomPositiveAdj();
        } else {
            return randomNegativeAdj();
        }
    }
    public static double totalSentiment(String fileName) {
        String placeholder = "";
        double totalSentiment = 0.0;
        String review = textToString(fileName);

        review = review.replaceAll("\\p{Punct}", "");
        for (int i = 0; i < review.length(); i++) {
            if (review.substring(i, i + 1).equals(" ")) {
                totalSentiment += sentimentVal(placeholder);
                placeholder = "";
            } else {
                placeholder += review.substring(i, i + 1);
            }
        }
        return totalSentiment;
    }
    public static int starRating(String fileName) {
        double totalSentiment = totalSentiment(fileName);
        if (totalSentiment > 15) {
            return 4;
        } else if (totalSentiment > 10) {
            return 3;
        } else if (totalSentiment > 5) {
            return 2;
        } else if (totalSentiment > 0) {
            return 1;
        } else {
            return 0;
        }
    }
    public static String fakeReview(String fileName) {
        String review = textToString(fileName);
        String fake = "";

        for (int i = 0; i < review.length() - 1; i++) {
            if (review.substring(i, i + 1).equals("*")) {
                i++;
                String replace = "";
                boolean isWord = true;
                while (isWord) {
                    replace += review.substring(i, i + 1);
                    i++;
                    if (review.substring(i, i + 1).equals(" ")) {
                        isWord = false;
                    }
                }
                replace = replace.replaceAll("\\p{Punct}", "");
                replace = randomAdjective() + " ";
                fake += replace;
            } else {
                fake += review.substring(i, i + 1);
            }
        }
        return fake;
    }

    public static String fakeReviewStronger(String fileName) {
        String review = textToString(fileName);
        String fake = "";

        for (int i = 0; i < review.length() - 1; i++) {
            if (review.substring(i, i + 1).equals("*")) {
                i++;
                String replace = "";
                boolean isWord = true;
                while (isWord) {
                    replace += review.substring(i, i + 1);
                    i++;
                    if (review.substring(i, i + 1).equals(" ")) {
                        isWord = false;
                    }
                }
                replace = replace.replaceAll("\\p{Punct}", "");
                if (sentimentVal(replace) > 0) {
                    replace = randomPositiveAdj() + " ";
                } else if (sentimentVal(replace) < 0) {
                    replace = randomNegativeAdj() + " ";
                } else {
                    replace = randomAdjective() + " ";
                }
                fake += replace;
            } else {
                fake += review.substring(i, i + 1);
            }
        }
        return fake;
    }
}