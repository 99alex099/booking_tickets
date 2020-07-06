package services.implementations;

import dao.jpa.PlaneRepository;
import dao.jpa.PlaneRowRepository;
import dto.PlaneDTO;
import dto.PlaneRowDTO;
import dto.converters.interfaces.PlaneConverter;
import dto.converters.interfaces.PlaneRowConverter;
import org.apache.log4j.Logger;
import validators.exceptions.general.InvalidTextException;
import services.interfaces.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validators.exceptions.planes.PlaneIdNotFoundException;
import validators.interfaces.Validator;

import java.util.List;

@Service
public class PlaneServiceImpl implements PlaneService {

    private static final Logger LOGGER = Logger.getLogger(PlaneServiceImpl.class);

    private final PlaneRepository planeRepository;
    private final PlaneRowRepository planeRowRepository;
    private final PlaneConverter planeConverter;
    private final PlaneRowConverter planeRowConverter;
    private final Validator<PlaneDTO> planeValidator;
    private final Validator<PlaneRowDTO> planeRowValidator;

    @Autowired
    public PlaneServiceImpl(PlaneRepository planeRepository,
                            PlaneRowRepository planeRowRepository,
                            PlaneConverter planeConverter,
                            PlaneRowConverter planeRowConverter,
                            Validator<PlaneDTO> planeValidator,
                            Validator<PlaneRowDTO> planeRowValidator) {

        this.planeRepository = planeRepository;
        this.planeRowRepository = planeRowRepository;
        this.planeConverter = planeConverter;
        this.planeRowConverter = planeRowConverter;
        this.planeValidator = planeValidator;
        this.planeRowValidator = planeRowValidator;
    }

    @Override
    public PlaneDTO findPlaneById(int id) throws PlaneIdNotFoundException {
        LOGGER.info("finding plane[id:" + id + "]");
        return   planeConverter.convertEntityToDTO(
                planeRepository.findById(id).orElseThrow(
                        () -> new PlaneIdNotFoundException(id)
                ));
    }

    @Override
    public List<PlaneDTO> findPlanes() {
        LOGGER.info("finding all planes");
        return planeConverter.convertEntityToDTO(
                planeRepository.findAll());
    }

    @Override
    public PlaneDTO addPlane(PlaneDTO plane) {
        LOGGER.info("trying to add plane " + plane.getModelName());
        planeValidator.isValid(plane);
        return  planeConverter.convertEntityToDTO(
                planeRepository.save(
                        planeConverter.convertDTOToEntity(plane)
                ));
    }

    @Override
    public List<PlaneRowDTO> findPlaneSeats(Integer planeId) {
        LOGGER.info("finding plane seats of plane[id:" + planeId + "]");
        return findPlaneSeats(
                planeConverter.convertEntityToDTO(
                planeRepository
                        .findById(planeId)
                        .orElseThrow(
                                () -> new PlaneIdNotFoundException(planeId)
                        )));
    }

    @Override
    public List<PlaneRowDTO> findPlaneSeats(PlaneDTO plane) {
        return planeRowConverter.convertEntityToDTO(
                planeRowRepository.findByPlane(
                        planeConverter.convertDTOToEntity(plane)));
    }

    @Override
    public PlaneRowDTO addPlaneSeats(PlaneRowDTO planeRow, Integer planeId) {
        LOGGER.info("trying to add planeRow[id:" + planeRow.getId() + "]");
        LOGGER.info("validator checks planeRow[id:" + planeRow.getId() + "]");
        planeRowValidator.isValid(planeRow);

        LOGGER.info("set field plane of planeRow[id:" + planeRow.getId() + "]");
        planeRow.setPlane(
                planeConverter.convertEntityToDTO(
                        planeRepository.findById(planeId).orElseThrow(
                                () -> new PlaneIdNotFoundException(planeId)
                        )
                )
        );

        LOGGER.info("added plane seat:" + planeRow.toString());

        return  planeRowConverter.convertEntityToDTO(planeRowRepository.save(
                planeRowConverter.convertDTOToEntity(planeRow)));

    }

    @Override
    public PlaneDTO savePlane(PlaneDTO plane) {
        LOGGER.info("trying to save plane[id:" + plane.getId() + "]");
        LOGGER.info("validator checks plane[id:" + plane.getId() + "]");
        planeValidator.isValid(plane);
        LOGGER.info("checks exists plane[id:" + plane.getId() + "] in database");
        if (planeRepository.existsById(plane.getId())) {
            LOGGER.info("plane " + plane.toString() + " saved");
            return planeConverter.convertEntityToDTO(
                    planeRepository.save(
                            planeConverter.convertDTOToEntity(plane)));
        } else {
            LOGGER.info("plane[id:" + plane.getId() + "] doesn't exist in database");
            throw new PlaneIdNotFoundException(plane.getId());
        }
    }

    @Override
    public void deletePlane(Integer id) {
        LOGGER.info("trying to delete plane[id:" + id + "]");
        planeRepository.deleteById(id);
    }

    @Override
    public void deletePlaneSeat(Integer id) {
        LOGGER.info("trying to delete planeSeat[id:" + id + "]");
        planeRowRepository.deleteById(id);
    }
}
