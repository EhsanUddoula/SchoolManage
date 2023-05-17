package com.example.mainclass;

public class Staff extends Employee {
    public String  designation;


    public Staff(Integer id, String name, String Class, String gender, String picture, String mobile)
    {
        super(id, name, Class, gender, picture, mobile);
        designation=Class;
    }
    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public String getName() {

        return  name;
    }

    public String getDesignation() {
        return designation;
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
