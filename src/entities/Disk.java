package entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "main")
public class Disk {
    @Column(name = "ctype")
    private final String type = "disk";
    @Column(name = "cdescription")
    private String description;
    @Column(name = "nstoragesize")
    private Double storageSize;
    @Column(name = "nstoragefree")
    private Double storageFree;
    @Column(name = "nstorageused")
    private Double storageUsed;
    @Column(name = "dtimestamp")
    private LocalDateTime timestamp;

    public Disk(String description, Integer storageSize, Integer storageUsed, Integer storageAlocUnits) {
        this.setDescription(description);
        this.setStorageSize(storageSize, storageAlocUnits);
        this.setStorageFree(storageAlocUnits, storageUsed);
        this.setStorageUsed(storageUsed, storageAlocUnits);
        this.timestamp = LocalDateTime.now();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStorageSize(Integer storageSize, Integer storageAlocUnits) {
        this.storageSize = (storageSize.doubleValue() * storageAlocUnits.doubleValue())/ 1000000;
    }

    public void setStorageFree(Integer storageAlocUnits, Integer storageUsed) {
        this.storageFree = ((storageSize.doubleValue() - storageUsed.doubleValue()) * storageAlocUnits.doubleValue()) / 1000000;
    }

    private void setStorageUsed(Integer storageUsed, Integer storageAlocUnits) {
        this.storageUsed = (storageUsed.doubleValue() * storageAlocUnits.doubleValue()) / 1000000;
    }

    @Override
    public String toString() {
        return "Disk{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", storageSize=" + storageSize +
                ", storageFree=" + storageFree +
                ", storageUsed=" + storageUsed +
                ", timestamp=" + timestamp +
                '}';
    }
}
