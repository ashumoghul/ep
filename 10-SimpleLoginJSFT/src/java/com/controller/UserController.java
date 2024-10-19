/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.entity.User;
import com.services.UserServices;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import Controllers.FacesUtil;

/**
 *
 * @author Jhon Baron
 */
@ManagedBean
@SessionScoped
public class UserController {

    /**
     * Creates a new instance of userController
     */
    User user_acces;
    User user_new;

    UserServices us = new UserServices();
    List<User> users = new LinkedList();

    public UserController() {
        user_acces = new User();
        user_new = new User();
        list();
    }

    public void verify() {
        try {
            boolean exist = false;
            list();
            for (User user : users) {
                if (user.getUsername().equalsIgnoreCase(user_acces.getUsername())
                        && user.getPassword().equalsIgnoreCase(user_acces.getPassword())) {

                    FacesUtil.addInfoMessage("User: " + user_acces.getUsername() + " exist!");
                    user_acces= new User();
                    exist = true;
                }
            }

            if (exist == false) {
                FacesUtil.addErrorMessage("This user doesn't exist.");
            }

        } catch (Exception e) {
            FacesUtil.addErrorMessage("Error in 'Verify' " + e.getMessage());
        }
    }

    public void add() {
        try {
            us.crear(user_new);
            FacesUtil.addInfoMessage("Welcome " + user_new.getUsername());
            user_new = new User();
        } catch (Exception e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }

    public void list() {
        try {
            users = us.consultarTodo(User.class);
        } catch (Exception e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    public User getUser_acces() {
        return user_acces;
    }

    public void setUser_acces(User user_acces) {
        this.user_acces = user_acces;
    }

    public User getUser_new() {
        return user_new;
    }

    public void setUser_new(User user_new) {
        this.user_new = user_new;
    }

    public UserServices getUs() {
        return us;
    }

    public void setUs(UserServices us) {
        this.us = us;
    }

}
