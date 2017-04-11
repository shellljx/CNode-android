package com.licrafter.cnode.model.entity;

/**
 * Created by lijx on 2017/4/11.
 */

public enum TAB {
    SHARE("share", "分享"), ASK("ask", "问答"), JOB("job", "招聘"), GOOD("good", "精华");

    private String zhName;
    private String enName;

    TAB(String enName, String zhName) {
        this.enName = enName;
        this.zhName = zhName;
    }

    public String getZhName() {
        return zhName;
    }

    public String getEnName() {
        return enName;
    }

    public static TAB ValueOf(String name) {
        switch (name) {
            case "share":
            case "分享":
                return SHARE;
            case "ask":
            case "问答":
                return ASK;
            case "job":
            case "招聘":
                return JOB;
            case "good":
            case "精华":
                return GOOD;
            default:
                return SHARE;
        }
    }

    @Override
    public String toString() {
        return zhName + "(" + enName + ")";
    }
}
