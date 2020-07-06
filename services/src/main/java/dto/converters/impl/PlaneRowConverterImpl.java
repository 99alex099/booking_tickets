package dto.converters.impl;

import dto.PlaneRowDTO;
import dto.converters.interfaces.PlaneRowConverter;
import entity.PlaneRow;
import org.springframework.stereotype.Service;

@Service
public class PlaneRowConverterImpl extends ConverterImpl<PlaneRow, PlaneRowDTO>
        implements PlaneRowConverter {
}
