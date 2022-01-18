package com.taniltekdemir.n11.dischargeSystem.receipt.service.entityService;

import com.taniltekdemir.n11.dischargeSystem.receipt.entity.Receipt;
import com.taniltekdemir.n11.dischargeSystem.gen.service.BaseEntityService;
import com.taniltekdemir.n11.dischargeSystem.receipt.repository.ReceiptRepository;
import org.springframework.stereotype.Service;

@Service
public class ReceiptEntityService extends BaseEntityService<Receipt, ReceiptRepository> {

    public ReceiptEntityService(ReceiptRepository repository) {
        super(repository);
    }
}
