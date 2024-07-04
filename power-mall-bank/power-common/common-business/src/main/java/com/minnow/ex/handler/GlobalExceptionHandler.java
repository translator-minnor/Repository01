package com.minnow.ex.handler;


import com.minnow.constant.BusinessEnum;
import com.minnow.ex.BusinessException;
import com.minnow.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<String> businessException(BusinessException e) {
        log.error(e.getMessage());
        return Result.fail(BusinessEnum.OPERATION_FAIL.getCode(),e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result<String> runTimeException(RuntimeException e) {
        log.error(e.getMessage());
        return Result.fail(BusinessEnum.SERVER_INNER_ERROR);
    }

    /**
     * 处理：权限不足异常
     * @param e
     * @throws AccessDeniedException
     */
    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        // 抛出AccessDeniedException异常，走自定义AccessDeniedException异常处理
        throw e;
    }
}
