package prog.ik.btest.model;

import java.util.HashMap;

public class RateListDTO {
    private String date;

    private String base;

    private HashMap<String, Double> rates;

    public RateListDTO() {}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
            this.date = date;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getBase() {
        return base;
    }

    public void setRates(HashMap<String, Double> rates) {
        this.rates = rates;
    }

    public HashMap<String, Double> getRates() {
        return rates;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s : base currency %s", date, base)).append("\n");
        rates.forEach((key, value) -> sb.append(key).append(" = ").append(value).append("\n"));

        return sb.toString();
    }
}
