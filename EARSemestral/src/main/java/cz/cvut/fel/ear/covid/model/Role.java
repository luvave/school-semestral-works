package cz.cvut.fel.ear.covid.model;

public enum Role {
    ADMIN("ROLE_ADMIN"), EDITOR("ROLE_EDITOR");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
