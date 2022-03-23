package phonebook;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Scanner directoryScanner = new Scanner(new File("/Users/ipadalka/Downloads/directory.txt"));
        Scanner findScanner = new Scanner(new File("/Users/ipadalka/Downloads/find.txt"));

        ArrayList<String> find;
        ArrayList<String> directory;
        ArrayList<String> bubbleSortDirectory;
        ArrayList<String> quickSortDirectory;

        long beforeLinearSearch;
        int linearSearchFound;
        long linerSearchTime;
        long beforeBubbleJumpSearch;
        long beforeBubbleSort;
        boolean bubbleSort;
        long bubbleSortTime;
        long beforeJumpSearch;
        long jumpSearchTime = 0L;
        int jumpSearchFound;
        long afterBubbleJumpSearch;
        long bubbleJumpSearchTime;
        long beforeQuickBinarySearch;
        long beforeQuickSort;
        long quickSortTime;
        long beforeBinarySearch;
        int binarySearchFound;
        long binarySearchTime;
        long quickBinarySearchTime;

        directory = new ArrayList<>();
        while (directoryScanner.hasNextLine()) {
            String[] parts = directoryScanner.nextLine().split("\\s");

            String name = "";
            for (int i = 0; i < parts.length; i++) {
                if (i == 0) continue;

                name += parts[i];

                if (i != parts.length - 1) name += " ";
            }

            directory.add(name);
        }
        directoryScanner.close();
        find = new ArrayList<>();
        while (findScanner.hasNextLine()) {
            find.add(findScanner.nextLine());
        }
        findScanner.close();

        // Liner Search
        System.out.println("Start searching (linear search)...");

        beforeLinearSearch = System.currentTimeMillis();
        linearSearchFound = new Search().listSearch(directory, find, Algorithm.LINEAR);

        linerSearchTime = System.currentTimeMillis() - beforeLinearSearch;

        System.out.println(found(linearSearchFound, find.size()) + "Time taken: " + formattedTime(linerSearchTime));

        System.out.println();

        // Bubble Sort + Jump Search
        System.out.println("Start searching (bubble sort + jump search)...");

        beforeBubbleJumpSearch = System.currentTimeMillis();

        // Bubble Sort
        beforeBubbleSort = System.currentTimeMillis();
        bubbleSortDirectory = new ArrayList<>(directory);
        bubbleSort = new Sort().bubbleSort(bubbleSortDirectory, linerSearchTime);

        bubbleSortTime = System.currentTimeMillis() - beforeBubbleSort;

        if (bubbleSort) {
            // Jump Search
            beforeJumpSearch = System.currentTimeMillis();
            jumpSearchFound = new Search().listSearch(bubbleSortDirectory, find, Algorithm.JUMP);

            jumpSearchTime = System.currentTimeMillis() - beforeJumpSearch;

            System.out.print(found(jumpSearchFound, find.size()));
        } else {
            // Linear Search
            beforeLinearSearch = System.currentTimeMillis();
            linearSearchFound = new Search().listSearch(bubbleSortDirectory, find, Algorithm.LINEAR);
            linerSearchTime = System.currentTimeMillis() - beforeLinearSearch;

            System.out.print(found(linearSearchFound, find.size()));
        }

        afterBubbleJumpSearch = System.currentTimeMillis();

        bubbleJumpSearchTime = afterBubbleJumpSearch - beforeBubbleJumpSearch;

        System.out.print("Time Taken: " + formattedTime(bubbleJumpSearchTime));
        System.out.print("\n" + sortingTime(bubbleSortTime));
        if (!bubbleSort) {
            System.out.print(" - STOPPED, moved to linear search\n");

            System.out.println(searchingTime(linerSearchTime));
        } else {
            System.out.println(searchingTime(jumpSearchTime));
        }

        System.out.println();

        // Quick Sort + Binary Search
        System.out.println("Start searching (quick sort + binary search)...");

        beforeQuickBinarySearch = System.currentTimeMillis();

        // Quick Sort
        beforeQuickSort = System.currentTimeMillis();
        quickSortDirectory = new ArrayList<>(directory);
        new Sort().quickSort(quickSortDirectory, 0, quickSortDirectory.size() - 1);

        quickSortTime = System.currentTimeMillis() - beforeQuickSort;

        // Binary Search
        beforeBinarySearch = System.currentTimeMillis();
        binarySearchFound = new Search().listSearch(quickSortDirectory, find, Algorithm.BINARY);

        binarySearchTime = System.currentTimeMillis() - beforeBinarySearch;

        quickBinarySearchTime = System.currentTimeMillis() - beforeQuickBinarySearch;

        System.out.println(found(binarySearchFound, find.size()) + "Time taken: " + formattedTime(quickBinarySearchTime));
        System.out.println(sortingTime(quickSortTime));
        System.out.println(searchingTime(binarySearchTime));

        System.out.println();
    }

    private static String found(int found, int total) {
        return ("Found " + found + " / " + total + " entries. ");
    }

    private static String formattedTime(long millis) {
        return (((millis / (1000 * 60)) % 60) + " min. " + ((millis / 1000) % 60) + " sec. " + (millis % 1000) + " ms.");
    }

    private static String sortingTime(long millis) {
        return "Sorting time: " + formattedTime(millis);
    }

    private static String searchingTime(long millis) {
        return "Searching time: " + formattedTime(millis);
    }
}
