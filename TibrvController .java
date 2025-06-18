@RestController
@RequestMapping("/api")
public class TibrvController {

    @Autowired
    private TibrvService tibrvService;

    @PostMapping("/sendTibrv")
    public ResponseEntity<String> sendTibrv(@RequestBody Map<String, String> payload) throws Exception {
        String message = payload.get("message");
        String response = tibrvService.sendAndReceive(message);
        return ResponseEntity.ok(response);
    }
}
