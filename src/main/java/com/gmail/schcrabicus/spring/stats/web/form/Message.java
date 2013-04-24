package com.gmail.schcrabicus.spring.stats.web.form;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: schcrabicus
 * Date: 15.04.13
 * Time: 7:19
 * To change this template use File | Settings | File Templates.
 */
public class Message implements Serializable {

    private String type;

    private String message;

    public Message( String type , String message ){
        this.type = type;
        this.message = message;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (message != null ? !message.equals(message1.message) : message1.message != null) return false;
        if (type != null ? !type.equals(message1.type) : message1.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
