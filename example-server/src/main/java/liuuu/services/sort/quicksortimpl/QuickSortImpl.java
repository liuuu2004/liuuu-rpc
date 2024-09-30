package liuuu.services.sort.quicksortimpl;

import liuuu.annotation.RpcService;
import liuuu.services.sort.QuickSortService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RpcService(group = "test1", version = "version1")
public class QuickSortImpl implements QuickSortService {
    static {
        System.out.println("QuickSortServiceImpl Created");
    }

    @Override
    public void quickSort(int[] input, int low, int high) {
//        log.info("Entered quickSort...");
        if (low < high) {
            int pivot = partition(input, low, high);
            quickSort(input, low, pivot - 1);
            quickSort(input, pivot, high);
        }
    }

    @Override
    public int[] quickSortAndReturn(int[] input, int low, int high) {
        int[] output = input;
        quickSort(output, 0, output.length-1);
        return output;
    }

    private int partition(int[] input, int low, int high) {
        int pivot = input[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (input[j] <= pivot) {
                ++i;
                swap(input, i, j);
            }
        }
        swap(input, i + 1, high);
        return i + 1;
    }

    private void swap(int[] input, int i, int j) {
        int temp = input[i];
        input[i] = input[j];
        input[j] = temp;
    }
}
