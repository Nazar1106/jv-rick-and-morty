package mate.academy.rickandmorty.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.RequestDto;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CurrentClient {
    private static final String BASE_URL = "https://rickandmortyapi.com/api/character/";
    private static final int FIRST_PAGE = 1;
    private static final int FORTY_TWO_S_PAGE = 42;
    private static final String ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_STATUS = "status";
    private static final String FIELD_GENDER = "gender";
    private static final String PAGE_FOR_URL = "?page=";
    private static final String FIELD_RESULTS = "results";
    private static final String CAN_T_GET_INFORMATION_MSG = "Can't get information";

    private final ObjectMapper objectMapper;
    private HttpClient httpClient;

    public RequestDto getCharacter() {
        httpClient = HttpClient.newHttpClient();
        GenerateRandomDigitService serviceRandomGenerate = new GenerateRandomDigitService();
        String randomDigit = serviceRandomGenerate.getRandomDigit();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL.concat(randomDigit)))
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(httpRequest,
                    HttpResponse.BodyHandlers.ofString());
            JsonNode jsonNode = objectMapper.readTree(response.body());

            Long id = jsonNode.get(ID).asLong();
            String externalId = String.valueOf(id);
            String name = jsonNode.get(FIELD_NAME).asText();
            String status = jsonNode.get(FIELD_STATUS).asText();
            String gender = jsonNode.get(FIELD_GENDER).asText();
            return new RequestDto(externalId, name, status, gender);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<RequestDto> requestDtoList() {
        httpClient = HttpClient.newHttpClient();
        List<RequestDto> dtoList = new ArrayList<>();
        for (int page = FIRST_PAGE; page <= FORTY_TWO_S_PAGE; page++) {
            String pageUrl = BASE_URL.concat(PAGE_FOR_URL + page);
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(pageUrl))
                    .GET()
                    .build();
            try {
                HttpResponse<String> response = httpClient.send(httpRequest,
                        HttpResponse.BodyHandlers.ofString());
                JsonNode jsonNode = objectMapper.readTree(response.body());
                JsonNode charactersNode = jsonNode.get(FIELD_RESULTS);
                for (JsonNode character : charactersNode) {
                    Long id = character.get(ID).asLong();
                    String externalId = String.valueOf(id);
                    String name = character.get(FIELD_NAME).asText();
                    String status = character.get(FIELD_STATUS).asText();
                    String gender = character.get(FIELD_GENDER).asText();
                    RequestDto requestDto = new RequestDto(externalId, name, status, gender);
                    dtoList.add(requestDto);
                }
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(CAN_T_GET_INFORMATION_MSG);
            }
        }
        return dtoList;
    }
}
