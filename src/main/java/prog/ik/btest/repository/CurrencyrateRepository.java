package prog.ik.btest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import prog.ik.btest.model.Currencyrate;

@Repository
public interface CurrencyrateRepository  extends JpaRepository<Currencyrate, Long> {

    @Query("SELECT c FROM Currencyrate c WHERE c.code = :code")
    Currencyrate findByCode(@Param("code") String code);
}
