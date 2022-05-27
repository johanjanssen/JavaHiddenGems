package com.examples.togglz;

import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;

public enum TogglzFeatures implements Feature {

    @EnabledByDefault
    @Label("Awesome Feature")
    AWESOME,

    @Label("Almost ready Feature")
    ALMOST_READY;
}
