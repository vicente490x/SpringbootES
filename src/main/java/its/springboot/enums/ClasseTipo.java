package its.springboot.enums;

public enum ClasseTipo {
    PRIMA_A("1A"),
    PRIMA_B("1B"),
    SECONDA_A("2A"),
    SECONDA_B("2B"),
    TERZA_A("3A"),
    TERZA_B("3B"),
    QUARTA_A("4A"),
    QUARTA_B("4B"),
    QUINTA_A("5A"),
    QUINTA_B("5B");

    private final String label;

    ClasseTipo(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
