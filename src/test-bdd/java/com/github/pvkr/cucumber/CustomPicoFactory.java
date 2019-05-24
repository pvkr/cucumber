package com.github.pvkr.cucumber;

import com.github.pvkr.cucumber.api.impl.NoteClientImpl;
import cucumber.runtime.java.picocontainer.PicoFactory;

public class CustomPicoFactory extends PicoFactory {
    public CustomPicoFactory() {
        addClass(NoteClientImpl.class);
    }
}