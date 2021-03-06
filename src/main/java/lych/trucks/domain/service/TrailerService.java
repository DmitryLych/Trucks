package lych.trucks.domain.service;

import lych.trucks.domain.model.Trailer;

import java.util.List;

/**
 * Service for {@link Trailer} work with database.
 */
public interface TrailerService {

    /**
     * Method for create trailer.
     *
     * @param driverId Driver DriverId.
     * @param truckId  Truck truckId.
     * @param trailer  Trailer trailer.
     * @return created trailer.
     */
    Trailer createTrailer(Integer driverId, Integer truckId, Trailer trailer);

    /**
     * Method for find trailer.
     *
     * @param truckId Truck truckId.
     * @return trailer which found.
     */
    Trailer fetchTrailer(Integer truckId);

    /**
     * Method for update trailer.
     *
     * @param trailer Trailer trailer.
     * @return updated trailer.
     */
    Trailer updateTrailer(Trailer trailer);

    /**
     * Method for fetch trailer by register sign.
     *
     * @param registerSign Trailer registerSign.
     * @return trailer which found.
     */
    Trailer fetchTrailerByRegisterSign(String registerSign);

    /**
     * Method for fetch trailer by volume.
     *
     * @param volume Trailer volume.
     * @return list of trailers which found.
     */
    List<Trailer> fetchTrailersByVolume(Integer volume);

    /**
     * Method for fetch trailers by type.
     *
     * @param type Trailer type.
     * @return list of trailers which found.
     */
    List<Trailer> fetchTrailersByType(String type);
}
