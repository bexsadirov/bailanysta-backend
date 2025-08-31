package kz.bailanysta.main.module.auth.confirmation_code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

/**
 * @author Beksultan
 */
@Repository
interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Integer> {


    ConfirmationCode findByDestination(String destination);

    @Transactional
    @Modifying
    @Query("DELETE FROM ConfirmationCode cc WHERE cc.lastSentTime < :expirationTime")
    void deleteExpired(LocalDateTime expirationTime);
}
