package Exercices;

public class C {
    public static void main(String[] args) {
        C x = new C();
        C y = new C();
        C z = x;
        System.out.println(z.i + " " + z.j);
    }
    public static int i;
    public int j;
    public C(){
        i++;
        j = i;
    }
}
