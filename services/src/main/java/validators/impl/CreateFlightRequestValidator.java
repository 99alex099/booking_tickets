package validators.impl;

import dto.CreateFlightRequestDTO;
import org.springframework.stereotype.Service;
import validators.exceptions.flights.PriceException;
import validators.exceptions.flights.DateIsIncorrectException;
import validators.interfaces.Validator;

@Service
public class CreateFlightRequestValidator extends ValidatorImpl implements Validator<CreateFlightRequestDTO> {
    @Override
    public void isValid(CreateFlightRequestDTO createFlightRequestDTO) {
        if (createFlightRequestDTO.getArrivalDay() < 0 || createFlightRequestDTO.getArrivalDay() > 31
                || createFlightRequestDTO.getArrivalHour() < 0 || createFlightRequestDTO.getArrivalHour() > 24
                || createFlightRequestDTO.getArrivalMinute() < 0 || createFlightRequestDTO.getArrivalMinute() > 60
                || createFlightRequestDTO.getArrivalMonth() < 0 || createFlightRequestDTO.getArrivalMonth() > 12
                || createFlightRequestDTO.getArrivalYear() < 0
                || createFlightRequestDTO.getDepartureDay() < 0 || createFlightRequestDTO.getDepartureDay() > 31
                || createFlightRequestDTO.getDepartureHour() < 0 || createFlightRequestDTO.getDepartureHour() > 24
                || createFlightRequestDTO.getDepartureMinute() < 0 || createFlightRequestDTO.getDepartureMinute() > 60
                || createFlightRequestDTO.getDepartureMonth() < 0 || createFlightRequestDTO.getDepartureMonth() > 12
                || createFlightRequestDTO.getDepartureYear() < 0) {
            throw new DateIsIncorrectException(createFlightRequestDTO);
        }
        if (createFlightRequestDTO.getOtherPrice() == null
                || createFlightRequestDTO.getOtherPrice() < 0
                || createFlightRequestDTO.getBusinessClassPrice() == null
                || createFlightRequestDTO.getBusinessClassPrice() < 0) {
            throw new PriceException(createFlightRequestDTO);
        }
    }
}
