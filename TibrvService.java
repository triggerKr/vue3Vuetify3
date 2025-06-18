@Service
public class TibrvService {

    public String sendAndReceive(String message) throws Exception {
        // 1. TIBCO 초기화
        Tibrv.open(Tibrv.IMPL_NATIVE);
        TibrvTransport transport = new TibrvRvdTransport();

        // 2. 고유한 reply subject 생성
        String replySubject = "subject.reply." + UUID.randomUUID();

        // 3. 응답 대기용 큐 생성
        BlockingQueue<String> responseQueue = new ArrayBlockingQueue<>(1);

        // 4. 리스너 등록 (콜백에서 큐에 응답 전달)
        TibrvListener listener = new TibrvListener(
            Tibrv.defaultQueue(),
            new TibrvResponseCallback(responseQueue),
            transport,
            replySubject,
            null
        );

        // 5. 메시지 전송
        TibrvMsg requestMsg = new TibrvMsg();
        requestMsg.setSendSubject("subject.req");
        requestMsg.setReplySubject(replySubject);
        requestMsg.addString("data", message);
        transport.send(requestMsg);

        // 6. 응답 대기 (최대 5초)
        String response = responseQueue.poll(5, TimeUnit.SECONDS);

        // 7. 리스너 자원 정리
        listener.destroy();
        Tibrv.close();

        // 8. 결과 반환
        return (response != null) ? response : "응답 없음 (Timeout)";
    }
}
