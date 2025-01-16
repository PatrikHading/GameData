package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GameController {

    @GetMapping("/start")
    public String showStartPage(Model model) {
        model.addAttribute("message");
        return "start";
    }

    @PostMapping("/addPlayer")
    public String addPlayer(Model model) {
        model.addAttribute("message", "Player added successfully");
        return "redirect:/start";
    }

    @PostMapping("/startGame")
    public String startGame(Model model) {
        model.addAttribute("message", "Game started successfully");
        return "redirect:/start";
    }
}
