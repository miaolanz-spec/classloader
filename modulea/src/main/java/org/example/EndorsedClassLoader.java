package org.example;

import java.io.File;
import java.net.URL;

public class EndorsedClassLoader extends ReverseDelegateClassLoaderA {
    public EndorsedClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    protected String findLibrary(String libname) {
        // Custom logic to find a library
        String libPath = "/path/to/libs/" + System.mapLibraryName(libname);
        File endorsedLib = new File(libPath);
        if (endorsedLib.exists()) {
            return endorsedLib.toString();
        }
        return super.findLibrary(libname);
    }
}