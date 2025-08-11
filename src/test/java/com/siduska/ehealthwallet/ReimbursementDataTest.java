package com.siduska.ehealthwallet;

import com.siduska.ehealthwallet.entitiy.Reimbursement;
import com.siduska.ehealthwallet.entitiy.StatusEnum;
import com.siduska.ehealthwallet.repository.ReimbursementRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReimbursementDataTest {

    @Autowired
    private ReimbursementRepository reimbursementRepository;

    @DisplayName("JUnit Data test for createReimbursement")
    @Test
    @Order(1)
    @Rollback(value = false)
    void createReimbursementTest() {
        Reimbursement reimbursement = Reimbursement.builder()
                .status(StatusEnum.PENDING)
                .patientName("John")
                .identificationNumber("1234567890")
                .cost(BigDecimal.valueOf(100)).build();
        reimbursementRepository.save(reimbursement);
        Assertions.assertThat(reimbursement.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit Data test for getReimbursementById")
    @Test
    @Order(2)
    public void getReimbursementById(){
        Reimbursement reimbursement = reimbursementRepository.findById(1L).get();
        Assertions.assertThat(reimbursement.getId()).isEqualTo(1L);
    }

    @DisplayName("JUnit Data test for getListOfReimbursements")
    @Test
    @Order(3)
    public void getListOfReimbursementsTest(){
        List<Reimbursement> reimbursements = reimbursementRepository.findAll();
        Assertions.assertThat(reimbursements.size()).isGreaterThan(0);
    }

    @DisplayName("JUnit Data test for updateReimbursement")
    @Test
    @Order(5)
    @Rollback(value = false)
    public void updateReimbursementTest(){
        Reimbursement reimbursement = reimbursementRepository.findById(1L).get();
        Assertions.assertThat(reimbursement).isNotNull();
        reimbursement.setIdentificationNumber("00000111");
        Reimbursement reimbursementUpdated =  reimbursementRepository.save(reimbursement);
        Assertions.assertThat(reimbursementUpdated.getIdentificationNumber()).isEqualTo("00000111");
    }

    @DisplayName("JUnit Data test for deleteReimbursement")
    @Test
    @Order(6)
    @Rollback(value = false)
    public void deleteReimbursementTest(){
        Reimbursement reimbursement = reimbursementRepository.findById(1L).get();
        reimbursementRepository.delete(reimbursement);
        Reimbursement reimbursementTest = null;
        Optional<Reimbursement> reimbursementTestRef = reimbursementRepository.findByIdentificationNumber( "00000111" );
        if (reimbursementTestRef.isPresent()) {
            reimbursementTest = reimbursementTestRef.get();
        }
        Assertions.assertThat(reimbursementTest).isNull();
    }
}
