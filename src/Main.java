import java.util.Random;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        MyHashTable<MyTestingClass, String> table = new MyHashTable<>();
        Random rand = new Random();

        for (int i = 0; i < 10000; i++) {
            int a = rand.nextInt();
            String b = UUID.randomUUID().toString();
            MyTestingClass key = new MyTestingClass(a, b);
            String value = "value " + i;
            table.put(key, value);
        }

        table.printBucketSizes();
    }
}