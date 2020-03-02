package com.softniels.foregroundservicex;

public class ResponseEntity {
    // {
    //     "classname":"",
    //     "items":[],
    //     "statuscode":422,
    //     "reasonstring":"error",
    //     "message":"Informe o nome do usuário",
    //     "data":null
    // }
    private String classname;
    //private String items;
    private Integer statuscode;
    private String reasonstring;
    private String message;
    //private String data;

    public String getClassName() {
        return classname;
    }
    public Integer getStatusCode() {
        return statuscode;
    }
    public String getReasonsString() {
        return reasonstring;
    }
    public String getMessage() {
        return message;
    }

    public void setClassName(String classname) {
        this.classname = classname;
    }
    public void setStatusCode(Integer statuscode) {
        this.statuscode = statuscode;
    }
    public void setReasonsString(String reasonstring) {
        this.reasonstring = reasonstring;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}