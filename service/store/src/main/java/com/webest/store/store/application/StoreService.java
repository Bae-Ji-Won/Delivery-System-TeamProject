package com.webest.store.store.application;

import com.webest.store.store.api.dto.CreateStoreRequest;
import com.webest.store.store.api.dto.StoreResponse;
import com.webest.store.store.api.dto.UpdateStoreAddressRequest;
import com.webest.store.store.domain.Store;
import com.webest.store.store.domain.StoreRepository;
import com.webest.store.store.domain.StoreStatus;
import com.webest.store.store.exception.StoreErrorCode;
import com.webest.store.store.exception.StoreException;
import com.webest.store.store.infra.naver.NaverGeoClient;
import com.webest.store.store.infra.naver.dto.GeoResponse;
import com.webest.store.store.infra.naver.dto.NaverAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.geom.Arc2D;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final NaverGeoClient naverGeoClient;

    // 가게 생성
    // 가게 주소, 위경도, 배달 반경, 배달 팁은 생성 시 설정하지 않고 따로 업데이트
    @Transactional
    public StoreResponse saveStore(CreateStoreRequest request) {
        Store store = request.toEntity();
        storeRepository.save(store);
        return StoreResponse.of(store);
    }

    // 가게 주소로 위 경도 등록
    @Transactional
    public StoreResponse updateStoreAddress(UpdateStoreAddressRequest request) {
        Store store = findStoreById(request.storeId());

        // 네이버 Geocoding API로 위경도 가져오기
        GeoResponse geoResponse = naverGeoClient.getCoordinatesFromAddress(request.address());
        if (geoResponse.getAddresses() != null && !geoResponse.getAddresses().isEmpty()) {
            NaverAddress addressInfo = geoResponse.getAddresses().getFirst();

            Double latitude = Double.parseDouble(addressInfo.getY()); // 위도
            Double longitude = Double.parseDouble(addressInfo.getX()); // 경도

            store.updateAddress(request.address(), latitude, longitude);

        } else {
            throw new StoreException(StoreErrorCode.INVALID_ADDRESS);
        }
        return StoreResponse.of(store);
    }

    // 가게 단건 조회
    public StoreResponse getStoreById(Long id) {
        Store store = findStoreById(id);
        return StoreResponse.of(store);
    }



    // ID로 카테고리를 찾는 공통 메서드
    private Store findStoreById(Long id) {
        return storeRepository.findById(id).orElseThrow(
                () -> new StoreException(StoreErrorCode.STORE_NOT_FOUND)
        );
    }

    public Page<StoreResponse> getAllStores(Pageable pageable) {
        Page<Store> stores = storeRepository.findAll(pageable);
        return stores.map(StoreResponse::of);
    }
}
