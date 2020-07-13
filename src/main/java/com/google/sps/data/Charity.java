package main.java.com.google.sps.data;

public final class Charity {
    private final String CharityName;
    private final long id;
    private final String location;

    public Charity(String CharityName, long id, String location) {
        this.CharityName = CharityName;
        this.id = id;
        this.location = location;
    }
}
