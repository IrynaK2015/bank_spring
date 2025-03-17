package prog.ik.btest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import prog.ik.btest.model.Account;
import prog.ik.btest.model.Transaction;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.account = :account and t.created BETWEEN :from and :to ORDER BY t.id DESC")
    public List<Transaction> findByDateRange(@Param("account") Account account, @Param("from") Date dateFrom, @Param("to") Date dateTo);
}
