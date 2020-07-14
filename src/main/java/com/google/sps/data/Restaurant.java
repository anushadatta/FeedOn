package com.google.sps.data;

public final class Restaurant {
    private final String name;
    private final long id;
    private final String location;
    private final String description;

    public Restaurant(long id, String name, String location, String description) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
    }
}
