package dto.converters.impl;

import dto.converters.interfaces.Converter;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import services.implementations.FlightServiceImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class ConverterImpl<Entity, DTO> implements Converter<Entity, DTO> {

    private static final int ENTITY_PARAMETER_INDEX = 0;
    private static final int DTO_PARAMETER_INDEX = 1;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger LOGGER = Logger.getLogger(ConverterImpl.class);

    protected ConverterImpl() {

    }

    private Class getDTOClass() {
        return (Class) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[DTO_PARAMETER_INDEX];
    }
    private Class getEntityClass() {
        return (Class) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[ENTITY_PARAMETER_INDEX];
    }

    @Override
    public DTO convertEntityToDTO(Entity entity) {
        LOGGER.info("converting entity " + entity.getClass().getName() + " to dto");
        return modelMapper.map(entity, (Type) getDTOClass());
    }

    @Override
    public Entity convertDTOToEntity(DTO dto) {
        LOGGER.info("converting dto " + dto.getClass().getName() + " to entity");
        return modelMapper.map(dto, (Type) getEntityClass());
    }

    @Override
    public List<DTO> convertEntityToDTO(List<Entity> entityList) {
        List<DTO> dtoList = new LinkedList<>();

        LOGGER.info("converting list of entity to dto");
        for (Entity entity : entityList) {
            dtoList.add(convertEntityToDTO(entity));
        }

        return dtoList;
    }

    @Override
    public List<Entity> convertDTOToEntity(List<DTO> dtoList) {
        List<Entity> entityList = new LinkedList<>();

        LOGGER.info("converting list of dto to entity");
        for (DTO dto: dtoList) {
            entityList.add(convertDTOToEntity(dto));
        }

        return entityList;
    }
}
