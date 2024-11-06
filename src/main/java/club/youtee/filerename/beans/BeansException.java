package club.youtee.filerename.beans;

/**
 * @author Xinglong.Li
 * @date 2024-11-06
 */
public abstract class BeansException extends RuntimeException {

    /**
     * Create a new BeansException with the specified message.
     * @param msg the detail message
     */
    public BeansException(String msg) {
        super(msg);
    }

    /**
     * Create a new BeansException with the specified message
     * and root cause.
     * @param msg the detail message
     * @param cause the root cause
     */
    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
