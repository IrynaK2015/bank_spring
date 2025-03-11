package prog.ik.btest.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import prog.ik.btest.model.Account;
import prog.ik.btest.model.Client;
import prog.ik.btest.model.Currencyrate;
import prog.ik.btest.service.ClientService;
import prog.ik.btest.service.CurrencyrateService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class ClientController {

    static final int ITEMS_PER_PAGE = 10;
    final private ClientService clientService;
    final private CurrencyrateService currencyrateService;

    public ClientController(ClientService clientService,
                            CurrencyrateService currencyrateService) {
        this.clientService = clientService;
        this.currencyrateService = currencyrateService;
    }

    @GetMapping("/client/new")
    public String clientAddPage(Model model) {
        return "client_new";
    }

    @PostMapping(value="/client/add")
    public String clientAdd(@RequestParam String firstname,
                            @RequestParam String lastname,
                            @RequestParam Long taxnumber,
                            @RequestParam String email,
                            @RequestParam String address,
                            Model model) {
        if (Objects.nonNull(clientService.findClientByEmail(email))) {
            model.addAttribute("error", "This email already exists");

            return "client_new";
        }
        Client client = new Client(firstname, lastname, address, email, taxnumber);
        client.addAccount(new Account(client, currencyrateService.findByCode("UAH")));
        clientService.addClient(client);

        return "redirect:/client/" + client.getId();
    }

    @GetMapping("/client/{id}")
    public String clientEdit(@PathVariable(value = "id") long clientId,
                             @RequestParam(required = false, defaultValue = "0") Integer id,
                             Model model) {
        Client client = clientService.findClientById(clientId);
        if (Objects.isNull(client)) {
            model.addAttribute("error", "Client not found");

            return "client_new";
        }
        model.addAttribute("client", client);
        List<String> used = new ArrayList<>();
        for(Account account : client.getAccounts()) {
            used.add(account.getCurrency().getCode());
        }
        model.addAttribute("unusedCurrencies", currencyrateService.findUnused(used));

        return "client_edit";
    }

    @PostMapping(value="/client/save")
    public String clientSave(@RequestParam Long id,
                            @RequestParam String firstname,
                            @RequestParam String lastname,
                            @RequestParam Long taxnumber,
                            @RequestParam String email,
                            @RequestParam String address,
                            Model model) {
        Client client = clientService.findClientById(id);
        if (Objects.isNull(client)) {
            model.addAttribute("error", "Client not found");

            return "client_new";
        }
        if (!email.equals(client.getEmail()) && Objects.nonNull(clientService.findClientByEmail(email))) {
            model.addAttribute("error", "This email already exists");

            return "client_new";
        }

        if (!email.equals(client.getEmail()))           client.setEmail(email);
        if (!firstname.equals(client.getFirstName()))   client.setFirstName(firstname);
        if (!lastname.equals(client.getLastName()))     client.setLastName(lastname);
        if (!taxnumber.equals(client.getTaxNumber()))   client.setTaxNumber(taxnumber);
        if (!address.equals(client.getAddress()))       client.setAddress(address);
        clientService.updateClient(client);

        return "redirect:/client/" + client.getId();
    }

    @GetMapping("/clients")
    public String clientSearchPage(Model model,
                                   @RequestParam(required = false, defaultValue = "0") Integer page) {
        if (page < 0) page = 0;
        List<Client> clients = clientService.findAll(PageRequest.of(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));
        model.addAttribute("clients", clients);
        model.addAttribute("allPages", getPageCount());
        model.addAttribute("total", clientService.count());

        return "client_list";
    }

    @GetMapping("/client/{id}/account/new/{currencyId}")
    public String createClientAccount(@PathVariable(value = "id") long clientId,
                                      @PathVariable(value = "currencyId") long currencyId,
                                      Model model) {
        Client client = clientService.findClientById(clientId);
        if (Objects.isNull(client)) {
            model.addAttribute("error", "Client not found");

            return "client_new";
        }
        Currencyrate currency = currencyrateService.findById(currencyId);
        if (Objects.isNull(currency)) {
            model.addAttribute("error", "Currency not found");

            return "redirect:/client/" + client.getId();
        }
        if (client.isAccountExisting(currency.getCode())) {
            model.addAttribute("error", "This account already exists");

            return "redirect:/client/" + client.getId();
        }

        client.addAccount(new Account(client, currency));
        clientService.updateClient(client);

        return "redirect:/client/" + client.getId();
    }

    private long getPageCount() {
        long totalCount = clientService.count();
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }
}