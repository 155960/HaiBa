package com.zengqiang.future.common;

public class Const {

    public interface Ftp{
        String IMG="/img";
        String VIDEO="/video";
        String FILE="/file";
    }

    public interface Good{
        int IMG=1;
        int VIDEO=2;
        int FILE=3;
        int ITEM=4;
    }

    public  interface Post{
        int GOOD=1;
        int ITEM=2;
        int GOOD_AND_ITEM=3;
    }

    public interface CachePrefix{
        String HOTPOST="hotpost";//地区热点数据
    }
}
