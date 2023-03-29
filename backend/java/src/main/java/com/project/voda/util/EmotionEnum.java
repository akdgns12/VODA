package com.project.voda.util;

import lombok.Getter;

@Getter
public enum EmotionEnum {

    EMOTION(new String[] {"sadness","surprise","angry","neutral","happiness"}),
    CNT(5),
    IMGPATH(new String[]{"/assets/sadness","/assets/surprise","/assets/angry","/assets/neutral","/assets/happiness"});
    private String[] content;
    private int cnt;
    EmotionEnum( String[] content){
        this.content = content;
    }
    EmotionEnum( int cnt){
        this.cnt = cnt;
    }

}
