package geostore.model;

/**
 * Created by endamccormack on 21/04/2017.
 */
public class GeostoreItem {

    private String localAuthorityCode;
    private String officialName;
    private String wkbGeometry;

    @Override
    public String toString() {
        return "GeostoreItem{" +
                "localAuthorityCode='" + localAuthorityCode + '\'' +
                ", officialName='" + officialName + '\'' +
                ", wkbGeometry='" + wkbGeometry + '\'' +
                '}';
    }

    public GeostoreItem(String localAuthorityCode, String officialName, String wkbGeometry) {
        this.localAuthorityCode = localAuthorityCode;
        this.officialName = officialName;
        this.wkbGeometry = wkbGeometry;
    }

    public String getLocalAuthorityCode() {
        return localAuthorityCode;
    }

    public void setLocalAuthorityCode(String localAuthorityCode) {
        this.localAuthorityCode = localAuthorityCode;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public String getWkbGeometry() {
        return wkbGeometry;
    }

    public void setWkbGeometry(String wkbGeometry) {
        this.wkbGeometry = wkbGeometry;
    }
}
