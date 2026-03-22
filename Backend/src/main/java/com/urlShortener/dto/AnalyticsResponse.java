package com.urlShortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnalyticsResponse {

    private String originalUrl;
    private String shortUrl;
    private int clickCount;
}
