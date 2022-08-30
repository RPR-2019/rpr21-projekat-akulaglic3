package ba.unsa.etf.rpr.Models;

import java.util.List;
import java.util.Optional;

public class ShoppingList {
    private User buyer;
    private List<Item> itemList;

    public ShoppingList(User buyer, List<Item> itemList) {
        this.buyer = buyer;
        this.itemList = itemList;
    }

    public ShoppingList() {
    }

    public void addItem(Item item){
        Optional<Item> result = itemList.stream().filter(singleItem ->
               singleItem.getDrug().equals(item.getDrug())
       ).findFirst();

       if (!result.isPresent()){
           itemList.add(item);
       }else{
           Item resultItem = result.get();
           resultItem.setAmount(resultItem.getAmount()+1);
       }
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
