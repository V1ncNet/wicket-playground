package de.vinado.app.playground.bootstrap.presentation.ui.modal;

import java.io.Serializable;

import lombok.Data;

@Data
public class DemoDto implements Serializable {

    private String message;

    private Integer amount = 0;
}
