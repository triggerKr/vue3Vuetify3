public class TibrvResponseCallback implements TibrvMsgCallback {

    private final BlockingQueue<String> queue;

    public TibrvResponseCallback(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void onMsg(TibrvListener listener, TibrvMsg msg) {
        try {
            // 응답 메시지에서 "result" 필드 추출
            String result = msg.getField("result").data.toString();
            queue.offer(result);  // 응답 큐에 전달
        } catch (TibrvException e) {
            e.printStackTrace();
            queue.offer("응답 처리 중 오류 발생");
        }
    }
}
