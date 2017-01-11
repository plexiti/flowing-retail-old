package io.flowing.retail.commands.orchestration.camunda;

import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;

import io.flowing.retail.commands.Order;
import io.flowing.retail.commands.OrderEventProducer;
import io.flowing.retail.commands.OrderRepository;

public class ShipGoodsAdapter extends CommandPubEventSubAdapter {

  @Override
  public void execute(ActivityExecution execution) throws Exception {
    OrderEventProducer eventProducer = new OrderEventProducer();
    Order order = orderRepository.getOrder((String)execution.getVariable("orderId")); 
    String pickId = (String)execution.getVariable("pickId");
    
    execution.setVariableLocal("responseEventName", "GoodsShipped");
    // TODO: Maybe use eventId and responseId?
    // what about transactionId
    eventProducer.publishCommandShipGoods(order, pickId);
    // Can response be faster than the current transaction?
    // Kafka Client Commit?
  }

}
