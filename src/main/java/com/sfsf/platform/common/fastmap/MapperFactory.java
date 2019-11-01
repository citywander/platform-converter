package com.sfsf.platform.common.fastmap;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;


public class MapperFactory {
    
    private static final String IMPLEMENTATION_SUFFIX = "Impl";

    private MapperFactory() {
    }

    /**
     * Returns an instance of the given mapper type.
     *
     * @param clazz The type of the mapper to return.
     * @param <T> The type of the mapper to create.
     *
     * @return An instance of the given mapper type.
     */
    public static <T> T getMapper(Class<T> clazz) {
        try {
            List<ClassLoader> classLoaders = collectClassLoaders( clazz.getClassLoader() );

            return getMapper( clazz, classLoaders );
        }
        catch ( ClassNotFoundException | NoSuchMethodException e ) {
            throw new RuntimeException( e );
        }
    }

    private static <T> T getMapper(Class<T> mapperType, Iterable<ClassLoader> classLoaders)
            throws ClassNotFoundException, NoSuchMethodException {

        for ( ClassLoader classLoader : classLoaders ) {
            T mapper = doGetMapper( mapperType, classLoader );
            if ( mapper != null ) {
                return mapper;
            }
        }

        throw new ClassNotFoundException("Cannot find implementation for " + mapperType.getName() );
    }

    private static <T> T doGetMapper(Class<T> clazz, ClassLoader classLoader) throws NoSuchMethodException {
        try {
            @SuppressWarnings( "unchecked" )
            Class<T> implementation = (Class<T>) classLoader.loadClass( clazz.getName() + IMPLEMENTATION_SUFFIX );
            Constructor<T> constructor = implementation.getDeclaredConstructor();
            constructor.setAccessible( true );

            return constructor.newInstance();
        }
        catch (ClassNotFoundException e) {
            return getMapperFromServiceLoader( clazz, classLoader );
        }
        catch ( InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException( e );
        }
    }

    /**
     * Returns the class of the implementation for the given mapper type.
     *
     * @param clazz The type of the mapper to return.
     * @param <T> The type of the mapper to create.
     *
     * @return A class of the implementation for the given mapper type.
     *
     * @since 1.3
     */
    public static <T> Class<? extends T> getMapperClass(Class<T> clazz) {
        try {
            List<ClassLoader> classLoaders = collectClassLoaders( clazz.getClassLoader() );

            return getMapperClass( clazz, classLoaders );
        }
        catch ( ClassNotFoundException e ) {
            throw new RuntimeException( e );
        }
    }

    private static <T> Class<? extends T> getMapperClass(Class<T> mapperType, Iterable<ClassLoader> classLoaders)
        throws ClassNotFoundException {

        for ( ClassLoader classLoader : classLoaders ) {
            Class<? extends T> mapperClass = doGetMapperClass( mapperType, classLoader );
            if ( mapperClass != null ) {
                return mapperClass;
            }
        }

        throw new ClassNotFoundException( "Cannot find implementation for " + mapperType.getName() );
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<? extends T> doGetMapperClass(Class<T> clazz, ClassLoader classLoader) {
        try {
            return (Class<? extends T>) classLoader.loadClass( clazz.getName() + IMPLEMENTATION_SUFFIX );
        }
        catch ( ClassNotFoundException e ) {
            T mapper = getMapperFromServiceLoader( clazz, classLoader );
            if ( mapper != null ) {
                return (Class<? extends T>) mapper.getClass();
            }

            return null;
        }
    }

    private static <T> T getMapperFromServiceLoader(Class<T> clazz, ClassLoader classLoader) {
        ServiceLoader<T> loader = ServiceLoader.load( clazz, classLoader );

        for ( T mapper : loader ) {
            if ( mapper != null ) {
                return mapper;
            }
        }

        return null;
    }

    private static List<ClassLoader> collectClassLoaders(ClassLoader classLoader) {
        List<ClassLoader> classLoaders = new ArrayList<>( 3 );
        classLoaders.add( classLoader );

        if ( Thread.currentThread().getContextClassLoader() != null ) {
            classLoaders.add( Thread.currentThread().getContextClassLoader() );
        }

        classLoaders.add( MapperFactory.class.getClassLoader() );

        return classLoaders;
    }
    

    public class DynamicClassLoader extends ClassLoader {
        public Class<?> defineClass(String name, byte[] b) {
            return defineClass(name, b, 0, b.length);
        }
    }

}
