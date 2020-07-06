package dto.converters.impl;

import dao.jpa.FlightRepository;
import dto.FlightDTO;
import dto.converters.interfaces.FlightConverter;
import entity.Flight;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightConverterImpl extends ConverterImpl<Flight, FlightDTO>
        implements FlightConverter{

}
