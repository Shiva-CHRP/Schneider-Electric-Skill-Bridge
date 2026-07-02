package schneider.pojo;

import java.util.List;

public class ZoneData {
	private String zoneName;
	private List<ClusterData> clusters;
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public List<ClusterData> getClusters() {
		return clusters;
	}
	public void setClusters(List<ClusterData> clusters) {
		this.clusters = clusters;
	}
	
}
