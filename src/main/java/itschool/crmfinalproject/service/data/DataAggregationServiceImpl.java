package itschool.crmfinalproject.service.data;

import itschool.crmfinalproject.entity.app.Company;
import itschool.crmfinalproject.entity.app.Contact;
import itschool.crmfinalproject.entity.app.event.Event;
import itschool.crmfinalproject.model.analysis.IncomeEventParticipationDataDTO;
import itschool.crmfinalproject.model.contact.ContactBaseDTO;
import itschool.crmfinalproject.repository.ContactRepository;
import itschool.crmfinalproject.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataAggregationServiceImpl implements DataAggregationService {

    private final EventRepository eventRepository;
    private final ContactRepository contactRepository;

//    @Override
//    public AggregatedDataDTO getAggregatedData() {
//        List<Event> events = eventRepository.findAll();
//        List<Contact> contacts = contactRepository.findAll();
//
//        // Now, you can aggregate data from both sources
//        // For illustration, let's just count items from both sources
//        AggregatedDataDTO aggregatedData = new AggregatedDataDTO();
//        aggregatedData.setTotalEvents(events.size());
//        aggregatedData.setTotalContacts(contacts.size());
//
//        // Additional complex aggregation logic can be implemented here
//        // For example, correlating event participation with company income
//
//        return aggregatedData;
//    }


    @Override
    public List<IncomeEventParticipationDataDTO> getIncomeEventParticipationData() {
        List<Event> events = eventRepository.findAll();

        return events.stream().map(event -> {
            String category = event.getEventCategory();
            Set<String> participatingCompanies = new HashSet<>();
            Set<Contact> participantContacts = event.getParticipantContacts().stream()
                    .map(this::findContactByDTO)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());

            double totalIncome = participantContacts.stream()
                    .map(Contact::getCompany)
                    .filter(Objects::nonNull)
                    .peek(company -> participatingCompanies.add(company.getName()))
                    .map(Company::getIncomeFromCompany)
                    .filter(Objects::nonNull)
                    .mapToDouble(Double::doubleValue)
                    .sum();

            int totalParticipants = participantContacts.size();

            return new IncomeEventParticipationDataDTO(category, totalIncome, totalParticipants, participatingCompanies);
        }).toList();
    }

    private Optional<Contact> findContactByDTO(ContactBaseDTO dto) {
        return contactRepository.findById(dto.id());
    }
}
