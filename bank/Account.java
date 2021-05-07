package bank;

class Account {

    private String name;
    private Boolean block;
    private int balance;
    private int threshold;


    public Account(String name, int balance, int threshold, Boolean block) {
        this.name = name;
        this.balance = balance;
        this.threshold = threshold;
        this.block = false;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getBlock() {
        return block;
    }

    public void setBlock(Boolean block) {
        this.block = block;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public String toString() {
        return ""+name+" | "+balance+" | "+threshold+" | "+block+"\n";
    }
}
