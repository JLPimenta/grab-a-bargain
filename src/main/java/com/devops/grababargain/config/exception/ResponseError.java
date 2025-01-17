package com.devops.grababargain.config.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Builder
public class ResponseError {

    private Integer status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<?> errors;
    private String stacktrace;

    public static ResponseError notMappedException(Exception ex) {
        return ResponseError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Ocorreu uma exceção não esperada na aplicação.")
                .debugMessage(ex.getMessage())
                .stacktrace(Arrays.toString(ex.getStackTrace()))
                .build();
    }

    public static ResponseError badRequest(String message) {
        return ResponseError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .build();
    }

    public static ResponseError badRequest(MethodArgumentNotValidException exception) {
        return ResponseError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getBody().getDetail())
                .errors(exception.getFieldErrors().stream().map(FieldDataValidation::new).toList())
                .build();
    }

    public static ResponseError notFound(String message) {
        return ResponseError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(message)
                .build();
    }

    public static ResponseError constraintException(Exception ex) {
        return ResponseError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Ação não permitida. Existem vínculos entre este registro e outros itens no sistema.")
                .debugMessage(ex.getMessage())
                .build();

    }

    public static ResponseError entityDataException(Exception ex) {
        return ResponseError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Ocorreu um erro ao realizar a operação no banco de dados. Reporte o erro ao administrador do sistema.")
                .debugMessage(ex.getMessage())
                .build();
    }
}