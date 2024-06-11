package com.example.mobilepose;

public class ChildItem {

    private String ChildItemTitle;
    private String ChildItemPrice;

    public ChildItem(String childItemTitle, String childItemPrice)
    {
        this.ChildItemTitle = childItemTitle;
        this. ChildItemPrice= childItemPrice;
    }

    public String getChildItemTitle()
    {
        return ChildItemTitle;
    }

    public void setChildItemTitle(String childItemTitle)
    {
        ChildItemTitle = childItemTitle;
    }

    public String getChildItemPrice() {
        return ChildItemPrice;
    }

    public void setChildItemPrice(String childItemPrice) {
        ChildItemPrice = childItemPrice;
    }
}