package com.todoApp.todo_withdb.common;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NonNull;

@Data
public class ErrorResponseaDto {
    @NonNull
    private String message;
}
