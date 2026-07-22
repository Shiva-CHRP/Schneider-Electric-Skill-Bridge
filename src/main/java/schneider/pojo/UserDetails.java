package schneider.pojo;

import java.util.Map;

public class UserDetails {
	private Map<String,String> personalInformation;

    private Map<String,String> organizationCategory;

    private Map<String,String> location;

    public Map<String, String> getPersonalInformation() {
		return personalInformation;
	}

	public void setPersonalInformation(Map<String, String> personalInformation) {
		this.personalInformation = personalInformation;
	}

	public Map<String, String> getOrganizationCategory() {
		return organizationCategory;
	}

	public void setOrganizationCategory(Map<String, String> organizationCategory) {
		this.organizationCategory = organizationCategory;
	}

	public Map<String, String> getLocation() {
		return location;
	}

	public void setLocation(Map<String, String> location) {
		this.location = location;
	}

	

}
