package com.sheldonxu.AccountBook.acctype;

/**
 * Created by sheldon.Xu on 15-4-9.
 */
public class C {
    private String id;
    private String name;

    @Override
    public String toString() {
        return name;
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
