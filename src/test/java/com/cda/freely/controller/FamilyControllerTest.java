package com.cda.freely.controller;


import com.cda.freely.config.SecurityConfig;
import com.cda.freely.controller.family.FamilyController;
import com.cda.freely.entity.Family;
import com.cda.freely.repository.FamilyRepository;
import com.cda.freely.service.FamilyService;
import io.github.cdimascio.dotenv.Dotenv;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {FamilyController.class})
@AutoConfigureMockMvc(addFilters = false)
//@TestPropertySource(locations = "classpath:application.properties")
//@WithMockUser
class FamilyControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private FamilyService familyService;

    @MockBean
    private FamilyRepository familyRepository;
    

    private static final String FAMILY_NAME = "The Little Prince";
    private static final String FAMILY_DESCRIPTION = "The Little Prince description";
    private static final Long FAMILY_ID = 1L;

    @BeforeEach
    void setUp() {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }

    @Test
    void getFamily() throws Exception{
        RequestBuilder request = get("/family").contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("family"))
                .andReturn();
        System.out.println(mvcResult.getResponse());

    }

    @Test
    void getFamilies() throws Exception {
        final Family family = new Family();
        family.setId(FAMILY_ID);
        family.setName(FAMILY_NAME);
        family.setDescription(FAMILY_DESCRIPTION);

        final List<Family> families = Arrays.asList(family);
        System.out.println(families.size());

        Mockito.when(this.familyService.getFamilies()).thenReturn(families);

        RequestBuilder request = get("/family/all").contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$[0].name", is("The Little Prince")))
                .andReturn();

    }

}
