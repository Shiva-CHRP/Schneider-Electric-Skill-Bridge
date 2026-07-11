package schneider.pojo;

import java.util.Map;

public class UserTypeData {

	private Map<String, Map<String, FieldDetails>> fields;

	public Map<String, Map<String, FieldDetails>> getFields() {
		return fields;
	}

	public void setFields(Map<String, Map<String, FieldDetails>> fields) {

		this.fields = fields;
	}

}
