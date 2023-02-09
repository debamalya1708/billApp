package com.dp.billapp.controller;


import com.dp.billapp.model.Showroom;
import com.dp.billapp.service.ShowroomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value="/showroom")

public class ShowroomController {

    private ShowroomService showroomService;

    @PostMapping("/save")
    public ResponseEntity<?> saveShowroom(@RequestBody Showroom showroom){
        if(showroom == null)
            return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
       Showroom details =showroomService.save(showroom);
       return ResponseEntity.ok(details);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllShowroom(){
        List<Showroom> allShowroom = showroomService.getAllShowroom();
        return ResponseEntity.ok(allShowroom);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> findShowroomById(long id){
       Optional<Showroom> showroomOptional =showroomService.getShowroomById(id);
       if(!showroomOptional.isPresent())
           return new ResponseEntity<>("Showrrom doesn't exist",HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(showroomOptional);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateShowroom(@RequestBody Showroom showroom){
        return ResponseEntity.ok(showroomService.update(showroom));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteShowroom(@PathVariable long id){
        Optional<Showroom> showroom =showroomService.getShowroomById(id);
        if(!showroom.isPresent())
            return new ResponseEntity<>("Showroom doesn't exist",HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(showroomService.delete(id));
    }
}
