package chinhnd.controller;

import chinhnd.model.SmartPhone;
import chinhnd.service.SmartPhoneServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@RequestMapping("/phone")
@CrossOrigin(origins = "*")
public class SmartPhoneController {
    @Autowired
    SmartPhoneServiceImpl smartPhoneService;

    @PostMapping
    public ResponseEntity<?> createSmartphone(@RequestBody SmartPhone smartphone) {
        return new ResponseEntity<>(smartPhoneService.save(smartphone), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> allPhones() {
        return new ResponseEntity<>(smartPhoneService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{producer}")
    public ResponseEntity<?> findAllByName(@PathVariable String producer) {
        return new ResponseEntity<>(smartPhoneService.findAllByProducerContaining(producer), HttpStatus.OK);
    }

//    @GetMapping("/list")
//    public ModelAndView getAllSmartphonePage() {
//        ModelAndView modelAndView = new ModelAndView("/list");
//        modelAndView.addObject("smartphones", smartPhoneService.findAll());
//        System.out.println("modelAndView ---->"+modelAndView);
//        return modelAndView;
//    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        System.out.println("goi ham by ID");
        Optional<SmartPhone> smartPhone = smartPhoneService.findById(id);
        if (!smartPhone.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println("smartPhone"+smartPhone);
        return new ResponseEntity<>(smartPhone.get(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSmartphone(@PathVariable Long id) {
        Optional<SmartPhone> smartphoneOptional = smartPhoneService.findById(id);
        if (!smartphoneOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        smartPhoneService.deleteById(id);
        return new ResponseEntity<>(smartphoneOptional.get(), HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSmartPhone(@PathVariable Long id, @RequestBody  SmartPhone smartPhone){
        System.out.println("goi ham put");
        System.out.println("producer == "+smartPhone.getProducer());
        Optional<SmartPhone> smartPhone1 = smartPhoneService.findById(id);
        if (!smartPhone1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        smartPhone1.get().setProducer(smartPhone.getProducer());
        smartPhone1.get().setModel(smartPhone.getModel());
        smartPhone1.get().setPrice(smartPhone.getPrice());
        smartPhone1.get().setImage(smartPhone.getImage());

        return new ResponseEntity<>(smartPhoneService.save(smartPhone1.get()), HttpStatus.OK);
    }
}
