package applications;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Skill {
    private String name;
    private SortedMap<String, Position> positions = new TreeMap<>();

    public Skill(String name) {
	super();
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void addPosition(Position pos) {
	positions.put(pos.getName(), pos);
    }

    /**
     * Il metodo getPositions di Skill dà la lista delle posizioni che chiedono
     * lo skill, ordinate alfabeticamente per nome.
     * 
     **/

    public List<Position> getPositions() {
	return new ArrayList<>(positions.values());
    }

    @Override
    public String toString() {
	return "Name: " + name + positions.values().stream().map(p -> p.getName()).collect(Collectors.joining("-"));
    }

}
