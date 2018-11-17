package com.kmutt.dailyemofinal.Model;

import android.text.Editable;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String username;
    private String password;
    private String email;
    private String birthDate;

    private  String sex;
    private int height, age;
    private double weight;

    private String process;
    private boolean traffic;
    private boolean mood;
    private int heartRateValue;
    private String heartTime;
    private int sleepMinute;
    private int percent;


    public User(String s, String toString, String string, String s1, String toString1, String string1){

    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String username, String password, String email, String sex, String high, String age, String bd, String inputWeightText, String percent) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.sex = sex;
        this.height = (int) Integer.parseInt(high);
        this.age = (int) Integer.parseInt(age);
        this.birthDate = bd;
        this.weight = (int) Integer.parseInt(inputWeightText);
        this.percent = (int) Integer.parseInt(percent);
    }

    public User(String username, String password, String email, String sex, String high, String age, String bd, String inputWeightText) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.sex = sex;
        this.height = Integer.parseInt(high);
        this.age = Integer.parseInt(age);
        this.birthDate = bd;
        this.weight = Integer.parseInt(inputWeightText);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

   public String getSex(){
        return sex;
   }
   public void setSex(String sex){
        this.sex = sex;
   }
   public int getHeight(){
        return height;
   }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWeight(){
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getAge(){
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> mapping = new HashMap<>();
        mapping.put("username",this.username);
        mapping.put("age", this.age);
        mapping.put("email", this.email);
        mapping.put("password", this.password);
        mapping.put("sex", this.sex);
        mapping.put("birthDate", this.birthDate);
        mapping.put("high",this.height);
        mapping.put("weight",this.weight);
        mapping.put("percent",this.percent);
        return mapping;
    }
}
