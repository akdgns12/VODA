package com.project.voda.util;

import lombok.Getter;

@Getter
public enum EmotionEnum {

    EMOTION(new String[] {"sadness","surprise","angry","neutral","happiness"}),
    CNT(5),
    IMGPATH(new String[]{"sadness.png","surprise.png","angry.png","neutral.png","happiness.png"});
    private String[] content;
    private int cnt;
    EmotionEnum( String[] content){
        this.content = content;
    }
    EmotionEnum( int cnt){
        this.cnt = cnt;
    }

}
