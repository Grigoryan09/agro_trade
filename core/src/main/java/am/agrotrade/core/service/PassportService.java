package am.agrotrade.core.service;

import am.agrotrade.common.dto.passport.PassportInfoDto;
import am.agrotrade.common.dto.passport.request.CreateAndUpdatePassportRequest;

/**
 * Handles passport data for user profiles.
 */
public interface PassportService {

    /**
     * Returns passport data for the specified user.
     *
     * @param userId user identifier
     * @return passport data
     */
    PassportInfoDto getPassport(long userId);

    /**
     * Creates passport data for the specified user.
     *
     * @param userId user identifier
     * @param request passport payload
     * @return created passport data
     */
    PassportInfoDto add(long userId, CreateAndUpdatePassportRequest request);

    /**
     * Updates passport data for the specified user.
     *
     * @param userId user identifier
     * @param request updated passport payload
     * @return updated passport data
     */
    PassportInfoDto update(long userId, CreateAndUpdatePassportRequest request);

    /**
     * Deletes passport data for the specified user.
     *
     * @param userId user identifier
     */
    void delete(long userId);
}
