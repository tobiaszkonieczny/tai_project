/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tai.project_tai.controllers;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author tobia
 */
@Named(value = "navigationController")
@SessionScoped
public class NavigationController implements Serializable{
    public String getNextView() {
        return "translationAlias";
    }
}
