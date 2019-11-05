package com.sfsf.platform.common.fastmap.converter;


public class ReverseConversion extends SimpleConversion {
    
    private SimpleConversion simpleConversion;

    public static ReverseConversion inverse(SimpleConversion simpleConversion) {
        return new ReverseConversion( simpleConversion );
    }

    private ReverseConversion(SimpleConversion simpleConversion) {
        this.simpleConversion = simpleConversion;
    }

}
