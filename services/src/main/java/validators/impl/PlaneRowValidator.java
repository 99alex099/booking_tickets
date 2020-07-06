package validators.impl;

import dto.PlaneRowDTO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import validators.exceptions.planeseats.CountOfSeatsIsIncorrect;
import validators.interfaces.Validator;

@Service
public class PlaneRowValidator extends ValidatorImpl implements Validator<PlaneRowDTO> {

    private static final Logger LOGGER = Logger.getLogger(FlightValidator.class);
    @Override
    public void isValid(PlaneRowDTO planeRowDTO) {
        LOGGER.info("checking seats of plane row on valid");
        if (planeRowDTO.getCountOfSeats() < 0 || planeRowDTO.getCountOfSeats() == null) {
            throw new CountOfSeatsIsIncorrect(planeRowDTO);
        }
    }
}
