package hac.ex5.controller;

import hac.ex5.model.ScamReport;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Controller to handle WebSocket communication for scam reports.
 * Enables real-time interaction by receiving and broadcasting scam report messages to subscribed clients.
 */
@Controller
public class WebSocketController {

    /**
     * Maps incoming messages on "/scamReports" to this method which then broadcasts the message
     * to subscribers of "/topic/scamReports".
     * @param scamReport the scam report received from the client
     * @return the scam report that will be broadcast to subscribed clients
     */
    @MessageMapping("/scamReports")
    @SendTo("/topic/scamReports")
    public ScamReport scamReport(ScamReport scamReport) {
        // Simply returns the received scam report which will be automatically sent to the subscribed topic
        return scamReport;
    }
}
