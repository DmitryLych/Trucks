package lych.trucks.application.controller;

import lombok.RequiredArgsConstructor;
import lych.trucks.application.dto.request.InsurancePolicyRequest;
import lych.trucks.application.dto.response.InsurancePolicyResponse;
import lych.trucks.domain.model.InsurancePolicy;
import lych.trucks.domain.service.InsurancePolicyService;
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

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InsurancePolicyController {

    private final DozerBeanMapper dozerBeanMapper;

    private final InsurancePolicyService insurancePolicyService;

    @RequestMapping(value = "/companies/{companyId}/drivers/{driverId}/insurance", method = RequestMethod.POST)
    public ResponseEntity create(@PathVariable final Integer driverId, @RequestBody final InsurancePolicyRequest request) {

        final InsurancePolicy insurancePolicyToCreate = dozerBeanMapper.map(request, InsurancePolicy.class);

        final InsurancePolicy insurancePolicyToResponse = insurancePolicyService.create(driverId, insurancePolicyToCreate);

        final InsurancePolicyResponse response = dozerBeanMapper.map(insurancePolicyToResponse, InsurancePolicyResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @RequestMapping(value = "/companies/{companyId}/drivers/{driverId}/insurance", method = RequestMethod.GET)
    public ResponseEntity fetchAll(@PathVariable final Integer driverId) {

        final List<InsurancePolicyResponse> response = new ArrayList<>();

        insurancePolicyService.fetchAll(driverId).forEach(insurancePolicy -> response.add(dozerBeanMapper.map(insurancePolicy, InsurancePolicyResponse.class)));

        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(value = "/companies/{companyId}/drivers/{driverId}/insurance/{insuranceId}", method = RequestMethod.GET)
    public ResponseEntity fetch(@PathVariable final Integer insuranceId) {

        final InsurancePolicy insurancePolicyToResponse = insurancePolicyService.fetch(insuranceId);

        final InsurancePolicyResponse response = dozerBeanMapper.map(insurancePolicyToResponse, InsurancePolicyResponse.class);

        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(value = "/companies/{companyId}/drivers/{driverId}/insurance", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody final InsurancePolicyRequest request) {

        final InsurancePolicy insurancePolicyToUpdate = dozerBeanMapper.map(request, InsurancePolicy.class);

        final InsurancePolicy insurancePolicyToResponse = insurancePolicyService.update(insurancePolicyToUpdate);

        final InsurancePolicyResponse response = dozerBeanMapper.map(insurancePolicyToResponse, InsurancePolicyResponse.class);

        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(value = "/companies/{companyId}/drivers/{driverId}/insurance/{insuranceId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable final Integer insuranceId) {

        final InsurancePolicy insurancePolicyToResponse = insurancePolicyService.fetch(insuranceId);

        insurancePolicyService.delete(insuranceId);

        final InsurancePolicyResponse response = dozerBeanMapper.map(insurancePolicyToResponse, InsurancePolicyResponse.class);

        return ResponseEntity.ok().body(response);
    }
}