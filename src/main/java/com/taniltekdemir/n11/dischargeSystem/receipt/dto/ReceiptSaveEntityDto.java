package com.taniltekdemir.n11.dischargeSystem.receipt.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReceiptSaveEntityDto {

    private Long userId;
    private Long debtId;
    private LocalDate recordDate;
}
