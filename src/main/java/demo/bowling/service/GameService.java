package demo.bowling.service;

import demo.bowling.dto.PlayerDTO;
import demo.bowling.dto.RollDTO;
import demo.bowling.service.data.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@CacheConfig(cacheNames = {"games"})
public class GameService {

    private List<Game> games = new ArrayList<>();

    @Autowired
    public GameService() {
    }


    @CachePut
    public void addGame(Game game) {
        games.add(game);
    }

    @Cacheable
    public List<Game> getAll() {
        return games;
    }

    @Cacheable
    public Game getGame(int id) {
        return getAll().stream().filter(game -> game.getId() == id).findFirst().orElse(null);
    }

    @CachePut
    public int addPlayer(int id, PlayerDTO playerDTO) {
        Game game = getGame(id);
        if (game != null) {
            return getGame(id).addPlayer(playerDTO);
        }
        return -1;
    }

    @CachePut
    public boolean addRoll(int id, RollDTO rollDTO) {
        Game game = getGame(id);
        return game != null && getGame(id).addRoll(rollDTO);
    }

}
