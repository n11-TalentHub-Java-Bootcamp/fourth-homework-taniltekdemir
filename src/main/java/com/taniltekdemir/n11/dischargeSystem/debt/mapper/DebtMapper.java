package com.taniltekdemir.n11.dischargeSystem.debt.mapper;

import com.taniltekdemir.n11.dischargeSystem.debt.dto.DebtDto;
import com.taniltekdemir.n11.dischargeSystem.debt.dto.DebtSaveEntityDto;
import com.taniltekdemir.n11.dischargeSystem.debt.entity.Debt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DebtMapper {

    DebtMapper INSTANCE = Mappers.getMapper(DebtMapper.class);

    @Mapping(target = "userId", source = "user.id")
    DebtDto convertDebtToDebtDto(Debt debt);

    List<DebtDto> convertDebtListToDebtDtoList(List<Debt> debtList);

    @Mapping(target = "user.id", source = "userId")
    Debt convertDebtSaveEntityToDebt(DebtSaveEntityDto debtSaveEntityDto);

}
