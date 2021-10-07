package ttl.larku.app;

import ttl.larku.domain.Student;

import java.lang.reflect.InvocationTargetException;

/**
 * @author whynot
 */
public class LoadClass {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Student> clz = loadClass("ttl.larku.domain.Student");
        Student s = clz.getDeclaredConstructor().newInstance();
        System.out.println(s);
    }

    public static <T> Class<T> loadClass(String name) throws ClassNotFoundException {
        @SuppressWarnings({"unchecked", "rawtype"})
        Class<T> found = (Class<T>)(Class.forName(name));

        return found;
    }


}
