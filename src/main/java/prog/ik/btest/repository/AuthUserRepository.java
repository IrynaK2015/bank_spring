package prog.ik.btest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import prog.ik.btest.model.AuthUser;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long>
{
    @Query("SELECT u FROM AuthUser u where u.username = :username")
    AuthUser findByLogin(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM AuthUser u WHERE u.username = :username")
    boolean existsByLogin(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM AuthUser u WHERE u.email = :email")
    boolean existsByEmail(String email);
}
