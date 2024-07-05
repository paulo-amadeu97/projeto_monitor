package entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
@Entity
@Table(name = "main")
public class Cpu {
    @Column(name = "ctype")
    private final String type = "cpu";
    @Column(name = "nusedcpu")
    private Integer usedCpu;
    @Column(name = "ntempcpu")
    private Double tempCpu;
    @Column(name = "dtimestamp")
    private LocalDateTime timestamp;

    public Cpu(Integer usedCpu, Integer tempCpu) {
        this.setTempCpu(tempCpu);
        this.setUsedCpu(usedCpu);
        this.timestamp = LocalDateTime.now();
    }

    public void setTempCpu(Integer tempCpu) {
        this.tempCpu =  tempCpu.doubleValue() / 1000;
    }

    public void setUsedCpu(Integer usedCpu) {
        this.usedCpu = usedCpu;
    }

    @Override
    public String toString(){
        return "Uso de cpu: " + this.usedCpu +
                "\nTemperatura: " + this.tempCpu +
                "\ntimestamp: " + this.timestamp;
    }
}
