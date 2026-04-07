package com.choonsik.korail.controller;

import com.choonsik.korail.config.auth.PrincipalDetails;
import com.choonsik.korail.entity.Seat;
import com.choonsik.korail.entity.Train;
import com.choonsik.korail.entity.User;
import com.choonsik.korail.service.PaymentService;
import com.choonsik.korail.service.SeatService;
import com.choonsik.korail.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PaymentController {

    @Autowired
    private TrainService trainService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payment")
    public String paymentPage(@RequestParam(value = "trainNumber", required = false, defaultValue = "") String trainNumber,
                              @RequestParam(value = "seatIds", required = false, defaultValue = "") String seatIds,
                              Model model) {

        Train train = trainService.findTrainByNumber(trainNumber);

        List<Long> seatIdList = new ArrayList<>();

        if (!seatIds.trim().isEmpty()) {
            seatIdList = Arrays.stream(seatIds.split(","))
                    .map(Long::parseLong)  // 각 요소를 Long 타입으로 변환
                    .collect(Collectors.toList());
        }

        // 선택된 좌석들 찾기
        List<Seat> selectedSeats = seatService.findSeatsByIds(seatIdList);

        // 모델에 속성 추가
        model.addAttribute("train", train);
        model.addAttribute("trainNumber", trainNumber);
        model.addAttribute("selectedSeats", selectedSeats);
        model.addAttribute("seatIds", seatIds);

        return "payment"; // payment.html로 이동
    }


    @PostMapping("/payment")
    public String processPayment(@RequestParam(value = "seatIds") String seatIds,
                                 @RequestParam(value = "paymentMethod") String paymentMethod,
                                 @RequestParam(value = "amount") String amount,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails,
                                 RedirectAttributes redirectAttributes) {

        List<Long> seatIdList = Arrays.stream(seatIds.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        User user = principalDetails.getUser();
        boolean paymentSuccess = paymentService.processPayment(seatIdList, paymentMethod, amount, user);

        if (paymentSuccess) {
            redirectAttributes.addFlashAttribute("paymentSuccess", true);
            return "redirect:/user";
        } else {
            redirectAttributes.addFlashAttribute("paymentError", "결제에 실패하였습니다. 다시 시도해주세요.");
            return "redirect:/payment";
        }
    }
}