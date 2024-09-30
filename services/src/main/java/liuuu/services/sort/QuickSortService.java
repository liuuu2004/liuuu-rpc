package liuuu.services.sort;

public interface QuickSortService {
    void quickSort(int[] input, int low, int high);
    int[] quickSortAndReturn(int[] input, int low, int high);
}
