package model;

import interfaces.Login;

public abstract class User implements Login {
	protected String name;
    protected String email;
    protected String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    @Override
    public boolean login(String password) {
        return this.password.equals(password);
    }

    @Override
    public void logout() {
        System.out.println(name + " logged out.");
    }

    // Metode abstrak
    public abstract String getRole();
}
