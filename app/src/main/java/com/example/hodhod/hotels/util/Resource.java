package com.example.hodhod.hotels.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/*
    This is a generic class used to wrap the response.
    It exposes network status using a Resource class that encapsulate both the data and its state
 */
public class Resource<T> {

    @NonNull
    public final Status status;
    @Nullable
    public final T data;
    @Nullable
    public final String message;
    public final boolean isLoading;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message, boolean isLoading) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.isLoading = isLoading;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, null, false);
    }

    public static <T> Resource<T> error(@NonNull String msg) {
        return new Resource<>(Status.ERROR, null, msg, false);
    }

    public static Resource loading(boolean isLoading) {
        return new Resource<>(Status.LOADING, null, null, isLoading);
    }

    public enum Status {SUCCESS, ERROR, LOADING}
}
