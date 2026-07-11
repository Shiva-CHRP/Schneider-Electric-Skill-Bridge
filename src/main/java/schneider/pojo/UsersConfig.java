package schneider.pojo;

import java.util.Map;

public class UsersConfig {
	
	private Map<String, UserTypeData> userType;

    public Map<String, UserTypeData> getUserType() {
        return userType;
    }


    public void setUserType(Map<String, UserTypeData> userType) {
        this.userType = userType;
    }
}
