package com.tree.treeapp;

public class TreeItem {

    public static final String TREE_ID = "TREE_ID";
    public static final String TREE_NAME = "TREE_NAME";
    public static final String TREE_NICKNAME = "TREE_NICKNAME";
    public static final String TREE_CYCLE = "TREE_CYCLE";
    public static final String TREE_ADDRESS = "TREE_ADDRESS";

    private long id;
    private String name;
    private String nickname;

    private int cycle;

    private double[] address;

    public TreeItem(long id, String name, String nickname, int cycle, double[] address) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.cycle = cycle;
        this.address = address;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public int getCycle() {
        return cycle;
    }

    public double[] getAddress() {
        return address;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }
}
