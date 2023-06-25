package com.cda.freely.service;

import com.cda.freely.entity.Family;
import com.cda.freely.repository.FamilyRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
//@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringJUnitConfig
@SpringBootTest(classes = FamilyService.class)
class FamilyServiceTest {
    private static final String FAMILY_NAME = "The Little Prince";
    private static final String FAMILY_DESCRIPTION = "The Little Prince description";
    private static final Long FAMILY_ID = 1L;
    private List<Family> families = new ArrayList<>();

    @Autowired
    private FamilyService familyService;

    @MockBean
    private FamilyRepository familyRepository;

    @BeforeEach
    void setUp() {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        final Family family = new Family();
        family.setId(FAMILY_ID);
        family.setName(FAMILY_NAME);
        family.setDescription(FAMILY_DESCRIPTION);

        Mockito.when(this.familyRepository.findById(family.getId()))
                .thenReturn(Optional.of(family));
         this.families = Arrays.asList(family);

        Mockito.when(this.familyRepository.findAll())
                .thenReturn(families);
    }
    @Test
    void findById() {
        // GIVEN
        final Long id = FAMILY_ID;
        // WHEN
        final Optional<Family> family = this.familyService.findById(id);
        // THEN
        assertEquals(id,family.get().getId());

    }

    @Test
    void getFamilies() {
        final List<Family> family = this.families;
        final List<Family> families = this.familyService.getFamilies();

        assertIterableEquals(family, families);
        assertEquals(family.get(0).getName(), families.get(0).getName());
        assertEquals(family.size(), families.size());

    }
}
