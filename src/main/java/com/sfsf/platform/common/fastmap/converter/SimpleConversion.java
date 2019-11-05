package com.sfsf.platform.common.fastmap.converter;

import java.util.Collections;
import java.util.Set;


public abstract class SimpleConversion {
    
    protected Set<Class<?>> getToConversionImportTypes() {
        return Collections.emptySet();
    }

}
