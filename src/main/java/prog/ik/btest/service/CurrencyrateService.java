package prog.ik.btest.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prog.ik.btest.model.Currencyrate;
import prog.ik.btest.model.SupportedCurrency;
import prog.ik.btest.repository.CurrencyrateRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CurrencyrateService {

    private final CurrencyrateRepository currencyrateRepository;

    public CurrencyrateService(CurrencyrateRepository currencyrateRepository) {
        this.currencyrateRepository = currencyrateRepository;
    }

    @Transactional
    public void addBaseCurrencyrate() {
        if (Objects.isNull(findByCode(SupportedCurrency.UAH.name()))) {
            addCurrencyrate(
                new Currencyrate(SupportedCurrency.UAH.name(), SupportedCurrency.UAH.getLabel(), 1)
            );
        }
    }

    @Transactional
    public void addCurrencyrate(Currencyrate currencyrate) {
        currencyrateRepository.save(currencyrate);
    }

    @Transactional
    public void editCurrencyrate(Currencyrate currencyrate) {
        currencyrateRepository.save(currencyrate);
    }

    @Transactional(readOnly=true)
    public Currencyrate findByCode(String code) {
        return currencyrateRepository.findByCode(code);
    }

    @Transactional(readOnly=true)
    public Currencyrate findById(Long id) {
        return currencyrateRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteCurrencyrateById(Long id) {
        currencyrateRepository.deleteById(id);
    }

    @Transactional(readOnly=true)
    public List<Currencyrate> findAll() {
        return currencyrateRepository.findAll();
    }


    @Transactional(readOnly=true)
    public long count() {
        return currencyrateRepository.count();
    }

    public List<Currencyrate> findUnused(List<String> codes) {
        List<Currencyrate> available = currencyrateRepository.findAll();
        List<Currencyrate> unused = available.stream()
                .filter(currencyrate -> !codes.contains(currencyrate.getCode()))
                .collect(Collectors.toList());
        return unused;
    }

    public List<Currencyrate> getDefaultCurrencyrates() {
        List<Currencyrate> currencyrates = new ArrayList<>();
        currencyrates.add(new Currencyrate(
                SupportedCurrency.USD.name(), SupportedCurrency.USD.getLabel(),41.6814d
        ));
        currencyrates.add(new Currencyrate(
                SupportedCurrency.EUR.name(), SupportedCurrency.EUR.getLabel(), 43.4737d
        ));

        return currencyrates;
    }

    @Transactional
    public void modifyCurrencyRates(List<Currencyrate> currencyrates) {
        for (Currencyrate rate : currencyrates) {
            Currencyrate found = findByCode(rate.getCode());
            if (Objects.isNull(found)) {
                addCurrencyrate(rate);
            } else if (!rate.getCode().equals(SupportedCurrency.UAH.name())) {
                found.setRate(rate.getRate());
                editCurrencyrate(found);
            }
        }
    }
}
