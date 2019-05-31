# Getting Started

### Handle command

```java
@BotCommand(command = "/test")
class TestCommand extends Command {
    
    public boolean accept(Update update, TelegramLongPollingBot bot) {
        // do something...
    }
}
```
