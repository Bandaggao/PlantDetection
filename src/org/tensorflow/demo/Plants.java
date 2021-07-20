package org.tensorflow.demo;

public class Plants {
    private final String name;
    private final String type;

    public Plants(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
