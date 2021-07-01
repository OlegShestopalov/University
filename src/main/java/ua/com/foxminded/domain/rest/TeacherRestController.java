package ua.com.foxminded.domain.rest;

import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import ua.com.foxminded.domain.entity.Teacher;
import ua.com.foxminded.domain.model.TeacherDto;
import ua.com.foxminded.domain.service.TeacherService;
import ua.com.foxminded.exception.AlreadyExistException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/teachers")
public class TeacherRestController {

    private final TeacherService teacherService;
    private final ModelMapper modelMapper;

    @Autowired
    public TeacherRestController(TeacherService teacherService, ModelMapper modelMapper) {
        this.teacherService = teacherService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        try {
            TeacherDto teacherDto = modelMapper.map(teacherService.findById(id), TeacherDto.class);
            return ResponseEntity.ok(teacherDto);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @GetMapping("/name")
    public ResponseEntity findByName(@RequestParam String name) {
        try {
            List<TeacherDto> teachersDto = mapList(teacherService.findByPersonalData(name), TeacherDto.class);
            return ResponseEntity.ok(teachersDto);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid TeacherDto teacherDto) {
        try {
            Teacher teacher = modelMapper.map(teacherDto, Teacher.class);
            teacherService.create(teacher);
            return ResponseEntity.ok("Teacher successfully saved");
        } catch (AlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @PutMapping
    public ResponseEntity update(@RequestBody TeacherDto teacherDto) {
        try {
            Teacher teacher = modelMapper.map(teacherDto, Teacher.class);
            teacherService.update(teacher);
            return ResponseEntity.ok("Teacher successfully updated");
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            teacherService.delete(id);
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
            List<TeacherDto> teachersDto = mapList(teacherService.findAll(), TeacherDto.class);
            return ResponseEntity.ok(teachersDto);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/pages/{pageNumber}")
    public ResponseEntity findAllOnPage(@PathVariable("pageNumber") int pageNumber) {
        try {
            List<TeacherDto> teachersDto = mapPage(teacherService.findAll(pageNumber), TeacherDto.class);
            return ResponseEntity.ok(teachersDto);
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
