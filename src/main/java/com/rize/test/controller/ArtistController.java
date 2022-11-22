package com.rize.test.controller;

import com.rize.test.controller.validator.ValidCategory;
import com.rize.test.model.Artist;
import com.rize.test.service.ArtistServiceImpl;
import com.rize.test.utilities.artist.ResponseMessage;
import com.rize.test.utilities.artist.ResponseStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/artists")
@Validated
public class ArtistController {

    private final ArtistServiceImpl artistService;

    public ArtistController(ArtistServiceImpl artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> show(@PathVariable Integer id) {
        return ResponseEntity.ok(artistService.findById(id).orElse(null));
    }

    @GetMapping(value="")
    public ResponseEntity<List<Artist>> index(
            @RequestParam(value="search", required = false) String search,
            @RequestParam(value="category", required = false) String category,
            @RequestParam(value="month", required = false) Integer month
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(artistService.getAll(search, category, month));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseMessage> create(
            @RequestParam(value="first_name") @NotBlank String firstName,
            @RequestParam(value="last_name") @NotBlank String lastName,
            @RequestParam("birthday") @DateTimeFormat(pattern="MM-dd-yyyy") Date birthday,
            @RequestParam("email") @Email(message="Email is invalid") String email,
            @RequestParam("category") @ValidCategory String category,
            @RequestParam(value = "middle_name", required = false) String middleName,
            @RequestParam(value = "notes", required = false) String notes
    ) {
        return ResponseEntity.ok(artistService.saveArtist(new Artist(firstName, lastName, birthday, email, category.toUpperCase(), middleName, notes)));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseMessage> delete(@RequestParam("id") Integer id){
        return ResponseEntity.ok(artistService.deleteArtist(id));
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseMessage> handleMissingParams(MissingServletRequestParameterException exception){
        return ResponseEntity.ok(new ResponseMessage(ResponseStatus.ERROR, String.format("Missing parameter: %s", exception.getParameterName())));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseMessage> parseException(IllegalArgumentException exception){
        return ResponseEntity.ok(new ResponseMessage(ResponseStatus.ERROR, exception.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseMessage> constraintException(ConstraintViolationException exception){
        return ResponseEntity.ok(new ResponseMessage(ResponseStatus.ERROR, exception.getMessage()));
    }
}
