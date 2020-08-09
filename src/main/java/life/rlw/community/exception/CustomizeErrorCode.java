package life.rlw.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUN(2001,"问题不存在了"),
    TARGET_PARAM_NOT_FOUN(2002,"未选中任何问题或评论就行回复"),
    NO_LOGIN(2003,"请登录后再进行评论"),
    SYS_ERROR(2004,"请稍微再试"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"你回复的评论不存在");

    @Override
    public String getMessage() {
        return message;
    }

    private Integer code;
    private String message;


    CustomizeErrorCode(Integer code,String message ) {
        this.message = message;
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }

}
