@PostMapping("/send")
public ResponseEntity<?> sendWithReply(@RequestBody Map<String, Object> payload) {
    String id = (String) payload.get("id");
    String data = (String) payload.get("data");

    String replySubject = "_REPLY." + id;

    try {
        TibrvMsg requestMsg = new TibrvMsg();
        requestMsg.setSendSubject("REQ.SUBJECT");
        requestMsg.setReplySubject(replySubject);
        requestMsg.update("id", id);
        requestMsg.update("data", data);

        // replySubject에 대한 임시 리스너 등록
        TibrvQueue queue = Tibrv.defaultQueue();
        TibrvTransport transport = new TibrvRvdTransport("7525", null, "tcp:7500");

        // 응답 처리 콜백
        new TibrvListener(queue, new TibrvMsgCallback() {
            public void onMsg(TibrvListener l, TibrvMsg msg) {
                try {
                    String responseData = msg.get("data").toString();

                    Map<String, Object> result = new HashMap<>();
                    result.put("id", id);
                    result.put("data", responseData);

                    simpMessagingTemplate.convertAndSend("/topic/rv", result);

                    // 👇 리스너 제거 (응답 1회성일 경우)
                    l.destroy();
                } catch (TibrvException e) {
                    e.printStackTrace();
                }
            }
        }, transport, replySubject, null);

        transport.send(requestMsg);

        return ResponseEntity.ok().build();

    } catch (TibrvException e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body("RV Error: " + e.getMessage());
    }
}
