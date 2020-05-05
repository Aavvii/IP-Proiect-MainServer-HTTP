/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbObjects;

/**
 *
 * @author Claudiu
 */
public class User {
    private double id;
    private String name;
    private String lastName;
    private String nickname;
    private String password;
    private String email;

    
    
    public User(double id, String name, String lastName, String nickname, String password, String email) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }
    
    /*
    In cazul in care un user vrea sa faca Register acesta nu are un ID, asa ca primeste max(id) + 1, 
    deci in constructor voi asigna -1 ca sa deosebesc userii care fac login sau register
    */
    public User(String name, String lastName, String nickname, String password, String email) {
        this.id = -1;
        this.name = name;
        this.lastName = lastName;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }

    public User() {
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }
    

    public void setName(String name) {
        this.name = name;
    }
    

    public String getName() {
        return name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
