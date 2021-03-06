package lych.trucks.domain.service;

import lych.trucks.domain.model.DriverLicense;

import java.util.List;

/**
 * Service for {@link DriverLicense} work with database.
 */
public interface DriverLicenseService {

    /**
     * Method for create Driver license.
     *
     * @param driverId      Driver driverId.
     * @param driverLicense DriverLicense driverLicense.
     * @return created DriverLicense driverLicense.
     */
    DriverLicense createDriverLicense(Integer driverId, DriverLicense driverLicense);

    /**
     * Method for fetch Driver license by id.
     *
     * @param driverId Driver driverId.
     * @return found DriverLicense response.
     */
    DriverLicense fetchDriverLicense(Integer driverId);

    /**
     * Method for update Driver license.
     *
     * @param driverLicense DriverLicense driverLicense.
     * @return updated DriverLicense driverLicense.
     */
    DriverLicense updateDriverLicense(DriverLicense driverLicense);

    /**
     * Method for fetch driver licenses by category.
     *
     * @param category DriverLicense category.
     * @return list of driver licenses which found.
     */
    List<DriverLicense> fetchDriverLicensesByCategory(String category);

    /**
     * Method for fetch driver license by special notes.
     *
     * @param specialNotes DriverLicense specialNotes.
     * @return list of driver license which found.
     */
    List<DriverLicense> fetchDriverLicensesBySpecialNotes(String specialNotes);
}
