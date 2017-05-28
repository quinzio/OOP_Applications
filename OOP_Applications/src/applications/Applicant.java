package applications;

import java.util.ArrayList;
import java.util.List;

public class Applicant {

    public class Capability {
	private Skill skill;
	private int grade;

	public Capability(Skill skill, int grade) {
	    super();
	    this.skill = skill;
	    this.grade = grade;
	}

	public Skill getSkill() {
	    return skill;
	}

	public void setSkill(Skill skill) {
	    this.skill = skill;
	}

	public int getGrade() {
	    return grade;
	}

	public void setGrade(int grade) {
	    this.grade = grade;
	}
    }

    private String name;
    private List<Capability> capabilities = new ArrayList<>();
    private boolean hasApplied = false;

    public Applicant(String name, List<Capability> capabilities) {
	this.name = name;
	if (capabilities != null)
	    this.capabilities = capabilities;
    }

    public void appCapability(Capability cap) {
	capabilities.add(cap);
    }

    public List<Capability> getCapabilities() {
	return capabilities;
    }

    public String getName() {
	return name;
    }

    public boolean applies() {
	boolean ret = hasApplied;
	hasApplied = true;
	return ret;
    }

    @Override
    public String toString() {
	return name;
    }
    
    
    

}
