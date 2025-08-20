
Enumeration<?> names = msg.getFieldNames();

while (names.hasMoreElements()) {
    String name = (String) names.nextElement();
    TibrvMsgField field = msg.getField(name);
    System.out.println("📦 필드 이름: " + field.name + " / 타입: " + field.type + " / 값: " + field.data);
}
