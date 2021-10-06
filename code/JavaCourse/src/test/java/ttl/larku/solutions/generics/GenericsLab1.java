package ttl.larku.solutions.generics;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author whynot
 */
public class GenericsLab1 {

    interface Shape
    {
        public void draw();
    }

    class Circle implements Shape
    {
        public void draw() {
            System.out.println("Circle::draw");
        }
    }

    class Triangle implements Shape {
        public void draw() {
            System.out.println("Triangle::draw");
        }
    }

    //TODO - Uncomment the 'drawThemAll' method and the lines
    // labelled A, B and C in the testDrawer method below.
    // Implement the 'drawThemAll method to allow the lines
    // labeled A, B and C to compile.
    // The 'drawThemAll' method should call draw on all
    // the elements given to it.
    // It should return the number of items drawn.
    // The goal is to make the test run successfully.

    //Note - We use 'extends' because we want the List to contain
    // *at least* Shapes because we want to call a Shape operation
    // on it's elements.  In exchange we give up the ability to
    // add anything into the List.  Try it and see what happens.
    public int drawThemAll(List<? extends Shape> shapes) {
        shapes.forEach(Shape::draw);
        return shapes.size();
    }


    @Test
    public void testDrawer() {
        int num = 0;
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Triangle());
        shapes.add(new Circle());
        num = drawThemAll(shapes);   //TODO - Line A
        assertEquals(2, num);

        List<Triangle> triangles = new ArrayList<>();
        triangles.add(new Triangle());
        num = drawThemAll(triangles); //TODO - Line B
        assertEquals(1, num);

        List<Circle> circles = new ArrayList<>();
        circles.add(new Circle());
        num = drawThemAll(circles); //TODO - Line C
        assertEquals(1, num);
    }
}
