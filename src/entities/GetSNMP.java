package entities;

import org.hibernate.type.StringRepresentableType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class GetSNMP {
    private String IP;

    public GetSNMP(String IP) {
        this.IP = IP;
    }

    public Integer getDataInt(String mib) throws IOException {
        InputStream inputStream = null;
        String line = null;
        try {
            String[] command = {"sh", "-c", "snmpwalk -v 2c -c public " + this.IP + " " + mib + " | awk '{print $4}'"};

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream)
            );
            line = bufferedReader.readLine();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return Integer.parseInt(line);
    }

    public String getDataStr(String mib) throws IOException {
        InputStream inputStream = null;
        String line = null;
        try {
            String[] command = {"sh", "-c", "snmpwalk -v 2c -c public " + this.IP + " " + mib + " | awk '{print $4}'"};

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream)
            );
            line = bufferedReader.readLine();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return line;
    }

    public List<String> getDataList(String mib) throws IOException {
        InputStream inputStream = null;
        List<String> dataList = new ArrayList<String>();
        try {
            String[] command = {"sh", "-c", "snmpwalk -v 2c -c public " + this.IP + " " + mib + " | awk '{print $4}'"};

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream)
            );
            String line;
            while ((line = bufferedReader.readLine())!=null){
                dataList.add(line);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return dataList;
    }
    public List<String> getIndexList(String mib, String filter) throws IOException {
        InputStream inputStream = null;
        List<String> dataList = new ArrayList<String>();
        try {
            String[] commandIndex = {"sh", "-c", "snmpwalk -v 2c -c public " + this.IP + " " + mib + " | grep " + filter + " | awk -F'.' '{print $2}' | awk '{print $1}'"};

            ProcessBuilder processBuilder = new ProcessBuilder(commandIndex);
            Process process = processBuilder.start();

            inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream)
            );
            String line;
            while ((line = bufferedReader.readLine())!=null){
                dataList.add(line);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return dataList;
    }

    public Map<String, List<String>> getDataMap(String[] mibList) throws IOException {
        InputStream inputStream = null;
        Map<String, List<String>> dataMap = new HashMap<>();
        List<String> ifnet = this.getDataList(mibList[0]);
        List<String> data = new ArrayList<String>();
        try {
            for(String key : ifnet) {
                data.clear();
                for (String mib : mibList) {
                    if(mib == mibList[0]){
                        continue;
                    } else {
                        String[] command = {"sh", "-c", "snmpwalk -v 2c -c public " + this.IP + " " + mib + " | awk '{print $4}'"};

                        ProcessBuilder processBuilder = new ProcessBuilder(command);
                        Process process = processBuilder.start();

                        inputStream = process.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(
                                new InputStreamReader(inputStream)
                        );
                        String line = bufferedReader.readLine();
                        data.add(line);
                    }
                }
                dataMap.put(key, data);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return dataMap;
    }

    public Map<String, List<String>> getDataMap(String[] mibList, List<String> index) throws IOException {
        InputStream inputStream = null;
        Map<String, List<String>> dataMap = new HashMap<>();
        //List<String> ifnet = this.getDataList(mibList[0]);
        List<String> data = new ArrayList<String>();
        try {
            for(String key : index) {
                data.clear();
                for (String mib : mibList) {
                    if(mib == mibList[0]){
                        continue;
                    } else {
                        for(String indexVal : index) {
                            String[] command = {"sh", "-c", "snmpwalk -v 2c -c public " + this.IP + " " + mib + "." + indexVal +" | awk '{print $4}'"};
                            //System.out.println(Arrays.stream(command).toList());

                            ProcessBuilder processBuilder = new ProcessBuilder(command);
                            Process process = processBuilder.start();

                            inputStream = process.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(
                                    new InputStreamReader(inputStream)
                            );
                            String line = bufferedReader.readLine();
                            data.add(line);
                        }
                    }
                }
                dataMap.put(key, data);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return dataMap;
    }
}
