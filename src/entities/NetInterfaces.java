package entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "main")
public class NetInterfaces {
    @Column(name = "ctype")
    private final String type = "net";
    @Column(name = "cdescription")
    private String description;
    @Column(name = "nupload")
    private Double upload;
    @Column(name = "ndownload")
    private Double download;
    @Column(name = "cstatus")
    private String status;
    @Column(name = "nvelocit")
    private Integer velocit;
    @Column(name = "dtimestamp")
    private LocalDateTime timestamp;

    public NetInterfaces(String description, Integer upload, Integer download, String status, Integer velocit) {
        this.setDescription(description);
        this.setStatus(status);
        this.setVelocit(velocit);
        this.setUpload(upload);
        this.setDownload(download);
        this.timestamp = LocalDateTime.now();
    }

    public void setUpload(Integer upload) {
        this.upload = upload.doubleValue() * 8 / 1000000;
    }

    public void setDownload(Integer download) {
        this.download = download.doubleValue() * 8 / 1000000;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setVelocit(Integer velocit) {
        Double v = (velocit.doubleValue() / 1000000);
        this.velocit = v.intValue();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "NetInterfaces{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", upload=" + upload +
                ", download=" + download +
                ", status='" + status + '\'' +
                ", velocit=" + velocit +
                ", timestamp=" + timestamp +
                '}';
    }
}
