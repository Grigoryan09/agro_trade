package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.user.request.RefreshTokenRequest;
import am.agrotrade.common.dto.user.response.RefreshTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Token V1", description = "Token lifecycle endpoints.")
@RestController
@RequestMapping("/auth/token")
public interface TokenV1API {

    @Operation(summary = "Refresh token", description = "Issues a new token pair using a valid refresh token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tokens refreshed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid refresh token request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Refresh token is invalid or expired", content = @Content)
    })
    @PostMapping("/refresh")
    RefreshTokenResponse refresh(@Valid @RequestBody RefreshTokenRequest request);
}
