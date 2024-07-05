import entities.*;
import factory.ConnectionFactory;
import factory.EntityManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {

        try {
            GetSNMP snmp1 = new GetSNMP("192.168.1.13");
            EntityManager entityManager = new EntityManager(ConnectionFactory.createConnection());

            //CPU
            new Thread(){
                @Override
                public void run() {
                    try {
                        while (true) {
                            Cpu cpu = new Cpu(snmp1.getDataInt(StringsSnmp.PORCENT_USED_CPU.getOid()), snmp1.getDataInt(StringsSnmp.TEMP_CPU_MILI_CEL.getOid()));
                            entityManager.save(cpu);
                        }
                    } catch (IOException | IllegalAccessException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

            //MEMORY
            new Thread(){
                @Override
                public void run() {
                    try {
                        while (true){
                            Memory memory = new Memory(snmp1.getDataInt(StringsSnmp.FREE_RAM.getOid()),snmp1.getDataInt(StringsSnmp.TOTAL_RAM.getOid()));
                            entityManager.save(memory);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();

            //NET
            new Thread(){
                @Override
                public void run() {
                    try {
                        String[] mibList = {StringsSnmp.INTERFACES.getOid(), StringsSnmp.IF_UPLOAD.getOid(),
                                            StringsSnmp.IF_DOWNLOAD.getOid(), StringsSnmp.IF_STATUS.getOid(),
                                            StringsSnmp.IF_VELOCIT.getOid()};
                        while (true) {
                            Map<String, List<String>> dataMap = snmp1.getDataMap(mibList);
                            dataMap.forEach((key, value) -> {
                                NetInterfaces netInterfaces = new NetInterfaces(key, Integer.parseInt(value.getFirst()), Integer.parseInt(value.get(1)),
                                        value.get(2), Integer.parseInt(value.get(3)));
                                //System.out.println(dataMap);
                                try {
                                    entityManager.save(netInterfaces);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();

            //DISK
            new Thread(){
                @Override
                public void run() {
                    try {
                        while (true) {
                            String[] mibList = {StringsSnmp.DISk_DESCRIPTION.getOid(), StringsSnmp.STORAGE_SIZE.getOid(),
                                    StringsSnmp.STORAGE_USED.getOid(), StringsSnmp.ALOCATION_UNITS.getOid()};
                            List<String> index = snmp1.getIndexList(StringsSnmp.DISk_DESCRIPTION.getOid(), " /");

                            Map<String, List<String>> dataMap = snmp1.getDataMap(mibList, index);
                            dataMap.forEach((key, value) -> {
                                String descr = null;
                                try {
                                    descr = snmp1.getDataStr(StringsSnmp.DISk_DESCRIPTION.getOid() + "." + key);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                Disk disk = new Disk(descr, Integer.parseInt(value.getFirst()), Integer.parseInt(value.get(1)), Integer.parseInt(value.get(2)));
                                try {
                                    entityManager.save(disk);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            });
                            sleep(900000); //QUINZE MINUTOS
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}