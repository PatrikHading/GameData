package com.example.GameData.service;


import com.example.GameData.model.Player;
import com.example.GameData.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void savePlayer(Player player) {
        Player savedPlayer = playerRepository.save(player);
        System.out.println("Player saved: " + savedPlayer);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
}
