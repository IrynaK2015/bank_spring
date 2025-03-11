package prog.ik.btest.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prog.ik.btest.model.Currencyrate;
import prog.ik.btest.repository.CurrencyrateRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyrateService {

    private final CurrencyrateRepository currencyrateRepository;

    public CurrencyrateService(CurrencyrateRepository currencyrateRepository) {
        this.currencyrateRepository = currencyrateRepository;
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
}
