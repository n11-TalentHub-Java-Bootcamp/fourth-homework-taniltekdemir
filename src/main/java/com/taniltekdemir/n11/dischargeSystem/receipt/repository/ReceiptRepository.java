package com.taniltekdemir.n11.dischargeSystem.receipt.repository;

import com.taniltekdemir.n11.dischargeSystem.receipt.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    List<Receipt> findAllByRecordDateBetween(LocalDate firstDate, LocalDate lastDate);

    List<Receipt> findAllByUser_Id(Long userId);

    @Query("SELECT r.debt.id FROM Receipt r WHERE r.user.id = :userId ")
    List<Long> findAllDebtIdByUserId(@Param("userId") Long userId);
}
