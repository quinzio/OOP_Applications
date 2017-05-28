package applications;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import applications.Applicant.Capability;

public class HandleApplications {
    private SortedMap<String, Skill> skills = new TreeMap<>();
    private SortedMap<String, Position> positions = new TreeMap<>();
    private SortedMap<String, Applicant> applicants = new TreeMap<>();

    /**
     * 
     * Il metodo addSkills inserisce un elenco di competenze dati i nomi. Lancia
     * un'eccezione se trova una competenza duplicata.
     * 
     * @throws ApplicationException
     **/
    public void addSkills(String... strings) throws ApplicationException {
	for (String s : strings) {
	    if (skills.containsKey(s))
		throw new ApplicationException();
	    skills.put(s, new Skill(s));
	}
    }

    /**
     * Il metodo addPosition inserisce una posizione (dato il nome) con l'elenco
     * delle competenze richieste (di cui sono dati i nomi). Lancia un'eccezione
     * se la posizione è già stata inserita o se non trova una competenza
     * richiesta tra quelle già inserite.
     * 
     * @throws ApplicationException
     */
    public void addPosition(String nome, String... skillsReqd) throws ApplicationException {
	if (positions.containsKey(nome))
	    throw new ApplicationException();
	for (String s : skillsReqd) {
	    if (positions.containsKey(s))
		throw new ApplicationException();
	}

	Position position = new Position(nome, null);
	for (String s : skillsReqd) {
	    position.addSkill(skills.get(s));
	    skills.get(s).addPosition(position);
	}
    }

    /**
     * Il metodo getSkill fornisce un oggetto Skill dato il nome; se non lo
     * trova tra quelli inseriti dà null
     */

    public Skill getSkill(String name) {
	return skills.get(name);
    }

    /**
     * 
     * Il metodo getPosition fornisce un oggetto Position dato il nome; se non
     * lo trova tra quelli inseriti dà null
     **/

    public Position getPosition(String name) {
	return positions.get(name);
    }

    /**
     * Il metodo addApplicant inserisce un richiedente (dato il nome) con
     * l'elenco delle sue capacità (capabilities). Un esempio di elenco è il
     * seguente: "java:9,sql:7". Ogni capacità è costituita dal nome della
     * competenza, dal separatore ':', e dal livello (compreso tra 1 e 10)
     * posseduto dal richiedente. Le capacità sono separate da virgole. Il
     * metodo lancia un'eccezione se il richiedente è già stato inserito, una
     * competenza non è stata inserita, un livello non appartiene al range
     * stabilito.
     * 
     * @throws ApplicationException
     * 
     **/
    public void addApplicant(String name, String capabilities) throws ApplicationException {
	if (applicants.containsKey(name))
	    throw new ApplicationException();
	Applicant applicant = new Applicant(name, null);

	for (String cap : capabilities.split(",")) {
	    String capName = cap.split(":")[0];
	    int capGrade = Integer.valueOf(cap.split(":")[1]);
	    if (skills.containsKey(capName))
		throw new ApplicationException();
	    if (capGrade > 10 || capGrade < 0)
		throw new ApplicationException();
	    applicant.appCapability(applicant.new Capability(skills.get(capName), capGrade));
	}
    }

    /**
     * 
     * Il metodo getCapabilities fornisce l'elenco delle capacità di un
     * richiedente (dato il nome) nel formato indicato sopra: le capacità sono
     * però ordinate alfabeticamente. Lancia un'eccezione se non trova il
     * richiedente tra quelli già inseriti. Se non ci sono capacità dà la
     * stringa vuota.
     * 
     * @throws ApplicationException
     * 
     **/
    public String getCapabilities(String name) throws ApplicationException {
	if (!applicants.containsKey(name))
	    throw new ApplicationException();
	return applicants.get(name).getCapabilities().stream().sorted(Comparator.comparing(c -> c.getSkill().getName()))
		.map(c -> c.getSkill().getName() + ":" + c.getGrade()).collect(Collectors.joining(","));

    }

    /***
     * 
     * Il metodo enterApplication permette ad un richiedente di candidarsi ad
     * una posizione. Lancia un'eccezione se il richiedente o la posizione non
     * sono stati inseriti, se il richiedente non ha una capacità per ciascuno
     * skill richiesto dalla posizione o si è già candidato ad una posizione. Ad
     * esempio, se la posizione chiede gli skill java e uml, il richiedente deve
     * avere tra le sue capabilities quella per java e quella per uml
     * 
     * @throws ApplicationException
     **/

    public void enterApplication(String applicant, String position) throws ApplicationException {
	if (!applicants.containsKey(applicant))
	    throw new ApplicationException();
	if (!positions.containsKey(position))
	    throw new ApplicationException();

	positions.get(position).addApplicant(applicants.get(applicant));
    }

    /**
     * 
     * Il metodo setWinner stabilisce il vincitore (richiedente) per una
     * posizione. Lancia un'eccezione se il richiedente non si è candidato alla
     * posizione, se c'è già un altro vincitore per la stessa posizione, o se la
     * somma delle capacità relative agli skill della posizione non è maggiore
     * del numero degli skill moltiplicati per 6 (ad es. per una posizione con 2
     * skill la soglia da superare è 12).
     * 
     * Fornisce la somma delle capacità del vincitore.
     * 
     * @throws ApplicationException
     * 
     */

    public int setWinner(String applicant, String position) throws ApplicationException {
	if (!applicants.containsKey(applicant))
	    throw new ApplicationException();
	if (!positions.containsKey(position))
	    throw new ApplicationException();

	return positions.get(position).setWinner(applicants.get(applicant));

    }

    /**
     * Il metodo skill_nApplicants dà il numero di richiedenti per skill; gli
     * skill sono ordinati alfabeticamente per nome.
     **/

    public SortedMap<String, Long> skill_nApplicants() {
	SortedMap<String, Long> v = applicants.values().stream().flatMap(a -> a.getCapabilities().stream())
		.collect(Collectors.groupingBy(c -> c.getSkill().getName(), TreeMap::new, Collectors.counting()));
	return v;
    }

    /**
     * 
     * Il metodo maxPosition dà il nome della posizione con il maggior numero di
     * candidati.
     **/

    public String maxPosition() {
	Position pos = positions.values().stream()
		.collect(Collectors.maxBy(Comparator.comparing(p -> p.getApplicants().size()))).get();
	if (pos != null)
	    return pos.getName();
	return null;
    }

}
