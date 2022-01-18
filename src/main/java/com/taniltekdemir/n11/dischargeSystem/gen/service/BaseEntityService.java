package com.taniltekdemir.n11.dischargeSystem.gen.service;

import com.taniltekdemir.n11.dischargeSystem.gen.entity.BaseEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public abstract class BaseEntityService<E extends BaseEntity, D extends JpaRepository> {

    private D repository;

    public List<E> findAll(){
        return repository.findAll();
    }

    public Optional<E> findById(Long id){
        return repository.findById(id);
    }

    public E save(E e){
        return (E) repository.save(e);
    }

    public void delete(E e){
        repository.delete(e);
    }

    public D getRepository(){
        return repository;
    }
}
