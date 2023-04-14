package com.example.parsingjsonexemple;

public class contact {
    private int id;
    private String name;
    private String job;
    private String email;
    private String phone;

    public contact(int id, String job, String email, String phone,String name) {
        this.id = id;
        this.name=name;
        this.job = job;
        this.email = email;
        this.phone = phone;
    }
    public contact( String job, String email, String phone,String name) {
        this.job = job;
        this.name=name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public contact() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
