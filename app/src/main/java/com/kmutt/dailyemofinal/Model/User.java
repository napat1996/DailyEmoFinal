package com.kmutt.dailyemofinal.Model;

public class User {

    private String username;
    private String password;
    private String email;

    private  String sex;
    private int height, age;
    private double weight;

    private String process;
    private boolean traffic;
    private boolean mood;
    private int heartRateValue;
    private String heartTime;
    private int sleepMinute;


    public User(String s, String toString, String string, String s1, String toString1, String string1){

    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
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
}
