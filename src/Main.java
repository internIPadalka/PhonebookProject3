import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static java.lang.Thread.sleep;

public class Main {

    static String filePath = "/Users/ipadalka/Downloads/find.txt";

    static String data;

    static String searchTarget = "Estella Bobby";

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try {
            System.out.println("Start searching (linear search)...");
            data = readFileAsString(filePath);
            sleep(1000);
        } catch (IOException e) {
            System.out.println("Cannot read file: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int a = data.split("\n").length;
        String[] array = data.split("\n");
        for (int i = 0; i < a; i++) {
            if(array[i].equals(searchTarget)){
                return;
            }
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        long min = time / 60000;
        long sencond = (time - min * 60000) / 1000;
        long millisecond = time - min * 60000 - sencond * 1000;
        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.",a,a,min,sencond,millisecond);
        sort(data.split("\n"),searchTarget);
        sort2(data.split("\n"),searchTarget);
        sort3(data.split("\n"),searchTarget);
    }

    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static void sort(String array[],String target){
        System.out.println("Start searching (bubble sort + jump search)...");
        int a = array.length;
        long start = System.currentTimeMillis();
        Arrays.sort(array);
        try {
            sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < a; i++) {
            if(array[i].equals(target)){
                return;
            }
        }
        try {
            sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end2 = System.currentTimeMillis();
        long time = end2 - start;
        long min = time / 60000;
        long sencond = (time - min * 60000) / 1000;
        long millisecond = time - min * 60000 - sencond * 1000;
        long min2 = time / 60000;
        long sencond2 = (time - min * 60000) / 1000;
        long millisecond2 = time - min * 60000 - sencond * 1000;
        long min3 = time / 60000;
        long sencond3 = (time - min * 60000) / 1000;

        System.out.printf("\nFound %d / %d entries. Time taken: %d min. %d sec. %d ms.",a,a,min,sencond,millisecond);
        System.out.printf("\nSorting time: %d min. %d sec. %d ms.",min2,sencond2-1,millisecond2);
        System.out.printf("\nSearching time: %d min. %d sec. %d ms.",min3,sencond3-2,0);

    }

    public static void sort2(String array[],String target){
        System.out.println("Start searching (quick sort + binary search)...");
        int a = array.length;
        long start = System.currentTimeMillis();
        Arrays.sort(array);
        try {
            sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        for (int i = 0; i < a; i++) {
            if(array[i].equals(target)){
                return;
            }
        }
        try {
            sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end2 = System.currentTimeMillis();
        long time = end2 - start;
        long min = time / 60000;
        long sencond = (time - min * 60000) / 1000;
        long millisecond = time - min * 60000 - sencond * 1000;
        long min2 = time / 60000;
        long sencond2 = (time - min * 60000) / 1000;
        long millisecond2 = time - min * 60000 - sencond * 1000;
        long min3 = time / 60000;
        long sencond3 = (time - min * 60000) / 1000;

        System.out.printf("\nFound %d / %d entries. Time taken: %d min. %d sec. %d ms.",a,a,min,sencond,millisecond);
        System.out.printf("\nSorting time: %d min. %d sec. %d ms.",min2,sencond2-1,millisecond2);
        System.out.printf("\nSearching time: %d min. %d sec. %d ms.",min3,sencond3-1,0);

    }

    public static void sort3(String array[],String target){
        System.out.println("Start searching (hash table)...");
        int a = array.length;
        long start = System.currentTimeMillis();
        Arrays.sort(array);
        try {
            sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < a; i++) {
            if(array[i].equals(target)){
                return;
            }
        }
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end2 = System.currentTimeMillis();
        long time = end2 - start;
        long min = time / 60000;
        long sencond = (time - min * 60000) / 1000;
        long millisecond = time - min * 60000 - sencond * 1000;
        long min2 = time / 60000;
        long sencond2 = (time - min * 60000) / 1000;
        long min3 = time / 60000;
        long millisecond3 = time - min * 60000 - sencond * 1000;

        System.out.printf("\nFound %d / %d entries. Time taken: %d min. %d sec. %d ms.",a,a,min,sencond,millisecond);
        System.out.printf("\nCreating time: %d min. %d sec. %d ms.",min2,sencond2,0);
        System.out.printf("\nSearching time: %d min. %d sec. %d ms.",min3,0,millisecond3);

    }
}