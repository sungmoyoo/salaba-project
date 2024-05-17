package salaba.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import salaba.service.PaymentService;
import salaba.vo.Member;
import salaba.vo.Reservation;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private IamportClient iamportClient;

    @Value("${iamport.api.key}")
    private String apiKey;

    @Value("${iamport.api.secretkey}")
    private String secretKey;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, secretKey);
    }

    @GetMapping("/payment/getLoginUser")
    public ResponseEntity<?> returnLoginUser(HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 유저가 없습니다.");
        }
        return ResponseEntity.ok(loginUser);
    }

    @PostMapping("/reservation/payment/complete")
    @Transactional
    public ResponseEntity<String> paymentComplete(@RequestBody Reservation reservation) throws IOException {

        try {
            int reservationNo = paymentService.getReservationKey();
            reservation.setChatFileName("chat-" + reservationNo);
            paymentService.addReservation(reservation);
            File file = new File("src/main/resources/chat/log/" + reservation.getChatFileName() + ".json");
            file.createNewFile();
            reservation.getPayment().setReservationNo(reservation.getReservationNo());
            paymentService.addPayment(reservation.getPayment());
            return ResponseEntity.ok().body("{\"message\":\"success\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }

    }


    @PostMapping("/payment/validation/{imp_uid}")
    @ResponseBody
    public IamportResponse<com.siot.IamportRestClient.response.Payment> validateIamport(@PathVariable String imp_uid) {
        IamportResponse<com.siot.IamportRestClient.response.Payment> payment = iamportClient.paymentByImpUid(imp_uid);
        return payment;
    }
}
