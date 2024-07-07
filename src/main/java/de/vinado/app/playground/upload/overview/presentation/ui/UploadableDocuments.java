package de.vinado.app.playground.upload.overview.presentation.ui;

import java.io.Serializable;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.Delegate;

@Value
@Accessors(fluent = false)
public class UploadableDocuments implements Serializable {

    @Delegate
    @Getter(AccessLevel.NONE)
    List<UploadableDocument> subject;
}
