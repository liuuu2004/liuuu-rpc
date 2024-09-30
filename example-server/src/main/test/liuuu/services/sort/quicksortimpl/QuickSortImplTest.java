package liuuu.services.sort.quicksortimpl;

import liuuu.services.sort.QuickSortService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertArrayEquals;

public class QuickSortImplTest {
    private QuickSortService quickSortService;

    @BeforeEach
    public void setUp() {
        quickSortService = new QuickSortImpl();
    }

    @Test
    public void testQuickSort() {
        int[] input = {3, 6, 8, 10, 1, 2, 1};
        int[] expected = {1, 1, 2, 3, 6, 8, 10};

        quickSortService.quickSort(input, 0, input.length-1);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testEmpty() {
        int[] input = {};
        int[] expected = {};

        quickSortService.quickSort(input, 0, input.length-1);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testSingle() {
        int[] input = {1};
        int[] expected = {1};

        quickSortService.quickSort(input, 0, input.length-1);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testQuickSortAndReturn() {
        int[] input = {3, 6, 8, 10, 1, 2, 1};
        int[] expected = {1, 1, 2, 3, 6, 8, 10};

        int[] output = quickSortService.quickSortAndReturn(input, 0, input.length);
        assertArrayEquals(output, expected);
    }
}
