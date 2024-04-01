package com.ssu.commerce.order.constant;

public enum OrderState {
    REQUESTED,  // 주문 생성 요청
    RETURNED,    // 반납됨

    FAILED,     // 주문 생성 실패


    /***
     *   TODO 현재 주문 생성 시 분산락을 통해 포인트 송금이 진행된다.
     *         이에 동시성 문제를 분산락으로 해결하고 있으며, 추후에는
     *         주문 생성은 모두 진행하고, 승인/거절을 도입할 예정
     */
}
