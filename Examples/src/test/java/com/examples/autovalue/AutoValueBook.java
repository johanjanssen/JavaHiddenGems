package com.examples.autovalue;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class AutoValueBook {
    static AutoValueBook create(String author, String title) {
        return new AutoValue_AutoValueBook(author, title);
    }

    abstract String author();
    abstract String title();
}
