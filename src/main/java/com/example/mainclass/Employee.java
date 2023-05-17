package com.example.mainclass;

public class Employee extends Person {
    public String designation;

    Employee(Integer id, String name,String Class, String gender, String picture, String mobile)
    {
        super(id, name,  gender, picture, mobile);
    }

    @Override
    Integer getId() {
        return null;
    }

    @Override
    String getName() {
        return null;
    }

    String getDesignation() {
        return designation;
    }

    @Override
    String getGender() {
        return null;
    }

    @Override
    String getPicture() {
        return null;
    }

    @Override
    String getMobile() {
        return null;
    }
}
