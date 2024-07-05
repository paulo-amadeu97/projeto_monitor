package entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "main")
public class Memory {
    @Column(name = "ctype")
    private final String type = "memory";
    @Column(name = "nfreemem")
    private Double freeMem;
    @Column(name = "ntotalmem")
    private Double totalMem;
    @Column(name = "nusedmem")
    private Double usedMem;
    @Column(name = "dtimestamp")
    private LocalDateTime timestamp;

    public Memory(Integer freeMem, Integer totalMem) {
        this.setFreeMem(freeMem);
        this.setTotalMem(totalMem);
        this.setUsedMem();
        this.timestamp = LocalDateTime.now();
    }

    public void setFreeMem(Integer freeMem) {
        this.freeMem = freeMem.doubleValue() / 1000;
    }

    public void setTotalMem(Integer totalMem) {
        this.totalMem = totalMem.doubleValue() / 1000;
    }

    private void setUsedMem(){
        this.usedMem = this.totalMem - this.freeMem;
    }
}
