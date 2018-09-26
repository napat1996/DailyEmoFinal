package com.kmutt.dailyemofinal.Model;

public class User {

    private String username;
    private String password;
    private String email;



    private String process;
    private boolean traffic;
    private boolean mood;
    private int heartRateValue;
    private String heartTime;
    private int sleepMinute;


    public User(){

    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String username, String password, String email, String process, boolean traffic, boolean mood, int heartRateValue, String heartTime, int sleepMinute) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.process = process;
        this.traffic = traffic;
        this.mood = mood;
        this.heartRateValue = heartRateValue;
        this.heartTime = heartTime;
        this.sleepMinute = sleepMinute;
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

    public boolean getTraffic(){
        return traffic;
    }

    public boolean getMood(){
        return mood;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public boolean isTraffic() {
        return traffic;
    }

    public void setTraffic(boolean traffic) {
        this.traffic = traffic;
    }

    public boolean isMood() {
        return mood;
    }

    public void setMood(boolean mood) {
        this.mood = mood;
    }

    public int getHeartRateValue() {
        return heartRateValue;
    }

    public void setHeartRateValue(int heartRateValue) {
        this.heartRateValue = heartRateValue;
    }

    public String getHeartTime() {
        return heartTime;
    }

    public void setHeartTime(String heartTime) {
        this.heartTime = heartTime;
    }

    public int getSleepMinute() {
        return sleepMinute;
    }

    public void setSleepMinute(int sleepMinute) {
        this.sleepMinute = sleepMinute;
    }

}
