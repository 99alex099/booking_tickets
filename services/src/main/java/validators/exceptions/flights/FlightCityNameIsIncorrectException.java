package validators.exceptions.flights;

import dto.FlightDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import validators.exceptions.general.InvalidTextException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FlightCityNameIsIncorrectException extends FlightException {

    FlightCityNameIsIncorrectException(FlightDTO flight, String cityName) {
        super(flight,
                "City's name " + cityName + " isn't correct");
    }
}
