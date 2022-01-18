package com.taniltekdemir.n11.dischargeSystem.user.service.entityService;

import com.taniltekdemir.n11.dischargeSystem.gen.service.BaseEntityService;
import com.taniltekdemir.n11.dischargeSystem.user.entity.User;
import com.taniltekdemir.n11.dischargeSystem.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserEntityService  extends BaseEntityService<User, UserRepository> {

    public UserEntityService(UserRepository repository) {
        super(repository);
    }
}
