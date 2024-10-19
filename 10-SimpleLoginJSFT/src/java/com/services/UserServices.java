/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.dao.ImplDao;
import com.entity.User;
import com.implDao.IUser;
import java.io.Serializable;

/**
 *
 * @author Jhon Baron
 */
public class UserServices extends ImplDao<User, Long> implements IUser, Serializable{
    
}
