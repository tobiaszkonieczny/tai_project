/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tai.project_tai.controllers;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author tobia
 */

@Named(value = "applicationController") //Definiuje że można się odwołać z xhtml po tej nazwie
@ApplicationScoped //scope na całą aplikację
public class ApplicationController {
    
    private String textFromApplicationScopedController = "Hello from application scoped controller!";
    
    public ApplicationController() {
    }

    public String getText() {
        return textFromApplicationScopedController;
    }

    public void setApplicationVersion(String textFromApplicationScopedController) {
        this.textFromApplicationScopedController = textFromApplicationScopedController;
    }
    
}