package com.cnc.plan;

/**
 *
 * @author kk
 */
public class Status {
    
    public int status;
    public String code;
    
    public Status(int code){
        this.status = code;
        this.code = "";
    }
    
     public Status(int status,String code){
        this.status = status;
        this.code = code;
    }
    
    public static Status OK = new Status(200,"OK");
    //包括参数错误
    public static Status BAD_REQUEST = new Status(400,"BAD REQUEST");
    public static Status INVALID_CREDENTIALS = new Status(401,"INVALID CREDENTIALS");
    public static Status FORBIDDEN = new Status(403,"FORBIDDEN");
    public static Status NOT_FOUND = new Status(404,"NOT FOUND");
    public static Status REQUEST_TIMEOUT = new Status(408,"REQUEST TIMEOUT");
    public static Status RETRY_LATER = new Status(417,"RETRY LATER");
    public static Status SREVER_INTERNAL_ERROR = new Status(500,"SERVER INTERNAL ERROR");
    public static Status COMMAND_NOT_IMPLEMENTED = new Status(501,"COMMAND NOT IMPLEMENTED");
    public static Status SERVICE_UNAVAILABLE = new Status(503,"SERVICE UNAVAILABLE");
    public static Status PROCESSING_ERROR = new Status(506,"PROCESSING ERROR");
    public static Status GATEWAY_TIMEOUT = new Status(504,"GATEWAY TIMEOUT");

    /**
     * This constructor is declared 'private' because we do not want to allow
     * anybody to instantiate instances of the class
     *
     */
    private Status() {
    }
}
