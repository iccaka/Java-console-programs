import java.util.*;
import java.util.concurrent.*;

public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        List<Integer> list = new ArrayList<>();  // Main list
        
        list.addAll(Arrays.asList(23,45,1,3,56,6,123,55,3,1,5,6,1,3,6,3,1,35,6,3,1,35,8,689,56,-1));  // Fill it up with some numbers

        ExecutorService es = Executors.newFixedThreadPool(10);  // Make a fixed thread pool
        Future<Boolean>[] futures = new Future[list.size()];  // We will store the results here

        for (int i = 0; i < list.size(); i++) {
            int currInt = i;
            futures[i] = es.submit(() -> isBiggest(list, list.get(currInt)));  // Execute a new Callable, passing the list and the current integer

            if (futures[i].get()){  // If the number is the biggest one...
                System.out.println(list.get(currInt));  // print it...
                es.awaitTermination(500, TimeUnit.MILLISECONDS);  // wait for every thread to terminate...
                es.shutdown();  // and finally shut down everything
                break;  // Exit from the for loop
            }
        }

    }

    private static boolean isBiggest(List<Integer> list, Integer integer) {
        list.sort(Comparator.naturalOrder());  // Sort the list

        if(list.get(list.size() - 1) > integer){  // get the highest value and check if it is bigger than the passed integer
            return false;
        }

        return true;
    }
}
