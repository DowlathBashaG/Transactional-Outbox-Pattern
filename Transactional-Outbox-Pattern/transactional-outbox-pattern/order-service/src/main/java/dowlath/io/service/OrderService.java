package dowlath.io.service;

import dowlath.io.common.dto.OrderRequestDTO;
import dowlath.io.common.mapper.OrderDTOtoEntityMapper;
import dowlath.io.common.mapper.OrderEntityToOutboxEntityMapper;
import dowlath.io.entity.Order;
import dowlath.io.entity.Outbox;
import dowlath.io.repository.OrderRepository;
import dowlath.io.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderDTOtoEntityMapper orderDTOtoEntityMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OutboxRepository outboxRepository;

    @Autowired
    private OrderEntityToOutboxEntityMapper orderEntityToOutboxEntityMapper;


    @Transactional
    public Order createOrder(OrderRequestDTO orderRequestDTO) {

        Order order = orderDTOtoEntityMapper.map(orderRequestDTO);
        order = orderRepository.save(order);

        Outbox outbox = orderEntityToOutboxEntityMapper.map(order);
        outboxRepository.save(outbox);

        return order;
    }
}
