package com.taniltekdemir.n11.dischargeSystem.debt.dto;

import com.taniltekdemir.n11.dischargeSystem.debt.enums.EnumDebtType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
public class DebtDto {

    private Long id;
    private Long userId;
    private BigDecimal principalDebt;
    private Date dueDate;
    private BigDecimal amountOfDebt;
    private EnumDebtType typeOfDebt;
    private Long relationalDebtId;
    private BigDecimal lateFeeAmount;
    private LocalDate recordDate;
}
