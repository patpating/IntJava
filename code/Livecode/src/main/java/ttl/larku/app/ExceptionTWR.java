package ttl.larku.app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

/**
 * @author whynot
 */
public class ExceptionTWR {

    public static void main(String [] args) {
        tryWithResourceDemo();
    }

    public static void tryWithResourceDemo() {
        try(FileInputStream fis = new FileInputStream("pom.xml");
            FileInputStream fis2 = new FileInputStream("xzy.txt");) {

            int read = fis.read();

            System.out.println("read: " + read);
            String str = null;
            int x = str.length();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    static class MyClass implements AutoCloseable
//    {
//        private Socket socket;
//        public MyClass() {
//            try {
//                socket = new Socket("abc.com", 4849);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void close() {
//            try {
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println("MyClass::close called");
//        }
//    }

    public static void awfulExceptionCode(String[] args) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("pom.xml");

            int read = fis.read();

            System.out.println("read: " + read);
            String str = null;
            int x = str.length();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Closing file");
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
