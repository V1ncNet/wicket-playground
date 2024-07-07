package de.vinado.app.playground.upload.overview.model;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = false)
public class UploadResult implements Serializable {

    private UUID transferId;

    private State state = State.INITIAL;


    public enum State {

        INITIAL,
        SCHEDULED,
        UPLOADING,
        UPLOADED,
    }
}
