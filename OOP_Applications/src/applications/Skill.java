package applications;

import java.util.ArrayList;
import java.util.List;

public class Skill {
    private String name;
    private List<Position> positions = new ArrayList<>();

    public Skill(String name) {
	super();
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void addPosition(Position pos) {
	positions.add(pos);
    }

    /**
     * Il metodo getPositions di Skill dà la lista delle posizioni che chiedono
     * lo skill, ordinate alfabeticamente per nome.
     * 
     **/

    public List<Position> getPositions() {
	return positions;
    }

}
