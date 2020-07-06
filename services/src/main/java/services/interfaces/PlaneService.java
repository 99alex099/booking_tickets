package services.interfaces;

import dto.PlaneDTO;
import dto.PlaneRowDTO;
import validators.exceptions.general.InvalidTextException;

import java.util.List;

public interface PlaneService {
    PlaneDTO findPlaneById(int id);
    List<PlaneDTO> findPlanes();
    PlaneDTO addPlane(PlaneDTO plane);
    List<PlaneRowDTO> findPlaneSeats(Integer planeId);
    List<PlaneRowDTO> findPlaneSeats(PlaneDTO plane);
    /*PlaneRowDTO addPlaneSeats(Integer countOfSeats, Boolean isBusinessClass,
                              Integer planeId);*/
    PlaneRowDTO addPlaneSeats(PlaneRowDTO planeRow, Integer planeId);
    PlaneDTO savePlane(PlaneDTO plane);
    void deletePlane(Integer id);
    void deletePlaneSeat(Integer id);
}
