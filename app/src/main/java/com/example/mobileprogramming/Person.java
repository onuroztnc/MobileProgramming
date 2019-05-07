package com.example.mobileprogramming;

public class Person implements Comparable{


    String imgSrc;
    int callBtnSrc;
    String personNameSurname;
    String personId;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Person(String personId, String imgSrc, int callBtnSrc, String personNameSurname) {

        this.personId = personId;
        this.imgSrc = imgSrc;
        this.callBtnSrc = callBtnSrc;
        this.personNameSurname = personNameSurname;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public int getCallBtnSrc() {
        return callBtnSrc;
    }

    public void setCallBtnSrc(int callBtnSrc) {
        this.callBtnSrc = callBtnSrc;
    }



    public String getPersonNameSurname() {
        return personNameSurname;
    }

    public void setPersonNameSurname(String personNameSurname) {
        this.personNameSurname = personNameSurname;
    }

    @Override
    public int compareTo(Object o) {
        return this.personNameSurname.compareTo(((Person)o).getPersonNameSurname());
    }
}
