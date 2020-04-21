package com.jryyy.forum.controller;

import com.jryyy.forum.model.Response;
import com.jryyy.forum.service.WordService;
import com.jryyy.forum.utils.security.UserLoginToken;
import org.springframework.web.bind.annotation.*;

/**
 * @author OU
 */
@UserLoginToken
@RestController
@RequestMapping("/word")
public class WordController {

    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/initialize/{userId}")
    public Response initialize(@PathVariable Integer userId) throws Exception {
        return wordService.getWordLibrary(userId);
    }

    @GetMapping("/memory/{userId}")
    public Response memory(@PathVariable Integer userId, Boolean know) throws Exception {
        return wordService.memory(userId, know);
    }

    @DeleteMapping("/remove/{userId}")
    public Response remove(@PathVariable Integer userId) throws Exception {
        return wordService.remove(userId);
    }

    @GetMapping("/progress/{userId}")
    public Response progress(@PathVariable Integer userId) throws Exception {
        return wordService.progress(userId);
    }

    @PutMapping("/dailyDuty/{userId}")
    public Response dailyDuty(@PathVariable Integer userId, @RequestParam Integer dailyDuty) throws Exception {
        return wordService.dailyDuty(userId, dailyDuty);
    }

}
