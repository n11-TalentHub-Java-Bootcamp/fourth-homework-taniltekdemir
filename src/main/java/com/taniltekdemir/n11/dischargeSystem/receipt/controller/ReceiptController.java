package com.taniltekdemir.n11.dischargeSystem.receipt.controller;

import com.taniltekdemir.n11.dischargeSystem.receipt.dto.ReceiptDto;
import com.taniltekdemir.n11.dischargeSystem.receipt.service.ReceiptService;
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
@RequestMapping("/api/v1/receipts")
@CrossOrigin
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;

    private final UserEntityService userService;

    /**Tahsilat yapan*/
    @PostMapping
    public ResponseEntity<?> receiptProcess(@RequestParam("userId") Long userId,
                                            @RequestParam("debtId") Long debtId){

        try {
            receiptService.receiptProcess(userId, debtId);
            return ResponseEntity.ok("Tahsilat yapıldı");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Tahsilat sırasında hata alındı");
        }
    }

    /**Belirtilen tarihler arasında yapılan tahsilatlar listeleyebilen*/
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllByDateRange(@RequestParam String firstDates,
                                               @RequestParam String lastDates){

        try {
            LocalDate firstDate = LocalDate.parse(firstDates);
            LocalDate lastDate = LocalDate.parse(lastDates);

            if(firstDate.isAfter(lastDate)) {
                return ResponseEntity.badRequest().body("Belirtilen tarih aralığı hatalıdır. Lütfen kontrol ediniz");
            }
            List<ReceiptDto> receiptDtos = receiptService.findAllByDateRange(firstDate, lastDate);

            return ResponseEntity.ok(receiptDtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("İşlem gerçekleştirilemedi");
        }
    }

    /**Kullanıcının tüm tahsilatları listeleyebilen*/
    @GetMapping("/getAllByUserId")
    public ResponseEntity<?> getAllByUserId(@RequestParam Long userId){
        try {
            Optional<User> user = userService.findById(userId);
            if (!user.isPresent()) {
                return ResponseEntity.badRequest().body("Kullanıcı bulunamadı. Lütfen kontrol ediniz");
            }

            List<ReceiptDto> receiptDtos = receiptService.findAllByUserId(userId);

            return ResponseEntity.ok(receiptDtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("İşlem gerçekleştirilemedi");
        }
    }

    /**Kullanıcının ödediği toplam gecikme zamını dönen*/
    @GetMapping("/getTotalLateFeeAmountByUserId")
    public ResponseEntity<?> getTotalLateFeeAmountByUserId(@RequestParam Long userId){
        try {
            Optional<User> user = userService.findById(userId);
            if (!user.isPresent()) {
                return ResponseEntity.badRequest().body("Kullanıcı bulunamadı. Lütfen kontrol ediniz");
            }
            BigDecimal total = receiptService.findLateFeeTotalByUserId(userId);
            return ResponseEntity.ok(total);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("İşlem gerçekleştirilemedi");
        }
    }
}
