package prog.ik.btest.model;

public enum SupportedCurrency {
    UAH("UA hryvnja"),
    USD("USA dollar"),
    EUR("EU euro");

    public final String label;

    public String getLabel() {
        return label;
    }

    private SupportedCurrency(String label) {
        this.label = label;
    }
}
