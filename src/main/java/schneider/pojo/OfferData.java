package schneider.pojo;

import java.util.List;

public class OfferData {

	private String offerName;
	private List<CategoryData> categories;

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public List<CategoryData> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryData> categories) {
		this.categories = categories;
	}
}
