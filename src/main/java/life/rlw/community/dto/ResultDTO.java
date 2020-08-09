package life.rlw.community.dto;

import life.rlw.community.exception.CustomizeErrorCode;
import life.rlw.community.exception.CustomizeException;

import javax.xml.transform.Result;

public class ResultDTO {




    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResultDTO{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    private Integer code;
    private String message;

    public static ResultDTO errorOf(CustomizeErrorCode errorCoder) {
        return errorOf(errorCoder.getCode(),errorCoder.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }

    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }
    public static ResultDTO okOf(){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }
}
