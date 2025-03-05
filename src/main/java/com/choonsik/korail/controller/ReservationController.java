package com.choonsik.korail.controller;

import com.choonsik.korail.entity.Train;
import com.choonsik.korail.service.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final TrainService trainService;

    @GetMapping("/reservation")
    public String reservation(Model model) {
        List<Train> trains = trainService.getTrainList(); // 기차 목록을 가져옴
        model.addAttribute("trains", trains); // 모델에 기차 목록 추가
        return "reservation";
    }
}
