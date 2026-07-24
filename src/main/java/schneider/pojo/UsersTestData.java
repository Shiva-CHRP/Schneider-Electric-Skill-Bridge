package schneider.pojo;

import java.util.Map;

public class UsersTestData {

	private Map<String, UserScenario> userScenarios;
	
	private Map<String, UserScenario> editUserScenarios;

    public Map<String, UserScenario> getEditUserScenarios() {
		return editUserScenarios;
	}

	public void setEditUserScenarios(Map<String, UserScenario> editUserScenarios) {
		this.editUserScenarios = editUserScenarios;
	}

	public Map<String, UserScenario> getMandatoryValidationScenarios() {
		return mandatoryValidationScenarios;
	}

	public void setMandatoryValidationScenarios(Map<String, UserScenario> mandatoryValidationScenarios) {
		this.mandatoryValidationScenarios = mandatoryValidationScenarios;
	}

	public Map<String, UserScenario> getChangePasswordScenarios() {
		return changePasswordScenarios;
	}

	public void setChangePasswordScenarios(Map<String, UserScenario> changePasswordScenarios) {
		this.changePasswordScenarios = changePasswordScenarios;
	}

	private Map<String, UserScenario> mandatoryValidationScenarios;

    private Map<String, UserScenario> changePasswordScenarios;

	public Map<String, UserScenario> getUserScenarios() {
		return userScenarios;
	}

	public void setUserScenarios(Map<String, UserScenario> userScenarios) {
		this.userScenarios = userScenarios;
	}

	

}
