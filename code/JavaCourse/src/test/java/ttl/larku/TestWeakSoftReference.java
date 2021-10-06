package ttl.larku;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Test Weak and Soft References.  Either Run this with a small heap,
 * e.g. -Xmx512m, or change the numbers in the garbage loop appropriately.
 * Adding a verbose gc flag like -verbose:gc or -XX:+PrintGCDetails is
 * also useful to see how the gc behaves.
 *
 * @author whynot
 */
public class TestWeakSoftReference {

    public static void main(String[] args) throws IOException, URISyntaxException {
//        testWeakReferences();
        //testSoftReferences();
    }

    /**
     * We allocate some memory and check how much is available.  Then we allocate
     * many more objects to create memory pressure,
     * WeakReferences will be nulled and collected at the first Young GC.
     * This can be with just a little amount of memory pressure.
     */
    @Test
    public void testWeakReferences() {
        System.out.println("Testing Weak References");
        //This queue can be polled to know when referenced objects have been
        //garbage collected.  See the RefQueueChecker class below.
        ReferenceQueue<Data> referenceQueue = new ReferenceQueue<>();
        List<WeakReference<Data>> wrList = new ArrayList<>();

        //Run the RefQChecker in a separate thread.
        ExecutorService refExecutor = Executors.newSingleThreadExecutor();
        WeakReferenceQueueChecker sqc = new WeakReferenceQueueChecker(referenceQueue);
        refExecutor.submit(sqc);

        System.out.printf("Free memory At Start, %,d%n", Runtime.getRuntime().freeMemory());
        for (int i = 0; i < 10; i++) {
            WeakReference<Data> wr = new WeakReference<>(new Data(i), referenceQueue);
            wrList.add(wr);
        }

        System.out.printf("Free memory After Adding Weak References added, %,d%n", Runtime.getRuntime().freeMemory());
        //With lots of memory
        System.out.println("Weak References with No memory pressure");
        for (int i = 0; i < 10; i++) {
            System.out.println(wrList.get(i).get());
        }

        //Now a bit of pressure  on memory to cause a young GC
        List<Data> otherMemory = new ArrayList<>();
        for (int i = 0; i < 120; i++) {
            System.out.println("Adding pressure: " + i);
            otherMemory.add(new Data(i));
        }


        System.out.printf("Free memory After Pressure, %,d%n", Runtime.getRuntime().freeMemory());
        //After young GC, WeakReferences should be null
        System.out.println("Weak References After memory pressure");
        for (int i = 0; i < 10; i++) {
            System.out.println(i + ": " + wrList.get(i).get());
        }

        sqc.keepGoing = false;
        System.out.println("End Weak Reference test");
    }

    /**
     * SoftReferences will require more memory pressure to collect.
     *
     * SoftReferences will be nulled and collected at the first FULL GC.
     * This can take more pressure than for WeakReferences.
     */
    @Test
    public void testSoftReferences() {
        System.out.println("Testing Soft References");
        System.out.printf("Free memory At Start, %,d%n", Runtime.getRuntime().freeMemory());
        //This queue can be polled to know when referenced objects have been
        //garbage collected.  See the RefQueueChecker class below.
        ReferenceQueue<Data> referenceQueue = new ReferenceQueue<>();
        List<SoftReference<Data>> sRefList = new ArrayList<>();

        //Run the RefQChecker in a separate thread.
        ExecutorService refExecutor = Executors.newSingleThreadExecutor();
        SoftRefQChecker sqc = new SoftRefQChecker(referenceQueue);
        refExecutor.submit(sqc);

        int numSoft = 100;
        for (int i = 0; i < numSoft; i++) {
            SoftReference<Data> wr = new SoftReference<>(new Data(i), referenceQueue);
            sRefList.add(wr);
        }

        System.out.printf("Free memory After References, %,d%n", Runtime.getRuntime().freeMemory());
        //With lots of memory
        System.out.println("No memory pressure");
        for (int i = 0; i < numSoft; i++) {
            System.out.println(sRefList.get(i).get());
        }


        //Now put pressure on the memory
        List<Data> otherMemory = new ArrayList<>();
        //400+ or so will cause enough memory pressure to
        //collect the soft references
        int numHard = 207;
        for (int i = 0; i < numHard; i++) {
            System.out.println("Adding pressure: " + i);
            otherMemory.add(new Data(i));
        }

        System.out.printf("Free memory After Pressure, %,d%n", Runtime.getRuntime().freeMemory());

        //After memory pressure
        System.out.println("Soft References left at end");
        for (int i = 0; i < numSoft; i++) {
            System.out.println(i + ": " + sRefList.get(i).get());
        }

        sqc.keepGoing = false;
        System.out.println("End Soft Reference test");
    }


    class WeakReferenceQueueChecker implements Runnable {

        private final ReferenceQueue<Data> refQueue;

        public WeakReferenceQueueChecker(ReferenceQueue<Data> refQueue) {
            this.refQueue = refQueue;
        }

        private volatile boolean keepGoing = true;
        @Override
        public void run() {
            Reference<? extends Data> ref = null;
            while(keepGoing) {
                System.out.println("WeakQueue Checker running:");
                try {
                    ref = refQueue.remove();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("WeakReference gone: " + ref);
                ref.clear();
            }
//            while(keepGoing) {
//                System.out.println("RQChecker running:");
//                ref = (Reference)refQueue.poll();
//                if(ref != null) {
//                    System.out.println("Reference gone: " + ref);
//                }
//                //ref.clear();
//            }
        }
    }

    class SoftRefQChecker implements Runnable {

        private final ReferenceQueue<Data> refQueue;

        public SoftRefQChecker(ReferenceQueue<Data> refQueue) {
            this.refQueue = refQueue;
        }

        private volatile boolean keepGoing = true;
        @Override
        public void run() {
            Reference<? extends Data> ref = null;
            while(keepGoing) {
                System.out.println("SoftReferenceQ Checker running:");
                try {
                    ref = refQueue.remove();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Soft Reference gone: " + ref);
                ref.clear();
            }
        }
    }

}

class Data {

    public int id;
    public byte[] iarr = new byte[1_000_000];

    public Data(int id) {
        this.id = id;
    }

    public String toString() {
        return "Data: id=" + id + ", iarr = " + iarr;
    }


}



