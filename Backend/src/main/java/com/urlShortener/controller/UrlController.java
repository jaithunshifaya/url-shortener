package com.urlShortener.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.urlShortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<?> shorten(
            @RequestBody Map<String, String> body,
            Authentication auth) {

        System.out.println("🔥 SHORTEN API HIT");

        System.out.println("Auth object: " + auth);

        if (auth == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("❌ User not authenticated (JWT issue)");
        }

        String url = body.get("url");

        if (url == null || url.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("❌ URL cannot be empty");
        }

        String username = auth.getName();
        System.out.println("✅ Logged in user: " + username);

        String shortUrl = urlService.shortenUrl(url, username);

        return ResponseEntity.ok(shortUrl);
    }


    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirect(@PathVariable String shortCode) {

        System.out.println("🔁 REDIRECT HIT for: " + shortCode);

        try {
            String originalUrl = urlService.getOriginalUrl(shortCode);

            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(originalUrl))
                    .build();

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("❌ Short URL not found");
        }
    }

    @GetMapping("/my-urls")
    public ResponseEntity<?> getMyUrls(Authentication auth) {

        System.out.println("🔥 MY URLS API HIT");
        System.out.println("Auth object: " + auth);

        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("❌ User not authenticated");
        }

        String username = auth.getName();

        return ResponseEntity.ok(
                urlService.getUserUrls(username)
        );
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<?> deleteUrl(@PathVariable String shortCode, Authentication auth) {

        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("❌ Not authenticated");
        }

        String username = auth.getName();

        urlService.deleteUrl(shortCode, username);

        return ResponseEntity.ok("✅ URL deleted successfully");
    }

    @PostMapping("/custom")
    public ResponseEntity<?> customShorten(
            @RequestBody Map<String, String> body,
            Authentication auth) {

        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Not authenticated");
        }

        String url = body.get("url");
        String customCode = body.get("customCode");

        return ResponseEntity.ok(
                urlService.createCustomUrl(url, customCode, auth.getName())
        );
    }
    @GetMapping("/qr/{shortCode}")
    public ResponseEntity<byte[]> generateQR(@PathVariable String shortCode) throws Exception {

        String url = "http://localhost:8080/" + shortCode;

        int width = 300;
        int height = 300;

        BitMatrix matrix = new MultiFormatWriter()
                .encode(url, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", pngOutputStream);

        return ResponseEntity.ok()
                .header("Content-Type", "image/png")
                .body(pngOutputStream.toByteArray());
    }

    @GetMapping("/analytics/{shortCode}")
    public ResponseEntity<?> getAnalytics(@PathVariable String shortCode) {

        return ResponseEntity.ok(
                urlService.getAnalytics(shortCode)
        );
    }
}