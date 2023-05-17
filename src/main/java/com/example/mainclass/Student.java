package com.example.mainclass;

public class Student extends Person {
    public  String Clas;
    public Student(Integer id, String name, String Class, String gender, String picture, String mobile)
    {
        super(id, name,  gender, picture, mobile);
        this.Clas=Class;
    }
    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public String getName() {

        return name;
    }

    public String getClas() {
        return Clas;
    }
    @Override
    public String getGender() { return gender; }
    @Override
    public String getPicture() {
        return picture;
    }
    @Override
    public String getMobile() { return  mobile; }
}
