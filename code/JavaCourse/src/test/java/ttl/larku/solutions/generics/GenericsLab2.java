package ttl.larku.solutions.generics;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * TODO - Comments indicate where you need to make changes. In some places code is
 * commented out and you need to uncomment it and make it work.
 * In other places you need to make changes to compiling code.
 *
 * @author whynot
 */
public class GenericsLab2 {

    abstract class Pet {

        private final String name;

        public Pet(String name) {
            this.name = name;
        }

        public abstract void speak();
    }

    //TODO - Uncomment the class and finish implementation.
    class Dog extends Pet {

        public Dog(String name) {
            super(name);
        }

        @Override
        public void speak() {
            System.out.println("Woof");
        }
    }

    //TODO - Uncomment the class and finish implementation.
    // Also may need to look up how Llama's speak.
    class Llama extends Pet {
        public Llama(String name) {
            super(name);
        }

        @Override
        public void speak() {
            System.out.println("hmmouwwww");
        }
    }


    //TODO - This function performs an operation on each Pet in a List
    // and then adds it to another List.  It's signature will need
    // some fiddling around with to get everything to compile.

    //Note - We use 'super' on the first argument because we want a List that will
    // accept Pets. Which means a List of either Pet or any of it's super classes.
    // We use 'extends' on the second argument because we need for the elements
    // to be *at least* of type Pet so we can invoke Pet behaviours on them.
    public void fromOneToTheOther(List<? super Pet> to, List<? extends Pet> from) {
        for (Pet pet : from) {
            pet.speak();
            to.add(pet);
        }
    }


    /**
     * TODO - All the code in the 4 tests below is commented out.  All you should do
     * here is uncomment out the code.  You should not change the code in
     * any way.
     * Warning - For extra fun, there is a sneaky instructor trick below.
     * One of the calls to the 'fromOneToTheOther' function will *never* compile
     * because it would result in a corrupted List if if was allowed.
     * But only one, all the remaining calls should work.
     */
    @Test
    public void test1() {
        List<Pet> pets = new ArrayList<>();
        Dog dog = new Dog("Rufus");
        pets.add(dog);
        Llama llama = new Llama("Joey");
        pets.add(llama);

        List<Pet> giveMe = new ArrayList<>();
        fromOneToTheOther(giveMe, pets);
        assertEquals(2, giveMe.size());
    }

    @Test
    public void test2() {
        List<Pet> pets = new ArrayList<>();
        Dog dog = new Dog("Rufus");
        Llama llama = new Llama("Joey");
        pets.add(dog);
        List<Pet> giveMe = new ArrayList<>();

        List<Dog> dogs = new ArrayList<>();
        dogs.add(dog);
        fromOneToTheOther(giveMe, dogs);
        assertEquals(1, giveMe.size());
    }

    @Test
    public void test3() {
        Dog dog = new Dog("Rufus");
        List<Dog> dogs = new ArrayList<>();
        dogs.add(dog);

        List<Dog> giveMeDogs = new ArrayList<>();
        //NOTE - This call below will never compile.  We are passing a List<Dog>
        // into a function whose purpose is to add Pets into that
        // argument.  Which could result in Llammas getting into a
        // List of Dogs.  Not a good thing.

//        fromOneToTheOther(giveMeDogs, dogs);

        assertEquals(0, giveMeDogs.size());
    }

    @Test
    public void test4() {
        Llama llama = new Llama("Joey");
        List<Llama> llammas = new ArrayList<>();
        llammas.add(llama);
        List<Pet> giveMe = new ArrayList<>();

        fromOneToTheOther(giveMe, llammas);
        assertEquals(1, giveMe.size());

    }
}
