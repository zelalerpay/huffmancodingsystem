import java.util.ArrayList;
import java.util.List;

public class MinPriorityQueue<T extends Comparable<T>> {

    private int size = 0;
    private List<T> list;

    public MinPriorityQueue() {
        list = new ArrayList<>();
    }

    public int len() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T poll() {
        return poll(0);
    }

    public void add(T elem) {
        list.add(elem);
        siftUp(size);
        size++;
    }

    private T poll(int i) {
        if (size == 0) {
            throw new IllegalStateException("Priority queue is empty");
        }

        size--;
        T dataToReturn = list.get(i);

        if (size > 0) {
            T lastElement = list.remove(size);
            list.set(i, lastElement);
            siftDown(i);
        } else {
            list.clear();
        }

        return dataToReturn;
    }

    private void siftDown(int k) {
        while (true) {
            int left = 2 * k + 1;
            int right = 2 * k + 2;
            int smallest = k;

            if (left < size && isLess(left, smallest)) {
                smallest = left;
            }

            if (right < size && isLess(right, smallest)) {
                smallest = right;
            }

            if (smallest != k) {
                swap(k, smallest);
                k = smallest;
            } else {
                break;
            }
        }
    }

    private void siftUp(int k) {
        while (k > 0) {
            int parentIdx = (k - 1) / 2;

            if (isLess(k, parentIdx)) {
                swap(k, parentIdx);
                k = parentIdx;
            } else {
                break;
            }
        }
    }

    private boolean isLess(int i, int j) {
        T elem1 = list.get(i);
        T elem2 = list.get(j);
        return elem1.compareTo(elem2) <= 0;
    }

    private void swap(int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
