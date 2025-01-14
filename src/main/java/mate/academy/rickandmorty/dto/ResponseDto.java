package mate.academy.rickandmorty.dto;

public record ResponseDto(Long id, String externalId, String name,
                          String status,
                          String gender) {}
