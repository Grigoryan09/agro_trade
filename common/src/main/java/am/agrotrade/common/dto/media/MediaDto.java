package am.agrotrade.common.dto.media;

public record MediaDto(
        Long id,
        String fileName,
        String fileType,
        String subFolder,
        Long entityId,
        String url
) {

    public static String buildUrl(String baseUrl, String subFolder, String fileName) {
        if (fileName == null || subFolder == null) return null;
        return String.format("%s/media/%s/%s", baseUrl, subFolder, fileName);
    }
}
