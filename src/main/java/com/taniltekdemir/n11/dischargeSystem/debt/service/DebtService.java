package com.taniltekdemir.n11.dischargeSystem.debt.service;

import com.taniltekdemir.n11.dischargeSystem.debt.dto.DebtDto;
import com.taniltekdemir.n11.dischargeSystem.debt.dto.DebtSaveEntityDto;
import com.taniltekdemir.n11.dischargeSystem.debt.entity.Debt;
import com.taniltekdemir.n11.dischargeSystem.debt.mapper.DebtMapper;
import com.taniltekdemir.n11.dischargeSystem.debt.repository.DebtRepository;
import com.taniltekdemir.n11.dischargeSystem.debt.service.entityService.DebtEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
@Transactional
public class DebtService {

    private final DebtEntityService debtEntityService;

    private final DebtRepository debtRepository;

    public static Double lateFeeRate = 2.0;
    public static Double lateFeeRateNearDates = 1.5;


    public DebtDto save(DebtSaveEntityDto debtSaveEntityDto){

        Debt debt = DebtMapper.INSTANCE.convertDebtSaveEntityToDebt(debtSaveEntityDto);

        debt = debtEntityService.save(debt);

        return DebtMapper.INSTANCE.convertDebtToDebtDto(debt);
    }

    public List<DebtDto> findAllByDateRange(LocalDate firstDate, LocalDate lastDate){
        List<Debt> debtList = debtRepository.findAllByRecordDateBetween(firstDate, lastDate);
        List<DebtDto> returnList = new ArrayList<>();
        for(Debt debt : debtList){
            returnList.add(DebtMapper.INSTANCE.convertDebtToDebtDto(debt));
        }

        return returnList;
    }

    public List<DebtDto> findAllByUserId(Long userId){
        BigDecimal baseDebt = BigDecimal.ZERO;

        List<Debt> debtList = debtRepository.findAllByUser_IdAndAmountOfDebtGreaterThan(userId, baseDebt);
        setLateLeeAmount(debtList);

        return DebtMapper.INSTANCE.convertDebtListToDebtDtoList(debtList);
    }

    public List<DebtDto> findAllOverDueDebtByUserId(Long userId){
        BigDecimal baseDebt = BigDecimal.ZERO;

        List<Debt> debtList = debtRepository.findAllByUser_IdAndDueDateBeforeAndAmountOfDebtGreaterThan(userId, LocalDate.now(), baseDebt);
        setLateLeeAmount(debtList);

        return DebtMapper.INSTANCE.convertDebtListToDebtDtoList(debtList);
    }

    public BigDecimal findTotalDebtByUserId(Long userId){
        BigDecimal baseDebt = BigDecimal.ZERO;
        List<Debt> debtList = debtRepository.findAllByUser_IdAndAmountOfDebtGreaterThan(userId, baseDebt);
        setLateLeeAmount(debtList);
        List<BigDecimal> debtAmountList = debtList.stream().map(Debt::getAmountOfDebt).collect(Collectors.toList());
        List<BigDecimal> lateFeeAmountList = debtList.stream().filter(debt -> debt.getLateFeeAmount() != null).map(Debt::getLateFeeAmount).collect(Collectors.toList());
        debtAmountList.addAll(lateFeeAmountList);

        return debtAmountList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal findOverDueTotalDebtByUserId(Long userId){
        List<BigDecimal> debtList = debtRepository.findAllOverDueAmountOfDebt(userId, LocalDate.now());

        BigDecimal totalDebt;

        totalDebt = debtList.stream().collect(Collectors.reducing(BigDecimal.ZERO, BigDecimal::add));
        return totalDebt;
    }

    public BigDecimal findLateFeeTotalByUserId(Long userId){

        List<Debt> debtList = debtRepository.findAllByUser_IdAndDueDateBeforeAndAmountOfDebtGreaterThan(userId, LocalDate.now(), BigDecimal.ZERO);

        List<BigDecimal> lateFeeList = debtList.stream().map(this::calculateLateFee).collect(Collectors.toList());

        BigDecimal totalDebt;

        totalDebt = lateFeeList.stream().collect(Collectors.reducing(BigDecimal.ZERO, BigDecimal::add));
        return totalDebt;
    }

    public BigDecimal calculateLateFee(Debt debt) {
        double rate = lateFeeRate;
        long dayCount = 0L;
        BigDecimal dailyAmount = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        LocalDate thresholdDate = LocalDate.parse("2018-01-01");
        if (debt.getDueDate().isBefore(thresholdDate)) {
            rate = lateFeeRateNearDates;
        }
        dayCount = DAYS.between(debt.getDueDate(), LocalDate.now());
        dailyAmount = debt.getPrincipalDebt().multiply(BigDecimal.valueOf(rate)).divide(BigDecimal.valueOf(100L), 2, RoundingMode.CEILING);
        totalAmount = dailyAmount.multiply(BigDecimal.valueOf(dayCount));
        totalAmount = (totalAmount.compareTo(BigDecimal.valueOf(1)) < 0) ? BigDecimal.valueOf(1) : totalAmount;
        return totalAmount;
    }

    public Debt findFirstByUserIdAndDebtId(Long userId, Long debtId) {
        return debtRepository.findFirstByUser_IdAndId(userId, debtId);
    }

    public void updateDebtInfo(Long id) {
        debtRepository.updateDebtInfo(id, BigDecimal.ZERO);
    }

    public List<BigDecimal> findLateFeeDebtsAmountList(List<Long> debtIdList) {
        return debtRepository.findLateFeeDebts(debtIdList);
    }

    private void setLateLeeAmount(List<Debt> debtList) {
        for (Debt debt : debtList) {
            if(debt.getDueDate().isBefore(LocalDate.now())) {
                debt.setLateFeeAmount(calculateLateFee(debt));
            }
        }
    }
}