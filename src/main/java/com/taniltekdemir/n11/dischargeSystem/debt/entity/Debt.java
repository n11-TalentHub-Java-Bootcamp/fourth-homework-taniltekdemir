package com.taniltekdemir.n11.dischargeSystem.debt.entity;

import com.taniltekdemir.n11.dischargeSystem.debt.enums.EnumDebtType;
import com.taniltekdemir.n11.dischargeSystem.gen.entity.BaseEntity;
import com.taniltekdemir.n11.dischargeSystem.user.entity.User;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "DEBT")
@Data
public class Debt implements BaseEntity {

    @SequenceGenerator(name = "generator")
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "generator")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_DEBT_ID"))
    private User user;

    @Column(precision = 19, scale = 2, name = "principal_debt", updatable = false)
    private BigDecimal principalDebt;  //ANABORC

    @Column(name = "due_date")
    private LocalDate dueDate;  //VADE TARİHİ

    @Column(precision = 19, scale = 2, name = "amount_of_debt")
    private BigDecimal amountOfDebt;

    @Transient
    @Column(precision = 19, scale= 2, name = "late_fee_amount")
    private BigDecimal lateFeeAmount;

    @Column(name = "type_of_debt")
    private EnumDebtType typeOfDebt;

    @Column(name = "relational_debt_id")
    private Long relationalDebtId;  //İLİŞKİŞİ BORÇ -> Bunu GECİKME ZAMMINDA KULLANACAĞIZ

    @Column(name = "record_date")
    private LocalDate recordDate;   // İŞLEM TARİHİ

}
