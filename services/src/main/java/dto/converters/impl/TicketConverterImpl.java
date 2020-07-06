package dto.converters.impl;

import dto.TicketDTO;
import dto.converters.interfaces.TicketConverter;
import entity.Ticket;
import org.springframework.stereotype.Service;

@Service
public class TicketConverterImpl extends ConverterImpl<Ticket, TicketDTO> implements TicketConverter {

}
