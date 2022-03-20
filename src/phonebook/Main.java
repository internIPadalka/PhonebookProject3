package phonebook;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static phonebook.Search.listSearch;
import static phonebook.Sort.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner directoryScanner = new Scanner(new File("/Users/ipadalka/Downloads/directory.txt"));
        Scanner findScanner = new Scanner(new File("/Users/ipadalka/Downloads/find.txt"));

        ArrayList<String> directory = new ArrayList<>();
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

        ArrayList<String> find = new ArrayList<>();
        while (findScanner.hasNextLine()) {
            find.add(findScanner.nextLine());
        }
        findScanner.close();

        // Liner Search
        System.out.println("Start searching (linear search)...");

        long beforeLinearSearch = System.currentTimeMillis();
        int linearSearchFound = listSearch(directory, find, Algorithm.LINEAR);

        long linerSearchTime = System.currentTimeMillis() - beforeLinearSearch;

        System.out.println(found(linearSearchFound, find.size()) + "Time taken: " + formattedTime(linerSearchTime));

        System.out.println();

        // Bubble Sort + Jump Search
        System.out.println("Start searching (bubble sort + jump search)...");

        long beforeBubbleJumpSearch = System.currentTimeMillis();

        // Bubble Sort
        long beforeBubbleSort = System.currentTimeMillis();
        ArrayList<String> bubbleSortDirectory = new ArrayList<>(directory);
        boolean bubbleSort = bubbleSort(bubbleSortDirectory, linerSearchTime);

        long bubbleSortTime = System.currentTimeMillis() - beforeBubbleSort;

        long beforeJumpSearch;
        long jumpSearchTime = 0L;
        int jumpSearchFound;
        if (bubbleSort) {
            // Jump Search
            beforeJumpSearch = System.currentTimeMillis();
            jumpSearchFound = listSearch(bubbleSortDirectory, find, Algorithm.JUMP);

            jumpSearchTime = System.currentTimeMillis() - beforeJumpSearch;

            System.out.print(found(jumpSearchFound, find.size()));
        } else {
            // Linear Search
            beforeLinearSearch = System.currentTimeMillis();
            linearSearchFound = listSearch(bubbleSortDirectory, find, Algorithm.LINEAR);
            linerSearchTime = System.currentTimeMillis() - beforeLinearSearch;

            System.out.print(found(linearSearchFound, find.size()));
        }

        long afterBubbleJumpSearch = System.currentTimeMillis();

        long bubbleJumpSearchTime = afterBubbleJumpSearch - beforeBubbleJumpSearch;

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

        long beforeQuickBinarySearch = System.currentTimeMillis();

        // Quick Sort
        long beforeQuickSort = System.currentTimeMillis();
        ArrayList<String> quickSortDirectory = new ArrayList<>(directory);
        quickSort(quickSortDirectory, 0, quickSortDirectory.size() - 1);

        long quickSortTime = System.currentTimeMillis() - beforeQuickSort;

        // Binary Search
        long beforeBinarySearch = System.currentTimeMillis();
        int binarySearchFound = listSearch(quickSortDirectory, find, Algorithm.BINARY);

        long binarySearchTime = System.currentTimeMillis() - beforeBinarySearch;

        long quickBinarySearchTime = System.currentTimeMillis() - beforeQuickBinarySearch;

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


enum Algorithm {
    LINEAR, JUMP, BINARY
}

abstract class Search {
    public static int listSearch(ArrayList<String> directory, ArrayList<String> find, Algorithm algorithm) {
        int found = 0;

        for (String target : find) {
            switch (algorithm) {
                case LINEAR:
                    found += linearSearch(directory, target) ? 1 : 0;
                    break;
                case JUMP:
                    found += jumpSearch(directory, target) ? 1 : 0;
                    break;
                case BINARY:
                    found += binarySearch(directory, target) ? 1 : 0;
                    break;
                default:
                    return 0;
            }
        }

        return found;
    }

    public static boolean linearSearch(ArrayList<String> directory, String find) {
        for (String contact : directory) {
            if (contact.equalsIgnoreCase(find)) {
                return true;
            }
        }

        return false;
    }

    public static boolean jumpSearch(ArrayList<String> directory, String find) {
        int blockSize = (int) Math.floor(Math.sqrt(directory.size()));

        int index = 0;
        while (index < directory.size()) {
            if (directory.get(index).equalsIgnoreCase(find)) return true;

            if (directory.get(index).compareToIgnoreCase(find) > 0) {
                for (int i = index - 1; i > index - blockSize; i--) {
                    if (directory.get(index).equalsIgnoreCase(find)) return true;
                }

                return false;
            }

            index += blockSize;
        }

        for (int i = directory.size() - 1; i > directory.size() - blockSize; i--) {
            if (directory.get(i).equals(find)) return true;
        }

        return false;
    }

    public static boolean binarySearch(ArrayList<String> directory, String find) {
        int left = 0;
        int right = directory.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            String midElement = directory.get(mid);

            if (midElement.equalsIgnoreCase(find)) return true;
            else if (midElement.compareToIgnoreCase(find) > 0) right = mid - 1;
            else left = mid + 1;
        }

        return false;
    }
}

abstract class Sort {
    public static boolean bubbleSort(ArrayList<String> list, long maxTime) {
        long millis = System.currentTimeMillis();
        long limit = maxTime * 10;

        for (int size = list.size(); size > 1; size--) {
            if (System.currentTimeMillis() - millis > limit) {
                return false;
            }

            for (int i = 0; i < size; i++) {
                if (System.currentTimeMillis() - millis > limit) {
                    return false;
                }

                if ((i + 1 < size) && (list.get(i).compareToIgnoreCase(list.get(i + 1)) == 1)) {
                    String tmp = list.get(i);

                    list.set(i, list.get(i + 1));
                    list.set(i + 1, tmp);
                }
            }
        }

        return true;
    }

    public static void quickSort(ArrayList<String> list, int left, int right) {
        if (left < right) {
            String pivot = list.get(right);

            int pointer = left - 1;

            for (int i = left; i <= right - 1; i++) {
                if (list.get(i).compareToIgnoreCase(pivot) < 0) {
                    pointer++;

                    swap(list, pointer, i);
                }
            }

            swap(list, pointer + 1, right);

            int pi = (pointer + 1);

            quickSort(list, left, pi - 1);
            quickSort(list, pi + 1, right);
        }
    }

    private static void swap(ArrayList<String> list, int i, int j) {
        String temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}