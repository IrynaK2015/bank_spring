package prog.ik.btest.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prog.ik.btest.model.Client;
import prog.ik.btest.repository.AccountRepository;
import prog.ik.btest.repository.ClientRepository;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    public ClientService(ClientRepository clientRepository,
                         AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }


    @Transactional
    public void addClient(Client client) {
        clientRepository.save(client);
        accountRepository.save(client.getAccount("UAH"));
    }

    @Transactional
    public void updateClient(Client client) {
        clientRepository.save(client);
    }

    @Transactional
    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }

    @Transactional(readOnly=true)
    public Client findClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly=true)
    public Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Transactional(readOnly=true)
    public List<Client> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly = true)
    public long count() {
        return clientRepository.count();
    }
}
