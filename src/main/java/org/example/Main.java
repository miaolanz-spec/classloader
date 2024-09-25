package org.example;

import org.example.b1;
import org.example.EndorsedClassLoader;
import java.net.URL;
import java.io.File;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws Exception {
        // Load JARs
        File moduleAFile = new File("modulea/target/module-a-1.0-SNAPSHOT.jar");
        File moduleBFile = new File("moduleb/target/module-b-1.0-SNAPSHOT.jar");

        URL[] urls = {
                moduleAFile.toURI().toURL(),
                moduleBFile.toURI().toURL()
        };

        // Create class loader with module B as parent
        EndorsedClassLoader classLoader = new EndorsedClassLoader(urls, Main.class.getClassLoader());

        // Load class a1 and cast it to b1
        Class<?> a1Class = classLoader.loadClass("org.example.a1");
        Object a1Instance = a1Class.getDeclaredConstructor().newInstance();

        // Cast and call method
        b1 b1Instance = (b1) a1Instance;
        b1Instance.performAction();

        // Alternatively, using reflection
        Method performActionMethod = a1Class.getMethod("performAction");
        performActionMethod.invoke(a1Instance);
    }
}