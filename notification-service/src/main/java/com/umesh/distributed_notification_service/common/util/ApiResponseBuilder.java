package com.umesh.distributed_notification_service.common.util;

import com.umesh.distributed_notification_service.common.dto.ApiResponse;

public final class ApiResponseBuilder {

    private ApiResponseBuilder() {
    }

    public static <T> ApiResponse<T> success(
            String message,
            T data) {

        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> failure(
            String message,
            T data) {

        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .data(data)
                .build();
    }

}