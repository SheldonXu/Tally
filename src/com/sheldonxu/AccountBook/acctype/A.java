package com.sheldonxu.AccountBook.acctype;

import java.util.List;

/**
 * Created by sheldon.Xu on 15-4-9.
 */
public class A {
    private String id;
    private String name;
    private List<B> bs;

    @Override
    public String toString() {
        return name;
    }

    public List<B> getBs() {
        return bs;
    }

    public void setBs(List<B> bs) {
        this.bs = bs;
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
