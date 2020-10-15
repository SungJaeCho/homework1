package common;

public class SoldOutException extends Exception{
    private static final long serialVersionUID = 1L;
    final Object value;
    public SoldOutException(Object value, String message){
        super(message);
        this.value = value;
    }
    public Object getValue(){
        return value;
    }
}
