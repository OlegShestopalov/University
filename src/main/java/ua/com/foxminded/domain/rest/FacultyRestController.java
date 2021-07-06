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
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.domain.model.FacultyDto;
import ua.com.foxminded.domain.service.FacultyService;
import ua.com.foxminded.exception.AlreadyExistException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/faculties")
public class FacultyRestController {

    private final FacultyService facultyService;
    private final ModelMapper modelMapper;

    @Autowired
    public FacultyRestController(FacultyService facultyService, ModelMapper modelMapper) {
        this.facultyService = facultyService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Get a faculty by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the faculty",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FacultyDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Faculty not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        try {
            FacultyDto facultyDto = modelMapper.map(facultyService.findById(id), FacultyDto.class);
            return ResponseEntity.ok(facultyDto);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @Operation(summary = "Get faculty by name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved faculty by name.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = FacultyDto.class)))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid faculty name supplied",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Faculty not found",
                    content = @Content)})
    @GetMapping("/name")
    public ResponseEntity findByName(@RequestParam String name) {
        try {
            List<FacultyDto> facultiesDto = mapList(facultyService.findByName(name), FacultyDto.class);
            return ResponseEntity.ok(facultiesDto);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @Operation(summary = "Create faculty")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully created faculty",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FacultyDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Unable to create faculty",
                    content = @Content)})
    @PostMapping
    public ResponseEntity save(@RequestBody @Valid FacultyDto facultyDto) {
        try {
            Faculty faculty = modelMapper.map(facultyDto, Faculty.class);
            facultyService.create(faculty);
            return ResponseEntity.ok(faculty);
        } catch (AlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @Operation(summary = "Update faculty")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully updated faculty",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FacultyDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Unable to update faculty",
                    content = @Content)})
    @PutMapping
    public ResponseEntity update(@RequestBody @Valid FacultyDto facultyDto) {
        try {
            Faculty faculty = modelMapper.map(facultyDto, Faculty.class);
            facultyService.update(faculty);
            return ResponseEntity.ok("Faculty successfully updated");
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @Operation(summary = "Delete faculty")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Faculty deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid faculty id supplied",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Faculty not found",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            facultyService.delete(id);
            return ResponseEntity.ok(id);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @Operation(summary = "Get all faculties")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all faculties",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = FacultyDto.class)))),
            @ApiResponse(
                    responseCode = "404",
                    description = "No faculties found",
                    content = @Content)})
    @GetMapping
    public ResponseEntity findAll() {
        try {
            List<FacultyDto> facultiesDto = mapList(facultyService.findAll(), FacultyDto.class);
            return ResponseEntity.ok(facultiesDto);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get all faculties by pages")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found pages with faculties",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = FacultyDto.class)))),
            @ApiResponse(
                    responseCode = "404",
                    description = "No pages with faculties found",
                    content = @Content)})
    @GetMapping("/pages/{pageNumber}")
    public ResponseEntity findAllOnPage(@PathVariable("pageNumber") int pageNumber) {
        try {
            List<FacultyDto> facultiesDto = mapPage(facultyService.findAll(pageNumber), FacultyDto.class);
            return ResponseEntity.ok(facultiesDto);
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
