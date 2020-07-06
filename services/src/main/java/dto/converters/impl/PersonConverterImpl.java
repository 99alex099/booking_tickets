package dto.converters.impl;

import dto.PersonDTO;
import dto.converters.interfaces.PersonConverter;
import entity.Person;
import org.springframework.stereotype.Service;

@Service
public class PersonConverterImpl extends ConverterImpl<Person, PersonDTO>
        implements PersonConverter {

}
