package lych.trucks.application.controller;

import lombok.RequiredArgsConstructor;
import lych.trucks.application.dto.request.TruckRequest;
import lych.trucks.application.dto.response.TruckResponse;
import lych.trucks.domain.model.Truck;
import lych.trucks.domain.service.TruckService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for {@link Truck}.
 */
@RestController
@RequestMapping("/cargo/v1/{userId}/companies/{companyId}/drivers/{driverId}/trucks")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TruckController {

    private final DozerBeanMapper dozerBeanMapper;

    private final TruckService truckService;

    /**
     * Method for create truck.
     *
     * @param companyId a company id.
     * @param userId    a user id.
     * @param driverId  Driver driverId.
     * @param request   TruckRequest request.
     * @return TruckResponse response mapped from created truck.
     */
    @PostMapping
    @PreAuthorize("@defaultCompanyService.canAccess(#userId,#companyId)")
    public ResponseEntity createTrucks(@PathVariable final Integer userId,
                                       @PathVariable final Integer companyId,
                                       @PathVariable final Integer driverId,
                                       @RequestBody final TruckRequest request) {
        final Truck truckToCreate = dozerBeanMapper.map(request, Truck.class);
        final Truck truckToResponse = truckService.createTruck(driverId, truckToCreate);

        final TruckResponse response = dozerBeanMapper.map(truckToResponse, TruckResponse.class);
        return ResponseEntity.ok(response);
    }

    /**
     * Method for update truck.
     *
     * @param companyId a company id.
     * @param userId    a user id.
     * @param request   TruckRequest request.
     * @return TruckResponse response mapped from updated truck.
     */
    @PutMapping
    @PreAuthorize("@defaultCompanyService.canAccess(#userId,#companyId)")
    public ResponseEntity updateTrucks(@PathVariable final Integer userId,
                                       @PathVariable final Integer companyId,
                                       @RequestBody final TruckRequest request) {
        final Truck truckToUpdate = dozerBeanMapper.map(request, Truck.class);
        final Truck truckToResponse = truckService.updateTruck(truckToUpdate);

        final TruckResponse response = dozerBeanMapper.map(truckToResponse, TruckResponse.class);
        return ResponseEntity.ok(response);
    }

    /**
     * Method for display some truck.
     *
     * @param userId   a user id.
     * @param driverId Driver driverId.
     * @return TruckResponse response mapped from found truck.
     */
    @GetMapping
    @PreAuthorize("@defaultDriverService.canAccess(#userId,#driverId)")
    public ResponseEntity fetchTrucks(@PathVariable final Integer userId,
                                      @PathVariable final Integer driverId) {
        final Truck truckToResponse = truckService.fetchTruck(driverId);

        final TruckResponse response = dozerBeanMapper.map(truckToResponse, TruckResponse.class);
        return ResponseEntity.ok(response);
    }

    /**
     * Method for fetch truck by register sign.
     *
     * @param companyId    a company id.
     * @param userId       a user id.
     * @param registerSign {@link Truck} registerSign.
     * @return {@link TruckResponse} response mapped from truck which found.
     */
    @GetMapping(path = "/register/{registerSign}")
    @PreAuthorize("@defaultCompanyService.canAccess(#userId,#companyId)")
    public ResponseEntity fetchTrucksByRegisterSign(@PathVariable final Integer userId,
                                                    @PathVariable final Integer companyId,
                                                    @PathVariable final String registerSign) {
        final Truck truckToResponse = truckService.fetchTruckByRegisterSign(registerSign);

        final TruckResponse response = dozerBeanMapper.map(truckToResponse, TruckResponse.class);
        return ResponseEntity.ok(response);
    }

    /**
     * Method for fetch truck by body number.
     *
     * @param companyId  a company id.
     * @param userId     a user id.
     * @param bodyNumber {@link Truck} bodyNumber.
     * @return {@link TruckResponse} response mapped from truck which found.
     */
    @GetMapping(path = "/number/{bodyNumber}")
    @PreAuthorize("@defaultCompanyService.canAccess(#userId,#companyId)")
    public ResponseEntity fetchTrucksByBodyNumber(@PathVariable final Integer userId,
                                                  @PathVariable final Integer companyId,
                                                  @PathVariable final String bodyNumber) {
        final Truck truckToResponse = truckService.fetchTruckByBodyNumber(bodyNumber);

        final TruckResponse response = dozerBeanMapper.map(truckToResponse, TruckResponse.class);
        return ResponseEntity.ok(response);
    }
}
