package com.taniltekdemir.n11.dischargeSystem.debt.repository;

import com.taniltekdemir.n11.dischargeSystem.debt.entity.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {

    List<Debt> findAllByRecordDateBetween(LocalDate firstDate, LocalDate lastDate);

    List<Debt> findAllByUser_IdAndAmountOfDebtGreaterThan(Long userId, BigDecimal baseDebt);

    @Query("SELECT d.amountOfDebt FROM Debt d WHERE d.user.id = :userId ")
    List<BigDecimal> findAllAmountOfDebt(@Param("userId") Long userId);

    List<Debt> findAllByUser_IdAndDueDateBeforeAndAmountOfDebtGreaterThan(Long userId, LocalDate nowDate, BigDecimal baseDebt);

    @Query("SELECT d.amountOfDebt FROM Debt d WHERE d.user.id = :userId AND d.dueDate < :nowDate")
    List<BigDecimal> findAllOverDueAmountOfDebt(@Param("userId") Long userId, @Param("nowDate") LocalDate nowDate);

    Debt findFirstByUser_IdAndId(Long userId, Long deptId);

    @Modifying
    @Query("UPDATE Debt d SET d.amountOfDebt = :newValue WHERE d.id = :id")
    void updateDebtInfo(@Param("id") Long id, @Param("newValue") BigDecimal newValue);

    @Query("SELECT d.principalDebt FROM Debt d WHERE d.id IN :debtIdList AND d.typeOfDebt = 1")
    List<BigDecimal> findLateFeeDebts(@Param("debtIdList") List<Long> debtIdList);
}
