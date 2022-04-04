package com.examples.guice;

import com.google.inject.Singleton;

@Singleton
class PrintService {
    public String enrichInfo(String info) {
        return "Much richer " + info;
    }
}
