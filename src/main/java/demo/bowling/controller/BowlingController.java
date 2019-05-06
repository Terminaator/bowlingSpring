package demo.bowling.controller;

import demo.bowling.dto.PlayerDTO;
import demo.bowling.dto.RollDTO;
import demo.bowling.dto.game.GameDTO;
import demo.bowling.dto.game.GamesDTO;
import demo.bowling.service.GameService;
import demo.bowling.service.data.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("game")
public class BowlingController {
    private final GameService gameService;
    private final AtomicInteger counter = new AtomicInteger();

    @Autowired
    public BowlingController(GameService gameService) {
        this.gameService = gameService;
    }


    @PostMapping(path = "/create", produces = "application/json")
    public ResponseEntity<GamesDTO> create() {
        Game game = new Game(counter.incrementAndGet());
        gameService.addGame(game);
        return ResponseEntity.ok(game.generateGamesDTO());
    }

    @PostMapping(path = "/{id}/add", consumes = "application/json")
    public ResponseEntity<GameDTO> addPlayer(@PathVariable int id, @RequestBody PlayerDTO playerDTO) {
        int playerId = gameService.addPlayer(id, playerDTO);
        if (playerId == -1) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(gameService.getGame(id).generateGameDTO(playerId));
    }

    @PostMapping(path = "/{id}/roll", consumes = "application/json")
    public ResponseEntity<GameDTO> addRoll(@PathVariable int id, @RequestBody RollDTO rollDTO) {
        if (!gameService.addRoll(id, rollDTO)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(gameService.getGame(id).generateGameDTO(rollDTO.getPlayerId()), HttpStatus.OK);
    }

    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity<List<GamesDTO>> getGames() {
        return ResponseEntity.ok(gameService.getAll().stream().map(Game::generateGamesDTO).collect(Collectors.toList()));
    }
}
