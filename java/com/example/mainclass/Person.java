package com.example.mainclass;

abstract class Person {
    public  Integer id;
    public  String name;
    public  String gender;
    public String picture;
    public  String mobile;

    Person(Integer id, String name, String gender, String picture, String mobile)
    {
        this.id=id;
        this.name=name;

        this.gender=gender;
        this.picture = picture;
        this.mobile=mobile;
    }

    abstract Integer getId();

    abstract String getName();

    abstract String getGender() ;

    abstract String getPicture();

    abstract String getMobile() ;
}
