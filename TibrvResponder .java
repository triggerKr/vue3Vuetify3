public class TibrvResponder {
    public static void main(String[] args) throws TibrvException {
        Tibrv.open(Tibrv.IMPL_NATIVE);
        TibrvTransport transport = new TibrvRvdTransport();

        new TibrvListener(
            Tibrv.defaultQueue(),
            (listener, msg) -> {
                System.out.println("수신 메시지: " + msg.getField("data").data);
                TibrvMsg reply = new TibrvMsg();
                reply.addString("result", "수신 완료: " + msg.getField("data").data);
                transport.sendReply(reply, msg);
            },
            transport,
            "subject.req",
            null
        );

        System.out.println("Responder 대기 중...");
        while (true) {
            Tibrv.defaultQueue().dispatch(); // 메시지 처리 loop
        }
    }
}
