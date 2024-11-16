package com.myproject.SpringStarter.Until.Constants;

public enum Privillage {
    RESET_ANY_USER_PASSWORD(1l,"RESET_ANY_USER_PASSWORD"),
    ACCESS_ADMIN_PANEL(2l,"ACCESS_ADMIN_PANEL");
    
    private Long id;
    private String name;
    private Privillage(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public Long getId() {
        return id;
    }
}