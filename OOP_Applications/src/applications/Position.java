package applications;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import applications.Applicant.Capability;

public class Position {
    private String name;
    private SortedSet<Skill> skills = new TreeSet<>();
    private Map<String, Applicant> applying = new TreeMap<>();
    private Applicant winner;

    public Position(String name, SortedSet<Skill> skills) {
	super();
	this.name = name;
	if (skills != null)
	    this.skills = skills;
    };

    public void addSkill(Skill skill) {
	skills.add(skill);
    }

    public void addApplicant(Applicant appl) throws ApplicationException {
	List<Skill> lsk = appl.getCapabilities().stream().map(Capability::getSkill).collect(Collectors.toList());
	for (Skill sk : skills.toArray(new Skill[0])) {
	    if (!lsk.contains(sk))
		throw new ApplicationException();
	}
	if (appl.applies())
	    throw new ApplicationException(); // registra applicant
					      // automaticamente

	applying.put(appl.getName(), appl);
    }

    public String getName() {
	return name;
    }

    /**
     * Il metodo getApplicants di Position fornisce la lista ordinata
     * alfabeticamente dei nomi dei candidati alla posizione.
     **/
    public List<Applicant> getApplicants() {
	return new ArrayList<>(applying.values());

    }

    public int setWinner(Applicant applicant) throws ApplicationException {
	if (!applying.containsKey(applicant.getName()))
	    throw new ApplicationException();

	int sumOfGrades = applicant.getCapabilities().stream().filter(c -> skills.contains(c.getSkill()))
		.mapToInt(c -> c.getGrade()).sum();
	int nOfskills = skills.size();
	if (sumOfGrades < nOfskills * 6) {
	    throw new ApplicationException();
	}
	if (winner != null)
	    throw new ApplicationException();
	winner = applicant;
	return sumOfGrades;

    }

    /**
     * Il metodo getWinner di Position dà il nome del vincitore o null se manca.
     **/

    public String getWinner() {
	if (winner == null)
	    return null;
	return winner.getName();
    }

}
