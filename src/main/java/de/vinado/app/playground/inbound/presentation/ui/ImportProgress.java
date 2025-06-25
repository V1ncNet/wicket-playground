package de.vinado.app.playground.inbound.presentation.ui;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImportProgress implements Serializable {

    private String message;

    private int value;
}
