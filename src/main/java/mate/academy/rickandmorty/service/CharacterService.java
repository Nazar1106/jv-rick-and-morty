package mate.academy.rickandmorty.service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.RequestDto;
import mate.academy.rickandmorty.dto.ResponseDto;
import mate.academy.rickandmorty.entity.Character;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.repository.CharacterRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CharacterService {

    private final CurrentClient client;

    private final CharacterMapper characterMapper;

    private final CharacterRepository repository;

    public ResponseDto saveCharacter() {
        RequestDto character = client.getCharacter();
        Character model = characterMapper.toModel(character);
        repository.save(model);
        return characterMapper.toDto(model);
    }

    @PostConstruct
    public void saveAllCharacters() {
        List<RequestDto> requestDtos = client.requestDtoList();
        List<Character> characters = new ArrayList<>();
        for (RequestDto dto : requestDtos) {
            Character model = characterMapper.toModel(dto);
            characters.add(model);
        }
        repository.saveAll(characters);
    }

    public List<ResponseDto> getAll() {
        List<Character> all = repository.findAll();
        return characterMapper.toDtos(all);
    }

    public List<ResponseDto> findCharactersByName(String name) {
        List<Character> allByName = repository.findAllByName(name);
        return characterMapper.toDtos(allByName);
    }
}
