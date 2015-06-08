/**
 *
 */
package com.junesix.common.exception;


import com.junesix.common.utils.log.ApiLogger;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jolestar@gmail.com
 */
public class MatrixException extends RuntimeException {

    public static final String DEFAULT_ENCODING = "UTF-8";

    private final Map<String, Object> parameters = new HashMap<String, Object>();
    private ExcepFactor factor;
    private static final String ERROR_FORMAT = "{\"apistatus\":0,\"result\":{\"error\":\"%s\",\"error_zh_CN\":\"%s\",\"error_code\":%s,\"request\":\"%s\"}}";
    // TODO 多国语错误提示
    private String errorMsgCn;

    /**
     *
     */
    private static final long serialVersionUID = -6705744099814945437L;

    protected MatrixException(ExcepFactor factor) {
        this(factor, factor.getErrorMsg(), factor.getErrorMsgCn());
    }

    protected MatrixException(ExcepFactor factor, Object message, String errorMsgCn) {
        super(message == null ? factor.getErrorMsg() : message.toString());
        this.factor = factor;
        this.errorMsgCn = errorMsgCn;
    }

    protected MatrixException(ExcepFactor factor, Throwable exception, String errorMsgCn) {
        super(exception);
        this.factor = factor;
        this.errorMsgCn = errorMsgCn;
    }

    protected MatrixException(ExcepFactor factor, Object[] args) {
        this(factor, factor.getErrorMsg(args), factor.getErrorMsgCn(args));
    }

    protected MatrixException(ExcepFactor factor, Object[] errorArgs, Object[] errorCnArgs) {
        this(factor, factor.getErrorMsg(errorArgs), factor.getErrorMsgCn(errorCnArgs));
    }

    // FIXME 默认异常不应该输出详细错误信息？
    protected MatrixException(Exception e) {
        this(ExcepFactor.E_DEFAULT, e.getMessage());
    }

    protected MatrixException(String message) {
        this(ExcepFactor.E_DEFAULT, message);
    }

    protected MatrixException(ExcepFactor factor, Object message) {
        this(factor, message, factor.getErrorMsgCn());
    }

    protected MatrixException(ExcepFactor factor, Throwable exception) {
        this(factor, exception, factor.getErrorMsgCn());
    }

    public void setTraceHeader(String name, Object value) {
        getTraceHeaders().put(name, value);
    }

    public Map<String, Object> getTraceHeaders() {
        return parameters;
    }

    public ExcepFactor getFactor() {
        return factor;
    }

    public String getErrorMsgCn() {
        return this.errorMsgCn;
    }

    public void setErrorMsgCn(String errorMsgCn) {
        this.errorMsgCn = errorMsgCn;
    }


    /**
     * 只供构造对象时使用
     *
     * @param factor
     */
    protected void setFactor(ExcepFactor factor) {
        this.factor = factor;
    }

    public String formatException(String path) {
        if (path == null) {
            throw new IllegalArgumentException(
                    " path argument is null");
        }
        ExcepFactor factor = this.getFactor();
        return String.format(ERROR_FORMAT, this.getMessage(), this.getErrorMsgCn(), factor.getErrorCode(), path);
    }

    public void formatException(String path, Writer writer) {
        String result = this.formatException(path);
        try {
            writer.write(result);
        } catch (IOException e) {
            ApiLogger.error(e);
        }
    }
}