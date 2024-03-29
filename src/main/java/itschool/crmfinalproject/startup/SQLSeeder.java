package itschool.crmfinalproject.startup;

import com.github.javafaker.Faker;
import itschool.crmfinalproject.entity.app.Address;
import itschool.crmfinalproject.entity.app.Company;
import itschool.crmfinalproject.entity.app.Contact;
import itschool.crmfinalproject.entity.app.Sector;
import itschool.crmfinalproject.repository.CompanyRepository;
import itschool.crmfinalproject.repository.ContactRepository;
import itschool.crmfinalproject.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class SQLSeeder implements CommandLineRunner {

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
        if (sectorRepository.count() == 0 && companyRepository.count() == 0 && contactRepository.count() == 0) {
            IntStream.range(0, 10).forEach(i -> {
                Sector sector = createUniqueSector();
                sectorRepository.save(sector);

                IntStream.range(0, 10).forEach(j -> {
                    Company company = createUniqueCompany(sector);
                    companyRepository.save(company);

                    IntStream.range(0, 10).forEach(k -> {
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
            sector.setCreatedBy("seeder");
        } while (!usedSectorNames.add(sector.getSectorName()));
        return sector;
    }

    private Company createUniqueCompany(Sector sector) {
        Company company = new Company();
        do {
            company.setName(faker.company().name());
            company.setEvaluation(faker.number().randomDouble(2, 10000, 100000));
            company.setIncomeFromCompany(faker.number().randomDouble(2, 10000, 100000));
            company.setAddress(createAddress());
            company.setSector(sector);
            company.setCreatedBy("seeder");
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
            contact.setTags(new HashSet<>(faker.lorem().words(5)));
            contact.setAddress(createAddress());
            contact.setCompany(company);
            contact.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(30)));
            contact.setCreatedBy("seeder");
        } while (contactRepository.findByEmail(contact.getEmail()).isPresent());
        return contact;
    }

    private Address createAddress() {
        Address address = new Address();
        address.setStreet(faker.address().streetAddress());
        address.setCity(faker.address().city());
        address.setState(faker.address().state());
        address.setCountry(faker.address().country());
        return address;
    }

    private String getUniqueEmail() {
        String email;
        do {
            email = faker.internet().emailAddress();
        } while (!usedEmails.add(email));
        return email;
    }
}