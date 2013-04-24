package com.gmail.schcrabicus.spring.stats.util.propertyeditors;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * Custom PropertyEditorSupport to convert from String to
 * Date using JODA.
 */
public class DateTimeEditor extends PropertyEditorSupport {

    private final String dateFormat;
    private final boolean allowEmpty;

    /**
     * Create a new DateTimeEditor instance, using the given format for
     * parsing and rendering.
     * <p/>
     * The "allowEmpty" parameter states if an empty String should be allowed
     * for parsing, i.e. get interpreted as null value. Otherwise, an
     * IllegalArgumentException gets thrown.
     *
     * @param dateFormat DateFormat to use for parsing and rendering
     * @param allowEmpty if empty strings should be allowed
     */
    public DateTimeEditor( String dateFormat, boolean allowEmpty ) {
        this.dateFormat = dateFormat;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public String getAsText() {
        if(getValue() == null) return ""; //return null if the property is null
        DateTime value = (( DateTime ) getValue()).withZone(DateTimeZone.UTC); //if the property is not null, read it as a DateTime
        return value != null ? value.toString() : "";
    }

    @Override
    public void setAsText( String text ) throws IllegalArgumentException {
        if ( allowEmpty && !StringUtils.hasText(text) ) {
            // Treat empty String as null value.
            setValue( null );
        } else {
            DateTime val = DateTimeFormat.forPattern(dateFormat).parseDateTime( text ).withZoneRetainFields(DateTimeZone.UTC);
            setValue( val );
        }
    }

}
