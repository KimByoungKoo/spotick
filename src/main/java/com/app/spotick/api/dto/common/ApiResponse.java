package com.app.spotick.api.dto.common;

import lombok.Builder;
import lombok.Data;

@Data
public class ApiResponse<T> {
    boolean success;
    String message;
    T data;

    @Builder
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

}
