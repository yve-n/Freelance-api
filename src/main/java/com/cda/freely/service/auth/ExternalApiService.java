package com.cda.freely.service.auth;
import com.cda.freely.controller.auth.ApiResponse;
import com.cda.freely.controller.auth.AuthController;
import com.cda.freely.controller.auth.externalApiData.ExternalApiResponseToJson;
import com.cda.freely.controller.auth.externalApiData.PeriodeUniteLegale;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Collections;


@Service
public class ExternalApiService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${INSEE_API_URL}")
    private String inseeApiUrl;
    @Value("${INSEE_API_TOKEN}")
    private String inseeToken;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public ApiResponse fetchCompanyInfosBySiren(String siren) throws JsonProcessingException {
        logger.info("siren===================> {}", siren);
        String token = inseeToken;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        // Get today's date and format it as a string
        String dateToday = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String url = UriComponentsBuilder.fromHttpUrl(inseeApiUrl)
                .pathSegment(siren) // Inclure le siren dans la requête
                .queryParam("date", dateToday)  //queryParam est utilisée pour ajouter le paramètre de requête à l'URL
                .toUriString();  //toUriString est utilisé pour convertir le tout en une chaîne URL

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        ExternalApiResponseToJson externalApiResponseToJson = objectMapper.readValue(responseBody, ExternalApiResponseToJson.class);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSiren(externalApiResponseToJson.getUniteLegale().getSiren());

        if (!externalApiResponseToJson.getUniteLegale().getPeriodesUniteLegale().isEmpty()) {
            PeriodeUniteLegale firstPeriode = externalApiResponseToJson.getUniteLegale().getPeriodesUniteLegale().get(0);
            apiResponse.setName(firstPeriode.getDenominationUniteLegale());
        }
        apiResponse.setStatusCode(response.getStatusCodeValue());
        return apiResponse;


    }
}
