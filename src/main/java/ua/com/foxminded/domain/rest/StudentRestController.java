package ua.com.foxminded.domain.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import ua.com.foxminded.domain.entity.Student;
import ua.com.foxminded.domain.model.StudentDto;
import ua.com.foxminded.domain.service.StudentService;
import ua.com.foxminded.exception.AlreadyExistException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/students")
public class StudentRestController {

    private final StudentService studentService;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentRestController(StudentService studentService, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Get a student by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the student",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StudentDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        try {
            StudentDto studentDto = modelMapper.map(studentService.findById(id), StudentDto.class);
            return ResponseEntity.ok(studentDto);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @Operation(summary = "Get student by name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved student by name.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = StudentDto.class)))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid student name supplied",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content)})
    @GetMapping("/name")
    public ResponseEntity findByName(@RequestParam String name) {
        try {
            List<StudentDto> studentsDto = mapList(studentService.findByPersonalData(name), StudentDto.class);
            return ResponseEntity.ok(studentsDto);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @Operation(summary = "Create student")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully created student",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StudentDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Unable to create studnet",
                    content = @Content)})
    @PostMapping
    public ResponseEntity save(@RequestBody @Valid StudentDto studentDto) {
        try {
            Student student = modelMapper.map(studentDto, Student.class);
            studentService.create(student);
            return ResponseEntity.ok("Student successfully saved");
        } catch (AlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @Operation(summary = "Update student")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully updated studnet",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StudentDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Unable to update student",
                    content = @Content)})
    @PutMapping
    public ResponseEntity update(@RequestBody StudentDto studentDto) {
        try {
            Student student = modelMapper.map(studentDto, Student.class);
            studentService.update(student);
            return ResponseEntity.ok("Student successfully updated");
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @Operation(summary = "Delete student")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Student deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid student id supplied",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            studentService.delete(id);
            return ResponseEntity.ok(id);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @Operation(summary = "Get all students")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all students",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = StudentDto.class)))),
            @ApiResponse(
                    responseCode = "404",
                    description = "No students found",
                    content = @Content)})
    @GetMapping
    public ResponseEntity findAll() {
        try {
            List<StudentDto> stuedntsDto = mapList(studentService.findAll(), StudentDto.class);
            return ResponseEntity.ok(stuedntsDto);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get all students by pages")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found pages with students",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = StudentDto.class)))),
            @ApiResponse(
                    responseCode = "404",
                    description = "No pages with students found",
                    content = @Content)})
    @GetMapping("/pages/{pageNumber}")
    public ResponseEntity findAllOnPage(@PathVariable("pageNumber") int pageNumber) {
        try {
            List<StudentDto> studentsDto = mapPage(studentService.findAll(pageNumber), StudentDto.class);
            return ResponseEntity.ok(studentsDto);
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
