package com.choonsik.korail.controller;

import com.choonsik.korail.dto.TrainDto;
import com.choonsik.korail.entity.Train;
import com.choonsik.korail.service.TrainService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class TrainController {

    private final TrainService trainService;

    @GetMapping("/list")
    public String trainList(Model model) {
        // 서비스에서 trainList 데이터를 가져옴 (예시로 service.getTrainList() 사용)
        List<Train> trains = trainService.getTrainList();

        // 모델에 데이터를 추가
        model.addAttribute("trainList", trains);

        return "trainList";
    }

    @GetMapping("/trainForm")
    public String train() {
        return "trainForm";
    }

    @PostMapping("/trainForm")
    public String trainCreate(@Valid TrainDto trainDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            return "trainForm";
        }
        trainService.createTrain(trainDto);
        return "redirect:/admin/list";
    }

    @GetMapping("/search")
    public List<TrainDto> getTrains(@RequestParam String departureStation,
                                    @RequestParam String arrivalStation){
        return trainService.getTrainByStations(departureStation, arrivalStation);
    }
}
