package lych.trucks.domain.service;

import lombok.RequiredArgsConstructor;
import lych.trucks.domain.model.Company;
import lych.trucks.domain.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link CompanyService}.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultCompanyService implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public List<Company> fetchAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company createCompany(final Company company) {
        validateCompany(company);

        return companyRepository.save(company);
    }

    @Override
    public Company fetchCompany(final Integer id) {
        return Optional.ofNullable(companyRepository.findOne(id))
                .orElseThrow(() -> new IllegalArgumentException("Can`t find Company by Id."
                        + " Company with this Id: '" + id + "' not exist."));
    }

    @Override
    public Company deleteCompany(final Integer id) {

        final Company company = fetchCompany(id);

        companyRepository.delete(id);

        return company;
    }

    @Override
    @SuppressWarnings("PMD.NPathComplexity")
    public Company updateCompany(final Company company) {
        validateCompany(company);

        final Company saved = fetchCompany(company.getId());

        company.setAddress(company.getAddress() == null ? saved.getAddress() : company.getAddress());
        company.setCompanyName(company.getCompanyName() == null ? saved.getCompanyName() : company.getCompanyName());
        company.setDrivers(company.getDrivers() == null ? saved.getDrivers() : company.getDrivers());
        company.setEmail(company.getEmail() == null ? saved.getEmail() : company.getEmail());
        company.setTelephoneNumber(company.getTelephoneNumber() == null ? saved.getTelephoneNumber()
                : company.getTelephoneNumber());

        return createCompany(company);
    }

    @Override
    public Company fetchCompanyByCompanyName(final String companyName) {

        return Optional.ofNullable(companyRepository.findByCompanyName(companyName))
                .orElseThrow(() -> new IllegalArgumentException("Can`t find Company by Company name."
                        + "Company with this Company name: '" + companyName
                        + "' not exist."));
    }

    private static void validateCompany(final Company company) {
        if (company == null) {
            throw new IllegalArgumentException("Company can`t be null.");
        }
    }
}
