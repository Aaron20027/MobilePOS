package com.example.mobilepose.Model;

import com.example.mobilepose.Model.Product;

import java.util.List;

public class ParentItem {
    private String ParentItemTitle;
    private List<Product> ChildItemList;

    public ParentItem(String ParentItemTitle, List<Product> ChildItemList)
    {
        this.ParentItemTitle = ParentItemTitle;
        this.ChildItemList = ChildItemList;
    }
    public String getParentItemTitle()
    {
        return ParentItemTitle;
    }

    public void setParentItemTitle(String parentItemTitle)
    {
        ParentItemTitle = parentItemTitle;
    }

    public List<Product> getChildItemList()
    {
        return ChildItemList;
    }

    public void setChildItemList(List<Product> childItemList)
    {
        ChildItemList = childItemList;
    }
}
