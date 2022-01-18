package com.taniltekdemir.n11.dischargeSystem.receipt.mapper;

import com.taniltekdemir.n11.dischargeSystem.receipt.dto.ReceiptDto;
import com.taniltekdemir.n11.dischargeSystem.receipt.dto.ReceiptSaveEntityDto;
import com.taniltekdemir.n11.dischargeSystem.receipt.entity.Receipt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReceiptMapper {

    ReceiptMapper INSTANCE = Mappers.getMapper(ReceiptMapper.class);

    ReceiptDto convertReceiptToREceiptDto(Receipt receipt);

    List<ReceiptDto> convertReceiptListToReceiptDtoList(List<Receipt> receiptList);

    @Mappings({
            @Mapping(target = "user.id", source = "userId"),
            @Mapping(target = "debt.id", source = "debtId"),
    })
    Receipt convertReceiptSaveEntityToReceipt(ReceiptSaveEntityDto receiptSaveEntityDto);
}
