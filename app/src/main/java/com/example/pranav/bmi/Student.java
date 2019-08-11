package com.example.pranav.bmi;

public class Student {


    private String bmi;
    private String name;

    public String getRno() {
        return bmi;
    }

    public void setRno(String bmi) {
        this.bmi = bmi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student(String bmi, String name) {

        this.bmi = bmi;
        this.name = name;
    }



}
