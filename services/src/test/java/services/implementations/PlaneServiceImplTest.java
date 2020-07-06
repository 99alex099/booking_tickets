package services.implementations;

import dao.jpa.PlaneRepository;
import dto.PlaneDTO;
import dto.converters.interfaces.PlaneConverter;
import entity.Plane;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import validators.exceptions.planes.ModelNameIsIncorrectException;
import validators.exceptions.planes.PlaneIdNotFoundException;
import validators.interfaces.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class PlaneServiceImplTest {
    @Mock
    PlaneRepository planeRepository;
    @Mock
    Validator<PlaneDTO> planeValidator;
    @Mock
    PlaneConverter planeConverter;
    @InjectMocks
    PlaneServiceImpl planeService;

    @Test
    void findPlaneByIdIsSuccessfully() {
        MockitoAnnotations.initMocks(this);

        int correctlyId = 7;
        PlaneDTO planeDTO = PlaneDTO.builder()
                .id(correctlyId)
                .modelName("boeing")
                .build();
        Plane planeEntity = Plane.builder()
                .id(correctlyId)
                .modelName("boeing")
                .build();

        Optional<Plane> planeOptional = Optional.of(planeEntity);
        when(planeRepository.findById(correctlyId)).thenReturn(planeOptional);
        when(planeConverter.convertDTOToEntity(planeDTO)).thenReturn(planeEntity);
        when(planeConverter.convertEntityToDTO(planeEntity)).thenReturn(planeDTO);

        assertEquals(planeDTO, planeService.findPlaneById(correctlyId));
    }
    @Test
    void findPlaneByIdIsIncorrect() {
        MockitoAnnotations.initMocks(this);

        int incorrectlyId = 3;
        PlaneDTO planeDTO = PlaneDTO.builder()
                .id(incorrectlyId)
                .modelName("boeing")
                .build();
        Plane planeEntity = Plane.builder()
                .id(incorrectlyId)
                .modelName("boeing")
                .build();

        Optional<Plane> planeOptional = Optional.empty();
        when(planeRepository.findById(incorrectlyId)).thenReturn(planeOptional);
        when(planeConverter.convertDTOToEntity(planeDTO)).thenReturn(planeEntity);
        when(planeConverter.convertEntityToDTO(planeEntity)).thenReturn(planeDTO);

        try {
            planeService.findPlaneById(incorrectlyId);
            Assert.fail("Expected PlaneIdNotFound");
        } catch (PlaneIdNotFoundException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    void findPlanes() {
        MockitoAnnotations.initMocks(this);

        List<Plane> entities = new ArrayList<>();
        List<PlaneDTO> DTOs = new ArrayList<>();

        when(planeRepository.findAll()).thenReturn(entities);
        when(planeConverter.convertEntityToDTO(entities)).thenReturn(DTOs);
        when(planeConverter.convertDTOToEntity(DTOs)).thenReturn(entities);

        assertEquals(DTOs,
                planeService.findPlanes());
    }

    @Test
    void addCorrectlyPlane() {
        MockitoAnnotations.initMocks(this);


        int correctlyId = 3;
        PlaneDTO planeDTO = PlaneDTO.builder()
                .id(correctlyId)
                .modelName("boeing")
                .build();
        Plane planeEntity = Plane.builder()
                .id(correctlyId)
                .modelName("boeing")
                .build();

        Optional<Plane> planeOptional = Optional.empty();
        when(planeRepository.save(planeEntity)).thenReturn(planeEntity);
        when(planeConverter.convertDTOToEntity(planeDTO)).thenReturn(planeEntity);
        when(planeConverter.convertEntityToDTO(planeEntity)).thenReturn(planeDTO);

        assertEquals(planeDTO, planeService.addPlane(planeDTO));

    }
    @Test
    void addIncorrectlyPlane() {
        MockitoAnnotations.initMocks(this);

        int correctlyId = 3;
        PlaneDTO planeDTO = PlaneDTO.builder()
                .id(correctlyId)
                .modelName("boeing")
                .build();
        Plane planeEntity = Plane.builder()
                .id(correctlyId)
                .modelName("boeing")
                .build();

        doThrow(ModelNameIsIncorrectException.class)
                .when(planeValidator)
                .isValid(planeDTO);
        when(planeRepository.save(planeEntity)).thenReturn(planeEntity);
        when(planeConverter.convertDTOToEntity(planeDTO)).thenReturn(planeEntity);
        when(planeConverter.convertEntityToDTO(planeEntity)).thenReturn(planeDTO);

        try {
            planeService.addPlane(planeDTO);
            fail();
        } catch (ModelNameIsIncorrectException e) {
            assertTrue(true);
        }
    }
    @Test
    void findPlaneSeats() {
    }

    @Test
    void testFindPlaneSeats() {
    }

    @Test
    void addPlaneSeats() {
    }

    @Test
    void savePlane() {
    }
}