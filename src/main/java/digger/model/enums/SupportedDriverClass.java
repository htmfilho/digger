package digger.model.enums;

import java.util.ArrayList;
import java.util.List;

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

    public static List<SupportedDriverClass> getListSupportedDriverClasses() {
        List<SupportedDriverClass> supportedDriverClasses = new ArrayList<>(3);
        supportedDriverClasses.add(H2);
        supportedDriverClasses.add(POSTGRES);
        supportedDriverClasses.add(SQLSERVER);
        return supportedDriverClasses;
    }
}