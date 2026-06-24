package am.agrotrade.common.dto.news.response;

import am.agrotrade.common.dto.news.BaseNewsInfoDto;

import java.util.List;

public record NewsResponse(List<BaseNewsInfoDto> baseNewsInfoDto) {
}
