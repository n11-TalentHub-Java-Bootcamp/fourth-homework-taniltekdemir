package com.taniltekdemir.n11.dischargeSystem.debt.controller;

import com.taniltekdemir.n11.dischargeSystem.debt.dto.DebtDto;
import com.taniltekdemir.n11.dischargeSystem.debt.dto.DebtSaveEntityDto;
import com.taniltekdemir.n11.dischargeSystem.debt.service.DebtService;
import com.taniltekdemir.n11.dischargeSystem.user.entity.User;
import com.taniltekdemir.n11.dischargeSystem.user.service.entityService.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/debts")
@CrossOrigin
@RequiredArgsConstructor
public class DebtController {

    private final DebtService debtService;

    private final UserEntityService userService;

/** Borç kaydeden*/
    @PostMapping
    public ResponseEntity<?> save(@RequestBody DebtSaveEntityDto debtSaveEntityDto){

        DebtDto debtDto = debtService.save(debtSaveEntityDto);

        return ResponseEntity.ok(debtDto);
    }

    /** Belirtilen tarihler arasında oluşturulan borçlar listeleyen*/
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllByDateRange(@RequestParam String firstDates,
                                               @RequestParam String lastDates){

        LocalDate firstDate = LocalDate.parse(firstDates);
        LocalDate lastDate = LocalDate.parse(lastDates);

        if(firstDate.isAfter(lastDate)) {
            return ResponseEntity.badRequest().body("Belirtilen tarih aralığı hatalıdır. Lütfen kontrol ediniz");
        }
        List<DebtDto> debtDtos = debtService.findAllByDateRange(firstDate, lastDate);

        return ResponseEntity.ok(debtDtos);
    }

    /** Bir kullanıcının tüm borçları listeleyen*/
    @GetMapping("/getAllByUserId")
    public ResponseEntity<?> getAllByUserId(@RequestParam Long userId){

        Optional<User> user = userService.findById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body("Kullanıcı bulunamadı. Lütfen kontrol ediniz");
        }

        List<DebtDto> debtDtos = debtService.findAllByUserId(userId);

        return ResponseEntity.ok(debtDtos);
    }

    /** Bir kullanıcının vadesi geçmiş borçları listeleyen*/
    @GetMapping("/getAllOverDueByUserId")
    public ResponseEntity<?> getAllOverDueByUserId(@RequestParam Long userId){

        Optional<User> user = userService.findById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body("Kullanıcı bulunamadı. Lütfen kontrol ediniz");
        }

        List<DebtDto> debtDtos = debtService.findAllOverDueDebtByUserId(userId);

        return ResponseEntity.ok(debtDtos);
    }

    /** Bir kullanıcının toplam borç tutarını dönen*/
    @GetMapping("/getTotalDebtByUserId")
    public ResponseEntity<?> getTotalDebtByUserId(@RequestParam Long userId){

        Optional<User> user = userService.findById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body("Kullanıcı bulunamadı. Lütfen kontrol ediniz");
        }

        BigDecimal totalDebt = debtService.findTotalDebtByUserId(userId);

        return ResponseEntity.ok(totalDebt);
    }


    /** Bir kullanıcının vadesi geçmiş toplam borç tutarını dönen*/
    @GetMapping("/getOverDueTotalByUserId")
    public ResponseEntity<?> getOverDueTotalByUserId(@RequestParam Long userId){

        Optional<User> user = userService.findById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body("Kullanıcı bulunamadı. Lütfen kontrol ediniz");
        }

        BigDecimal totalDebt = debtService.findOverDueTotalDebtByUserId(userId);

        return ResponseEntity.ok(totalDebt);
    }


    /** Bir kullanıcının anlık gecikme zammı tutarını dönen*/
    @GetMapping("/getLateFeeTotalDebtByUserId")
    public ResponseEntity<?> getLateFeeTotalDebtByUserId(@RequestParam Long userId){

        Optional<User> user = userService.findById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body("Kullanıcı bulunamadı. Lütfen kontrol ediniz");
        }

        BigDecimal totalDebt = debtService.findLateFeeTotalByUserId(userId);

        return ResponseEntity.ok(totalDebt);
    }
}
