package demo.bowling.dto.game;

import bowling.bowlingMechanic.table.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GamesDTO {
    private List<Table> tables;
    private int id;

    public GamesDTO(int id, List<Table> tables) {
        this.id = id;
        this.tables = tables;
    }
}
