package com.googlesps.feedon.data;

/**
 * Class for charity users
 */
public final class Charity {
    private final long id;
    private final String CharityName;
    private final String location;
    private final String description;

    public Charity(long id, String CharityName, String location, String description) {
        this.CharityName = CharityName;
        this.id = id;
        this.location = location;
        this.description = description;
    }
}
