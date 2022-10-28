package com.exhibit.dao.model;

import com.exhibit.services.ServiceFactory;
import com.exhibit.services.UserService;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static com.exhibit.util.constants.UserConstants.AUTHORIZED_USER;
import static com.exhibit.util.constants.UserConstants.USER_DEFAULT_MONEY;

public class User implements Serializable {
    private long id;
    private String login;

    private String password;

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

    public boolean isTicketPresent(long exhibitionId){
        UserService service = ServiceFactory.getInstance().getUserService();
        return service.isTicketPreset(getLogin(), exhibitionId);
    }

    public List<Ticket> getUserTickets(){
        UserService service = ServiceFactory.getInstance().getUserService();
        return service.getUserTickets(this);
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
    public static Builder newBuilder() {
        return new User().new Builder();
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


