public class MyTestingClass {
    private int a;
    private String b;

    public MyTestingClass(int a, String b) {
        this.a = a;
        this.b = b;
    }
    @Override
    public int hashCode() {
        int p = 7919;
        int q = 10007;
        return (a * p) + (b.hashCode() * q);
    }

}
