package io.github.martinheywang.products.api.model.properties;

import java.time.LocalDateTime;

import javafx.beans.property.SimpleObjectProperty;

public class SimpleDateTimeProperty extends SimpleObjectProperty<LocalDateTime> {
    
    /**
     * Creates a new {@link SimpleDateTimeProperty} using the value of {@link LocalDateTime#MIN}.
     */
    public SimpleDateTimeProperty(){
        super(LocalDateTime.MIN);
    }

     /**
     * Creates a new {@link SimpleDateTimeProperty} using the given value.
     */
    public SimpleDateTimeProperty(LocalDateTime value){
        super(value);
    }
}
