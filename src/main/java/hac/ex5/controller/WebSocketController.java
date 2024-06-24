package hac.ex5.controller;

import hac.ex5.model.ScamReport;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/scamReports")
    @SendTo("/topic/scamReports")
    public ScamReport scamReport(ScamReport scamReport) {
        return scamReport;
    }
}
