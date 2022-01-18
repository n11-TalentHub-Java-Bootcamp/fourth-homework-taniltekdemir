package com.taniltekdemir.n11.dischargeSystem.receipt.entity;

import com.taniltekdemir.n11.dischargeSystem.debt.entity.Debt;
import com.taniltekdemir.n11.dischargeSystem.gen.entity.BaseEntity;
import com.taniltekdemir.n11.dischargeSystem.user.entity.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "RECEIPT")
@Data
public class Receipt implements BaseEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_RECEIPT_ID"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "debt_id", foreignKey = @ForeignKey(name = "FK_DEBT_RECEIPT_ID"))
    private Debt debt;

    @Column(name = "record_date")
    private LocalDate recordDate;   // TAHSİLAT TARİHİ
}
