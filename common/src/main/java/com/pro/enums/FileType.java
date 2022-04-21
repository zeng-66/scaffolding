package com.pro.enums;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public enum FileType {
    IMAGE(Lists.newArrayList("jpg","jpeg","gif","bmp","png","tga")),
    VIDEO(Lists.newArrayList("avi","mp4","mp3","flv","rmvb","3gp")),
    ;
    private List<String> suffix ;
}
