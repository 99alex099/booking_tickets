package validators.impl;

import dto.PlaneDTO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import validators.exceptions.planes.ModelNameIsIncorrectException;
import validators.exceptions.planes.ModelNameIsNullException;
import validators.interfaces.Validator;

@Service
public class PlaneValidator extends ValidatorImpl implements Validator<PlaneDTO> {

    private static final Logger LOGGER = Logger.getLogger(PlaneValidator.class);
    @Override
    public void isValid(PlaneDTO planeDTO) {
        LOGGER.info("validator checks model name " + planeDTO.getModelName());
        if (modelNameIsNotCorrect(planeDTO.getModelName())) {
            throw new ModelNameIsIncorrectException(planeDTO);
        }
        LOGGER.info("validator checks model name(" + planeDTO.getModelName()
                + ") when it's empty");
        if (planeDTO.getModelName() == null || planeDTO.getModelName().equals("")) {
            throw new ModelNameIsNullException(planeDTO);
        }
    }

    private boolean modelNameIsNotCorrect(String modelName) {
        for (int i = 0; i < modelName.length(); i++) {
            final char symbol = modelName.charAt(i);

            if (symbolIsNotLetter(symbol)
                    && symbolIsNotNumber(symbol)
                    && symbol != '-'
                    && symbol != ' ') {
                return true;
            }
        }
        return false;
    }
}
