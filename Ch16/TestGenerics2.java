import java.util.*;

abstract class Animal {

    void eat() {
        System.out.println("Animal eating.");
    }
}

class Dog extends Animal {

    void bark() { }
}

class Cat extends Animal {

    void meow() { }
}

public class TestGenerics2 {

    public void takeAnimals(ArrayList<? extends Animal> animals) {

        for (Animal a : animals) {
            a.eat();
        }
    }

    public void go() {
        ArrayList<Animal> animals = new ArrayList<Animal>();

        animals.add(new Dog());
        animals.add(new Cat());
        animals.add(new Dog());
        takeAnimals(animals);
    }

    public static void main(String[] args) {
        new TestGenerics2().go();
    }
}