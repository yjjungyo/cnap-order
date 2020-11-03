package bookmarket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long>{
//mongoDB를 사용하기 위해 interface 변경
//public interface OrderRepository extends JpaRepository<Order, UUID> {
}