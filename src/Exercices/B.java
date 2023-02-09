package Exercices;

public class B {
    public static void execAction(int i, A a) {
        i = 10;
        a = new A();
        a.i = 100;
    }
    public B() {
        System.out.print("Salut");
    }
    public B(int i){
        this();
        System.out.println("Bonjour " + i);
    }

    public static void main(String[] args) {
        B monB = new B(1010);
    }
}
