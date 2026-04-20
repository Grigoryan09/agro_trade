package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.media.response.MediaResponse;
import am.agrotrade.common.enums.EntityType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Media V1", description = "Media upload endpoints.")
@RestController
@RequestMapping("/agro-trade-service/api/v1/media")
public interface MediaV1API {

    @Operation(summary = "Upload media", description = "Uploads one or more files and binds them to an entity.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Files uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid upload request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Target entity not found", content = @Content)
    })
    @PostMapping("/{entityType}/{entityId}")
    MediaResponse uploadImages(
            @Parameter(description = "Entity type that will own the uploaded files")
            @PathVariable EntityType entityType,
            @Parameter(description = "Identifier of the target entity", example = "1")
            @PathVariable long entityId,
            @Parameter(
                    description = "Files to upload",
                    content = @Content(mediaType = "multipart/form-data", schema = @Schema(type = "string", format = "binary"))
            )
            @RequestParam("files") List<MultipartFile> files);
}
