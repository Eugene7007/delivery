package uz.spring.delivery.controller;

//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import uz.spring.delivery.dto.response.BillSplitResponseDto;
//import uz.spring.delivery.service.BillService;

//@Slf4j
//@RestController
//@AllArgsConstructor
//@RequestMapping("/api/v1/bills")
//@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
//public class BillController {
//
//    BillService billService;
//
//    @PostMapping("/splitting")
//    public ResponseEntity<BillSplitResponseDto> split(@RequestBody BillSplitRequestDto requestDto){
//
//        return ResponseEntity.ok(billService.split(requestDto));
//    }
//}
