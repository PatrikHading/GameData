package com.example.GameData;

import com.example.GameData.model.Player;
import com.example.GameData.service.PlayerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;


import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/archive")
    public String archive() {
        return "archive";
    }

    @GetMapping("/registeredPlayers")
    public String getAllPlayers(Model model) {
        List<Player> players = playerService.getAllPlayers();
        model.addAttribute("players", players);
        return "registeredPlayers";
    }

    @GetMapping("/game")
    public String game(Model model, HttpSession session) {
        List<Player> team1Players = (List<Player>) session.getAttribute("team1Players");
        if (team1Players == null) {
            team1Players = new ArrayList<>();
            session.setAttribute("team1Players", team1Players);
        }
        List<Player> team2Players = (List<Player>) session.getAttribute("team2Players");
        if (team2Players == null) {
            team2Players = new ArrayList<>();
            session.setAttribute("team2Players", team2Players);
        }

        List<Player> availablePlayers = playerService.getAllPlayers();
        availablePlayers.removeAll(team1Players);

        model.addAttribute("team1Players", team1Players);
        model.addAttribute("team2Players", team2Players);
        model.addAttribute("availablePlayers", availablePlayers);

        return "game";
    }

    @PostMapping("/addToTeam1")
    public String addToTeam1(@RequestParam("playerId") Integer playerId, HttpSession session, Model model) {

        Player playerToAdd = playerService.getAllPlayers().stream().filter(player -> player.getId() == playerId).findFirst().orElse(null);

        if (playerToAdd != null) {
            List<Player> team1Players = (List<Player>) session.getAttribute("team1Players");
            if (team1Players == null) {
                team1Players = new ArrayList<>();
            }
            if (team1Players.size() < 20) {
                // Check if player isn't already in the team
                if (!team1Players.contains(playerToAdd)) {
                    team1Players.add(playerToAdd);
                    session.setAttribute("team1Players", team1Players);
                }
            }
        }
         return "redirect:/game";
    }

    @PostMapping("/addToTeam2")
    public String addToTeam2(@RequestParam("playerId") Integer playerId, HttpSession session, Model model) {

        Player playerToAdd = playerService.getAllPlayers().stream().filter(player -> player.getId() == playerId).findFirst().orElse(null);

        if (playerToAdd != null) {
            List<Player> team2Players = (List<Player>) session.getAttribute("team2Players");
            if (team2Players == null) {
                team2Players = new ArrayList<>();
            }
            if (team2Players.size() < 20) {
                // Check if player isn't already in the team
                if (!team2Players.contains(playerToAdd)) {
                    team2Players.add(playerToAdd);
                    session.setAttribute("team2Players", team2Players);
                }
            }
        }
        return "redirect:/game";
    }

    @PostMapping("/removeAllFromTeam1")
    public String removeAllFromTeam1(HttpSession session, Model model) {
        List<Player> team1Players = (List<Player>) session.getAttribute("team1Players");
        if (team1Players != null) {
            team1Players.clear();
            session.setAttribute("team1Players", team1Players);
        }
        return "redirect:/game";
    }

    @PostMapping("/removeAllFromTeam2")
    public String removeAllFromTeam2(HttpSession session, Model model) {
        List<Player> team2Players = (List<Player>) session.getAttribute("team2Players");
        if (team2Players != null) {
            team2Players.clear();
            session.setAttribute("team2Players", team2Players);
        }
        return "redirect:/game";
    }


}