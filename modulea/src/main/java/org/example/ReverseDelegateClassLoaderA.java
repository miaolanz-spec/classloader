package org.example;

import java.net.URL;
import java.net.URLClassLoader;
import java.io.File;

public class ReverseDelegateClassLoaderA extends URLClassLoader {
    private ClassLoader parent;

    public ReverseDelegateClassLoaderA(URL[] urls, ClassLoader parent) {
        super(urls, null);
        this.parent = parent;
    }

    public ReverseDelegateClassLoaderA(URL[] urls) {
        super(urls, null);
        this.parent = getSystemClassLoader();
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            return super.loadClass(name);
        } catch (ClassNotFoundException ex) {
            if (parent != null) {
                return parent.loadClass(name);
            } else {
                throw ex;
            }
        }
    }

    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        try {
            return super.loadClass(name, resolve);
        } catch (ClassNotFoundException ex) {
            if (parent != null) {
                return parent.loadClass(name);
            } else {
                throw ex;
            }
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            return super.findClass(name);
        } catch (ClassNotFoundException ex) {
            if (parent != null) {
                return parent.loadClass(name);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public URL getResource(String name) {
        URL url = super.getResource(name);
        if (url == null && parent != null) {
            url = parent.getResource(name);
        }
        return url;
    }

    @Override
    public URL findResource(String name) {
        URL url = super.findResource(name);
        if (url == null && parent != null) {
            url = parent.getResource(name);
        }
        return url;
    }
}