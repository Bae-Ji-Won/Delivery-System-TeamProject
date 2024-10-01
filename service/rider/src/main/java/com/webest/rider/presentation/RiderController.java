package com.webest.rider.presentation;

import static com.webest.web.exception.ErrorCode.INVALID_INPUT;

import com.webest.rider.application.RiderService;
import com.webest.rider.domain.model.RiderSearchCondition;
import com.webest.rider.presentation.dtos.request.RiderCreateRequestDto;
import com.webest.rider.presentation.dtos.request.RiderRegisterAddressRequestDto;
import com.webest.rider.presentation.dtos.request.RiderUpdateRequestDto;
import com.webest.rider.presentation.dtos.response.RiderResponseDto;
import com.webest.web.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/riders")
@RestController
public class RiderController {

    private final RiderService riderService;

    /**
     * 라이더 생성
     */
    @PostMapping
    public CommonResponse<Long> createRider(
        @RequestBody RiderCreateRequestDto requestDto
    ) {
        Long riderId = riderService.createRider(requestDto);

        return CommonResponse.success(riderId);
    }

    /**
     * 라이더에 속한 주소 등록
     */
    @PutMapping("/{id}/address")
    public CommonResponse<RiderResponseDto> registerAddressToRider(
        @PathVariable("id") Long riderId,
        @RequestBody RiderRegisterAddressRequestDto requestDto
    ) {
        RiderResponseDto riderResponseDto = riderService.registerAddressToRider(riderId,
            requestDto);

        return CommonResponse.success(riderResponseDto);
    }

    /**
     * 라이더 단일 조회
     */
    @GetMapping("/{id}")
    public CommonResponse<RiderResponseDto> findRiderById(
        @PathVariable("id") Long id
    ) {

        RiderResponseDto riderById = riderService.findRiderById(id);

        return CommonResponse.success(riderById);
    }


    /**
     * 라이더 검색
     */
    @GetMapping
    public CommonResponse<Page<RiderResponseDto>> findRiderList(
        @PageableDefault(size = 10, page = 0) Pageable pageable,
        @ModelAttribute RiderSearchCondition condition
    ) {
        Page<RiderResponseDto> riderListByCondition = riderService.findRiderListByCondition(
            pageable, condition);

        return CommonResponse.success(riderListByCondition);
    }

    /**
     * 라이디 아이디로 업데이트
     */
    @PatchMapping("/{id}")
    public CommonResponse<?> updateRiderById(
        @PathVariable("id") Long id,
        @RequestBody RiderUpdateRequestDto requestDto
    ) {
        RiderResponseDto riderResponseDto = riderService.updateRiderById(id, requestDto);

        return CommonResponse.success(riderResponseDto);
    }

    /**
     * 라이더 삭제
     */
    @DeleteMapping("/{id}")
    public CommonResponse<Long> deleteRiderById(
        @PathVariable("id") Long id
    ) {
        Long deletedId = riderService.deleteRiderById(id);

        return CommonResponse.success(deletedId);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<? extends CommonResponse<?>> IllegalArgumentExceptionHandler(
        IllegalArgumentException exception
    ) {

        return ResponseEntity.status(INVALID_INPUT.getStatus())
            .body(
                CommonResponse.error(INVALID_INPUT.getStatus(), INVALID_INPUT.getMessage()));
    }
}
