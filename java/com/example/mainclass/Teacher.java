package com.example.mainclass;

public class Teacher extends Employee{
    public String  subject;


    public Teacher(Integer id, String name, String Class, String gender, String picture, String mobile)
    {
        super(id, name, Class, gender, picture, mobile);
        subject=Class;
    }
    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public String getName() {

        return name;
    }
    //sup
    public String getSubject() {
        return subject;
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
