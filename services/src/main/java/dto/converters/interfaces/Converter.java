package dto.converters.interfaces;

import java.util.List;

public interface Converter<Entity, DTO> {
    DTO convertEntityToDTO(Entity entity);
    Entity convertDTOToEntity(DTO dto);
    List<DTO> convertEntityToDTO(List<Entity> entityList);
    List<Entity> convertDTOToEntity(List<DTO> dtoList);
}
