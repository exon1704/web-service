package com.nova.toolkit;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    /*private final String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss a"));*/
    private Object data;
    private String message;
    private Integer code;
    private Object size;
    private Map<String, Object> page;
}
