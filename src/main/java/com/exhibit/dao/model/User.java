package com.exhibit.dao.model;

import java.io.Serializable;
import java.util.Objects;

import static com.exhibit.dao.constants.UserConstants.AUTHORIZED_USER;
import static com.exhibit.dao.constants.UserConstants.USER_DEFAULT_MONEY;

public class User implements Serializable {
    private long id;
    private String login;

     private transient String password;

    private String role;
    private double money;

    public User(String login, String password) {
        this.id = 0;
        this.login = login;
        this.password = password;
        this.role = AUTHORIZED_USER;
        this.money = USER_DEFAULT_MONEY;
    }

    public User() {
    }

    public static Builder newBuilder() {
        return new User().new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), login, password, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", money=" + money +
                ", id=" + id +
                '}';
    }

    public class Builder {
        private Builder() {
        }

        public Builder setId(long id) {
            User.this.id = id;
            return this;
        }

        public Builder setLogin(String login) {
            User.this.login = login;
            return this;
        }

        public Builder setPassword(String password) {
            User.this.password = password;
            return this;
        }

        public Builder setRole(String role) {
            User.this.role = role;
            return this;
        }

        public Builder setMoney(double money) {
            User.this.money = money;
            return this;
        }

        public User build() {
            return User.this;
        }
    }
}



