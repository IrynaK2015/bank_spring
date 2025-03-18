package prog.ik.btest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import prog.ik.btest.model.Currencyrate;
import prog.ik.btest.model.SupportedCurrency;
import prog.ik.btest.service.CurrencyrateService;
import prog.ik.btest.service.RateRetriever;
import prog.ik.btest.service.Utility;

import java.util.List;
import java.util.Objects;

@Controller
public class CurrencyrateController
{
    private final CurrencyrateService currencyrateService;

    private final RateRetriever rateRetriever;

    public CurrencyrateController(CurrencyrateService currencyrateService, RateRetriever rateRetrieve) {
        this.currencyrateService = currencyrateService;
        this.rateRetriever = rateRetrieve;
    }

    @GetMapping("/currencies")
    public String onIndex(Model model) {
        currencyrateService.addBaseCurrencyrate();

        List<Currencyrate> currencyrates = rateRetriever.getImportedCurrencyRates();
        boolean isUpdated = currencyrates.isEmpty();
        if (currencyrates.isEmpty() && currencyrateService.count() == 1)
            currencyrates.addAll(currencyrateService.getDefaultCurrencyrates());

        currencyrateService.modifyCurrencyRates(currencyrates);

        model.addAttribute("currencies", currencyrateService.findAll());
        model.addAttribute("total", currencyrateService.count());
        model.addAttribute("updated", isUpdated);

        return "currencies";
    }


    @ModelAttribute("username")
    public String getUsername() {
        return Utility.getCurrentUser().getUsername();
    }
}
