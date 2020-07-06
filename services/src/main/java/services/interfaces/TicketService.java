package services.interfaces;

import dto.PersonDTO;
import dto.TicketDTO;
import validators.exceptions.tickets.UserIsNotOwnerException;

import java.util.List;

public interface TicketService {
    TicketDTO findTicket(Integer id);
    List<TicketDTO> findFreeTickets(int flightID);
    /*String generateTicketInfo(List<TicketDTO> ticketList);
    String generateTicketInfo(TicketDTO ticket);*/
    /*boolean ticketIsFree(TicketDTO ticket);*/
    /*boolean setTicketOwner(TicketDTO ticket, PersonDTO owner);*/
    /*TicketDTO bookTicket(int rowNumber, int seatNumber, int flightID, PersonDTO owner);*/
    TicketDTO bookTicket(TicketDTO ticket, PersonDTO owner);
    //List<TicketDTO> findPersonTickets(PersonDTO person);
    //boolean isBusinessClass(TicketDTO ticket);
    void cancelBooking(int flightID, String username) throws UserIsNotOwnerException;
//    List<Ticket> addTickets(List<Ticket> tickets);
}