package dto.converters.impl;

import dto.PlaneDTO;
import dto.converters.interfaces.PlaneConverter;
import entity.Plane;
import org.springframework.stereotype.Service;

@Service
public class PlaneConverterImpl extends ConverterImpl<Plane, PlaneDTO>
        implements PlaneConverter {

}
