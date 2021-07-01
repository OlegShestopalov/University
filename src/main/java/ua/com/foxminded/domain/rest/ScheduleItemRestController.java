package ua.com.foxminded.domain.rest;

import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.foxminded.domain.entity.ScheduleItem;
import ua.com.foxminded.domain.model.ScheduleItemDto;
import ua.com.foxminded.domain.service.ScheduleItemService;
import ua.com.foxminded.exception.AlreadyExistException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/scheduleItems")
public class ScheduleItemRestController {

    private final ScheduleItemService scheduleItemService;
    private final ModelMapper modelMapper;

    @Autowired
    public ScheduleItemRestController(ScheduleItemService scheduleItemService, ModelMapper modelMapper) {
        this.scheduleItemService = scheduleItemService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        try {
            ScheduleItemDto scheduleItemDto = modelMapper.map(scheduleItemService.findById(id), ScheduleItemDto.class);
            return ResponseEntity.ok(scheduleItemDto);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid ScheduleItemDto scheduleItemDto) {
        try {
            ScheduleItem scheduleItem = modelMapper.map(scheduleItemDto, ScheduleItem.class);
            scheduleItemService.create(scheduleItem);
            return ResponseEntity.ok("ScheduleItem successfully saved");
        } catch (AlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @PutMapping
    public ResponseEntity update(@RequestBody ScheduleItemDto scheduleItemDto) {
        try {
            ScheduleItem scheduleItem = modelMapper.map(scheduleItemDto, ScheduleItem.class);
            scheduleItemService.update(scheduleItem);
            return ResponseEntity.ok("ScheduleItem successfully updated");
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            scheduleItemService.delete(id);
            return ResponseEntity.ok(id);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @GetMapping
    public ResponseEntity findAll() {
        try {
            List<ScheduleItemDto> scheduleItemsDto = mapList(scheduleItemService.findAll(), ScheduleItemDto.class);
            return ResponseEntity.ok(scheduleItemsDto);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/pages/{pageNumber}")
    public ResponseEntity findAllOnPage(@PathVariable("pageNumber") int pageNumber,
                                        @RequestParam("day")
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        try {
            List<ScheduleItemDto> scheduleItemsDto = mapPage(scheduleItemService.findAll(pageNumber, day), ScheduleItemDto.class);
            return ResponseEntity.ok(scheduleItemsDto);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/pagesByDay/{pageNumber}")
    public ResponseEntity findByDayOnPage(@PathVariable("pageNumber") int pageNumber,
                                          @RequestParam("day")
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        try {
            List<ScheduleItemDto> scheduleItemsDto = mapPage(scheduleItemService.findByDay(pageNumber, day), ScheduleItemDto.class);
            return ResponseEntity.ok(scheduleItemsDto);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/pagesForGroup/{pageNumber}")
    public ResponseEntity findByGroupNameOrDay(@PathVariable("pageNumber") int pageNumber,
                                               @RequestParam("day")
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day,
                                               @RequestParam("name") String name) {
        try {
            List<ScheduleItemDto> scheduleItemsDto = mapPage(scheduleItemService.findByGroupNameOrDay(pageNumber, name, day), ScheduleItemDto.class);
            return ResponseEntity.ok(scheduleItemsDto);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/pagesForTeacher/{pageNumber}")
    public ResponseEntity findByTeacherNameOrDay(@PathVariable("pageNumber") int pageNumber,
                                                 @RequestParam("day")
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day,
                                                 @RequestParam("name") String name) {
        try {
            List<ScheduleItemDto> scheduleItemsDto = mapPage(scheduleItemService.findByTeacherNameOrDay(pageNumber, name, day), ScheduleItemDto.class);
            return ResponseEntity.ok(scheduleItemsDto);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    private <S, T> List<T> mapPage(Page<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

}
