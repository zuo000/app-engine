package com.appengine.frame.help.resources;

import com.appengine.frame.filters.GlobalExceptionHandler;
import com.appengine.common.exception.ExcepFactor;
import com.appengine.common.exception.EngineException;
import com.appengine.common.exception.EngineExceptionHelper;
import com.appengine.frame.utils.log.ApiLogger;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 404处理
 *
 * @author sofn
 * @version 1.0 Created at: 2015-04-29 16:19
 */
@RestController
public class ErrorHandlerResource implements ErrorController {

    public static final String ERROR_PATH = "/error";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(value = ERROR_PATH)
    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    public String error(HttpServletRequest request) {
        String path = (String) request.getAttribute("javax.servlet.error.request_uri");
        String errorMsg = (String) request.getAttribute("javax.servlet.error.message");
        int status = (int) request.getAttribute("javax.servlet.error.status_code");

        Exception exception = (Exception) request.getAttribute(GlobalExceptionHandler.GlobalExceptionAttribute);
        EngineException apiException;
        if (exception != null && exception instanceof EngineException) {
            apiException = (EngineException) exception;
        } else if (status == 405) {
            apiException = EngineExceptionHelper.localException(ExcepFactor.E_METHOD_ERROR);
        } else if (status == 404) {
            apiException = EngineExceptionHelper.localException(ExcepFactor.E_API_NOT_EXIST);
        } else if (status == 415) {
            apiException = EngineExceptionHelper.localException(ExcepFactor.E_UNSUPPORT_MEDIATYPE_ERROR, new Object[]{"unknow"});
        } else if (status >= 400 && status < 500) {
            apiException = EngineExceptionHelper.localException(ExcepFactor.E_ILLEGAL_REQUEST, errorMsg);
        } else if (status == 503) {
            apiException = EngineExceptionHelper.localException(ExcepFactor.E_SERVICE_UNAVAILABLE);
        } else {
            apiException = EngineExceptionHelper.localException(ExcepFactor.E_DEFAULT);
            ApiLogger.error(errorMsg, exception);
        }
        return apiException.formatException(path);
    }
}
