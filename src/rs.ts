// rv.ts
import axios from 'axios';
import { Client, over } from '@stomp/stompjs';

type RvPromiseMap = Map<string, (value: any) => void>;

const resolveMap: RvPromiseMap = new Map();
let stompClient: Client;

export function initStomp() {
  stompClient = over(new WebSocket("ws://localhost:8080/ws")); // 경로 확인 필요

  stompClient.connect({}, () => {
    console.log("STOMP 연결 성공");

    stompClient.subscribe("/topic/rv", (message) => {
      const payload = JSON.parse(message.body);
      const { id, data } = payload;

      const resolve = resolveMap.get(id);
      if (resolve) {
        resolve(data); // await 중인 함수에 결과 전달
        resolveMap.delete(id); // 메모리 누수 방지
      }
    });
  });
}

export async function sendToRv(id: string, data: string): Promise<any> {
  return new Promise((resolve, reject) => {
    // ID에 대한 resolve 저장
    resolveMap.set(id, resolve);

    // RV 서버에 메시지 전송 요청
    axios.post("/send", { id, data })
      .catch((err) => {
        resolveMap.delete(id);
        reject(err);
      });

    // 타임아웃 처리 (예: 10초)
    setTimeout(() => {
      if (resolveMap.has(id)) {
        resolveMap.delete(id);
        reject(new Error("응답 시간 초과"));
      }
    }, 10000);
  });
}
