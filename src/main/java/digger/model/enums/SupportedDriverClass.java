package digger.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum SupportedDriverClass {
    H2("org.h2.Driver"),
    POSTGRES("org.postgresql.Driver"),
    SQLSERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver");

    private String driverClassName;

    SupportedDriverClass(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getDriverClassName() {
        return this.driverClassName;
    }

    public static Map<String, String> getMapSupportedDriverClasses() {
        Map<String, String> supportedDriverClasses = new HashMap<>();
        supportedDriverClasses.put(SupportedDriverClass.H2.name(), SupportedDriverClass.H2.getDriverClassName());
        supportedDriverClasses.put(SupportedDriverClass.POSTGRES.name(), SupportedDriverClass.POSTGRES.getDriverClassName());
        supportedDriverClasses.put(SupportedDriverClass.SQLSERVER.name(), SupportedDriverClass.SQLSERVER.getDriverClassName());
        return supportedDriverClasses;
    }
}