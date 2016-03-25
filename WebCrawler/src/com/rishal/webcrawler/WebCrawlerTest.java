package com.rishal.webcrawler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


class WebCrawlerTest {
    public static void main(String args[] ) throws Exception {
        String mydate="160310";
        //Date dt=new Date();
        
        SimpleDateFormat sd=new SimpleDateFormat("YYmmdd",Locale.ENGLISH);
        Date date = sd.parse(mydate);
        //String fd=sd.format(mydate);
        System.out.println(date);
        
    }
}

