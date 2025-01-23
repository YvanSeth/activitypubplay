@ActivityPubController
class App{

    @RequestMapping("/")
    pub String hello() {
        return "Hello World";
    }
}
