package com.e.note.data.network;

import androidx.room.Ignore;

public class BaseResponse {

    @Ignore
    private Boolean error;
    @Ignore
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
