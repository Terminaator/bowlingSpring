package demo.bowling.dto.game;

import bowling.bowlingMechanic.table.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDTO {
    private Table table;
    private int id;

    public GameDTO(int id, Table table) {
        this.id = id;
        this.table = table;
    }
}
