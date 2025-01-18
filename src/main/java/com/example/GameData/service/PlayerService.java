package com.example.GameData.service;


import com.example.GameData.model.Player;
import com.example.GameData.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player savePlayer(Player player) {
        Player savedPlayer = playerRepository.save(player);
        System.out.println("Player saved: " + savedPlayer);
        return savedPlayer;
    }
}
