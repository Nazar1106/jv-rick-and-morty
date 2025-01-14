package mate.academy.rickandmorty.mapper;

import java.util.List;
import mate.academy.rickandmorty.dto.RequestDto;
import mate.academy.rickandmorty.dto.ResponseDto;
import mate.academy.rickandmorty.entity.Character;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CharacterMapper {

    @Mapping(target = "id", ignore = true)
    Character toModel(RequestDto requestDto);

    ResponseDto toDto(Character character);

    List<ResponseDto> toDtos(List<Character> characterList);
}
