package prog.ik.btest.service;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import prog.ik.btest.model.Currencyrate;
import prog.ik.btest.model.RateListDTO;
import prog.ik.btest.model.SupportedCurrency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RateRetriever {
    public RateListDTO getRateList() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<RateListDTO> response = restTemplate.getForEntity(Constants.CURRENCY_RATE_UTL, RateListDTO.class);

            return response.getBody();
        } catch (Exception ex) {
            return new RateListDTO();
        }
    }


    public RateListDTO getSupportedRateList() {
        RateListDTO rateList = getRateList();

        Map<String, Double> filtered = rateList.getRates().entrySet().stream()
                .filter(rate -> EnumUtils.isValidEnum(SupportedCurrency.class, rate.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        rateList.setRates((HashMap<String, Double>) filtered);

        return rateList;
    }

    public List<Currencyrate> getImportedCurrencyRates() {
        List<Currencyrate> currencyrates = new ArrayList<>();

        RateListDTO rateList = getSupportedRateList();
        HashMap<String, Double> rates = rateList.getRates();

        if (!rates.isEmpty()) {
            SupportedCurrency.valueOf(rateList.getBase());
            if (rateList.getBase().equals(SupportedCurrency.EUR.toString())) {
                currencyrates.add(
                    new Currencyrate(
                        SupportedCurrency.EUR.name(), SupportedCurrency.EUR.getLabel(), rates.get(SupportedCurrency.UAH.toString())
                    )
                );
                currencyrates.add(
                    new Currencyrate(
                        SupportedCurrency.USD.name(),
                        SupportedCurrency.USD.getLabel(),
                    rates.get(SupportedCurrency.UAH.toString()) / rates.get(SupportedCurrency.USD.toString())
                    )
                );
            }
        }

        /*if (currencyrates.isEmpty()) {
            currencyrates.addAll(getDefaultCurrencyRates());
        }*/

        return currencyrates;
    }

    public List<Currencyrate> getDefaultCurrencyRates() {
        List<Currencyrate> currencyrates = new ArrayList<>();
        currencyrates.add(new Currencyrate(
            SupportedCurrency.USD.name(), SupportedCurrency.USD.getLabel(),41.6814d
        ));
        currencyrates.add(new Currencyrate(
            SupportedCurrency.EUR.name(), SupportedCurrency.EUR.getLabel(), 43.4737d
        ));

        return currencyrates;
    }
}
