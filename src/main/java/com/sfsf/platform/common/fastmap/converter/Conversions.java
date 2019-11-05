package com.sfsf.platform.common.fastmap.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import static com.sfsf.platform.common.fastmap.converter.ReverseConversion.inverse;

public class Conversions {
    
    private final Map<Key, SimpleConversion> conversions = new HashMap<>();
    
    private void register(Class<?> sourceClass, Class<?> targetClass, SimpleConversion conversion) {
        conversions.put( new Key( sourceClass, targetClass ), conversion );
        conversions.put( new Key( targetClass, sourceClass ), inverse( conversion ) );
    }
    

    private static class Key {
        private final Class<?> sourceType;
        private final Class<?> targetType;

        private Key(Class<?> sourceType, Class<?> targetType) {
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        @Override
        public String toString() {
            return "Key [sourceType=" + sourceType + ", targetType="
                + targetType + "]";
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ( ( sourceType == null ) ? 0 : sourceType.hashCode() );
            result = prime * result + ( ( targetType == null ) ? 0 : targetType.hashCode() );
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if ( this == obj ) {
                return true;
            }
            if ( obj == null ) {
                return false;
            }
            if ( getClass() != obj.getClass() ) {
                return false;
            }
            Key other = (Key) obj;

            if ( !Objects.equals( sourceType, other.sourceType ) ) {
                return false;
            }

            if ( !Objects.equals( targetType, other.targetType ) ) {
                return false;
            }

            return true;
        }
    }

}
