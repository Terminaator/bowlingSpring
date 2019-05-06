package demo.bowling.service.data;

import bowling.Bowling;
import bowling.bowlingMechanic.frame.chance.roll.Roll;
import bowling.bowlingMechanic.player.Player;
import demo.bowling.dto.PlayerDTO;
import demo.bowling.dto.RollDTO;
import demo.bowling.dto.game.GameDTO;
import demo.bowling.dto.game.GamesDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Setter
@Getter
public class Game {
    private final AtomicInteger counter = new AtomicInteger();
    private int id;
    private Bowling bowling = new Bowling();

    public Game(int id) {
        this.id = id;
    }

    public int addPlayer(PlayerDTO playerDTO) {
        int id = counter.incrementAndGet();
        if (bowling.addPlayer(new Player(id, playerDTO.getName())))
            return id;
        else return -1;
    }

    public boolean addRoll(RollDTO rollDTO) {
        return bowling.addRoll(bowling.getPlayer(rollDTO.getPlayerId()), new Roll(rollDTO.getRoll()));
    }

    public GameDTO generateGameDTO(int id) {
        return new GameDTO(id, bowling.playerStatus(bowling.getPlayer(id)));
    }

    public GamesDTO generateGamesDTO() {
        return new GamesDTO(id, bowling.gameStatus());
    }
}
