package ttl.larku.streams.vcf;

/**
 * @author whynot
 */
public class Contact {
    private String vcfVersion;
    private String name;
    private String fullName;
    private String organization;
    private String title;
    private String photo;
    private String phoneNumber;
    private String createTime;

    public String getVcfVersion() {
        return vcfVersion;
    }

    public void setVcfVersion(String vcfVersion) {
        this.vcfVersion = vcfVersion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "vcfVersion='" + vcfVersion + '\'' +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", organization='" + organization + '\'' +
                ", title='" + title + '\'' +
                ", photo='" + photo + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
