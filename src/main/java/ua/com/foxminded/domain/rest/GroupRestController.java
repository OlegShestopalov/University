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
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.domain.model.GroupDto;
import ua.com.foxminded.domain.service.GroupService;
import ua.com.foxminded.exception.AlreadyExistException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupRestController {

    private final GroupService groupService;
    private final ModelMapper modelMapper;

    @Autowired
    public GroupRestController(GroupService groupService, ModelMapper modelMapper) {
        this.groupService = groupService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Get a group by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the group",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GroupDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Group not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        try {
            GroupDto groupDto = modelMapper.map(groupService.findById(id), GroupDto.class);
            return ResponseEntity.ok(groupDto);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @Operation(summary = "Get group by name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved group by name.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GroupDto.class)))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid group name supplied",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Group not found",
                    content = @Content)})
    @GetMapping("/name")
    public ResponseEntity findByName(@RequestParam String name) {
        try {
            List<GroupDto> groupsDto = mapList(groupService.findByName(name), GroupDto.class);
            return ResponseEntity.ok(groupsDto);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @Operation(summary = "Create group")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully created group",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GroupDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Unable to create group",
                    content = @Content)})
    @PostMapping
    public ResponseEntity save(@RequestBody @Valid GroupDto groupDto) {
        try {
            Group group = modelMapper.map(groupDto, Group.class);
            groupService.create(group);
            return ResponseEntity.ok("Group successfully saved");
        } catch (AlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @Operation(summary = "Update group")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully updated group",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GroupDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Unable to update group",
                    content = @Content)})
    @PutMapping
    public ResponseEntity update(@RequestBody GroupDto groupDto) {
        try {
            Group group = modelMapper.map(groupDto, Group.class);
            groupService.update(group);
            return ResponseEntity.ok("Faculty successfully updated");
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @Operation(summary = "Delete group")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Group deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid group id supplied",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Group not found",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            groupService.delete(id);
            return ResponseEntity.ok(id);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error has occurred");
        }
    }

    @Operation(summary = "Get all groups")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all groups",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GroupDto.class)))),
            @ApiResponse(
                    responseCode = "404",
                    description = "No groups found",
                    content = @Content)})
    @GetMapping
    public ResponseEntity findAll() {
        try {
            List<GroupDto> groupsDto = mapList(groupService.findAll(), GroupDto.class);
            return ResponseEntity.ok(groupsDto);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get all groups by pages")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found pages with groups",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GroupDto.class)))),
            @ApiResponse(
                    responseCode = "404",
                    description = "No pages with groups found",
                    content = @Content)})
    @GetMapping("/pages/{pageNumber}")
    public ResponseEntity findAllOnPage(@PathVariable("pageNumber") int pageNumber) {
        try {
            List<GroupDto> groupsDto = mapPage(groupService.findAll(pageNumber), GroupDto.class);
            return ResponseEntity.ok(groupsDto);
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
