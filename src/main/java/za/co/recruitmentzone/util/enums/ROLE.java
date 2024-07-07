package za.co.recruitmentzone.util.enums;

public enum ROLE {
    ROLE_ADMIN,
    ROLE_MANAGER,
    ROLE_EMPLOYEE,
    ROLE_GUEST,
    ROLE_PROMETHEUS;

    public String getRoleName() {
        return switch (this) {
            case ROLE_ADMIN -> {
                String theRole =ROLE_ADMIN.toString();
                yield theRole.substring("ROLE_".length());
            }
            case ROLE_MANAGER -> {
                String theRole =ROLE_MANAGER.toString();
                yield theRole.substring("ROLE_".length());
            }
            case ROLE_EMPLOYEE -> {
                String theRole =ROLE_EMPLOYEE.toString();
                yield theRole.substring("ROLE_".length());
            }
            case ROLE_GUEST -> {
                String theRole =ROLE_GUEST.toString();
                yield theRole.substring("ROLE_".length());
            }
            case ROLE_PROMETHEUS -> {
                String theRole =ROLE_PROMETHEUS.toString();
                yield theRole.substring("ROLE_".length());
            }
            // ROLE_PROMETHEUS
        };
    }



}
