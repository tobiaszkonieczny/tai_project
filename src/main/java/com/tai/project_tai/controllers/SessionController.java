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
@Named(value = "sessionController")
@SessionScoped
public class SessionController implements Serializable{
    public String logout(){
        javax.faces.context.ExternalContext externalContext =
                javax.faces.context.FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }
}
