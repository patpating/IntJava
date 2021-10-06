package ttl.larku.junit5.dynamic;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ttl.larku.domain.dummy.ComplexOperation;
import ttl.larku.domain.dummy.SimpleOperation;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

/**
 * Dynamic Tests. The basic trick is to return a Stream, Iterable, Iterator etc.
 * of DynamicTest objects.
 */
public class DynamicTestAdvanced {

    // Standard Test for SimpleOperation etc
    @Test
    @Disabled
    public void testSimpleOperationALessThan0() {
        SimpleOperation si = new SimpleOperation();
        assertThrows(RuntimeException.class, () -> {
            si.operation(-1, 20);
        });
    }

    @Test
    @Disabled
    public void testSimpleOperationBLessThan10() {
        SimpleOperation si = new SimpleOperation();
        assertThrows(RuntimeException.class, () -> {
            si.operation(10, 6);
        });
    }

    @Test
    @Disabled
    public void testSimpleOperationWithGoodValues() {
        SimpleOperation si = new SimpleOperation();
        assertThat(si.operation(10, 25), greaterThan(0));
    }


    @TestFactory
    Collection<DynamicNode> dynamicTestforDummyObjectsAll()
            throws ClassNotFoundException, IOException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String packageName = "ttl.larku.domain.dummy";
        List<Class<?>> classes = getClassesForPackage(packageName);

        List<DynamicNode> tests = classes.stream().peek((arg) -> System.out.println("Peek 1 with " + arg))
                .map(classToTest -> {
                    // Method opMethod = classToTest.getDeclaredMethod("operation", int.class,
                    // int.class);
                    Method opMethod = getMethodFromName(classToTest, "operation");
                    Object obj = createInstance(classToTest);

                    return dynamicContainer(classToTest.getName(),
                            Stream.of(dynamicTest(opMethod.getName(), () -> {
                                assertThrows(Exception.class, () -> {
                                    opMethod.invoke(obj, -1, 25);
                                });
                            }), dynamicTest(opMethod.getName(), () -> {
                                assertThrows(Exception.class, () -> {
                                    opMethod.invoke(obj, 10, 6);
                                });
                            }), dynamicTest(opMethod.getName(), () -> {
                                assertThat((Integer) (opMethod.invoke(obj, 10, 25)), greaterThan(0));
                            })));
                }).peek((arg) -> System.out.println("Peek 2 with " + arg)).collect(Collectors.toList());

        return tests;
    }

    @TestFactory
    Collection<DynamicNode> testGetAValue() {
        ComplexOperation co = new ComplexOperation();
        //List<DynamicNode> tests = List.of(
        List<DynamicNode> tests = Arrays.asList(
                dynamicContainer("ComplexOperation",
                        Stream.of(
                                dynamicTest("getAValue", () -> {
                                    assertEquals(3, co.getAValue(5));
                                }),
                                dynamicTest("getAValue", () -> {
                                    assertEquals(4, co.getAValue(15));
                                }),
                                dynamicTest("getAValue", () -> {
                                    assertEquals(5, co.getAValue(25));
                                })
                        )
                ));


        return tests;
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {"ttl.larku.dao.inmemory"})
    public void testGetClasses(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = getClassesForPackage(packageName);
        System.out.println("classes for " + packageName + " are " + classes);
    }

    private Method getMethodFromName(Class<?> clazz, String methodName) {
        try {
            Method m = clazz.getDeclaredMethod("operation", int.class, int.class);
            return m;
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Object createInstance(Class<?> clazz) {
        try {
            Object obj = clazz.getDeclaredConstructor().newInstance();
            return obj;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * Search the classpath for Classes for a particular package
     *
     * @param packageName
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static List<Class<?>> getClassesForPackage(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path); // Load as resources
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if (!resource.getPath().contains("test-classes")) {
                // Create Files out of them
                dirs.add(new File(resource.getFile()));
            }
        }
        List<Class<?>> classes = dirs.stream().flatMap(file -> Arrays.stream(file.listFiles())).filter(file -> {
            boolean b = file.isFile() && file.getName().endsWith(".class");
            return b;
        }).map((file) -> DynamicTestAdvanced.getClassFromFile(packageName, file))
                .filter(cl -> !cl.isInterface() && !Modifier.isAbstract(cl.getModifiers()))
                .collect(Collectors.toList());

        return classes;

    }

    private static Class<?> getClassFromFile(String packageName, File file) {
        try {
            String name = file.getName().replaceAll("/", ".");
            name = packageName + "." + name.substring(0, name.indexOf(".class"));
            Class<?> cl = Class.forName(name);
            return cl;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Disabled
    @TestFactory
    Collection<DynamicTest> dynamicTestforDummyObjectsOne()
            throws ClassNotFoundException, IOException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String packageName = "ttl.larku.domain.dummy";
        List<Class<?>> classes = getClassesForPackage(packageName);

        Class<?> classToTest = classes.get(0);
        Method opMethod = classToTest.getDeclaredMethod("operation", int.class, int.class);
        Object obj = classToTest.getDeclaredConstructor().newInstance();
        //return List.of(dynamicTest("1st dynamic test", () -> {
        return Arrays.asList(dynamicTest("1st dynamic test", () -> {
            assertThrows(Exception.class, () -> {
                opMethod.invoke(obj, -1, 25);
            });
        }), dynamicTest("1st dynamic test", () -> {
            assertThrows(Exception.class, () -> {
                opMethod.invoke(obj, 10, 6);
            });
        }), dynamicTest("1st dynamic test", () -> {
            assertEquals(35, opMethod.invoke(obj, 10, 25));
        }));
    }
}
