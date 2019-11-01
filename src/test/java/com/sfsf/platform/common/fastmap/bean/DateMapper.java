package com.sfsf.platform.common.fastmap.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateMapper {

    public String asString(Date date) {
        return date != null ? new SimpleDateFormat( "yyyy-MM-dd" )
            .format( date ) : null;
    }

    public Date asDate(String date) {
        try {
            return date != null ? new SimpleDateFormat( "yyyy-MM-dd" )
                .parse( date ) : null;
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
    }
}
