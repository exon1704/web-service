//package com.nova.exception;
//
//import com.nova.exception.reporte.ReporteException;
//import com.nova.toolkit.ApiResponse;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//@ControllerAdvice
//public class NovaExceptionManager {
//
//
//    @ExceptionHandler(value = {DataIntegrityViolationException.class})
//    @ResponseStatus(HttpStatus.CONFLICT)
//    ResponseEntity<ApiResponse> handlerDataIntegrityViolationException(DataIntegrityViolationException e) {
//        return new ResponseEntity<>(ApiResponse.builder()
//                .data(e.getCause())
//                .code(HttpStatus.CONFLICT.value())
//                .message(e.getMessage())
//                .build(), HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler(ReporteException.class)
//    public ResponseEntity<ApiResponse> handleReporteException(ReporteException ex) {
//        return new ResponseEntity<>(ApiResponse.builder()
//                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                .message(ex.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse> handleOtherExceptions(Exception ex) {
//        // Manejo genérico para otras excepciones no manejadas específicamente
//        return new ResponseEntity<>(ApiResponse.builder()
//                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                .message(ex.getLocalizedMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}