package liuuu.services.sort;

import liuuu.annotation.RpcReference;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class QuickSortController {
    private static final Logger log = LoggerFactory.getLogger(QuickSortController.class);
    @RpcReference(version = "version1", group = "test1")
    private QuickSortService quickSortService;

    public void test() throws InterruptedException {
        int[] input = {3, 6, 8, 10, 1, 2, 1};
        log.debug("Before QuickSort: [{}]", Arrays.toString(input));

        Thread.sleep(2000);
        int[] sortedArray = quickSortService.quickSortAndReturn(input, 0, input.length - 1);
        log.debug("After QuickSort: [{}]", Arrays.toString(sortedArray));
    }
}
