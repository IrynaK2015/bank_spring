package prog.ik.btest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import prog.ik.btest.model.Currencyrate;
import prog.ik.btest.service.CurrencyrateService;

import java.util.Objects;

@Controller
public class CurrencyrateController
{
    private final CurrencyrateService currencyrateService;

    public CurrencyrateController(CurrencyrateService currencyrateService) {
        this.currencyrateService = currencyrateService;
    }

    @GetMapping("/currencies")
    public String onIndex(Model model) {
        if (Objects.isNull(currencyrateService.findByCode("UAH"))) {
            currencyrateService.addCurrencyrate(
                    new Currencyrate("UAH", "hryvnja", 1)
            );
        }
        if (Objects.isNull(currencyrateService.findByCode("USD"))) {
            currencyrateService.addCurrencyrate(
                    new Currencyrate("USD", "USA dollar", 41.6814d)
            );
        }
        if (Objects.isNull(currencyrateService.findByCode("EUR"))) {
            currencyrateService.addCurrencyrate(
                    new Currencyrate("EUR", "EURO", 43.4737d)
            );
        }
        model.addAttribute("currencies", currencyrateService.findAll());
        model.addAttribute("total", currencyrateService.count());

        return "currencies";
    }
}
