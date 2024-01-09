package util;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class TypeScanner {

    public static Set<Class<?>> scan(String packageName, boolean recursive) {
        if (packageName == null || packageName.equals("")) {
            throw new RuntimeException("package error:" + packageName);
        }

        packageName = packageName.replace(".", "/");
        int index = packageName.lastIndexOf("/");
        if (index == packageName.length() - 1) {
            packageName = packageName.substring(0, index);
        }
        Set<Class<?>> classSet = new LinkedHashSet<>();

        try {
            Enumeration<URL> enumeration = Thread.currentThread().getContextClassLoader().getResources(packageName);

            while (enumeration.hasMoreElements()) {
                URL url = enumeration.nextElement();
                if (url.getProtocol().equals("jar")) {
                    URLConnection urlConnection = url.openConnection();
                    scanByJar(classSet, (JarURLConnection)urlConnection, packageName, recursive);
                } else if (url.getProtocol().equals("file")) {
                    File file = new File(url.getFile());
                    scanByFile(classSet, file, packageName, recursive);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("scan failed", e);
        }

        return classSet;
    }

    private static void scanByJar(Set<Class<?>> classSet, JarURLConnection jarURLConnection, String packageName, boolean recursive) throws Exception {
        JarFile jarFile = jarURLConnection.getJarFile();

        packageName = packageName + "/";

        Enumeration<JarEntry> entryEnumeration = jarFile.entries();
        while (entryEnumeration.hasMoreElements()) {
            JarEntry jarEntry = entryEnumeration.nextElement();
            if (!jarEntry.getName().endsWith(".class")) {
                continue;
            }
            if (!jarEntry.getName().startsWith(packageName)) {
                continue;
            }
            if (recursive) {
                String pointEntryName = jarEntry.getName().replace("/", ".");
                classSet.add(getClass(pointEntryName.substring(0, pointEntryName.lastIndexOf(".class"))));

            } else {
                if (!jarEntry.getName().substring(packageName.length()).contains("/")) {
                    String pointEntryName = jarEntry.getName().replace("/", ".");
                    classSet.add(getClass(pointEntryName.substring(0, pointEntryName.lastIndexOf(".class"))));
                }
            }
        }
    }

    private static void scanByFile(Set<Class<?>> classSet, File file, String packageName, boolean recursive) throws Exception {

        if (!file.isDirectory()) {
            throw new IllegalStateException();
        }

        packageName = packageName.replace("/", ".");

        File[] files = file.listFiles();
        if (files != null) {
            for (File innerFile : files) {
                if (innerFile.isDirectory()) {
                    if (recursive) {
                        scanByFile(classSet, innerFile, packageName + "." + innerFile.getName(), recursive);
                    }
                } else {
                    if (innerFile.getName().endsWith(".class")) {
                        String className = innerFile.getName().substring(0, innerFile.getName().lastIndexOf(".class"));
                        classSet.add(getClass(packageName + "." + className));
                    }
                }
            }
        }
    }

    private static Class<?> getClass(String className) throws ClassNotFoundException {
        return Thread.currentThread().getContextClassLoader().loadClass(className);
    }

    public static void main(String[] args) {
        String packageName = "wm";

        Set<Class<?>> classSet = scan(packageName, true);
        for (Class<?> c : classSet) {
            System.out.println(c.getName());
        }
    }
}
