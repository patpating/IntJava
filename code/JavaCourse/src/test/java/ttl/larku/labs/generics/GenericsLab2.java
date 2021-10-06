package ttl.larku.labs.generics;

import java.util.List;

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

        /**
         * Output an appropriate sound.
         */
        public abstract void speak();
    }

    //TODO - Uncomment the class and finish implementation.
    /*
    class Dog extends Pet {
    }
     */

    //TODO - Uncomment the class and finish implementation.
    // Also may need to look up how Llama's speak.
    /*
    class Llama extends Pet {
    }
    */


    //TODO - This function performs an operation on each Pet in a List
    // and then adds it to another List.  It's signature will need
    // some fiddling around with to get everything to compile.
    public void fromOneToTheOther(List<Pet> to, List<Pet> from) {
        for (Pet pet : from) {
            pet.speak();
            to.add(pet);
        }
    }


    /**
     * TODO - All the code in the 4 tests below is commented out.  All you should do
     *  here is uncomment out the code.  You should not change the code in
     *  any way.
     *  Warning - For extra fun, there is a sneaky instructor trick below.
     *  One of the calls to the 'fromOneToTheOther' function will *never* compile
     *  because it would result in a corrupted List if it was allowed.
     *  But only one, all the remaining calls should work.
     */
//    @Test
//    public void test1() {
//        List<Pet> pets = new ArrayList<>();
//        Dog dog = new Dog("Rufus");
//        pets.add(dog);
//        Llama llama = new Llama("Joey");
//        pets.add(llama);
//
//        List<Pet> giveMe = new ArrayList<>();
//        fromOneToTheOther(giveMe, pets);
//        assertEquals(2, giveMe.size());
//    }
//
//    @Test
//    public void test2() {
//        List<Pet> pets = new ArrayList<>();
//        Dog dog = new Dog("Rufus");
//        Llama llama = new Llama("Joey");
//        pets.add(dog);
//        List<Pet> giveMe = new ArrayList<>();
//
//        List<Dog> dogs = new ArrayList<>();
//        dogs.add(dog);
//        fromOneToTheOther(giveMe, dogs);
//        assertEquals(1, giveMe.size());
//    }
//
//    @Test
//    public void test3() {
//        Dog dog = new Dog("Rufus");
//        List<Dog> dogs = new ArrayList<>();
//        dogs.add(dog);
//
//        List<Dog> giveMeDogs = new ArrayList<>();
//        fromOneToTheOther(giveMeDogs, dogs);
//
//        assertEquals(1, giveMeDogs.size());
//    }
//
//    @Test
//    public void test4() {
//        Llama llama = new Llama("Joey");
//        List<Llama> llammas = new ArrayList<>();
//        llammas.add(llama);
//        List<Pet> giveMe = new ArrayList<>();
//
//        fromOneToTheOther(giveMe, llammas);
//        assertEquals(1, giveMe.size());
//
//    }
}
