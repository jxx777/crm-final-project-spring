package itschool.crmfinalproject.analysis.service;

import itschool.crmfinalproject.analysis.model.*;
import itschool.crmfinalproject.companies.entity.Company;
import itschool.crmfinalproject.contacts.entity.Contact;
import itschool.crmfinalproject.sectors.entity.Sector;
import itschool.crmfinalproject.events.document.Event;
import itschool.crmfinalproject.contacts.mapper.ContactMapper;
import itschool.crmfinalproject.contacts.model.ContactBaseDTO;
import itschool.crmfinalproject.contacts.repository.ContactRepository;
import itschool.crmfinalproject.events.repository.EventRepository;
import itschool.crmfinalproject.sectors.entity.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataAggregationServiceImpl implements DataAggregationService {

    private final EventRepository eventRepository;
    private final ContactRepository contactRepository;
    private final SectorRepository sectorRepository;
    private final ContactMapper contactMapper;

    @Override
    public AggregatedDataDTO getAggregatedData() {
        List<Event> events = eventRepository.findAll();
        List<Contact> contacts = contactRepository.findAll();

        double totalIncome = events.stream()
                .mapToDouble(Event::getIncome)
                .sum();

        double avgContactsPerEvent = events.stream()
                .filter(event -> event.getParticipantContacts() != null)
                .mapToInt(event -> event.getParticipantContacts().size())
                .average()
                .orElse(0.0);

        return new AggregatedDataDTO(
            events.size(),
            contacts.size(),
            totalIncome,
            avgContactsPerEvent
        );
    }

    @Override
    public List<IncomeEventParticipationDataDTO> getIncomeEventParticipationData() {
        List<Event> events = eventRepository.findAll();

        return events.stream().map(event -> {
            String category = event.getEventCategory();
            Set<String> participatingCompanies = new HashSet<>();
            Set<Contact> participantContacts = event.getParticipantContacts().stream()
                    .map(this::findContactByDTO)
                    .filter(Objects::nonNull)
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

    private Contact findContactByDTO(ContactBaseDTO contactBaseDTO) {
        return contactRepository.findById(contactBaseDTO.id()).orElse(null);
    }

    @Override
    public List<EventParticipationDetailDTO> getEventParticipationDetails() {
        // Iterate over events and for each event, identify participant companies
        return eventRepository.findAll().stream()
                .map(event -> new EventParticipationDetailDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getTime(),
                        event.getParticipantContacts().size(),
                        event.getIncome()))
                .toList();
    }

    @Override
    public List<SectorRevenueDTO> getSectorRevenueByEventIncome() {
        // Map each sector to its total revenue derived from events
        return sectorRepository.findAll().stream()
                .map(sector -> {
                    double totalRevenue = calculateTotalRevenueForSector(sector);
                    return new SectorRevenueDTO(sector.getSectorName(), totalRevenue);
                })
                .toList();
    }

    private double calculateTotalRevenueForSector(Sector sector) {
        // For each company in a sector, calculate income from associated events
        return sector.getCompanies().stream()
                .flatMapToDouble(company -> findEventsByCompany(company).stream().mapToDouble(Event::getIncome))
                .sum();
    }

    private List<Event> findEventsByCompany(Company company) {
        // Find events where any of the company's contacts are participants
        return eventRepository.findAll().stream()
                .filter(event -> event.getParticipantContacts().stream().anyMatch(contactBaseDTO ->
                        company.getContacts().stream().anyMatch(contact ->
                                contact.getId().equals(contactBaseDTO.id())
                        ))).toList();
    }

    @Override
    public List<CrossDatabaseCommentAnalysisDTO> getCrossDatabaseCommentAnalysis() {
        // Analyze comments across events and contacts
        return contactRepository.findAll().stream()
                .map(contact -> new CrossDatabaseCommentAnalysisDTO(
                        contact.getId().toString(),
                        contact.getFirstName() + " " + contact.getLastName(),
                        countCommentsForContact(contact)
                ))
                .toList();
    }

    private long countCommentsForContact(Contact contact) {
        // Count comments in events where the contact is a participant
        return eventRepository.findAll().stream()
                .filter(event -> event.getParticipantContacts().contains(contactMapper.toContactBaseDTO(contact)))
                .flatMapToLong(event -> event.getCommentIds().stream().mapToLong(id -> 1))
                .sum();
    }
}