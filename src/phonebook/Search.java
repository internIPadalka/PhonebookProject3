package phonebook;

import java.util.ArrayList;

enum Algorithm {
    LINEAR, JUMP, BINARY
}

public class Search {
    public int listSearch(ArrayList<String> directory, ArrayList<String> find, Algorithm algorithm) {
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

    public boolean linearSearch(ArrayList<String> directory, String find) {
        for (String contact : directory) {
            if (contact.equalsIgnoreCase(find)) {
                return true;
            }
        }

        return false;
    }

    public boolean jumpSearch(ArrayList<String> directory, String find) {
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

    public boolean binarySearch(ArrayList<String> directory, String find) {
        int left = 0;
        int right = directory.size() - 1;
        int mid;
        String midElement;

        while (left <= right) {
            mid = (left + right) / 2;
            midElement = directory.get(mid);

            if (midElement.equalsIgnoreCase(find)) return true;
            else if (midElement.compareToIgnoreCase(find) > 0) right = mid - 1;
            else left = mid + 1;
        }

        return false;
    }
}
