package com.ssu.commerce.order.service;

import com.ssu.commerce.order.dto.request.ReturnBookRequestDto;
import com.ssu.commerce.order.dto.response.ReturnBookResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    public void createOrder() {
        /*
         * TODO 구현이 필요합니다.
         *  1. 도서 조회 API call (성엽)
         *   도서 상세 내용을 조회한다.
         *   -> request (bookId)
         *   -> response (bookId, 대출가능여부, 대여가능기간, 대여 장소(책 주인 위치), 대여비, 보증금, 배송 flag - 픽업 or 배송, 배송비 고정)
         *  2. 계좌(포인트) 조회 API call (상일)
         *   -> response (포인트 얼마있는지)
         *  3. 결제 정보 생성 (배송 flag - 픽업 or 배송) (주해)
         *   잔고 확인, 잔고 없으면 rollback
         *   -> response (orderId)
         *   -> order 에 flag 를 둬서 실패시에 fail ... 상태 남겨두기
         *  4. 결제 진행 (주해)
         *    2-1. 계좌(포인트) 수정 API call (상일)
         *      -> request (포인트 +/-)
         *  5. history 남기기
//         *  1-2. 책 주인에게 도서 대여 가능 여부 check (도서 - 성엽)
//         *    -> ok -> 상태 wait 로 변경 (주문 대기 상태면 여러 고객이 주문대기를 걸 수 있다.)
         */

        // TODO return 주문 정보 (to front)
    }

    // TODO (front -> order_confirmOrder)
    public void confirmOrder() {
        /*
         * TODO
         *  1. 책 주인이 주문 대기 queue 에서 선택하여 승인
         *  3. 결제 상태 변경
         *  3. 도서 배송 API call (order -> delivery) (현우)
         *   -> request (orderLocation, bookId)
         *   -> response ()
         *  5. history 남기기
         */
    }

//    public RentalBookResponseDto rentalBook(
//            RentalBookRequestDto requestDto
//    ) {
//        /*
//
//         * TODO
//         *  1. 책 주인이 주문 대기 queue 에서 선택하여 승인 (confirmOrder)
//         *  2. 도서 대여비 결제 (주해)
//         *   대여할 도서의 대여비를 결제한다.
//         *   -> request (userId, bookId, 대여비)
//         *   -> response (결제 내역)
//         *  3. 도서 배송 API call (order -> delivery) (현우)
//         *   -> request (orderLocation, bookId)
//         *   -> response ()
//         */
//        return RentalBookResponseDto.builder().build();
//    }

    public ReturnBookResponseDto returnBook(
            ReturnBookRequestDto responseDto
    ) {
        /*
         * TODO 구현이 필요합니다.
         *  1. 도서 조회 API call
         *  2. 도서 반납
         *  3. 도서 배송 API call
         */
        return ReturnBookResponseDto.builder().build();
    }

}
