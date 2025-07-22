import com.tibco.tibrv.*;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.*;

@RestController
public class RvRequestController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

    public RvRequestController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendWithTimeout(@RequestBody Map<String, Object> payload) {
        String id = (String) payload.get("id");
        String data = (String) payload.get("data");

        String sendSubject = "REQ.SUBJECT";
        String replySubject = "_REPLY." + id;

        try {
            // Init TIBRV (단 한 번만)
            if (!Tibrv.isValid()) {
                Tibrv.open(Tibrv.IMPL_NATIVE);
                TibrvRvdTransport transport = new TibrvRvdTransport("7525", null, "tcp:7500");
                Tibrv.defaultQueue();
            }

            TibrvTransport transport = new TibrvRvdTransport("7525", null, "tcp:7500");

            // 메시지 작성
            TibrvMsg requestMsg = new TibrvMsg();
            requestMsg.setSendSubject(sendSubject);
            requestMsg.setReplySubject(replySubject);
            requestMsg.update("id", id);
            requestMsg.update("data", data);

            // 응답 리스너 등록
            TibrvQueue queue = Tibrv.defaultQueue();

            TibrvListener listener = new TibrvListener(queue, new TibrvMsgCallback() {
                @Override
                public void onMsg(TibrvListener l, TibrvMsg msg) {
                    try {
                        String responseData = msg.get("data").toString();

                        Map<String, Object> result = new HashMap<>();
                        result.put("id", id);
                        result.put("data", responseData);

                        // WebSocket 전송
                        messagingTemplate.convertAndSend("/topic/rv", result);

                        // 응답 받았으므로 리스너 제거
                        l.destroy();
                    } catch (TibrvException e) {
                        e.printStackTrace();
                    }
                }
            }, transport, replySubject, null);

            // 🔐 타임아웃 등록 (10초 후 자동 제거)
            scheduler.schedule(() -> {
                if (listener != null) {
                    System.out.println("⏰ 응답 시간 초과. 리스너 제거: " + replySubject);
                    listener.destroy();
                }
            }, 10, TimeUnit.SECONDS);

            // 메시지 전송
            transport.send(requestMsg);

            return ResponseEntity.ok().body("메시지 전송 완료");

        } catch (TibrvException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("RV 오류: " + e.getMessage());
        }
    }
}
