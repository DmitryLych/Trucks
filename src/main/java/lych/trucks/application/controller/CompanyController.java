package lych.trucks.application.controller;

import lombok.RequiredArgsConstructor;
import lych.trucks.application.dto.request.CompanyRequest;
import lych.trucks.application.dto.response.CompanyResponse;
import lych.trucks.domain.model.Company;
import lych.trucks.domain.service.CompanyService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Rest controller for {@link Company}.
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyController {

    private final CompanyService companyService;

    private final DozerBeanMapper dozerBeanMapper;

    /**
     * Method for create company.
     *
     * @param request CompanyRequest request.
     * @return CompanyResponse response mapped from created company.
     */
    @RequestMapping(value = "/companies", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody final CompanyRequest request) {

        final Company companyToSave = dozerBeanMapper.map(request, Company.class);

        final Company companyToResponse = companyService.create(companyToSave);

        final CompanyResponse response = dozerBeanMapper.map(companyToResponse, CompanyResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Method for display some company.
     *
     * @param companyId Company companyId.
     * @return CompanyResponse response mapped from found company.
     */
    @RequestMapping(value = "/companies/{companyId}", method = RequestMethod.GET)
    public ResponseEntity fetch(@PathVariable final Integer companyId) {

        final Company companyToResponse = companyService.fetch(companyId);

        final CompanyResponse response = dozerBeanMapper.map(companyToResponse, CompanyResponse.class);

        return ResponseEntity.ok().body(response);
    }

    /**
     * Method for update some company.
     *
     * @param request CompanyRequest request.
     * @return CompanyResponse response mapped from updated company.
     */
    @RequestMapping(value = "/companies", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody final CompanyRequest request) {

        final Company companyToUpdate = dozerBeanMapper.map(request, Company.class);

        final Company companyToResponse = companyService.update(companyToUpdate);

        final CompanyResponse response = dozerBeanMapper.map(companyToResponse, CompanyResponse.class);

        return ResponseEntity.ok().body(response);
    }

    /**
     * Method for delete some company.
     *
     * @param companyId Company companyId.
     * @return CompanyResponse response mapped from deleted company.
     */
    @RequestMapping(value = "/companies/{companyId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable final Integer companyId) {

        final Company companyToResponse = companyService.fetch(companyId);

        companyService.delete(companyId);

        final CompanyResponse response = dozerBeanMapper.map(companyToResponse, CompanyResponse.class);

        return ResponseEntity.ok().body(response);
    }

    /**
     * Method for find all companies.
     *
     * @return List of found companies.
     */
    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    public ResponseEntity fetchAll() {

        final List<CompanyResponse> response = new ArrayList<>();

        companyService.fetchAll().forEach(company ->
                response.add(dozerBeanMapper.map(company, CompanyResponse.class)));

        return ResponseEntity.ok().body(response);
    }
}
