package com.dp.billapp.controller;

import com.dp.billapp.model.Goldrate;
import com.dp.billapp.model.Showroom;
import com.dp.billapp.service.GoldrateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value="/goldrate")
public class GoldrateController {

    GoldrateService goldrateService;
    @PostMapping("/save")
    public ResponseEntity<?> saveGoldRate(@RequestBody Goldrate goldrate){
        log.info("#  showroom - {}", goldrate);
        if(goldrate == null)
            return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
        Goldrate rate = goldrateService.saveGoldrate(goldrate);
        return ResponseEntity.ok(rate);
    }
    @GetMapping("/all")
    public ResponseEntity<?> getGoldRate(){
        List<Goldrate> rates = goldrateService.getGoldRate();
        return ResponseEntity.ok(rates.get(0));
    }
    @PostMapping("/update")
    public ResponseEntity<?> updateGoldRate(@RequestBody Goldrate goldrate){
        return ResponseEntity.ok(goldrateService.updateGoldrate(goldrate));
    }
}
