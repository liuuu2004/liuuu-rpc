package liuuu;

import liuuu.annotation.RpcScan;
import liuuu.services.sort.QuickSortController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RpcScan(basePackage = {"liuuu"})
public class NettyClientMain {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyClientMain.class);
//        HelloController helloController = (HelloController) applicationContext.getBean("helloController");
//        helloController.test();

        QuickSortController quickSortController = (QuickSortController) applicationContext.getBean("quickSortController");
        quickSortController.test();
    }
}
