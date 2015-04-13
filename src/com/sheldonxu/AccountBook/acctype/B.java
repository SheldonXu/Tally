package com.sheldonxu.AccountBook.acctype;

import java.util.List;

/**
 * Created by sheldon.Xu on 15-4-9.
 */
public class B {
    private String id;
    private String name;
    private List<C> cs;

    @Override
    public String toString() {
        return name;
    }

    public List<C> getCs() {
        return cs;
    }

    public void setCs(List<C> cs) {
        this.cs = cs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
