package com.choonsik.korail.controller;

import com.choonsik.korail.entity.Seat;
import com.choonsik.korail.entity.Train;
import com.choonsik.korail.repository.TrainRepository;
import com.choonsik.korail.service.SeatService;
import com.choonsik.korail.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/seat-selection")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @Autowired
    private TrainService trainService;

    @Autowired
    private TrainRepository trainRepository;

    // 좌석 선택 페이지로 이동 (기차 번호를 파라미터로 전달)
    @GetMapping
    public String showSeatSelectionPage(@RequestParam("trainId") Long trainId, Model model) {
        Train train = trainService.getTrainById(trainId);
        List<Seat> availableSeats = seatService.getAvailableSeats(trainId);
        model.addAttribute("train", train);
        model.addAttribute("trainId", trainId);
        model.addAttribute("availableSeats", availableSeats);
        return "seat-selection"; // seat-selection 페이지로 이동
    }

    @PostMapping("/reserve")
    public String reserveSeats(@RequestParam("trainId") Long trainId,
                               @RequestParam List<Long> selectedSeatIds,
                               RedirectAttributes redirectAttributes) {
        boolean reservationSuccess = seatService.reserveSeats(trainId, selectedSeatIds);

        if (reservationSuccess) {
            redirectAttributes.addAttribute("message", "예약이 완료되었습니다.");
        } else {
            redirectAttributes.addAttribute("message", "예약에 실패하였습니다. 다시 시도해주세요.");
        }

        Train train = trainRepository.findById(trainId).orElse(null);
        if (train != null) {
            redirectAttributes.addAttribute("trainNumber", train.getTrainNumber()); // URL 파라미터로 trainNumber 전달
        }

        redirectAttributes.addAttribute("seatIds", selectedSeatIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")));

        return "redirect:/payment"; // 결제 페이지로 리다이렉트
    }


}