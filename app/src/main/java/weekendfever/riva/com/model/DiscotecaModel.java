package weekendfever.riva.com.model;

public class DiscotecaModel {
    private String title;
    private String status;
    private String image;
    private String urlSites;
    private String horaLV;
    private String horaSD;


    public DiscotecaModel() {
    }

    public DiscotecaModel(String title, String status, String image, String urlSites, String horaLV, String horaSD) {
        this.title = title;
        this.status = status;
        this.image = image;
        this.urlSites = urlSites;
        this.horaLV = horaLV;
        this.horaSD = horaSD;
    }

    public String getUrlSites() {
        return urlSites;
    }

    public void setUrlSites(String urlSites) {
        this.urlSites = urlSites;
    }

    public String getHoraLV() {
        return horaLV;
    }

    public void setHoraLV(String horaLV) {
        this.horaLV = horaLV;
    }

    public String getHoraSD() {
        return horaSD;
    }

    public void setHoraSD(String horaSD) {
        this.horaSD = horaSD;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}