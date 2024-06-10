package com.example.mobilepose;

public class ChildItem {

    // Declaration of the variable
    private String ChildItemTitle;
    private String ChildItemPrice;

    // Constructor of the class
    // to initialize the variable*
    public ChildItem(String childItemTitle, String childItemPrice)
    {
        this.ChildItemTitle = childItemTitle;
        this. ChildItemPrice= childItemPrice;
    }

    // Getter and Setter method
    // for the parameter
    public String getChildItemTitle()
    {
        return ChildItemTitle;
    }

    public void setChildItemTitle(
            String childItemTitle)
    {
        ChildItemTitle = childItemTitle;
    }
}