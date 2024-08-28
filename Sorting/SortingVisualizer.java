import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Random;

/**
 * The `SortingVisualizer` class in Java creates a graphical representation of
 * sorting algorithms such
 * as bubble sort, selection sort, insertion sort, quick sort, and merge sort.
 */

public class SortingVisualizer extends JPanel {

    private int[] array;
    private int size = 50;
    private int maxVal = 500;

    public SortingVisualizer() {
        array = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(maxVal);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black); // Set color for all bars to red

        for (int i = 0; i < size; i++) {
            int height = array[i];
            int x = i * getWidth() / size;
            int y = getHeight() - height;
            g.fillRect(x, y, getWidth() / size, height);
        }
    }

    public void bubbleSort() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    repaint();
                    delay();
                }
            }
        }
    }

    public void mergeSort(int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;

            // Divide the array into two halves
            mergeSort(low, mid);
            mergeSort(mid + 1, high);

            // Merge the sorted halves
            merge(array, low, mid, high);
        }
    }

    private void merge(int[] arr, int low, int mid, int high) {
        int n1 = mid - low + 1;
        int n2 = high - mid;

        // Create temporary arrays
        int[] L = new int[n1];
        int[] R = new int[n2];

        // Copy data to temporary arrays
        System.arraycopy(arr, low, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);

        // Merge the temporary arrays back into arr
        int i = 0, j = 0, k = low;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
            repaint();
            delay();
        }

        // Copy the remaining elements of L[] if there are any
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
            repaint();
            delay();
        }

        // Copy the remaining elements of R[] if there are any
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
            repaint();
            delay();
        }
    }

    public void selectionSort() {
        for (int i = 0; i < size - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < size; j++) {
                if (array[j] < array[minIdx]) {
                    minIdx = j;
                }
            }
            swap(array, i, minIdx);
            repaint();
            delay();
        }
    }

    public void insertionSort() {
        for (int i = 1; i < size; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
                repaint();
                delay();
            }
            array[j + 1] = key;
            repaint();
            delay();
        }
    }

    public void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
                repaint();
                delay();
            }
        }
        swap(arr, i + 1, high);
        repaint();
        delay();
        return i + 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void delay() {
        try {
            Thread.sleep(50); // Adjust delay time as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sorting Visualizer");
        SortingVisualizer panel = new SortingVisualizer();
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Uncomment one of the following to visualize different sorting algorithms
        // panel.bubbleSort();
        // panel.selectionSort();
        // panel.insertionSort();
        // panel.quickSort(0, panel.size - 1);
        panel.mergeSort(0, panel.size - 1);
    }
}
