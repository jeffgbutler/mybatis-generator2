/**
 *    Copyright 2006-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator2.util;

import static org.mybatis.generator2.util.Messages.getString;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class creates the different objects needed by the generator.
 *
 * @author Jeff Butler
 */
public class ObjectFactory {
    
    /** The external class loaders. */
    private static List<ClassLoader> externalClassLoaders;
    
    /** The resource class loaders. */
    private static List<ClassLoader> resourceClassLoaders;
    
    static {
    	externalClassLoaders = new ArrayList<ClassLoader>();
        resourceClassLoaders = new ArrayList<ClassLoader>();
    }
    
    /**
     * Utility class. No instances allowed
     */
    private ObjectFactory() {
        super();
    }

    /**
     * Clears the class loaders.  This method should be called at the beginning of
     * a generation run so that and change to the classloading configuration
     * will be reflected.  For example, if the eclipse launcher changes configuration
     * it might not be updated if eclipse hasn't been restarted.
     * 
     */
    public static void reset() {
        externalClassLoaders.clear();
        resourceClassLoaders.clear();
    }

    /**
     * Adds a custom classloader to the collection of classloaders searched for resources. Currently, this is only used
     * when searching for properties files that may be referenced in the configuration file.
     *
     * @param classLoader
     *            the class loader
     */
    public static synchronized void addResourceClassLoader(
            ClassLoader classLoader) {
        ObjectFactory.resourceClassLoaders.add(classLoader);
    }

    /**
     * Adds a custom classloader to the collection of classloaders searched for "external" classes. These are classes
     * that do not depend on any of the generator's classes or interfaces. Examples are JDBC drivers, root classes, root
     * interfaces, etc.
     *
     * @param classLoader
     *            the class loader
     */
    public static synchronized void addExternalClassLoader(
            ClassLoader classLoader) {
        ObjectFactory.externalClassLoaders.add(classLoader);
    }
    
    /**
     * This method returns a class loaded from the context classloader, or the classloader supplied by a client. This is
     * appropriate for JDBC drivers, model root classes, etc. It is not appropriate for any class that extends one of
     * the supplied classes or interfaces.
     *
     * @param type
     *            the type
     * @return the Class loaded from the external classloader
     * @throws ClassNotFoundException
     *             the class not found exception
     */
    public static Class<?> externalClassForName(String type)
            throws ClassNotFoundException {

        Class<?> clazz;

        for (ClassLoader classLoader : externalClassLoaders) {
            try {
                clazz = Class.forName(type, true, classLoader);
                return clazz;
            } catch (Throwable e) {
                // ignore - fail safe below
            }
        }
        
        return internalClassForName(type);
    }

    /**
     * Creates a new Object object.
     *
     * @param type
     *            the type
     * @return the object
     */
    public static Object createExternalObject(String type) {
        Object answer;

        try {
            Class<?> clazz = externalClassForName(type);
            answer = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(getString(
                    "RuntimeError.6", type), e); //$NON-NLS-1$
        }

        return answer;
    }

    /**
     * Internal class for name.
     *
     * @param type
     *            the type
     * @return the class
     * @throws ClassNotFoundException
     *             the class not found exception
     */
    public static Class<?> internalClassForName(String type)
            throws ClassNotFoundException {
        Class<?> clazz = null;

        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            clazz = Class.forName(type, true, cl);
        } catch (Exception e) {
            // ignore - failsafe below
        }

        if (clazz == null) {
            clazz = Class.forName(type, true, ObjectFactory.class.getClassLoader());
        }

        return clazz;
    }

    /**
     * Gets the resource.
     *
     * @param resource
     *            the resource
     * @return the resource
     */
    public static URL getResource(String resource) {
        URL url;

        for (ClassLoader classLoader : resourceClassLoaders) {
            url = classLoader.getResource(resource);
            if (url != null) {
              return url;
            }
        }
        
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        url = cl.getResource(resource);

        if (url == null) {
            url = ObjectFactory.class.getClassLoader().getResource(resource);
        }

        return url;
    }

    /**
     * Creates a new Object object.
     *
     * @param type
     *            the type
     * @return the object
     */
    public static Object createInternalObject(String type) {
        Object answer;

        try {
            Class<?> clazz = internalClassForName(type);

            answer = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(getString(
                    "RuntimeError.6", type), e); //$NON-NLS-1$

        }

        return answer;
    }
}
