package com.cnc.plan;

/**
 *
 * @author kk
 */
public class ServiceException extends Exception {

    private Status status;

    public ServiceException(String msg) {
        this(msg, null, Status.PROCESSING_ERROR);
    }

    public ServiceException(String msg, Status status) {
        this(msg, null, status);
    }

    public ServiceException(String msg, Throwable cause) {
        this(msg, cause, Status.PROCESSING_ERROR);
    }

    public ServiceException(Throwable cause) {
        this(cause.getMessage(), cause, Status.PROCESSING_ERROR);
    }

    public ServiceException(String msg, Throwable cause, Status status) {
        super(msg, cause);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
    
    public String getMessage(){
        StringBuffer sb = new StringBuffer();
        sb.append("status=").append(status.status).append("(")
            .append(status.code).append(")")
            .append(super.getMessage());
        return sb.toString();
    }
}
