package com.taniltekdemir.n11.dischargeSystem.receipt.service;

import com.taniltekdemir.n11.dischargeSystem.debt.dto.DebtDto;
import com.taniltekdemir.n11.dischargeSystem.debt.dto.DebtSaveEntityDto;
import com.taniltekdemir.n11.dischargeSystem.debt.entity.Debt;
import com.taniltekdemir.n11.dischargeSystem.debt.enums.EnumDebtType;
import com.taniltekdemir.n11.dischargeSystem.debt.repository.DebtRepository;
import com.taniltekdemir.n11.dischargeSystem.debt.service.DebtService;
import com.taniltekdemir.n11.dischargeSystem.receipt.dto.ReceiptDto;
import com.taniltekdemir.n11.dischargeSystem.receipt.dto.ReceiptSaveEntityDto;
import com.taniltekdemir.n11.dischargeSystem.receipt.entity.Receipt;
import com.taniltekdemir.n11.dischargeSystem.receipt.mapper.ReceiptMapper;
import com.taniltekdemir.n11.dischargeSystem.receipt.repository.ReceiptRepository;
import com.taniltekdemir.n11.dischargeSystem.receipt.service.entityService.ReceiptEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReceiptService {

    private final ReceiptRepository receiptRepository;

    private final ReceiptEntityService receiptEntityService;

    private final DebtService debtService;

    private final DebtRepository debtRepository;


    public void receiptProcess(Long userId, Long debtId) {

        Long id;
        Debt debt = debtService.findFirstByUserIdAndDebtId(userId, debtId);
        if (debt == null) {
            throw new RuntimeException("Kullancıya ait borç bilgisi bulunamadı");
        }
        if (debt.getDueDate().isBefore(LocalDate.now())) {
            BigDecimal lateFeeAmount = debtService.calculateLateFee(debt);
            DebtSaveEntityDto saveEntityDto = createLateFeeDebt(debt.getId(), debt.getUser().getId(), lateFeeAmount);

            DebtDto lateFeeDebt = debtService.save(saveEntityDto);
            id = lateFeeDebt.getId();
            createReceipt(id, debt.getUser().getId());
        }
        id = debt.getId();
        createReceipt(id, debt.getUser().getId());
        debtService.updateDebtInfo(debt.getId());
    }

    public List<ReceiptDto> findAllByDateRange(LocalDate firstDate, LocalDate lastDate) {
        List<Receipt> receiptList = receiptRepository.findAllByRecordDateBetween(firstDate, lastDate);

        return ReceiptMapper.INSTANCE.convertReceiptListToReceiptDtoList(receiptList);
    }

    public List<ReceiptDto> findAllByUserId(Long userId) {

        List<Receipt> receiptList = receiptRepository.findAllByUser_Id(userId);

        return ReceiptMapper.INSTANCE.convertReceiptListToReceiptDtoList(receiptList);
    }

    public BigDecimal findLateFeeTotalByUserId(Long userId) {

        List<Long> debtIdList = receiptRepository.findAllDebtIdByUserId(userId);
        List<BigDecimal> lateFeeDebtAmountList = debtService.findLateFeeDebtsAmountList(debtIdList);
        return lateFeeDebtAmountList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private DebtSaveEntityDto createLateFeeDebt(Long id, Long userId, BigDecimal lateFeeAmount) {
        DebtSaveEntityDto saveEntityDto = new DebtSaveEntityDto();
        saveEntityDto.setUserId(userId);
        saveEntityDto.setPrincipalDebt(lateFeeAmount); //gecikme bedeli
        saveEntityDto.setDueDate(null);
        saveEntityDto.setAmountOfDebt(BigDecimal.ZERO);
        saveEntityDto.setTypeOfDebt(EnumDebtType.LATE_FEE);
        saveEntityDto.setRelationalDebtId(id);
        saveEntityDto.setRecordDate(LocalDate.now());
        return saveEntityDto;
    }

    private void createReceipt(Long debtId, Long userId) {
        ReceiptSaveEntityDto saveEntityDto = new ReceiptSaveEntityDto();
        saveEntityDto.setUserId(userId);
        saveEntityDto.setDebtId(debtId);
        saveEntityDto.setRecordDate(LocalDate.now());
        Receipt lateFee = ReceiptMapper.INSTANCE.convertReceiptSaveEntityToReceipt(saveEntityDto);
        receiptEntityService.save(lateFee);
    }
}
