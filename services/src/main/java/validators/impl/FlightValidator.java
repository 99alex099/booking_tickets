package validators.impl;

import dto.FlightDTO;
import dto.converters.impl.ConverterImpl;
import org.apache.log4j.Logger;
import validators.exceptions.flights.ArrivalCityNameIsIncorrectExceptionException;
import validators.exceptions.flights.DepartureCityNameIsInvalidExceptionException;
import org.springframework.stereotype.Service;
import validators.interfaces.Validator;

import javax.validation.constraints.Null;

@Service
public class FlightValidator extends ValidatorImpl implements Validator<FlightDTO> {

    private static final Logger LOGGER = Logger.getLogger(FlightValidator.class);
    @Override
    public void isValid(FlightDTO flightDTO) {

        LOGGER.info("checking arrival city name of flight[" + flightDTO.getArrivalCity() + "]");
        if (cityNameIsNotCorrect(flightDTO.getArrivalCity())) {
            throw new ArrivalCityNameIsIncorrectExceptionException(flightDTO);
        }
        LOGGER.info("checking departure city name of flight[" + flightDTO.getDepartureCity() + "]");
        if (cityNameIsNotCorrect(flightDTO.getDepartureCity())) {
            throw new DepartureCityNameIsInvalidExceptionException(flightDTO);
        }
    }

    private boolean cityNameIsNotCorrect(String cityName) {
        if (cityName != null) {
            for (int i = 0; i < cityName.length(); i++) {
                if (symbolIsNotLetter(cityName.charAt(i))
                        && cityName.charAt(i) != '-') {
                    return true;
                }
            }
        }
        return false;
    }
}
