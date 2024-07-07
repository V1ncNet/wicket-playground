package de.vinado.app.playground.upload.overview.model;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;

@Data
public class UploadResult implements Serializable {

    private UUID transferId;
}
