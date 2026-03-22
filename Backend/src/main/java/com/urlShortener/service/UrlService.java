package com.urlShortener.service;

import com.urlShortener.dto.AnalyticsResponse;
import com.urlShortener.dto.UrlResponse;
import com.urlShortener.entity.Url;
import com.urlShortener.entity.User;
import com.urlShortener.repository.UrlRepository;
import com.urlShortener.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UserRepository userRepository;

    public List<UrlResponse> getUserUrls(String username) {

        return urlRepository.findByUserUsername(username)
                .stream()
                .map(url -> new UrlResponse(
                        url.getOriginalUrl(),
                        "http://localhost:8080/" + url.getShortCode(),
                        url.getClickCount()
                ))
                .toList();
    }

    public String shortenUrl(String originalUrl, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String shortCode = generateShortCode();

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortCode(shortCode);
        url.setUser(user);

        urlRepository.save(url);

        return "http://localhost:8080/" + shortCode;
    }

    public String generateShortCode() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        String shortCode;
        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                sb.append(chars.charAt(random.nextInt(chars.length())));
            }
            shortCode = sb.toString();
        } while (urlRepository.findByShortCode(shortCode).isPresent());

        return shortCode;
    }

    public String getOriginalUrl(String shortCode) {

        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Not found"));

     
        url.setClickCount(url.getClickCount() + 1);
        urlRepository.save(url);

        return url.getOriginalUrl();
    }
    public void deleteUrl(String shortCode, String username) {

        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("URL not found"));

        if (!url.getUser().getUsername().equals(username)) {
            throw new RuntimeException("❌ You are not allowed to delete this URL");
        }

        urlRepository.delete(url);
    }
    public String createCustomUrl(String originalUrl, String customCode, String username) {

        if (urlRepository.findByShortCode(customCode).isPresent()) {
            throw new RuntimeException("❌ Custom code already taken");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortCode(customCode);
        url.setUser(user);

        urlRepository.save(url);

        return "http://localhost:8080/" + customCode;
    }

    public AnalyticsResponse getAnalytics(String shortCode) {

        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Not found"));

        return new AnalyticsResponse(
                url.getOriginalUrl(),
                "http://localhost:8080/" + shortCode,
                url.getClickCount()
        );
    }

}