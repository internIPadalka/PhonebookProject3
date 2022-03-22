package phonebook;

import java.util.ArrayList;

public class Sort {
    public boolean bubbleSort(ArrayList<String> list, long maxTime) {
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

    public void quickSort(ArrayList<String> list, int left, int right) {
        String pivot;
        int pointer;
        int pi;
        if (left < right) {
            pivot = list.get(right);

            pointer = left - 1;

            for (int i = left; i <= right - 1; i++) {
                if (list.get(i).compareToIgnoreCase(pivot) < 0) {
                    pointer++;

                    swap(list, pointer, i);
                }
            }

            swap(list, pointer + 1, right);

            pi = (pointer + 1);

            quickSort(list, left, pi - 1);
            quickSort(list, pi + 1, right);
        }
    }

    private void swap(ArrayList<String> list, int i, int j) {
        String temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
