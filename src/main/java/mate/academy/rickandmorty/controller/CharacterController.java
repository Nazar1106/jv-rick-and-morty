package mate.academy.rickandmorty.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.ResponseDto;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/character")
public class CharacterController {

    private final CharacterService service;

    @PostMapping
    public ResponseDto saveCharacter() {
        return service.saveCharacter();
    }

    @GetMapping("/search")
    public List<ResponseDto> searchCharacters(@RequestParam String name) {
        return service.findCharactersByName(name);
    }

    @GetMapping
    public List<ResponseDto> getAll() {
        return service.getAll();
    }
}
