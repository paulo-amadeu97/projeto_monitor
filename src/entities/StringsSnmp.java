package entities;

public enum StringsSnmp {
    INTERFACES("ifDescr"),
    IF_VELOCIT("ifSpeed"),
    IF_STATUS("ifOperStatus"),
    IF_DOWNLOAD("ifInOctets"),
    IF_UPLOAD("ifOutOctets"),

    PORCENT_USED_CPU("ssCpuUser.0"),
    TEMP_CPU_MILI_CEL("lmTempSensorsValue.10"),

    TOTAL_RAM("memTotalReal.0"),
    FREE_RAM("memTotalFree.0"),

    DISk_DESCRIPTION("hrStorageDescr"),
    STORAGE_SIZE("hrStorageSize"),
    STORAGE_USED("hrStorageUsed"),
    ALOCATION_UNITS("hrStorageAllocationUnits");




    private final String oid;

    StringsSnmp(String oid) {
        this.oid = oid;
    }

    public String getOid(){
        return this.oid;
    }
}
