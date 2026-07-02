package schneider.pojo;

import java.util.List;

public class CategoryData {
	
	private String categoryName;
	private List<String> subCategories;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<String> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<String> subCategories) {
		this.subCategories = subCategories;
	}
}
