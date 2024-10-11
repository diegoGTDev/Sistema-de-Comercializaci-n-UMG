/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.application;

/**
 *
 * @author PC
 */
public class AppInformation {
    private static String _currentUser;
    private static int _currentUserId;
    private static AppInformation _instance;
    private AppInformation(){
        
    }
    private static AppInformation getInstance(){
        if (_instance == null){
            _instance = new AppInformation();
        }
        return _instance;
    }

    public static String getCurrentUser() {
        return _currentUser;
    }

    public static void setCurrentUser(String _currentUser) {
        AppInformation._currentUser = _currentUser;
    }

    public static int getCurrentUserId() {
        return _currentUserId;
    }

    public static void setCurrentUserId(int _currentUserId) {
        AppInformation._currentUserId = _currentUserId;
    }
    
    
}
