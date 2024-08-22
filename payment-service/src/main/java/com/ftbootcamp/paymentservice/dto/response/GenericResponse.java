package com.ftbootcamp.paymentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GenericResponse <T>{

    private T data;
    private HttpStatus httpStatus;
    private String status;
    private String message;

    public static <T> GenericResponse<T> success(T data, HttpStatus httpStatus){
        return GenericResponse.<T>builder()
                .data(data)
                .httpStatus(httpStatus)
                .status(GenericResponseConstants.SUCCESS)
                .build();
    }

    public static <T> GenericResponse<T> failed(String message){
        return GenericResponse.<T>builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .status(GenericResponseConstants.FAILED)
                .message(message)
                .build();
    }
}
