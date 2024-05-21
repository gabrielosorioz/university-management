package br.com.idealizeall.universitymanagement.config;

public enum Scheme {

    PSQL_DB("jdbc:postgresql://localhost:5432/university", "postgres", "postgres", "org.postgresql.Driver");

    private String url;
    private String username;
    private String password;
    private String driver;

    Scheme(String url, String username, String password, String driver) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriver() {
        return driver;
    }
}
