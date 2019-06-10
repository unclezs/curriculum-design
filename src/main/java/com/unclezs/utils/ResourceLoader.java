package com.unclezs.utils;

import java.net.URL;

public class ResourceLoader {
    public static URL getFXMLResource(String fxmlName){
        String path="/"+fxmlName;
        URL url= ResourceLoader.class.getResource(path);
        //System.out.println(url.getPath());
        return url;
    }
    public static URL getImage(String icon){
        return ResourceLoader.class.getResource("/image/"+icon);
    }

}
