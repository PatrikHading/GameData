package com.example.GameData;

import com.example.GameData.model.Player;
import com.example.GameData.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class GameDataController {

    private final PlayerService playerService;

    @Autowired
    public GameDataController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/addPlayers")
    public String addPlayers() {
        return "addPlayers";
    }

    @PostMapping("/addPlayers")
    public String addPlayer(
            @RequestParam("firstname") String firstName,
            @RequestParam("lastname") String lastName,
            @RequestParam("jerseynumber") int jerseyNumber,
            Model model) {

        Player player = new Player(firstName, lastName, jerseyNumber);
        playerService.savePlayer(player);
        model.addAttribute("message", "Player added successfully");
        return "redirect:/addPlayers";

    }
}
