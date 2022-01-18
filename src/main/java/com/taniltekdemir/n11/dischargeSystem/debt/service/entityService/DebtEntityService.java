package com.taniltekdemir.n11.dischargeSystem.debt.service.entityService;

import com.taniltekdemir.n11.dischargeSystem.debt.entity.Debt;
import com.taniltekdemir.n11.dischargeSystem.debt.repository.DebtRepository;
import com.taniltekdemir.n11.dischargeSystem.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;

@Service
public class DebtEntityService extends BaseEntityService<Debt, DebtRepository> {

    public DebtEntityService(DebtRepository repository) {
        super(repository);
    }
}
