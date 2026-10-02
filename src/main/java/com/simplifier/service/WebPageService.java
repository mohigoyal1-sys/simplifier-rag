package com.simplifier.service;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;


@Service
public class WebPageService {

    public String fetch(String url) throws Exception {
        Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(15000)
                .get();

        return document.body().text();

    }
}