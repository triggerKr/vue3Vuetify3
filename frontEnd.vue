// send.vue
axios.post('/api/sendTibrv', { message: 'Hello TIBCO' })
  .then(res => {
    console.log('응답 메시지:', res.data);
  })
  .catch(err => {
    console.error('오류:', err);
  });
