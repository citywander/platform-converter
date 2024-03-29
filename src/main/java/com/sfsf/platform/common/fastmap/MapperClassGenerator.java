package com.sfsf.platform.common.fastmap;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

public class MapperClassGenerator {
    
    public MapperClassGenerator() {
    }
    
    @SuppressWarnings("unchecked")
    public <T> Class<T> generate(Class<T> type) throws CannotCompileException, IntrospectionException {
        ClassPool pool = new ClassPool(true);
        CtClass ct = this.genImplClassName(pool, type);        
        Class<T> to = (Class<T>)ct.toClass();
        return to;
    }
    
    /**
     * Gen class by mapper interface
     * @param <T>
     * @param type
     * @return
     * @throws IntrospectionException 
     * @throws CannotCompileException 
     */
    private <T> CtClass genImplClassName(ClassPool pool, Class<T> type) throws CannotCompileException, IntrospectionException {
        String pkgName = type.getPackage().getName();
        Mapper mapper = type.getAnnotation(Mapper.class);
        String className = pkgName + "." + type.getSimpleName() + mapper.implSuffixName();
        
        ClassLoader cl = type.getClassLoader();
        
        
        CtClass ct = pool.makeClass(className);
        ct.setInterfaces(new CtClass[]{pool.makeInterface(type.getName())});
        pool.insertClassPath(new LoaderClassPath(cl));
        renderMapperMethod(pool, type, ct);
        return ct;
    }
    
    private <T> void renderMapperMethod(ClassPool pool, Class<T> type, CtClass ct) throws CannotCompileException, IntrospectionException {
        Method[] methods = type.getDeclaredMethods();
        for (Method method: methods) {
            StringBuilder sb = new StringBuilder();
            if (method.isDefault()) {
                continue;
            }
            addMethodHeader(sb, pool, method);
            
            Class<?> returnType = method.getReturnType();
            this.appendNewLine(sb, returnType.getSimpleName(), "r = new", returnType.getSimpleName(),"()");
            
            Mapping[] mappings = method.getDeclaredAnnotationsByType(Mapping.class);
            addAllFields(sb, mappings, method.getParameterTypes()[0], method.getReturnType());
            sb.append("return r;");
            sb.append("\n").append("}");
            System.out.println(sb.toString());
            ct.addMethod(CtMethod.make(sb.toString(), ct));
        }
    }
    
    private void addMethodHeader(StringBuilder sb, ClassPool pool, Method method) {
    	 Class<?> returnType = method.getReturnType();
         pool.importPackage(returnType.getName());
         this.appendLine(sb, "public", returnType.getSimpleName(), method.getName(), "(");
         Class<?>[] parameterTypes = method.getParameterTypes();
         boolean appendComma = parameterTypes.length > 1? true: false;
         
         for (int i = 0; i < parameterTypes.length; i++){
             pool.importPackage(parameterTypes[i].getName());
             String sm = parameterTypes[i].getSimpleName();
             if (appendComma && i < parameterTypes.length) {
                 this.appendLine(sb, sm, "p"+i, ",");
             } else {
                 this.appendLine(sb, sm, "p"+i);
             }
         }
         this.appendLine(sb, ")","{\n");
    }
    
    
    private void addAllFields(StringBuilder sb, Mapping[] mappings, Class<?> srcType, Class<?> targetType) throws IntrospectionException {
        Map<String, String> mappingDict = new HashMap<>();
        for (Mapping mapping: mappings) {
            mappingDict.put(mapping.target(), mapping.source());
        }
        this.appendDefaultWriteMethod(sb, mappingDict, srcType, targetType);
        
        for(Entry<String, String> stEntry: mappingDict.entrySet()) {
            String targetFn = stEntry.getKey();
            String[] targetFns = targetFn.split("[.]");
            for (int i = 0; i < targetFns.length; i++) {
                try {
                    PropertyDescriptor targetWriteMethod = new PropertyDescriptor(targetFns[i], targetType);
                    Class<?> propType = targetWriteMethod.getPropertyType();
                    if (!propType.getName().startsWith("java")) {
                        String param = propType.getSimpleName().toLowerCase() + i;
                        sb.append(propType.getName()).append(" ");
                        sb.append(param).append(" = new " + propType.getName() + "();\n");
                    }
                } catch (IntrospectionException e) {
                    continue;
                }
            }
        }
    }
    
    private void appendDefaultWriteMethod(StringBuilder sb, Map<String, String> mappingDict, Class<?> srcType, Class<?> targetType) 
    		throws IntrospectionException {
        for (PropertyDescriptor pd : Introspector.getBeanInfo(targetType).getPropertyDescriptors()) {
            if (pd.getWriteMethod() == null || "class".equals(pd.getName())) {                
                continue;
            }
            String propName = pd.getName();
            String writeMethod = pd.getWriteMethod().getName();
            if (mappingDict.containsKey(propName)) {
                this.appendReadeWriteLine(sb, srcType, pd.getWriteMethod(), mappingDict.get(propName));
            } else {
                this.appendReadeWriteLine(sb, srcType, pd.getWriteMethod(), propName);
            }
            System.out.println(targetType + ":"  + writeMethod);
        }
    }
    
    
    private void appendMapperWriteMethod(StringBuilder sb, Class<?> srcType, Method writeMethod, String readMethod) 
        throws IntrospectionException{
    	
    }
    
    private void appendReadeWriteLine(StringBuilder sb, Class<?> srcType, Method writeMethod, String readMethod) {
        String[] readProps = readMethod.split("[.]");
        StringBuilder readString = new StringBuilder();
        boolean appendComma = false;
        Method lastReadMethod = null;
        for(String prop: readProps) {
            PropertyDescriptor srcReadMethod = null;
            try {
                srcReadMethod = new PropertyDescriptor(prop, srcType);
            } catch (IntrospectionException e) {
                return;
            }
            lastReadMethod = srcReadMethod.getReadMethod();
            if(!appendComma){
                appendComma = true;                
            } else {
                readString.append(".");
            }
            readString.append(lastReadMethod.getName()).append("()");
        }
        if (writeMethod.getParameterTypes()[0] != lastReadMethod.getReturnType()) {
            return;
        }
        this.appendNewLine(sb, "r."+ writeMethod.getName(), "(p0."+ readString+")");
    }
    
    
    
    private void appendNewLine(StringBuilder sb, String ...args) {
        for (String arg: args) {
        	if(arg.startsWith("(")) {
        		sb.append(arg);
        	}else {
        		sb.append(" ").append(arg);
        	}
        }
        sb.append(";\n");
    }
    
    private void appendLine(StringBuilder sb, String ...args) {
        for (String arg: args) {
            sb.append(arg).append(" ");
        }
    }

}
