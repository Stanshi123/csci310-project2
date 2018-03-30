package Server;

public class APIResponse {
    private Item[] items;

    public Item[] getItems() {
        return items;
    }

    public APIResponse() {
        super();
    }

    public APIResponse(Item[] items){
        this.items = items;
    }
}
