package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.passport.request.CreateAndUpdatePassportRequest;
import am.agrotrade.common.dto.passport.response.PassportInfoResponse;
import am.agrotrade.core.security.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing user passport information within the Agro Trade service.
 * <p>
 * Provides endpoints for retrieving, creating, updating, and deleting
 * passport details associated with the authenticated user's profile.
 */
@RestController
@RequestMapping("/agro-trade-service/api/v1/user/passport")
public interface PassportV1API {

    /**
     * Retrieves the passport information for the currently authenticated user.
     *
     * @param userPrincipal the authenticated user's security principal
     * @return {@link PassportInfoResponse} containing the user's passport details
     */
    @GetMapping()
    PassportInfoResponse get(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    );

    /**
     * Creates a new passport record for the authenticated user.
     *
     * @param user    the authenticated user's security principal
     * @param request the {@link CreateAndUpdatePassportRequest} containing passport data
     * @return {@link PassportInfoResponse} representing the newly created passport
     */
    @PostMapping()
    PassportInfoResponse create(
            @AuthenticationPrincipal UserPrincipal user,
            @Valid @RequestBody CreateAndUpdatePassportRequest request
    );

    /**
     * Updates the existing passport information for the authenticated user.
     *
     * @param user    the authenticated user's security principal
     * @param request the {@link CreateAndUpdatePassportRequest} with updated information
     * @return {@link PassportInfoResponse} representing the updated passport record
     */
    @PutMapping()
    PassportInfoResponse update(
            @AuthenticationPrincipal UserPrincipal user,
            @Valid @RequestBody CreateAndUpdatePassportRequest request
    );

    /**
     * Deletes the passport record associated with the authenticated user.
     *
     * @param user the authenticated user's security principal
     */
    @DeleteMapping()
    void delete(
            @AuthenticationPrincipal UserPrincipal user
    );

}