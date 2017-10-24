package quiz.db;

import com.sun.org.apache.bcel.internal.util.ClassLoader;

import java.util.Scanner;

public class TestHelper {

    public static String loadFile(String filename) {
        ClassLoader classLoader = new ClassLoader();
        return new Scanner(classLoader.getResourceAsStream(filename)).useDelimiter("\\Z").next();
    }
}