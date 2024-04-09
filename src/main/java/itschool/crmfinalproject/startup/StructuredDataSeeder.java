package itschool.crmfinalproject.startup;

import com.github.javafaker.Faker;
import itschool.crmfinalproject.common.entity.Address;
import itschool.crmfinalproject.companies.entity.Company;
import itschool.crmfinalproject.contacts.entity.Contact;
import itschool.crmfinalproject.sectors.entity.Sector;
import itschool.crmfinalproject.companies.repository.CompanyRepository;
import itschool.crmfinalproject.contacts.repository.ContactRepository;
import itschool.crmfinalproject.sectors.entity.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class StructuredDataSeeder implements CommandLineRunner {

    private final CompanyRepository companyRepository;
    private final ContactRepository contactRepository;
    private final SectorRepository sectorRepository;
    private final Faker faker;
    private final Random random = new Random();

    private final Set<String> usedEmails = new HashSet<>();
    private final Set<String> usedCompanyNames = new HashSet<>();
    private final Set<String> usedSectorNames = new HashSet<>();

    @Override
    @Transactional
    public void run(String... args) {
        if (sectorRepository.count() == 0) {
            IntStream.range(0, 15).forEach(i -> {
                Sector sector = createUniqueSector();
                sectorRepository.save(sector);

                int companiesInSector = random.nextInt(1, 5);
                IntStream.range(0, companiesInSector).forEach(j -> {
                    Company company = createUniqueCompany(sector);
                    companyRepository.save(company);

                    int contactsInCompany = random.nextInt(1, 10);
                    IntStream.range(0, contactsInCompany).forEach(k -> {
                        Contact contact = createUniqueContact(company);
                        contactRepository.save(contact);
                    });
                });
            });
        }
    }

    private Sector createUniqueSector() {
        Sector sector = new Sector();
        do {
            sector.setSectorName(faker.commerce().department());
            sector.setMarketCap(faker.number().randomDouble(2, 10000, 50000));
        } while (!usedSectorNames.add(sector.getSectorName()));
        return sector;
    }

    private Company createUniqueCompany(Sector sector) {
        Company company = new Company();
        do {
            company.setName(faker.company().name());
            company.setEvaluation(faker.number().randomDouble(2, 10000, 100000));
            company.setIncomeFromCompany(faker.number().randomDouble(2, 5000, 20000)); // Adding income attribute
            company.setAddress(createAddress());
            company.setSector(sector);
        } while (!usedCompanyNames.add(company.getName()));
        return company;
    }

    private Contact createUniqueContact(Company company) {
        Contact contact = new Contact();
        do {
            contact.setFirstName(faker.name().firstName());
            contact.setLastName(faker.name().lastName());
            contact.setEmail(getUniqueEmail());
            contact.setPhoneNumber(faker.phoneNumber().phoneNumber());
            contact.setPosition(faker.job().position());
            contact.setDescription(faker.lorem().paragraph());
            contact.setTags(generateSalesTags());
            contact.setAddress(createAddress());
            contact.setCompany(company);
            contact.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(30)));
        } while (!usedEmails.add(contact.getEmail()));
        return contact;
    }

    private Set<String> generateSalesTags() {
        List<String> tags = new ArrayList<>(List.of("Lead", "Prospect", "Converted", "Hot", "Cold"));
        Collections.shuffle(tags);
        return new HashSet<>(tags.subList(0, random.nextInt(1, tags.size())));
    }

    private Address createAddress() {
        return new Address(faker.address().streetAddress(), faker.address().city(), faker.address().state(), faker.address().country());
    }

    private String getUniqueEmail() {
        String email;
        do {
            email = faker.internet().emailAddress();
        } while (usedEmails.contains(email));
        return email;
    }
}